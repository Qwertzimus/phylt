package org.qwertzimus.phyltoisus.physic;

import org.lwjgl.util.vector.Vector2f;

public class BoxCollider2D {
	public Vector2f position, dimension;// rotation;

	public BoxCollider2D() {
		position = new Vector2f();
		dimension = new Vector2f(16,16);
		//rotation = new Vector2f();
	}

	public BoxCollider2D(float x,float y,float w,float h){
		position=new Vector2f(x,y);
		dimension=new Vector2f(w,h);
	}
	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	/*
	 * public Vector2f getRotation() { return rotation; }
	 * 
	 * public void setRotation(Vector2f rotation) { this.rotation = rotation; }
	 */
	public Vector2f getDimension() {
		return dimension;
	}

	public void setDimension(Vector2f dimension) {
		this.dimension = dimension;
	}

	public boolean intersectsWith(BoxCollider2D collider) {
		if (collider.position.x >= position.x
				&& collider.position.x <= position.x + dimension.x
				&& collider.position.y >= position.y
				&& collider.position.y <= position.y + dimension.y) {
			return true;
		}
		return false;
	}
}
