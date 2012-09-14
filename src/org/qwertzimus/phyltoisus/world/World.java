package org.qwertzimus.phyltoisus.world;

import org.qwertzimus.phyltoisus.gui.*;
import org.qwertzimus.phyltoisus.multiplayer.*;
import org.qwertzimus.phyltoisus.multiplayer.world.IWorld;
import org.qwertzimus.phyltoisus.physic.Time;

import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.*;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Vector2f;
import org.qwertzimus.phyltoisus.base.*;

public class World implements IWorld {
	HashMap<Integer, Chunk[][]> chunkE;
	List<renderAble> objects;
	List<Entity> entities;
	List<EntityController> entityController;
	FloatBuffer diffuse, ambient;
	public static int chunkUpdates = 20;
	public static int worldUpdaterSleepTime = 20;
	public static String worldPath = "./saves/world01/";
	public static long worldSeed = Long.valueOf("4684131546864462");
	public static boolean isFirstLoaded;
	public static long lastTime = Time.getTime() / 1000;
	public static int light;
	public static List<LightSource> lights;
	public static boolean isServer = false;

	public World() {
		objects = new ArrayList<renderAble>();
		entities = new ArrayList<Entity>();
		entityController = new ArrayList<EntityController>();
		lights = new ArrayList<LightSource>();
		chunkE = new HashMap<Integer, Chunk[][]>();
		File f = new File(worldPath);
		if (!f.exists()) {
			f.mkdirs();
		}
		System.out.println(worldSeed);
	}

	public void init(int e) {
		Chunk[][] chunks = chunkE.get(e);
		if (chunks == null) {
			chunks = new Chunk[15][15];
			chunkE.put(0, chunks);
		}
		for (int i = 0; i < chunks.length; i++) {
			for (int k = 0; k < chunks.length; k++) {
				chunks[k][i] = new Chunk();
				if (k > 0)
					chunks[k][i].setChunk(0, chunks[k - 1][i]);
				chunks[k][i].setPosition(new Vector2f(k * 256, i * 256));
				chunks[k][i].loadChunk();
				Light l1 = new Light();
				l1.setPosition(chunks[k][i].getPosition());
				l1.setLightIntensity(1f);
				l1.setRange(10);
//				lights.add(l1);

			}
		}

		for (int i = 0; i < chunks.length; i++) {
			for (int k = 0; k < chunks.length; k++) {
				if (k > 0)
					chunks[k][i].setChunk(0, chunks[k - 1][i]);
				if (k + 1 < chunks.length)
					chunks[k][i].setChunk(2, chunks[k + 1][i]);
				if (i > 0)
					chunks[k][i].setChunk(1, chunks[k][i - 1]);
				if (i + 1 < chunks.length)
					chunks[k][i].setChunk(3, chunks[k][i + 1]);

			}
		}
		for(int i=0;i<chunks.length;i++){
			for(int k=0;k<chunks.length;k++){
				chunks[k][i].getLightMap().updateLightBuffer();
			}
		}
		if (!isServer) {
			// final Chunk[][] chunkA=chunks;
			new Thread() {
				public void run() {
					while (true) {
						try {
							updateLights(0, lights.size());
							sleep(1);
						} catch (Exception e) {
							System.out.println(e);
						}
					}
				}
			}.start();

		}
		System.out.println("initalised");
	}

	public Chunk getChunk(float x, float y) {
		Chunk c = null;
		for (int s : chunkE.keySet()) {
			Chunk[][] chunks = chunkE.get(s);

			for (int i = 0; i < chunks.length; i++) {
				for (int k = 0; k < chunks.length; k++) {
					if (x == chunks[k][i].getPosition().x
							&& y == chunks[k][i].getPosition().y) {
						return c = chunks[k][i];
					} else {
						// System.out.println("geg:"+chunks[k][i].getPosition());
						// System.out.println("ges:"+x+" "+y);
					}
				}
			}
		}
		return c;
	}

	// always layer 0

	public Block getBlock(float x, float y) {
		Block b = null;
		int cX = (int) Math.floor(x / 256) * 256;
		int cY = (int) Math.floor(y / 256) * 256;
		int bX = (int) Math.floor(x / 16) * 16;
		int bY = (int) Math.floor(y / 16) * 16;
		Chunk c = null;
		try {
			c = getChunk(cX, cY);
			if (c != null) {
				b = c.getBlock((bX - cX) / 16, (bY - cY) / 16, 0);
			}
		} catch (Exception e) {
			System.out.println("blockselection" + e);
		}
		return b;
	}

