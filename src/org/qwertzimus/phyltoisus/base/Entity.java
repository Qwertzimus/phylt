package org.qwertzimus.phyltoisus.base;

//https://i.chzbgr.com/completestore/12/8/8/NVmxKjqeTk-CvnYCcxNdKg2.gif
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.io.FilenameFilter;
import java.io.Serializable;
import java.nio.FloatBuffer;
import java.util.*;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.opengl.*;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import org.qwertzimus.phyltoisus.gui.Animation;
import org.qwertzimus.phyltoisus.gui.Textures;
import org.qwertzimus.phyltoisus.network.ObjectNetworkReference;
import org.qwertzimus.phyltoisus.physic.BoxCollider2D;
import org.qwertzimus.phyltoisus.physic.Gravity;
import org.qwertzimus.phyltoisus.physic.Force;
import org.qwertzimus.phyltoisus.physic.Time;
import org.qwertzimus.phyltoisus.world.*;

/*declaring id states
 0.:standing_right	
 1.:standing_left
 2.:trotcycle_right
 3.:trotcycle_left
 4.:fly_right
 5.:fly_left
 6.:sprint_right
 7.:sprint_left
 8.:hover_left
 9.:hover_right
 10.:
 11.:
 12.:
 13.:
 14.:
 15.:
 16.:
 17.:
 18.:
 19.:
 20.:
 */
public class Entity implements Renderable {
	private List<Force> forces = new ArrayList<Force>(); // A list of all the forces
	private Force gravity = Gravity.getGravity(); // The Gravity Force
	private Force fly = new Force(new Vector2f(0, 5f), false); // The force upwards when flying
	private Force hover = new Force(new Vector2f(0, 8f), false); // The force upwards when falling (lower than gravity)
	private Force jumpFlyMode = new Force(new Vector2f(0, 2f*70), false);
	private Force jump = new Force(new Vector2f(0, 10f*70), false);
	
	Vector2f position;
	Vector2f velocity;
	Vector2f rotation;
	Rectangle collider;
	Chunk inChunk;
	float accelerationY = 3f;
	float accelerationX = 1f;
	float fraction = 0.95f;
	float maxVelocityX = 3f;
	float maxVelocityY = 6f;
	boolean isAcceleratingX, isAcceleratingY, isOnGround, isFinished,
			isColliding, collR, collL, collU, collD, isBouncy, isBurning, look,
			hasAnimationStartTime, canFly, canUseMagic, isAlive, isAnimated,
			isStanding, isFlying, isInHoverMode, shouldSlide;
	// List<List<Texture>> texture;
	HashMap<String, Animation> animation;
	int width, height;
	public int vertexBufferId, textureBufferId;
	boolean isReady;
	public FloatBuffer vertices = null;
	public static FloatBuffer textures = null; // rotating textures for serveral
												// entities
	// isn't possible anymore
	int textureId, size, offsetX, offsetY, offsetWidth, offsetHeight,
			jumpCounter, maxJumps;
	long animationStartTime;
	int animationId;
	public static FloatBuffer lightValues;

	public Entity() {
		this.forces.add(this.gravity);
		this.forces.add(this.fly);
		this.forces.add(this.hover);
		this.forces.add(this.jump);
		this.forces.add(this.jumpFlyMode);
		
		vertexBufferId = -1;
		textureBufferId = -1;
		position = new Vector2f();
		velocity = new Vector2f();
		rotation = new Vector2f();
		collider = new Rectangle();
		// texture = new ArrayList<List<Texture>>();
		animation = new HashMap<String, Animation>();
	}

	public Chunk getInChunk() {
		return inChunk;
	}

	public void setInChunk(Chunk inChunk) {
		this.inChunk = inChunk;
	}

	public float getMaxVelocityX() {
		return maxVelocityX;
	}
	
	public float getMaxVelocityY() {
		return maxVelocityY;
	}

	public void setMaxVelocityX(float maxVelocityX) {
		this.maxVelocityX = maxVelocityX;
	}

	public void setMaxVelocityY(float maxVelocityY) {
		this.maxVelocityY = maxVelocityY;
	}

