package org.qwertzimus.phyltoisus.multiplayer.world;

import org.lwjgl.util.vector.Vector2f;

public class Entity {
	public Vector2f position, rotation;

	public Entity() {
		position = new Vector2f();
		rotation = new Vector2f();

	}

	public float getX() {
		return position.x;
	}

	public float getY() {
		return position.y;
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

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public Vector2f getRotation() {
		return rotation;
	}

	public void setRotation(Vector2f rotation) {
		this.rotation = rotation;
	}
	
	
}