	public Chunk getChunkFromMemory(int x, int y, int i) {
		Chunk[][] chunks = chunkE.get(i);
		return chunks[x][y];
	}

	public Chunk[][] getChunks(int i) {
		Chunk[][] chunks = chunkE.get(i);
		return chunks;
	}

	public void setChunks(Chunk[][] chunk, int i) {
		Chunk[][] chunks = chunkE.get(i);
		chunks = chunks;
	}

	public List<renderAble> getObjects() {
		return objects;
	}

	public void setObjects(List<renderAble> objects) {
		this.objects = objects;
	}

	public List<EntityController> getEntityController() {
		return entityController;
	}

	public void setEntityController(List<EntityController> entityController) {
		this.entityController = entityController;
	}

	public static int getChunkUpdates() {
		return chunkUpdates;
	}

	public static void setChunkUpdates(int chunkUpdates) {
		World.chunkUpdates = chunkUpdates;
	}

	public static int getWorldUpdaterSleepTime() {
		return worldUpdaterSleepTime;
	}

	public static void setWorldUpdaterSleepTime(int worldUpdaterSleepTime) {
		World.worldUpdaterSleepTime = worldUpdaterSleepTime;
	}

	public static String getWorldPath() {
		return worldPath;
	}

	public static void setWorldPath(String worldPath) {
		World.worldPath = worldPath;
	}

	public static long getWorldSeed() {
		return worldSeed;
	}

	public static void setWorldSeed(long worldSeed) {
		World.worldSeed = worldSeed;
	}

	public static boolean isFirstLoaded() {
		return isFirstLoaded;
	}

	public static void setFirstLoaded(boolean isFirstLoaded) {
		World.isFirstLoaded = isFirstLoaded;
	}

	public static long getLastTime() {
		return lastTime;
	}

	public static void setLastTime(long lastTime) {
		World.lastTime = lastTime;
	}

	public static int getLight() {
		return light;
	}

	public static void setLight(int light) {
		World.light = light;
	}

	public static List<LightSource> getLights() {
		return lights;
	}

	public static void setLights(List<LightSource> lights) {
		World.lights = lights;
	}

