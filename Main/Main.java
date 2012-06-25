package Main;

import org.lwjgl.*;
import java.io.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.*;
import Geometry.*;
import Physics.*;
import org.lwjgl.util.vector.*;
import Mesh.*;
import java.util.*;

public class Main {
	boolean renderMode;
	boolean isFinished;
	boolean limitFPS;
	Player player;
	List<Entity> entities = new ArrayList<Entity>();

	public Main() {
		initGL();
	}

	public static void main(String[] args) {
		Main main = new Main();
		main.run();
	}

	public void initGL() {
		try {

			Display.create();
			Display.makeCurrent();
			Display.setVSyncEnabled(false);
		} catch (Exception e) {
			System.out.println(e);
		}
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluPerspective(90f,
				(float) (Display.getWidth() / Display.getHeight()), 0.01f, 100f);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
	}

	public void run() {
		Controls.loadControls();
		player = new Player();
		player.setMaxVelocity(new Vector3f(2f, 9.81f, 2f));

		try {

			Meshes.meshes.add(OBJLoader
					.loadMesh(new File("res/tournevis.obj")));
			Meshes.meshes.get(Meshes.meshes.size() - 1).updateDisplayList();
			Meshes.meshes.get(Meshes.meshes.size() - 1).name = "";
			for (int i = 0; i < 1; i++) {
				System.out.println(" awdawd");
				entities.add(new Entity());
				entities.get(i).mesh = Meshes.meshes
						.get(Meshes.meshes.size() - 1);
				if (i > 50) {
					entities.get(i).setPosition(1f * (100 - i), 0f, 0f);
				} else {
					entities.get(i).setPosition(0f, 0f, 1f * i);
				}
				System.out.println("vertexCount:"
						+ (Meshes.meshes.get(i).vertices.size() - 1));
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		while (!isFinished) {
			Display.update();
			if (Display.isCloseRequested()) {
				isFinished = true;
			}
			if (Display.isActive()) {
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT
						| GL11.GL_DEPTH_BUFFER_BIT);
				// GL11.glScalef(0.05f, 0.05f, 0.05f);
				// GL11.glCallList(testListId);
				// GL11.glScalef(0.01f, 0.01f, 0.01f);
				// GL11.glTranslatef(10f, 0f, 10f);
				// GL11.glCallList(bunnyListId);
				render();
				// p.draw();
				putput();
				player.update();
				drawGUI();
				if (limitFPS) {
					Display.sync(60);
				}
			}
			// System.out.println(Time.deltaTime);
			Time.updateFPS();
			Time.updateDeltaTime();
		}
	}

	public void putput() {

		Controls.handleInput();
		float mouseDX = Mouse.getDX() * 0.4f;
		float mouseDY = Mouse.getDY() * 0.4f;
		if (!player.isInInventory()) {
			if (player.getRotation().getY() + mouseDX >= 360) {
				player.setRotation(player.getRotation().getX(), player
						.getRotation().getY() + mouseDX - 360, player
						.getRotation().getZ());
			} else if (player.getRotation().getY() + mouseDX < 0) {
				player.setRotation(player.getRotation().getX(), 360
						- player.getRotation().getY() + mouseDX, player
						.getRotation().getZ());
			} else {
				player.setRotation(player.getRotation().getX(), player
						.getRotation().getY() + mouseDX, player.getRotation()
						.getZ());
			}
			if (player.getRotation().getX() - mouseDY >= -90
					&& player.getRotation().getX() - mouseDY <= 90) {
				player.setRotation(player.getRotation().getX() - mouseDY,
						player.getRotation().getY(), player.getRotation()
								.getZ());
			} else if (player.getRotation().getX() - mouseDY < -90) {
				player.setRotation(-90, player.getRotation().getY(), player
						.getRotation().getZ());
			} else if (player.getRotation().getX() - mouseDY > 90) {
				player.setRotation(90, player.getRotation().getY(), player
						.getRotation().getZ());
			}
			Mouse.setGrabbed(true);
			Mouse.setCursorPosition(Display.getWidth() / 2,
					Display.getHeight() / 2);

			if (Controls.isMenuPressed) {
				if (!Controls.isMenuPressedLastFrame) {
					System.out.println("change");
				}
			}
			if (Controls.isUpPressed) {
				player.getStatus().setMoving(true);
				player.moveForward();
			}
			if (Controls.isLeftPressed) {
				player.getStatus().setMoving(true);
				player.moveLeft();
			}
			if (Controls.isRightPressed) {
				player.getStatus().setMoving(true);
				player.moveRight();
			}
			if (Controls.isDownPressed) {
				player.getStatus().setMoving(true);
				player.moveBackward();
			}
			if (Controls.isHighPressed) {
				player.getStatus().setMoving(true);
				player.moveUp(2f);
			}
			if (Controls.isLowPressed) {
				player.getStatus().setMoving(true);
				player.moveDown(2f);
			}
			if (Controls.isJumpPressed) {
				if (player.getStatus().isOnGround()) {
					player.getVelocity().setY(9.81f);
				}
			}
			if (Controls.isFullScreenPressed) {
				if (!Controls.isFullScreenPressedLastFrame) {
					try {
						Display.setFullscreen(!Display.isFullscreen());
					} catch (Exception e) {

					}
				}
			}
			if (Controls.isPolygonModePressed) {
				if (!Controls.isPolygonModePressedLastFrame) {
					if (!renderMode) {
						GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK,
								GL11.GL_FILL);
						renderMode = true;
					} else {
						GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK,
								GL11.GL_LINE);
						renderMode = false;
					}
				}
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_O)) {
				// System.out.println(chunk.getX()+" "+chunk.getY()+" "+chunk.getZ());
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
				// world.markAllChunks();
				player.setPosition(0, 10.2f, 0);
				// System.gc();
			}
			if (Controls.isFlyPressed) {
				if (!Controls.isFlyPressedLastFrame) {
					player.getStatus()
							.setFlying(!player.getStatus().isFlying());
				}
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_L)) {
				// world.loadWorld();
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_P)) {
				System.out.println(player.getPosition().getX() + " "
						+ player.getPosition().getY() + " "
						+ player.getPosition().getZ() + " "
						+ player.getRotation().getX() + " "
						+ player.getRotation().getY() + " "
						+ player.getRotation().getZ() + " "
						+ player.getVelocity().getX() + " "
						+ player.getVelocity().getY() + " "
						+ player.getVelocity().getZ());
			}
			if (Controls.isLeftClicked) {
				if (!Controls.isLeftClickedLastFrame) {

				}
			}

			if (Controls.isRightClicked) {
				if (!Controls.isRightClickedLastFrame) {

				}

			}
		} else {
			Mouse.setGrabbed(false);
		}
		if (Controls.isInventoryPressed) {
			if (!Controls.isInventoryPressedLastFrame) {
				player.setInInventory(!player.isInInventory());
			}
		}
		player.updateCamera();
	}

	public void drawGUI() {

	}

	public void render() {
		player.getCamera().usePerspective();
		for (Entity entity : entities) {
			entity.render();
		}
	}
}
