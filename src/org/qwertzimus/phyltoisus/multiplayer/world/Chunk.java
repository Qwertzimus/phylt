package org.qwertzimus.phyltoisus.multiplayer.world;

import org.lwjgl.util.vector.Vector2f;

public class Chunk {
	public Vector2f position;
	public Block block[][][];
	public Chunk(){
		block= new Block[16][16][5];
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
	
	public Block getBlock(int x, int y, int layer) {
		return block[x][y][layer];
	}
	
}
