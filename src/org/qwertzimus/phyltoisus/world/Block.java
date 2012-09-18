package org.qwertzimus.phyltoisus.world;

import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.qwertzimus.phyltoisus.base.LightSource;
import org.qwertzimus.phyltoisus.base.Main;
import org.qwertzimus.phyltoisus.gui.Textures;

public class Block {
	boolean isSolid;
	int id;
	float lightValue;
	Chunk inChunk = null;
	List<LightSource> lights;

	public Block(Chunk c) {
		id = 5;
		inChunk = c;
	}

	public Block(Chunk c, int id) {
		inChunk = c;
		this.id = id;
	}
	
	public void setId(int id) {
		this.id = id;
		updateStatus();
		updateLightValue(World.getLights());
		inChunk.markDirty();
	}

	
	public synchronized void updateLightValue(List<LightSource> lights) {
		float lightValue=0;
		float curLightValue=0;
		for (LightSource ls : lights) {
			if((curLightValue=ls.getLightValue(this))>lightValue){
				ls.addBlock(this);
				lightValue=curLightValue;
			}
		}
		setLightValue(lightValue);
		inChunk.setUpdateLightMap(true);
	}

	public synchronized void setLightValue(float lightv) {
		lightValue = lightv;
	}

	public float getLightValue() {
		return lightValue;
	}

	public int getId() {
		return id;
	}

	public Chunk getChunk() {
		return inChunk;
	}

	public void setInChunk(Chunk inChunk) {
		this.inChunk = inChunk;
	}

	public void updateStatus() {
		if (id > 0) {
			isSolid = true;
		} else {
			isSolid = false;
		}
		if (inChunk != null) {
			updateLightValue(World.getLights());
			inChunk.markDirty();

		}
	}

	public Vector2f getPosition() {

		return new Vector2f(inChunk.getAbsoluteBlockPosition(this));
	}

	public boolean isSolid() {
		return isSolid;
	}

	public void setSolid(boolean isSolid) {
		this.isSolid = isSolid;
	}

	public List<LightSource> getLights() {
		return lights;
	}

	public void setLights(List<LightSource> lights) {
		this.lights = lights;
	}

	public Chunk getInChunk() {
		return inChunk;
	}

	public static float[] getVertexCoordinates(Vector2f position) {
		return new float[] { 16 + position.x, 0 + position.y, 16 + position.x,
				16 + position.y, 0 + position.x, 16 + position.y,
				0 + position.x, 0 + position.y };
	}

	public static float[] getTextureCoordinates(int id) {
		return Textures.getBlockTextureCoordinatesFromID(id);
	}
}