	public boolean isFinished() {
		return isFinished;
	}

	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}

	public boolean isColliding() {
		return isColliding;
	}

	public void setColliding(boolean isColliding) {
		this.isColliding = isColliding;
	}

	public boolean isCollR() {
		return collR;
	}

	public void setCollR(boolean collR) {
		this.collR = collR;
	}

	public boolean isCollL() {
		return collL;
	}

	public void setCollL(boolean collL) {
		this.collL = collL;
	}

	public boolean isCollU() {
		return collU;
	}

	public void setCollU(boolean collU) {
		this.collU = collU;
	}

	public boolean isCollD() {
		return collD;
	}

	public void setCollD(boolean collD) {
		this.collD = collD;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getOffsetWidth() {
		return offsetWidth;
	}

	public void setOffsetWidth(int offsetWidth) {
		this.offsetWidth = offsetWidth;
	}

	public int getOffsetHeight() {
		return offsetHeight;
	}

	public void setOffsetHeight(int offsetHeight) {
		this.offsetHeight = offsetHeight;
	}

	public void setOffset(int x, int y) {
		offsetX = x;
		offsetY = y;
	}

	public void setOffset(int x, int y, int w, int h) {
		offsetX = x;
		offsetY = y;
		offsetWidth = w;
		offsetHeight = h;
	}

	public int getOffsetX() {
		return offsetX;
	}

	public void setOffsetX(int offsetX) {
		this.offsetX = offsetX;
	}

	public int getOffsetY() {
		return offsetY;
	}

	public void setOffsetY(int offsetY) {
		this.offsetY = offsetY;
	}

	public int getAnimationId() {
		return animationId;
	}

	public void setAnimationId(int animationId) {
		this.animationId = animationId;
	}

	public boolean isBouncy() {
		return isBouncy;
	}

	public void setBouncy(boolean isBouncy) {
		this.isBouncy = isBouncy;
	}

	public boolean isBurning() {
		return isBurning;
	}

	public void setBurning(boolean isBurning) {
		this.isBurning = isBurning;
	}

	public boolean isLook() {
		return look;
	}

	public void setLook(boolean look) {
		this.look = look;
	}

	public boolean isHasAnimationStartTime() {
		return hasAnimationStartTime;
	}

	public void setHasAnimationStartTime(boolean hasAnimationStartTime) {
		this.hasAnimationStartTime = hasAnimationStartTime;
	}

	public boolean isInHoverMode() {
		return isInHoverMode;
	}

	public void setInHoverMode(boolean isInHoverMode) {
		this.isInHoverMode = isInHoverMode;
	}

	public int getJumpCounter() {
		return jumpCounter;
	}

	public void setJumpCounter(int jumpCounter) {
		this.jumpCounter = jumpCounter;
	}

	public int getMaxJumps() {
		return maxJumps;
	}

	public void setMaxJumps(int maxJumps) {
		this.maxJumps = maxJumps;
	}

	public boolean isCanFly() {
		return canFly;
	}

	public void setCanFly(boolean canFly) {
		this.canFly = canFly;
	}

	public boolean isCanUseMagic() {
		return canUseMagic;
	}

	public void setCanUseMagic(boolean canUseMagic) {
		this.canUseMagic = canUseMagic;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public boolean isAnimated() {
		return isAnimated;
	}

	public void setAnimated(boolean isAnimated) {
		this.isAnimated = isAnimated;
	}

	public boolean isStanding() {
		return isStanding;
	}

	public void setStanding(boolean isStanding) {
		this.isStanding = isStanding;
	}

	public boolean isFlying() {
		return isFlying;
	}

	public void setFlying(boolean isFlying) {
		this.isFlying = isFlying;
	}

	/*
	 * public List<List<Texture>> getTexture() { return texture; }
	 * 
	 * public void setTexture(List<List<Texture>> textures) { this.texture =
	 * textures; }
	 */
	public int getVertexBufferId() {
		return vertexBufferId;
	}

	public void setVertexBufferId(int vertexBufferId) {
		this.vertexBufferId = vertexBufferId;
	}

	public int getTextureBufferId() {
		return textureBufferId;
	}

	public void setTextureBufferId(int textureBufferId) {
		this.textureBufferId = textureBufferId;
	}

	public boolean isReady() {
		return isReady;
	}

	public void setReady(boolean isReady) {
		this.isReady = isReady;
	}

	public FloatBuffer getVertices() {
		return vertices;
	}

	public void setVertices(FloatBuffer vertices) {
		this.vertices = vertices;
	}

	public FloatBuffer getTextures() {
		return textures;
	}

	public static void setTextures(FloatBuffer textures) {
		Entity.textures = textures;
	}

	public int getTextureId() {
		return textureId;
	}

	public void setTextureId(int textureId) {
		this.textureId = textureId;
	}

	public long getAnimationStartTime() {
		return animationStartTime;
	}

	public void setAnimationStartTime(long animationStartTime) {
		this.animationStartTime = animationStartTime;
	}

	/*
	 * public void addAnimation(Animation animation) {
	 * this.animation.add(animation); }
	 */

	public void removeAnimation(Animation animation) {
		this.animation.remove(animation);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getX() {
		return position.x;
	}

	public float getY() {
		return position.y;
	}

	public int getChunkX() {
		return (int) Math.floor(position.x / 256) * 256;
	}

	public int getChunkX(Vector2f position) {
		return (int) Math.floor(position.x / 256) * 256;
	}

	public int getChunkY() {
		return (int) Math.floor(position.y / 256) * 256;
	}

	public int getChunkY(Vector2f position) {
		return (int) Math.floor(position.y / 256) * 256;
	}

	public int getBlockX() {
		return (int) Math.floor(position.x / 16) * 16;
	}

	public int getBlockY() {
		return (int) Math.floor(position.y / 16) * 16;
	}

	public int getBlockX(Vector2f position) {
		return (int) Math.floor(position.x / 16) * 16;
	}

	public int getBlockY(Vector2f position) {
		return (int) Math.floor(position.y / 16) * 16;
	}

	public void moveLeft() {

		velocity.x -= accelerationX * Time.deltaTime / 1000f;
		isAcceleratingX = true;
		look = false;
	}

	public void moveRight() {

		velocity.x += accelerationX * Time.deltaTime / 1000f;
		isAcceleratingX = true;
		look = true;
	}

	public void moveUp() {
		if (!canFly) return;
		//velocity.y += 2700 * Time.deltaTime / 1000f;
		fly.setEnabled(true);
		isAcceleratingY = true;
		isFlying = true;
	}

	public void moveDown() {

		velocity.y -= accelerationY * Time.deltaTime / 1000f;
		isAcceleratingY = true;
	}

	public synchronized void jump() {
		// System.out.println(isOnGround+" "+jumpCounter);
		if (isOnGround || jumpCounter < maxJumps && velocity.y < 1) {
			if (canFly) {
				this.velocity.y = 0;
				this.jumpFlyMode.setEnabled(true);
			} else {
				this.velocity.y = 0;
				this.jump.setEnabled(true);
			}
			jumpCounter++;
		}
	}

	public boolean hasWorldCollisionDown(Vector2f position) {
		for (int counter = 0; counter <= (int) (width / 16); counter++) {
			int cX = getChunkX(position), cY = getChunkY(position);
			int bX = (int) ((getBlockX(position) - cX)) / 16, bY = (int) ((getBlockY(position) - cY)) / 16;
			bX += counter;
			Chunk chu;
			if (bX > 15) {
				bX -= 16;
				cX += 256;
				// chu=Main.world.getChunk(cX+256,cY);
			} else if (bX < 0) {
				bX += 16;
				cX -= 256;
				// chu=Main.world.getChunk(cX-256,cY);
			} else {
				// chu=Main.world.getChunk(cX,cY);
			}
			if (bY > 15) {
				bY -= 16;
				cY += 256;
				// chu=Main.world.getChunk(cX,cY+256);
			} else if (bY < 0) {
				bY += 16;
				cY -= 256;
				// chu=Main.world.getChunk(cX,cY-256);
			} else {
				// chu=Main.world.getChunk(cX,cY);
			}
			chu = Main.world.getChunk(cX, cY);
			Block b = chu.getBlock(bX, bY, 0);

			if (b.isSolid()
					&& collider.intersects(new Rectangle(
							(int) b.getPosition().x, (int) b.getPosition().y,
							16, 16)) == true) {
				// b.setId(6);
				chu.markDirty();

				return true;
			}
		}
		return false;
	}

	public boolean hasWorldCollisionUp(Vector2f position) {
		for (int counter = 0; counter <= (int) (width / 16); counter++) {
			int cX = getChunkX(position), cY = getChunkY(position);
			// System.out.println("cX:"+cX+" "+"cY:"+cY);
			int bX = (int) ((getBlockX(position) - cX)) / 16, bY = (int) ((getBlockY(position) - cY)) / 16;
			bX += counter;
			bY += (int) (height / 16);
			// System.out.println("start "+bX+" "+bY);
			Chunk chu;
			if (bX > 15) {
				bX -= 16;
				cX += 256;
			} else if (bX < 0) {
				bX += 16;
				cX -= 256;
			} else {
				chu = Main.world.getChunk(cX, cY);
			}
			if (bY > 15) {
				bY -= 16;
				cY += 256;
			} else if (bY < 0) {
				bY += 16;
				cY -= 256;
			} else {
				chu = Main.world.getChunk(cX, cY);
			}
			chu = Main.world.getChunk(cX, cY);
			// System.out.println("end "+chu.getPosition()+" "+bX+" "+bY);
			Block b = chu.getBlock(bX, bY, 0);

			if (b.isSolid()
					&& collider.intersects(new Rectangle(
							(int) b.getPosition().x, (int) b.getPosition().y,
							16, 16)) == true) {
				// b.setId(6);
				chu.markDirty();
				return true;
			}
		}
		return false;
	}

	public boolean hasWorldCollisionRight(Vector2f position) {
		for (int counter = 0; counter <= (int) (height / 16); counter++) {
			int cX = getChunkX(position), cY = getChunkY(position);
			int bX = (int) ((getBlockX(position) - cX)) / 16, bY = (int) ((getBlockY(position) - cY)) / 16;
			bX += (int) (width / 16);
			bY += counter;
			Chunk chu;
			if (bX > 15) {
				bX -= 16;
				cX += 256;
			} else if (bX < 0) {
				bX += 16;
				cX -= 256;
			} else {
				// chu=Main.world.getChunk(cX,cY);
			}
			if (bY > 15) {
				bY -= 16;
				cY += 256;
			} else if (bY < 0) {
				bY += 16;
				cY -= 256;
			} else {
				// chu=Main.world.getChunk(cX,cY);
			}
			chu = Main.world.getChunk(cX, cY);
			Block b = chu.getBlock(bX, bY, 0);
			// System.out.println(chu.getX()+" "+chu.getY()+" "+bX+" "+bY);
			if (b.isSolid()
					&& collider.intersects(new Rectangle(
							(int) b.getPosition().x, (int) b.getPosition().y,
							16, 16)) == true) {
				// b.setId(6);
				chu.markDirty();
				return true;
			} else {
				// System.out.println(bX+" "+bY);
			}
		}
		return false;
	}

	public boolean hasWorldCollisionLeft(Vector2f position) {
		for (int counter = 0; counter <= (int) (height / 16); counter++) {
			int cX = getChunkX(position), cY = getChunkY(position);
			int bX = (int) ((getBlockX(position) - cX)) / 16, bY = (int) ((getBlockY(position) - cY)) / 16;
			// bX-=1;
			bY += counter;
			Chunk chu;
			if (bX > 15) {
				bX -= 16;
				cX += 256;
			} else if (bX < 0) {
				bX = 15;
				cX -= 256;
			} else {
				// chu=Main.world.getChunk(cX,cY);
			}
			if (bY > 15) {
				bY -= 16;
				cY += 256;
			} else if (bY < 0) {
				bY += 16;
				cY -= 256;
				// chu=Main.world.getChunk(cX,cY-256);
			} else {
				// chu=Main.world.getChunk(cX,cY);
			}
			chu = Main.world.getChunk(cX, cY);
			Block b = chu.getBlock(bX, bY, 0);

			if (b.isSolid()
					&& collider.intersects(new Rectangle(
							(int) b.getPosition().x, (int) b.getPosition().y,
							16, 16)) == true) {
				// b.setId(6);
				chu.markDirty();
				return true;
			}
		}
		return false;
	}

	public void updateCollider() {
		collider.setLocation((int) position.x, (int) position.y);
		collider.setSize(width + size + offsetWidth, height + size
				+ offsetHeight);
	}

	public void updateCollider(Vector2f position) {
		collider.setLocation((int) position.x, (int) position.y);
		collider.setSize(width + size + offsetWidth, height + size
				+ offsetHeight);
	}

	public void loadAnimations() {
		long t=System.currentTimeMillis();
		long c=0;
		for (String s : animation.keySet()) {
			Animation a = animation.get(s);
			if (!a.isLoaded) {
				a.loadAnimation();
				c=System.currentTimeMillis();
			}
		}
		if(c!=0)System.out.println(c-t);
	}

	public synchronized void update(float dt) {
		// TODO- anti-clipping measures
		adjustVelocity();
		float oldX = position.x;
		float oldY = position.y;
		float finalX = oldX;
		float finalY = oldY;
		
		loadAnimations();
		
		inChunk = Main.world.getChunk(getChunkX(), getChunkY());
		if (inChunk != null) {
			this.hover.setEnabled(canFly);
			for (Force force : this.forces) {
				if (!force.isEnabled()) continue;
				this.velocity.x += force.getDirection().x*dt;
				this.velocity.y += force.getDirection().y*dt;
			}
			
			finalX = this.position.x + this.velocity.x;
			finalY = this.position.y + this.velocity.y;
			
			inChunk = Main.world.getChunk(getChunkX(new Vector2f(oldX,
					finalY)), getChunkY(new Vector2f(oldX, finalY)));
			updateCollider(new Vector2f(oldX, finalY));
			
			if (hasWorldCollisionUp(new Vector2f(finalX, finalY))) {
				finalY = oldY;
				this.velocity.y = 0;
			}
			
			inChunk = Main.world.getChunk(getChunkX(new Vector2f(oldX,
					finalY)), getChunkY(new Vector2f(oldX, finalY)));
			updateCollider(new Vector2f(oldX, finalY));
			
			if (hasWorldCollisionDown(new Vector2f(oldX, finalY))) {
				finalY = oldY;
				this.velocity.y = 0;
				
				isOnGround = true;
				if (!isInHoverMode)
					isFlying = false;
			} else {
				isOnGround = false;
				if (isInHoverMode)
					isFlying = true;
			}
			
			inChunk = Main.world.getChunk(getChunkX(new Vector2f(finalX,
					oldY)), getChunkY(new Vector2f(finalX, oldY)));
			updateCollider(new Vector2f(finalX, oldY));
			if (hasWorldCollisionLeft(new Vector2f(finalX, oldY))) {
				finalX = oldX;
				this.velocity.x = 0;
			}
			
			inChunk = Main.world.getChunk(getChunkX(new Vector2f(finalX,
					oldY)), getChunkY(new Vector2f(finalX, oldY)));
			updateCollider(new Vector2f(finalX, oldY));

			if (hasWorldCollisionRight(new Vector2f(finalX, oldY))) {
				finalX = oldX;
				this.velocity.x = 0;
			}
			
			setPosition(finalX, finalY);
			
			inChunk = Main.world.getChunk(getChunkX(new Vector2f(finalX,
					finalY)), getChunkY(new Vector2f(finalX, finalY)));
			updateCollider(new Vector2f(finalX, finalY));
			
			if (this.isOnGround && !this.isAcceleratingX) {
				this.velocity.x = this.velocity.x * this.fraction;
			}
			this.isAcceleratingX = false;
			this.fly.setEnabled(false);
			this.jump.setEnabled(false);
			this.jumpFlyMode.setEnabled(false);
			
			statusUpdate();
			
			animate1();
		}


		/*try {
			inChunk = Main.world.getChunk(getChunkX(), getChunkY());
			if (inChunk != null) {
				
				loadAnimations();

				float newX = (position.x + velocity.x * Time.deltaTime / 1000f);
				float newY = (position.y + velocity.y * Time.deltaTime / 1000f);
				finalY = newY;
				inChunk = Main.world.getChunk(getChunkX(new Vector2f(finalX,
						finalY)), getChunkY(new Vector2f(finalX, finalY)));
				updateCollider(new Vector2f(finalX, finalY));
				if (hasWorldCollisionUp(new Vector2f(finalX, finalY))) {
					finalY = oldY;
					velocity.y = 0;
					// System.out.println("hasCollisionUP");

					// isFlying = false;
				} else {

					// isFlying = true;
				}
				inChunk = Main.world.getChunk(getChunkX(new Vector2f(finalX,
						finalY)), getChunkY(new Vector2f(finalX, finalY)));
				updateCollider(new Vector2f(finalX, finalY));
				if (hasWorldCollisionDown(new Vector2f(finalX, finalY))) {
					finalY = oldY;
					velocity.y = 0;
					// System.out.println("hasCollisionDOWN");
					isOnGround = true;
					if (!isInHoverMode)
						isFlying = false;
				} else {
					isOnGround = false;
					if (isInHoverMode)
						isFlying = true;
				}

				finalX = newX;
				inChunk = Main.world.getChunk(getChunkX(new Vector2f(finalX,
						finalY)), getChunkY(new Vector2f(finalX, finalY)));
				updateCollider(new Vector2f(finalX, finalY));
				if (hasWorldCollisionLeft(new Vector2f(finalX, finalY))) {
					finalX = oldX;
					velocity.x = 0;
					// System.out.println("hasCollisionLEFT");
				}
				inChunk = Main.world.getChunk(getChunkX(new Vector2f(finalX,
						finalY)), getChunkY(new Vector2f(finalX, finalY)));
				updateCollider(new Vector2f(finalX, finalY));

				if (hasWorldCollisionRight(new Vector2f(finalX, finalY))) {
					finalX = oldX;
					velocity.x = 0;
					// System.out.println("hasCollisionRIGHT");
				}
				setPosition(finalX, finalY);
				inChunk = Main.world.getChunk(getChunkX(new Vector2f(finalX,
						finalY)), getChunkY(new Vector2f(finalX, finalY)));
				updateCollider(new Vector2f(finalX, finalY));

				// setTextureId(texture.get(0).get(0).getTextureID());
				statusUpdate();

				if (!isAcceleratingX) {
					velocity.x = velocity.x * fraction;
					if (isOnGround) {
						velocity.x = velocity.x * fraction;
					}
				}
				if (!isAcceleratingY) {
					velocity.y = velocity.y * fraction;
					if (!isInHoverMode) {
						gravi();
					}
				}
				isAcceleratingX = false;
				isAcceleratingY = false;
				// setTextureId(animation.get(0).getFrame(5).getTextureID());
				animate1();

			} else {
				// float oldX = position.x;
				// float oldY = position.y;
				// float newX = (position.x + velocity.x * Time.deltaTime /
				// 1000f);
				// float newY = (position.y + velocity.y * Time.deltaTime /
				// 1000f);
				// position.x = newX;
				// position.y = newY;
			}

		} catch (Exception e) {
			position.x = oldX;
			position.y = oldY;
			// System.out.println(e);
		}*/
	}

	public float getFraction() {
		return fraction;
	}

	public void setFraction(float fraction) {
		this.fraction = fraction;
	}

	public boolean isAcceleratingX() {
		return isAcceleratingX;
	}

	public void setAcceleratingX(boolean isAcceleratingX) {
		this.isAcceleratingX = isAcceleratingX;
	}

	public boolean isAcceleratingY() {
		return isAcceleratingY;
	}

	public void setAcceleratingY(boolean isAcceleratingY) {
		this.isAcceleratingY = isAcceleratingY;
	}

	public boolean isOnGround() {
		return isOnGround;
	}

	public void setOnGround(boolean isOnGround) {
		this.isOnGround = isOnGround;
	}

	public Vector2f getPosition() {
		return position;
	}

	public synchronized void setPosition(Vector2f position) {
		this.position = position;
	}

	public synchronized void setPosition(float x, float y) {
		position.set(x, y);
		updateCollider();
	}

	public Vector2f getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2f velocity) {
		this.velocity = velocity;
	}

	public float getAccelerationX() {
		return accelerationX;
	}

	public void setAccelerationX(float acceleration) {
		this.accelerationX = acceleration;
	}

	public float getAccelerationY() {
		return accelerationY;
	}

	public void setAccelerationY(float acceleration) {
		this.accelerationY = acceleration;
	}

	public synchronized void adjustVelocity() {
		if (velocity.getX() > maxVelocityX) {
			velocity.setX(maxVelocityX);
		} else if (velocity.getX() < -maxVelocityX) {
			velocity.setX(-maxVelocityX);
		}
		if (velocity.getY() > maxVelocityY) {
			velocity.setY(maxVelocityY);
		} else if (velocity.getY() < -maxVelocityY) {
			velocity.setY(-maxVelocityY);
		}
		/*
		 * if(velocity.x<0.001&&velocity.x>-0.001) velocity.x=0; else
		 * if(velocity.x<0.01&&velocity.x>-0.001) velocity.x=0;
		 * if(velocity.y<0.001&&velocity.x>-0.001) velocity.y=0; else
		 * if(velocity.y<0.001&&velocity.y>-0.001) velocity.y=0;
		 */
	}

	public Vector2f getRotation() {
		return rotation;
	}

	public void setRotation(Vector2f rotation) {
		this.rotation = rotation;
	}

	public void statusUpdate() {
		if (velocity.getX() < 0.1 && velocity.getX() > -0.1) {
			isStanding = true;
		} else {
			isStanding = false;
		}
		if (isOnGround) {
			jumpCounter = 0;
		}
	}

	public Rectangle getCollider() {
		return collider;
	}

	public boolean isShouldSlide() {
		return shouldSlide;
	}

	public void setShouldSlide(boolean shouldSlide) {
		this.shouldSlide = shouldSlide;
	}

	public HashMap<String, Animation> getAnimation() {
		return animation;
	}

	public void setAnimation(HashMap<String, Animation> animation) {
		this.animation = animation;
	}

	public void setCollider(Rectangle collider) {
		this.collider = collider;
	}

	public boolean collidesWith(Entity entity) {
		if (entity.getCollider().intersects(collider)) {
			return true;
		}
		return false;
	}

	public void usePerspective() {
		GL11.glLoadIdentity();
		GL11.glTranslatef(Display.getWidth() / 2 - (int) position.x,
				Display.getHeight() / 2 - (int) position.y, 0);
		GL11.glRotatef(rotation.x, 1f, 0f, 0f);
		GL11.glRotatef(rotation.y, 0f, 1f, 0f);
	}

	public static float[] getVertexCoordinates(Vector2f position) {
		return new float[] { 0 + position.x, 0 + position.y, 16 + position.x,
				0 + position.y, 16 + position.x, 16 + position.y,
				0 + position.x, 16 + position.y };
	}

	public static float[] getTextureCoordinates(int id) {
		return Textures.getBlockTextureCoordinatesFromID(id);
	}

	/*
	 * public void loadTextures(String path) { File dir = new File(path); File[]
	 * animationsList = dir.listFiles(); texture = new
	 * ArrayList<List<Texture>>(); String[] animationPath; File subDir; int i =
	 * 0; for (File f : animationsList) { texture.add(new ArrayList<Texture>());
	 * System.out.println(f.getName()); subDir = new File(f + "/");
	 * System.out.println(subDir); animationPath = subDir.list(new
	 * FilenameFilter() { public boolean accept(File d, String name) { return
	 * name.endsWith(".png");
	 * 
	 * } }); for (int k = 0; k < animationPath.length; k++) { try { //
	 * System.out.println(f.getName()+"/"+animationPath[k]);
	 * System.out.println(path + subDir.getName() + "/" + animationPath[k]);
	 * 
	 * texture.get(i) .add(TextureLoader.getTexture( "PNG",
	 * ResourceLoader.getResourceAsStream(path + subDir.getName() + "/" +
	 * animationPath[k]), GL11.GL_LINEAR)); } catch (Exception ex) {
	 * System.out.println("Texture Loading Error: " + ex); } } i = i + 1;
	 * System.out.println(i); }
	 * 
	 * // texture= new Texture[][files.length]; try { // texture[0]=
	 * TextureLoader.getTexture("GIF", //
	 * ResourceLoader.getResourceAsStream("./images/Daring Do/standingleft.gif"
	 * )); } catch (Exception ex) {
	 * 
	 * }
	 * 
	 * }
	 */

	public void updateBuffer() {
		isReady = false;
		if (vertexBufferId == -1) {
			vertexBufferId = ARBVertexBufferObject.glGenBuffersARB();
		}
		if (textureBufferId == -1) {
			textureBufferId = ARBVertexBufferObject.glGenBuffersARB();
		}
		try {
			vertices.clear();
			textures.clear();
			vertices.mark();
			textures.mark();
		} catch (Exception ex) {
			vertices = BufferUtils.createFloatBuffer(8);
			textures = BufferUtils.createFloatBuffer(8);
		}
		vertices.put(new float[] { size + width - offsetX, 0 - offsetY,
				size + width - offsetX, size + height - offsetY, 0 - offsetX,
				size + height - offsetY, 0 - offsetX, 0 - offsetY });
		textures.put(new float[] { 0, 1, 0, 0, 1, 0, 1, 1 });
		vertices.rewind();
		textures.rewind();
		// ARBVertexBufferObject.glDeleteBuffersARB(vertexBufferId);
		ARBVertexBufferObject.glBindBufferARB(
				ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, vertexBufferId);
		ARBVertexBufferObject.glBufferDataARB(
				ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, vertices,
				ARBVertexBufferObject.GL_STATIC_DRAW_ARB);

		ARBVertexBufferObject.glBindBufferARB(
				ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, 0);

		ARBVertexBufferObject.glBindBufferARB(
				ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, textureBufferId);
		ARBVertexBufferObject.glBufferDataARB(
				ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, textures,
				ARBVertexBufferObject.GL_STATIC_DRAW_ARB);

		ARBVertexBufferObject.glBindBufferARB(
				ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, 0);

		// ARBVertexBufferObject.glDeleteBuffersARB(textureBufferId);
		isReady = true;
		// System.out.println("updated");
	}

	public void animate1() {
		if (look == false) {
			animationId = 0;
			if (isStanding) {

				if (!hasAnimationStartTime) {
					animationStartTime = System.currentTimeMillis();
					hasAnimationStartTime = true;
				} // step
				if (isOnGround) {
					if (System.currentTimeMillis() - animationStartTime > 2000 + animation
							.get("standing_right.gif").size() * 200) {
						animationStartTime = System.currentTimeMillis();
					}
					for (int i = 0; i < animation.get("standing_right.gif")
							.size(); i++) {
						if (System.currentTimeMillis() - animationStartTime > 2000 + (200 * i)) {
							setTextureId(animation.get("standing_right.gif")
									.getFrame(i).getTextureID());
						} else if (i == 0) {
							setTextureId(animation.get("standing_right.gif")
									.getFrame(i).getTextureID());
						}
					}

				}
				if (isFlying) {
					if (System.currentTimeMillis() - animationStartTime > animation
							.get("hover_right.gif").size() * 80) {
						animationStartTime = System.currentTimeMillis();
					}
					for (int i = 0; i < animation.get("hover_right.gif")
							.size(); i++) {
						if (System.currentTimeMillis() - animationStartTime > (80 * i)) {
							setTextureId(animation.get("hover_right.gif")
									.getFrame(i).getTextureID());
						}
					}
				}
			} else if (isFlying) {
				if (!hasAnimationStartTime) {
					animationStartTime = System.currentTimeMillis();
					hasAnimationStartTime = true;
				}
				if (System.currentTimeMillis() - animationStartTime > animation
						.get("hover_right.gif").size() * 80) {
					animationStartTime = System.currentTimeMillis();
				}
				for (int i = 0; i < animation.get("hover_right.gif").size(); i++) {
					if (System.currentTimeMillis() - animationStartTime > (80 * i)) {
						setTextureId(animation.get("hover_right.gif")
								.getFrame(i).getTextureID());
					}
				}
			} else if (isOnGround) {
				if (!hasAnimationStartTime) {
					animationStartTime = System.currentTimeMillis();
					hasAnimationStartTime = true;
				}
				if (System.currentTimeMillis() - animationStartTime > animation
						.get("trotcycle_right.gif").size() * 80) {
					animationStartTime = System.currentTimeMillis();
				}
				for (int i = 0; i < animation.get("trotcycle_right.gif").size(); i++) {
					if (System.currentTimeMillis() - animationStartTime > (80 * i)) {
						setTextureId(animation.get("trotcycle_right.gif")
								.getFrame(i).getTextureID());
					}
				}
			} else {
				setTextureId(animation.get("standing_right.gif").getFrame(0)
						.getTextureID());
			}
		}

		else if (animationId == 0) {
			hasAnimationStartTime = false;
		}

		if (look == true) {
			animationId = 1;
			if (isStanding) {
				if (!hasAnimationStartTime) {
					animationStartTime = System.currentTimeMillis();
					hasAnimationStartTime = true;
				}
				if (isOnGround) {
					if (System.currentTimeMillis() - animationStartTime > 2000 + animation
							.get("standing_left.gif").size() * 200) {
						animationStartTime = System.currentTimeMillis();
					}
					for (int i = 0; i < animation.get("standing_left.gif")
							.size(); i++) {
						if (System.currentTimeMillis() - animationStartTime > 2000 + (200 * i)) {
							setTextureId(animation.get("standing_left.gif")
									.getFrame(i).getTextureID());
						} else if (i == 0) {
							setTextureId(animation.get("standing_left.gif")
									.getFrame(i).getTextureID());
						}
					}
				}
				if (isFlying) {
					if (System.currentTimeMillis() - animationStartTime > animation
							.get("hover_left.gif").size() * 80) {
						animationStartTime = System.currentTimeMillis();
					}
					for (int i = 0; i < animation.get("hover_left.gif")
							.size(); i++) {
						if (System.currentTimeMillis() - animationStartTime > (80 * i)) {
							setTextureId(animation.get("hover_left.gif")
									.getFrame(i).getTextureID());
						}
					}

				}
			} else if (isFlying) {
				if (!hasAnimationStartTime) {
					animationStartTime = System.currentTimeMillis();
					hasAnimationStartTime = true;
				}
				if (System.currentTimeMillis() - animationStartTime > animation
						.get("hover_left.gif").size() * 80) {
					animationStartTime = System.currentTimeMillis();
				}
				for (int i = 0; i < animation.get("hover_left.gif").size(); i++) {
					if (System.currentTimeMillis() - animationStartTime > (80 * i)) {
						setTextureId(animation.get("hover_left.gif")
								.getFrame(i).getTextureID());
					}
				}
			} else if (isOnGround) {
				if (!hasAnimationStartTime) {
					animationStartTime = System.currentTimeMillis();
					hasAnimationStartTime = true;
				}
				if (System.currentTimeMillis() - animationStartTime > animation
						.get("trotcycle_left.gif").size() * 80) {
					animationStartTime = System.currentTimeMillis();
				}
				for (int i = 0; i < animation.get("trotcycle_left.gif").size(); i++) {
					if (System.currentTimeMillis() - animationStartTime > (80 * i)) {
						setTextureId(animation.get("trotcycle_left.gif")
								.getFrame(i).getTextureID());
					}

				}
			} else {
				setTextureId(animation.get("standing_left.gif").getFrame(0)
						.getTextureID());
			}
		} else if (animationId == 1) {
			hasAnimationStartTime = false;
		}

	}

	public void animate() {
		/*
		 * if (look == true) { animationId = 0; if (isStanding) {
		 * 
		 * if (!hasAnimationStartTime) { animationStartTime =
		 * System.currentTimeMillis(); hasAnimationStartTime = true; } // step
		 * if (isOnGround) { if (System.currentTimeMillis() - animationStartTime
		 * > 2000 + texture .get(0).size() * 200) { animationStartTime =
		 * System.currentTimeMillis(); } for (int i = 0; i <
		 * texture.get(0).size(); i++) { if (System.currentTimeMillis() -
		 * animationStartTime > 2000 + (200 * i)) {
		 * setTextureId(texture.get(0).get(i).getTextureID()); } else if (i ==
		 * 0) { setTextureId(texture.get(0).get(i).getTextureID()); } }
		 * 
		 * } if (isFlying) { if (System.currentTimeMillis() - animationStartTime
		 * > texture .get(4).size() * 80) { animationStartTime =
		 * System.currentTimeMillis(); } for (int i = 0; i <
		 * texture.get(4).size(); i++) { if (System.currentTimeMillis() -
		 * animationStartTime > (80 * i)) {
		 * setTextureId(texture.get(4).get(i).getTextureID()); } } } } else if
		 * (isFlying) {// Fluganimation if (!hasAnimationStartTime) {
		 * animationStartTime = System.currentTimeMillis();
		 * hasAnimationStartTime = true; } if (System.currentTimeMillis() -
		 * animationStartTime > texture .get(4).size() * 80) {
		 * animationStartTime = System.currentTimeMillis(); } for (int i = 0; i
		 * < texture.get(4).size(); i++) { if (System.currentTimeMillis() -
		 * animationStartTime > (80 * i)) {
		 * setTextureId(texture.get(4).get(i).getTextureID()); } } } else if
		 * (isOnGround) {// laufanimation if (!hasAnimationStartTime) {
		 * animationStartTime = System.currentTimeMillis();
		 * hasAnimationStartTime = true; } if (System.currentTimeMillis() -
		 * animationStartTime > texture .get(2).size() * 80) {
		 * animationStartTime = System.currentTimeMillis(); } for (int i = 0; i
		 * < texture.get(2).size(); i++) { if (System.currentTimeMillis() -
		 * animationStartTime > (80 * i)) {
		 * setTextureId(texture.get(2).get(i).getTextureID()); } } } else //
		 * wenn es keine passende Animation für diese Situation gibt // gucke in
		 * entsprechende Richtung {
		 * setTextureId(texture.get(0).get(0).getTextureID()); } }
		 * 
		 * else if (animationId == 0) { hasAnimationStartTime = false; }
		 * 
		 * if (look == false) { animationId = 1; if (isStanding) { if
		 * (!hasAnimationStartTime) { animationStartTime =
		 * System.currentTimeMillis(); hasAnimationStartTime = true; } if
		 * (isOnGround) { if (System.currentTimeMillis() - animationStartTime >
		 * 2000 + texture .get(1).size() * 200) { animationStartTime =
		 * System.currentTimeMillis(); } for (int i = 0; i <
		 * texture.get(1).size(); i++) { if (System.currentTimeMillis() -
		 * animationStartTime > 2000 + (200 * i)) {
		 * setTextureId(texture.get(1).get(i).getTextureID()); } else if (i ==
		 * 0) { setTextureId(texture.get(1).get(i).getTextureID()); } } } if
		 * (isFlying) { if (System.currentTimeMillis() - animationStartTime >
		 * texture .get(5).size() * 80) { animationStartTime =
		 * System.currentTimeMillis(); } for (int i = 0; i <
		 * texture.get(5).size(); i++) { if (System.currentTimeMillis() -
		 * animationStartTime > (80 * i)) {
		 * setTextureId(texture.get(5).get(i).getTextureID()); } }
		 * 
		 * } } else if (isFlying)// Fluganimation { if (!hasAnimationStartTime)
		 * { animationStartTime = System.currentTimeMillis();
		 * hasAnimationStartTime = true; } if (System.currentTimeMillis() -
		 * animationStartTime > texture .get(5).size() * 80) {
		 * animationStartTime = System.currentTimeMillis(); } for (int i = 0; i
		 * < texture.get(5).size(); i++) { if (System.currentTimeMillis() -
		 * animationStartTime > (80 * i)) {
		 * setTextureId(texture.get(5).get(i).getTextureID()); } } } else if
		 * (isOnGround)// laufanimation { if (!hasAnimationStartTime) {
		 * animationStartTime = System.currentTimeMillis();
		 * hasAnimationStartTime = true; } // step if
		 * (System.currentTimeMillis() - animationStartTime > texture
		 * .get(3).size() * 80) { animationStartTime =
		 * System.currentTimeMillis(); } for (int i = 0; i <
		 * texture.get(3).size(); i++) { if (System.currentTimeMillis() -
		 * animationStartTime > (80 * i)) {
		 * setTextureId(texture.get(3).get(i).getTextureID()); }
		 * 
		 * } } else // wenn es keine passende Animation für diese Situation gibt
		 * // gucke in entsprechende Richtung {
		 * setTextureId(texture.get(1).get(0).getTextureID()); } } else if
		 * (animationId == 1) { hasAnimationStartTime = false; }
		 */
	}

	public void draw() {
		if (isReady) 
		{
			System.out.println("awdad");
			GL11.glPushMatrix();
			GL11.glTranslatef((int) position.x, (int) position.y, 0);
			
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
			
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D,
					GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D,
					GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

			ARBVertexBufferObject.glBindBufferARB(
					ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, vertexBufferId);
			GL11.glVertexPointer(2, GL11.GL_FLOAT, 0, 0);
			ARBVertexBufferObject.glBindBufferARB(
					ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, 0);
			ARBVertexBufferObject.glBindBufferARB(
					ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, textureBufferId);
			GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, 0);
			if (inChunk != null) {		
				ARBShaderObjects.glUniform1fARB(
						Main.baseLightValueLoc,
						0.9f);

			}
			System.out.println(Main.baseLightValueLoc);
			GL11.glDrawArrays(GL11.GL_QUADS, 0, 1);
			ARBVertexBufferObject.glBindBufferARB(
					ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, 0);
			ARBShaderObjects.glUniform1fARB(Main.baseLightValueLoc, 0);
			GL11.glPopMatrix();
		}
	}

}