	public List<Entity> getEntities() {
		return entities;
	}

	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}

	public void registerEntity(Entity e) {
		entities.add(e);
	}

	public void removeEntity(Entity e) {
		entities.remove(e);
	}

	public void registerEntityController(EntityController e) {
		entityController.add(e);
	}

	public void removeEntityControllor(EntityController e) {
		entityController.remove(e);
	}

	public void update() {
		for (Entity e : entities) {
			e.update();
		}
	}

	public void updateAIs() {
		for (EntityController e : entityController) {
			e.setOk(true);
		}
	}

	// 50176 49152
	public synchronized void loadWorldParts(Entity e, int id) {
		Chunk[][] chunks = chunkE.get(id);
		int diffX = 0, diffY = 0;
		for (int i = 0; i < chunks.length; i++) {
			for (int k = 0; k < chunks.length; k++) {
				if ((diffX = (int) (e.getPosition().x - chunks[k][i]
						.getPosition().x)) >= -256 * 19 && diffX < -768) {
					try {

						if (chunks[k][i].shouldBeSaved())
							chunks[k][i].saveChunk();

					} catch (Exception ex) {

					}
					chunks[k][i].setPosition(
							chunks[k][i].getPosition().x - 3840,
							chunks[k][i].getPosition().y);

					chunks[k][i].loadChunk();
					chunks[k][i].setNeedsBufferUpdate(true);
//					chunks[k][i].updateBuffer();
//					chunks[k][i].setRebind(true);
					// System.out.println("update");
				} else if ((diffX = (int) (e.getPosition().x - chunks[k][i]
						.getPosition().x)) > 3072) {
					try {

						if (chunks[k][i].shouldBeSaved())
							chunks[k][i].saveChunk();

					} catch (Exception ex) {

					}
					chunks[k][i].setPosition(
							chunks[k][i].getPosition().x + 3840,
							chunks[k][i].getPosition().y);
					chunks[k][i].loadChunk();
					chunks[k][i].setNeedsBufferUpdate(true);
//					chunks[k][i].updateBuffer();
//					chunks[k][i].setRebind(true);
					// System.out.println("update");

				}
				if ((diffY = (int) (e.getPosition().y - chunks[k][i]
						.getPosition().y)) >= -256 * 19 && diffY < -768) {
					try {

						if (chunks[k][i].shouldBeSaved())
							chunks[k][i].saveChunk();

					} catch (Exception ex) {

					}
					chunks[k][i].setPosition(chunks[k][i].getPosition().x,
							chunks[k][i].getPosition().y - 3840);
					chunks[k][i].loadChunk();
					chunks[k][i].setNeedsBufferUpdate(true);
//					chunks[k][i].updateBuffer();
//					chunks[k][i].setRebind(true);
					// System.out.println("update");

				} else if ((diffY = (int) (e.getPosition().y - chunks[k][i]
						.getPosition().y)) > 3072) {
					try {

						if (chunks[k][i].shouldBeSaved())
							chunks[k][i].saveChunk();

					} catch (Exception ex) {

					}
					chunks[k][i].setPosition(chunks[k][i].getPosition().x,
							chunks[k][i].getPosition().y + 3840);
					chunks[k][i].loadChunk();
					chunks[k][i].setNeedsBufferUpdate(true);
//					chunks[k][i].updateBuffer();
//					chunks[k][i].markDirty();
//					chunks[k][i].setRebind(true);
					// System.out.println(chunks[k][i].getBlock(0, 0,
					// 0).getId());

				}
				if(!chunks[k][i].isReady()||chunks[k][i].needsBufferUpdate()){
					chunks[k][i].updateBuffer();
				}
			}
		}
	}

	public void save() throws IOException {
		for (int bö : chunkE.keySet()) {
			Chunk[][] chunks = chunkE.get(bö);
			for (int i = 0; i < chunks.length; i++) {
				for (int k = 0; k < chunks.length; k++) {

					chunks[k][i].saveChunk();

				}
			}
		}
	}

	public synchronized void updateLights(int start, int end) {
		if (end > lights.size())
			end = lights.size();
		for (int i = start; i < end; i++) {
			lights.get(i).updateBlocks();
		}
	}

	public synchronized void forceUpdateLights() {
		for (LightSource ls : lights) {
			ls.setForceUpdate(true);
			ls.updateBlocks();
			ls.setForceUpdate(false);
			try {
				Thread.sleep(1);
			} catch (Exception e) {
				System.out.println(e);
			}

		}
	}

	public void draw(Entity e) {
		Chunk[][] chunks = chunkE.get(0);
		ARBShaderObjects.glUseProgramObjectARB(Main.shaderId);
		int loc = ARBShaderObjects.glGetUniformLocationARB(Main.shaderId,
				"texure");
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, Textures.texture1.getTextureID());
		ARBShaderObjects.glUniform1iARB(loc, GL13.GL_ACTIVE_TEXTURE);
		light = ARBShaderObjects.glGetUniformLocationARB(Main.shaderId,
				"lightValue");

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
				GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER,
				GL11.GL_NEAREST);
		int updates = 0;
		// System.out.println("start loop");
		for (int i = 0; i < chunks.length; i++) {
			for (int k = 0; k < chunks.length; k++) {
				if (updates < chunkUpdates && !chunks[k][i].isReady()
						||chunks[k][i].needsLightMapUpdate()
						||chunks[k][i].needsRebind()) {
					
					chunks[k][i].rebindBufferMaybe();
					chunks[k][i].rebindLightMapMaybe();
				}

				if (Math.abs(Main.player.getPosition().x
						- chunks[k][i].getPosition().x) <= 1024
						&& Math.abs(Main.player.getPosition().y
								- chunks[k][i].getPosition().y) <= 1024) {
					chunks[k][i].draw();
				}
			}
		}
		boolean dar = false;
		for (int i = 1; i < entities.size(); i++) {
			dar = true;
			entities.get(i).draw();
		}
		entities.get(0).draw();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		ARBShaderObjects.glUseProgramObjectARB(0);
	}

	@Override
	public boolean isServer() {
		return isServer;
	}

}
