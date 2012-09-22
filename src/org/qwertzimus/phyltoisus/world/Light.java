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
	List<Block> changedBlocks,blocks;
	List<Float> changedBlocksLightValue;
	boolean forceUpdate, hasChanged;

	public Light() {
		position = new Vector2f();
		oldPosition = new Vector2f();
		changedBlocks = new ArrayList<Block>();
		blocks = new ArrayList<Block>();
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
		this.oldPosition.set(oldPosition);
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
		if(World.isFirstLoaded){
			if(hasChanged()){
				range=(int)(lightIntensity*30);
				for(int i=-range;i<range;i++){
					for(int k=-range;k<range;k++){
						Block b=Main.world.getBlock(position.x+(k*16), position.y+(i*16));
						if(b!=null){
							b.updateLightValue(Main.world.getLights());
						}
					}
				}
				setOldPosition(position);
			}
		}
	}

	public synchronized void updateLightMapsMaybe(final List<Chunk> chunks) {
		for (Chunk chunk : chunks) {
			chunk.updateLightMapMaybe();
		}
	}

	@Override
	public int getRange() {
		return range;
	}

	// TODO- fix this(but not here)
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

	public float getLightValue(Block b) {
		float lI = lightIntensity
				- (Math.abs((b.getPosition().x - position.x) / 16)
						* Math.abs((b.getPosition().x - position.x) / 16) + Math
						.abs((b.getPosition().y - position.y) / 16)
						* Math.abs((b.getPosition().y - position.y) / 16))
				/ 100;
		return lI;
	}
	public float getLightValue(Vector2f position) {
		float lI = lightIntensity
				- (Math.abs((position.x - this.position.x) / 16)
						* Math.abs((position.x - this.position.x) / 16) + Math
						.abs((position.y - this.position.y) / 16)
						* Math.abs((position.y - this.position.y) / 16))
				/ 100;
		return lI;
	}

	@Override
	public synchronized void addBlock(Block b) {
		blocks.add(b);
		
	}

	@Override
	public synchronized  void removeBlock(Block b) {
		blocks.remove(b);
	}

}
