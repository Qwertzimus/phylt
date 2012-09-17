package org.qwertzimus.phyltoisus.world;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.qwertzimus.phyltoisus.base.LightSource;
import org.qwertzimus.phyltoisus.base.Main;

public class Light implements LightSource {
	float lightIntensity;
	int range;
	Vector2f position, oldPosition;
	List<Block> changedBlocks;
	List<Float> changedBlocksLightValue;
	boolean forceUpdate, hasChanged;

	public Light() {
		position = new Vector2f();
		oldPosition = new Vector2f();
		changedBlocks = new ArrayList<Block>();
		changedBlocksLightValue = new ArrayList<Float>();
	}

	public int getChunkX() {
		return (int) Math.floor(position.x / 256) * 256;
	}

	public int getChunkY() {
		return (int) Math.floor(position.y / 256) * 256;
	}

	public int getBlockX() {
		return (int) Math.floor(position.x / 16) * 16;
	}

	public int getBlockY() {
		return (int) Math.floor(position.y / 16) * 16;
	}

	public Vector2f getOldPosition() {
		return oldPosition;
	}

	public void setOldPosition(Vector2f oldPosition) {
		this.oldPosition = oldPosition;
	}

	public List<Block> getChangedBlocks() {
		return changedBlocks;
	}

	public void setChangedBlocks(List<Block> changedBlocks) {
		this.changedBlocks = changedBlocks;
	}

	public List<Float> getChangedBlocksLightValue() {
		return changedBlocksLightValue;
	}

	public void setChangedBlocksLightValue(List<Float> changedBlocksLightValue) {
		this.changedBlocksLightValue = changedBlocksLightValue;
	}

	public void setForceUpdate(boolean forceUpdate) {
		this.forceUpdate = forceUpdate;
	}

	@Override
	public void setLightIntensity(float lightIntensity) {
		this.lightIntensity = lightIntensity;
	}

	@Override
	public float getLightIntensity() {
		return lightIntensity;
	}

	@Override
	public void setPosition(float x, float y) {
		position.set(x, y);

	}

	@Override
	public synchronized void setPosition(Vector2f position) {
		Chunk c = Main.world.getChunk(getChunkX(), getChunkY());
		if (c != null) {
			// c.removeLight(this);
		}
		this.position = position;

		// Main.world.getChunk(getChunkX(), getChunkY()).addLight(this);
	}

	@Override
	public Vector2f getPosition() {
		return position;
	}

	@Override
	public synchronized void updateBlocks() {
		if (Main.world.isFirstLoaded) {
			// System.out.println("update started");
			List<Block> thisBlocks = new ArrayList<Block>();
			final List<Chunk> chunksToUpdate = new ArrayList<Chunk>();
			if (hasChanged() || forceUpdate) {

				for (Block b : changedBlocks) {
					b.setLightValue(changedBlocksLightValue.get(0));
					changedBlocksLightValue.remove(0);
					if (!chunksToUpdate.contains(b)) {
						chunksToUpdate.add(b.getChunk());
						b.getChunk().setUpdateLightMap(true);
					}
				}
				changedBlocks.clear();
				changedBlocksLightValue.clear();
				try {
					for (int i = -range; i < range; i++) {
						for (int k = -range; k < range; k++) {
							Block b = Main.world.getBlock(
									position.x + (k * 16), position.y
											+ (i * 16));
							if (b != null) {
								float lI = lightIntensity
										- (Math.abs((b.getPosition().x - position.x) / 16)
												* Math.abs((b.getPosition().x - position.x) / 16) + Math
												.abs((b.getPosition().y - position.y) / 16)
												* Math.abs((b.getPosition().y - position.y) / 16))
										/ 100;
								if (lI > b.getLightValue()) {
									if (!chunksToUpdate.contains(b)) {
										chunksToUpdate.add(b.getChunk());
										b.getChunk().setUpdateLightMap(true);
									}
									changedBlocks.add(b);
									changedBlocksLightValue.add(b
											.getLightValue());
									b.setLightValue(lI);
								}
							}
						}
					}
				} catch (Exception e) {
					System.out.println("lightUpdate" + e);
				}
				updateLightMaps(chunksToUpdate);
				/*if (thread == false) {
					thread=true;
					new Thread(new Runnable() {
						public void run() {
							updateLightMaps(chunksToUpdate);
							thread=false;
						}
					}).start();
				}*/
				setHasChanged(false);
				oldPosition.set(position);

			}
		}

	}

	public synchronized void updateLightMaps(final List<Chunk> chunks) {
		for (Chunk chunk : chunks) {
			chunk.updateLightMap();
		}
	}

	@Override
	public int getRange() {
		return range;
	}

	// TODO- fix this
	public synchronized boolean hasChanged() {
		if (Math.floor(oldPosition.x / 16) * 16 != Math.floor(position.x / 16) * 16
				|| Math.floor(oldPosition.y / 16) * 16 != Math
						.floor(position.y / 16) * 16)
			setHasChanged(true);
		return hasChanged;
	}

	public synchronized void setHasChanged(boolean hasChanged) {
		this.hasChanged = hasChanged;
	}

	@Override
	public void setRange(int range) {
		this.range = range;
	}

	@Override
	public boolean forceUpdate() {
		return forceUpdate;
	}

}
