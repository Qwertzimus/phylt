package org.qwertzimus.phyltoisus.world;

import org.lwjgl.util.vector.Vector2f;

public abstract class Particle {
	protected long birth, lifeTime;
	protected Vector2f position,velocity;
	protected int textureId;
	
	public Particle() {
		birth = System.currentTimeMillis();
	}

	public Particle(long lifeTime) {
		birth = System.currentTimeMillis();
		this.lifeTime = lifeTime;
	}

	public long getBirth() {
		return birth;
	}

	public void setBirth(long birth) {
		this.birth = birth;
	}

	public long getLifeTime() {
		return lifeTime;
	}

	public void setLifeTime(long lifeTime) {
		this.lifeTime = lifeTime;
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public int getTextureId() {
		return textureId;
	}

	public void setTextureId(int textureId) {
		this.textureId = textureId;
	}
	public abstract void update();
}
