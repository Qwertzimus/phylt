package Main;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Player extends Entity {
	IntBuffer viewport;
	IntBuffer selectionBuffer;
	FloatBuffer modelviewMatrix;
	FloatBuffer projectionMatrix;
	FloatBuffer winZ;
	FloatBuffer pos;
	Camera camera;
	boolean isInInventory;

	public Player() {
		super();
		camera = new Camera();
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public boolean isInInventory() {
		return isInInventory;
	}

	public void setInInventory(boolean isInInventory) {
		this.isInInventory = isInInventory;
	}

	public void updateCamera() {
		camera.setPosition(position.x, position.y + 1f, position.z);
		camera.setRotation(rotation);
	}

}
