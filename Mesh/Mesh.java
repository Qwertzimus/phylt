package Mesh;

import org.lwjgl.util.vector.*;

public class Mesh {
	Vector3f position;
	Vector3f rotation;
	Skeleton skeleton;
	public Mesh() {
		position=new Vector3f();
		rotation=new Vector3f();
		skeleton=new Skeleton();
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}

}
