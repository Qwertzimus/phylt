package org.qwertzimus.phyltoisus.base;

import org.lwjgl.util.vector.*;

public interface Renderable {
	Vector2f position=new Vector2f();
	Vector2f rotation=new Vector2f();
	public void setPosition(Vector2f position);
	public void setRotation(Vector2f rotation);
	public Vector2f getPosition();
	public Vector2f getRotation();
	public void draw();
	
}
