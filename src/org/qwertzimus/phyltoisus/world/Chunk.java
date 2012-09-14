package org.qwertzimus.phyltoisus.world;

import java.io.*;
import java.rmi.Remote.*;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.*;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Vector2f;
import org.qwertzimus.phyltoisus.base.Entity;
import org.qwertzimus.phyltoisus.base.Main;
import org.qwertzimus.phyltoisus.base.renderAble;
import org.lwjgl.opengl.*;

/*		1
 * 	0		2
 * 		3
 */
public class Chunk implements renderAble {
	Vector2f position;
	Vector2f rotation;
	Block[][][] block;
	FloatBuffer vertices, textures;
	int vertexBufferId, textureBufferId;
	boolean isReady, shouldBeSaved, updateLightMap, rebind,needsBufferUpdate;
	int biomeType;
	Chunk chu[];
	Entity belongsTo;
	LightMap lightMap;

	public Chunk() {
		lightMap = new LightMap(this);
		lightMap.init();
		chu = new Chunk[4];
		isReady = false;
		position = new Vector2f();
		rotation = new Vector2f();
		vertexBufferId = -1;
		textureBufferId = -1;
		block = new Block[16][16][5];
		init();
	}

	public void init() {
		for (int i = 0; i < 5; i++) {
			for (int k = 0; k < 16; k++) {
				for (int j = 0; j < 16; j++) {
					block[j][k][i] = new Block(this);
				}
			}
		}

		if (vertexBufferId == -1) {
			vertexBufferId = ARBVertexBufferObject.glGenBuffersARB();
		}
		if (textureBufferId == -1) {
			textureBufferId = ARBVertexBufferObject.glGenBuffersARB();
		}
		updateLightMap();
	}

	public void setAllBlocksTo(int id) {
		for (int i = 0; i < 5; i++) {
			for (int k = 0; k < 16; k++) {
				for (int j = 0; j < 16; j++) {
					block[j][k][i].setId(2);
				}
			}
		}
		markDirty();
	}

	public Block[][][] getBlock() {
		return block;
	}

	public void setNeedsBufferUpdate(boolean needsBufferUpdate){
		this.needsBufferUpdate=needsBufferUpdate;
	}
	public boolean needsRebind() {
		return rebind;
	}

	public void setRebind(boolean rebind) {
		this.rebind = rebind;
	}

	public synchronized void updateLightMap() {
		lightMap.updateLightBuffer();
	}

	public boolean needsLightMapUpdate() {
		return updateLightMap;
	}

	public void setUpdateLightMap(boolean updateLightMap) {
		this.updateLightMap = updateLightMap;
	}

	public LightMap getLightMap() {
		return lightMap;
	}

	public void setLightMap(LightMap lightMap) {
		this.lightMap = lightMap;
	}

