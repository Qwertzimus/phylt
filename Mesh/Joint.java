package Mesh;

import org.lwjgl.util.vector.*;

public class Joint {

	Vector3f position;
	Vector3f rotation;

	public Joint() {
		position = new Vector3f();
		rotation = new Vector3f();
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
