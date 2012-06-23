package Main;

public class Player extends Entity {

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

}