	public void setBlock(Block[][][] block) {
		this.block = block;
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

	public void setTextures(FloatBuffer textures) {
		this.textures = textures;
	}

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

	public boolean shouldBeSaved() {
		return shouldBeSaved;
	}

	public synchronized void setShouldBeSaved(boolean shouldBeSaved) {
		this.shouldBeSaved = shouldBeSaved;
	}

	public int getBiomeType() {
		return biomeType;
	}

	public void setBiomeType(int biomeType) {
		this.biomeType = biomeType;
	}

	public Chunk[] getChu() {
		return chu;
	}

	public void setChu(Chunk[] chu) {
		this.chu = chu;
	}

	public Vector2f getPosition() {
		return position;
	}

	public Vector2f getAbsoluteBlockPosition(Block b) {
		Vector2f pos = null;
		for (int i = 0; i < 5; i++) {
			for (int k = 0; k < 16; k++) {
				for (int j = 0; j < 16; j++) {
					if (b == block[j][k][i]) {
						pos = new Vector2f(position.x + 16 * j, position.y + 16
								* k);
					}
				}
			}
		}
		return pos;
	}

	public synchronized void setPosition(Vector2f position) {
		this.position = position;
		markDirty();
	}

	public void setPosition(float x, float y) {
		position.set(x, y);
		markDirty();
	}

	public Vector2f getRotation() {
		return rotation;
	}

	public void setRotation(Vector2f rotation) {
		this.rotation = rotation;
	}

	public Block getBlock(int x, int y, int layer) {
		return block[x][y][layer];
	}

	public Block getBlock(float x, float y, int z) {
		for (int i = 0; i < 16; i++) {
			for (int k = 0; k < 16; k++) {
				if (block[k][i][z].getPosition().x == x
						&& block[k][i][z].getPosition().y == y) {
					return block[k][i][z];
				}
			}
		}
		return null;
	}

	public Block[][][] getBlocks() {
		return block;
	}

	public void setBlocks(Block[][][] blocks) {
		block = blocks;
	}

	public synchronized void setReady(boolean isReady) {
		this.isReady = isReady;
	}

	public synchronized void markDirty() {
		isReady = false;
	}

	public boolean isReady() {
		return isReady;
	}

	public void setChunk(int i, Chunk c) {
		chu[i] = c;
	}

	public Chunk getChunk(int i) {
		return chu[i];
	}

	public void loadChunk() {
		File f = new File(World.worldPath + "c" + position.x + "c" + position.y
				+ ".manf");
		if (f.exists()) {
			try {

				loadChunkFromFile(f);
			} catch (Exception ex) {
				System.out.println(ex);
				System.out.println("chunk file error"
						+ System.getProperty("line.seperator")
						+ "Generating new Chunk");
				generateChunk();
			}
		} else {
			generateChunk();
		}
	}

	public void loadChunk(int x, int y) {
		File f = new File(World.worldPath + "c" + x + "c" + y + ".manf");
		if (f.exists()) {
			try {
				setPosition(x, y);
				setAllBlocksTo(0);
				loadChunkFromFile(f);
			} catch (Exception ex) {
				System.out.println("chunk file error"
						+ System.getProperty("line.seperator")
						+ "Generating new Chunk");
				generateChunk();
			}
		} else {
			generateChunk();
		}
	}

	public boolean loadChunkFromFile(File file) {
		DataInputStream dis = null;
		try {
			dis = new DataInputStream(new FileInputStream(file));
			biomeType = dis.readInt();
			int c = 0;
			for (int i = 0; i < 5; i++) {
				for (int k = 0; k < 16; k++) {
					for (int j = 0; j < 16; j++) {
						block[j][k][i].setId(dis.readInt());
						block[j][k][i].setLightValue(0);
					}
				}
			}
		} catch (Exception e) {
			try {
				dis.close();
			} catch (Exception ex) {

			}
		}
		return true;
	}

	public void generateChunk() {
		int gid = 0;
		int lastY = 0;
		int y;
		Random rand = new Random(World.worldSeed + (long) position.x);

		if (chu[0] == null)
			y = rand.nextInt(16);
		else {
			y = 0;
			for (int i = 0; i < 16; i++) {
				if (chu[0].getBlock(15, i, 0).getId() == 0) {
					y = i;
					break;
				}
				if (i == 15)
					y = 0;
			}
		}
		for (int i = 0; i < 5; i++) {
			for (int k = 0; k < 16; k++) {
				if (position.y >= 0 && position.y <= 256) {
					lastY = y;
					do {
						y = rand.nextInt(16);
					} while (Math.abs(y - lastY) > 2);
				}

				for (int j = 0; j < 16; j++) {
					if (i == 0) {
						if (j >= y) {
							gid = 0;
						} else {
							gid = 1;
						}
						if (position.y < 0)
							gid = 1;
						else if (position.y >= 256)
							gid = 0;
						block[k][j][i].setId(gid);
					} else {
						block[k][j][i].setId(0);
					}
				}
			}
		}

		setShouldBeSaved(true);
	}

	public int generateBlockId(Block b) {
		int bId = 0;
		double ran = Math.random();
		// System.out.println(ran);
		if (b.getChunk().getPosition().y >= 0) {

		}
		return bId;
	}

	public void saveChunk() throws IOException {
		File f = new File(World.worldPath + "c" + position.x + "c" + position.y
				+ ".manf");
		f.createNewFile();
		DataOutputStream dos = new DataOutputStream(new FileOutputStream(f));
		dos.writeInt(biomeType);
		boolean b = false;
		for (int i = 0; i < 5; i++) {
			for (int k = 0; k < 16; k++) {
				for (int j = 0; j < 16; j++) {

					dos.writeInt(block[j][k][i].getId());
				}
			}
		}

		dos.flush();
		dos.close();
	}

	public synchronized void updateBuffer() {
		rebind=false;
		try {
			vertices.clear();
			textures.clear();

			vertices.mark();
			textures.mark();

		} catch (Exception ex) {
			vertices = BufferUtils.createFloatBuffer(8 * 16 * 16 * 5);
			textures = BufferUtils.createFloatBuffer(8 * 16 * 16 * 5);

		}
		for (int o = 0; o < 5; o++) {
			for (int i = 0; i < 16; i++) {
				for (int k = 0; k < 16; k++) {
					vertices.put(Block.getVertexCoordinates(new Vector2f(
							position.x + (k * 16), position.y + (i * 16))));
					textures.put(Block.getTextureCoordinates(block[k][i][o]
							.getId()));
					// lightValues.put(block[k][i][o].getLightValue());
				}
			}
		}
		vertices.rewind();
		textures.rewind();

		// ARBVertexBufferObject.glDeleteBuffersARB(vertexBufferId);

		// ARBVertexBufferObject.glDeleteBuffersARB(textureBufferId);

		// System.out.println("updated");
		needsBufferUpdate=false;
		rebind = true;
	}

	public void rebindLightMapMaybe() {
		lightMap.bindBuffer();
	}

	public void rebindBufferMaybe() {
		if (rebind) {
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

			setReady(true);
			rebind = false;
		}
	}

	public boolean needsBufferUpdate(){
		return needsBufferUpdate;
	}
	public void draw() {
		if (Main.world.isFirstLoaded||isReady) {
			if(lightMap.getLightValues()==null)System.out.println("notLOADED");
			// GL11.glTranslatef(-position.x, -position.y, 0);

			/*
			 * GL11.glVertexPointer(2,0,vertices);
			 * GL11.glTexCoordPointer(2,0,textures);
			 */
			ARBVertexBufferObject.glBindBufferARB(
					ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, vertexBufferId);
			GL11.glVertexPointer(2, GL11.GL_FLOAT, 0, 0);
			ARBVertexBufferObject.glBindBufferARB(
					ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, 0);
			ARBVertexBufferObject.glBindBufferARB(
					ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, textureBufferId);
			GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, 0);
			/*
			 * ARBVertexBufferObject.glBindBufferARB(
			 * ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, lightBufferId);
			 */
			ARBVertexProgram.glEnableVertexAttribArrayARB(2);
			ARBVertexBufferObject.glBindBufferARB(
					ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB,
					lightMap.getLightBufferId());
			ARBVertexProgram.glVertexAttribPointerARB(2, 1, GL11.GL_FLOAT,
					false, 0, 0);
			// System.out.println(ARBVertexShader.glGetAttribLocationARB(
			// Main.shaderId, "lightValue"));

			// ARBVertexProgram.glBindProgramARB(lightBufferId, Main.shaderId);
			GL11.glDrawArrays(GL11.GL_QUADS, 0, 16 * 16 * 5);
			ARBVertexBufferObject.glBindBufferARB(
					ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, 0);
			ARBVertexProgram.glDisableVertexAttribArrayARB(2);

		}
	}
}
