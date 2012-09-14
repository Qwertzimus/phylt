package org.qwertzimus.phyltoisus.world;

import org.lwjgl.util.vector.Vector2f;
import org.qwertzimus.phyltoisus.gui.Textures;

public class Block{
	boolean isSolid;
	int id;
	float lightValue;
	Chunk inChunk = null;
	Light l = null;

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
		if(l!=null){
			l.setHasChanged(true);
		}
		inChunk.markDirty();
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
			inChunk.markDirty();
			inChunk.setUpdateLightMap(true);
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

	public void setLight(Light light) {
		l=light;
	}

	public Light getLight() {
		return l;
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
