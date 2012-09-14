package org.qwertzimus.phyltoisus.multiplayer.world;

import java.io.IOException;

import org.qwertzimus.phyltoisus.world.Block;
import org.qwertzimus.phyltoisus.world.Chunk;
import org.qwertzimus.phyltoisus.base.Entity;
public interface IWorld {
	public Chunk getChunk(float x, float y);
	public Block getBlock(float x, float y);
	boolean isServer();
	void save() throws IOException;
	void loadWorldParts(Entity e,int id);
}
