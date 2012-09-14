package org.qwertzimus.phyltoisus.multiplayer.world;

import org.lwjgl.util.vector.Vector2f;

public class Block {
	int id=0;
	Chunk inChunk;
	public Block(){
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Chunk getInChunk() {
		return inChunk;
	}
	public void setInChunk(Chunk inChunk) {
		this.inChunk = inChunk;
	}
	
	public Vector2f getPosition() {
		return new Vector2f(inChunk.getAbsoluteBlockPosition(this));
	}

}
