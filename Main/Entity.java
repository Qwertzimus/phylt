package Main;

import org.lwjgl.util.vector.*;
import Physics.*;
import Mesh.*;
public class Entity {
	Vector3f position;
	Vector3f rotation;
	Vector3f velocity;
	Vector3f maxVelocity;
	float accelerationSpeed = 1.5f;
	float friction = 0.9f;
	float height;
	float width;
	float length;
	Mesh mesh;
	BoxCollider collider;
	Status status;

	public Entity() {
		mesh=new Mesh();
		status = new Status();
		position = new Vector3f();
		rotation = new Vector3f();
		velocity = new Vector3f();
		maxVelocity = new Vector3f();
	}

	public BoxCollider getCollider() {
		return collider;
	}

	public void setCollider(BoxCollider collider) {
		this.collider = collider;
	}

	public float getChunkX() {
		int x = (int) Math.floor(position.x);
		while (x % 16 != 0) {
			x--;
		}
		return (float) x;
	}

	public float getChunkY() {
		int y = (int) Math.floor(position.y);
		while (y % 16 != 0) {
			y--;
		}
		return (float) y;
	}

	public float getChunkZ() {
		int z = (int) Math.floor(position.z);
		while (z % 16 != 0) {
			z--;
		}
		return (float) z;
	}

	public float getBlockX() {
		return (float) Math.floor(position.x);
	}

	public float getBlockY() {
		return (float) Math.floor(position.y);
	}

	public float getBlockZ() {
		return (float) Math.floor(position.z);
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public void setPosition(float x, float y, float z) {
		position.set(x, y, z);
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}

	public void setRotation(float x, float y, float z) {
		rotation.set(x, y, z);
	}

	public Vector3f getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector3f velocity) {
		this.velocity = velocity;
	}

	public Vector3f getMaxVelocity() {
		return maxVelocity;
	}

	public void setMaxVelocity(Vector3f maxVelocity) {
		this.maxVelocity = maxVelocity;
	}

	public float getAccelerationSpeed() {
		return accelerationSpeed;
	}

	public void setAccelerationSpeed(float accelerationSpeed) {
		this.accelerationSpeed = accelerationSpeed;
	}

	public float getFriction() {
		return friction;
	}

	public void setFriction(float friction) {
		this.friction = friction;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void moveForward() {
		float oldVX = velocity.x;
		float oldVZ = velocity.z;
		velocity.x += (float) Time.deltaTime / 1000f
				* (float) Math.sin(Math.toRadians(rotation.y))
				* accelerationSpeed;
		velocity.z -= (float) Time.deltaTime / 1000f
				* (float) Math.cos(Math.toRadians(rotation.y))
				* accelerationSpeed;
		if (!status.isSprinting() && Math.abs(velocity.x) > maxVelocity.x) {
			velocity.x = oldVX;
			velocity.z = oldVZ;
		}
		if (status.isSprinting() && Math.abs(velocity.x) > maxVelocity.x * 2) {
			velocity.x = oldVX;
			velocity.z = oldVZ;
		}
		if (!status.isSprinting() && Math.abs(velocity.z) > maxVelocity.z) {
			velocity.x = oldVX;
			velocity.z = oldVZ;
		}
		if (!status.isSprinting() && Math.abs(velocity.x) > maxVelocity.z * 2) {
			velocity.x = oldVX;
			velocity.z = oldVZ;

		}
		// System.out.println(velocity.x+" "+velocity.z);
	}

	public void moveBackward() {
		float oldVX = velocity.x;
		float oldVZ = velocity.z;

		velocity.x -= (float) Time.deltaTime / 1000f
				* (float) Math.sin(Math.toRadians(rotation.y))
				* accelerationSpeed;
		velocity.z += (float) Time.deltaTime / 1000f
				* (float) Math.cos(Math.toRadians(rotation.y))
				* accelerationSpeed;
		if (!status.isSprinting() && Math.abs(velocity.x) > maxVelocity.x) {
			velocity.x = oldVX;
			velocity.z = oldVZ;
			// System.out.println("correction");
		}
		if (status.isSprinting() && Math.abs(velocity.x) > maxVelocity.x * 2) {
			velocity.x = oldVX;
			velocity.z = oldVZ;
			// System.out.println("correction");
		}
		if (!status.isSprinting() && Math.abs(velocity.z) > maxVelocity.z) {
			velocity.x = oldVX;
			velocity.z = oldVZ;
			// System.out.println("correction");
		}
		if (status.isSprinting() && Math.abs(velocity.x) > maxVelocity.z * 2) {
			velocity.x = oldVX;
			velocity.z = oldVZ;
			// System.out.println("correction");
		}

		// System.out.println(velocity.x+" "+velocity.z);
	}

	public void moveLeft() {
		float oldVX = velocity.x;
		float oldVZ = velocity.z;

		velocity.x += (float) Time.deltaTime / 1000f
				* (float) Math.sin(Math.toRadians(rotation.y - 90))
				* accelerationSpeed;
		velocity.z -= (float) Time.deltaTime / 1000f
				* (float) Math.cos(Math.toRadians(rotation.y - 90))
				* accelerationSpeed;
		if (!status.isSprinting() && Math.abs(velocity.x) > maxVelocity.x) {
			velocity.x = oldVX;
			velocity.z = oldVZ;
			// System.out.println("correction");
		}
		if (status.isSprinting() && Math.abs(velocity.x) > maxVelocity.x * 2) {
			velocity.x = oldVX;
			velocity.z = oldVZ;
			// System.out.println("correction");
		}
		if (!status.isSprinting() && Math.abs(velocity.z) > maxVelocity.z) {
			velocity.x = oldVX;
			velocity.z = oldVZ;
			// System.out.println("correction");
		}
		if (status.isSprinting() && Math.abs(velocity.x) > maxVelocity.z * 2) {
			velocity.x = oldVX;
			velocity.z = oldVZ;
			// System.out.println("correction");
		}
		// System.out.println(velocity.x+" "+velocity.z);
	}

	public void moveRight() {
		float oldVX = velocity.x;
		float oldVZ = velocity.z;

		velocity.setX(velocity.getX() + (float) Time.deltaTime / 1000f
				* (float) Math.sin(Math.toRadians(rotation.y + 90))
				* accelerationSpeed);
		velocity.setZ(velocity.getZ() - (float) Time.deltaTime / 1000f
				* (float) Math.cos(Math.toRadians(rotation.y + 90))
				* accelerationSpeed);
		if (!status.isSprinting() && Math.abs(velocity.x) > maxVelocity.x) {
			velocity.x = oldVX;
			velocity.z = oldVZ;
			// System.out.println("correction");
		}
		if (status.isSprinting() && Math.abs(velocity.x) > maxVelocity.x * 2) {
			velocity.x = oldVX;
			velocity.z = oldVZ;
			// System.out.println("correction");
		}
		if (!status.isSprinting() && Math.abs(velocity.z) > maxVelocity.z) {
			velocity.x = oldVX;
			velocity.z = oldVZ;
			// System.out.println("correction");
		}
		if (status.isSprinting() && Math.abs(velocity.x) > maxVelocity.z * 2) {
			velocity.x = oldVX;
			velocity.z = oldVZ;
			// System.out.println("correction");
		}
		// System.out.println(velocity.x+" "+velocity.z);
	}

	public void moveUp(float distance) {
		position.y += distance * Time.deltaTime / 1000f;
	}

	public void moveDown(float distance) {
		position.y -= distance * Time.deltaTime / 1000f;
	}

	public void update() {
		status.setOnGround(false);

		float oldPosX = position.getX();
		float oldPosY = position.getY();
		float oldPosZ = position.getZ();

		correctVelocity();

		float newPosX = position.getX() + velocity.x * Time.deltaTime / 1000f;
		float newPosY = position.getY() + velocity.y * Time.deltaTime / 1000f;
		float newPosZ = position.getZ() + velocity.z * Time.deltaTime / 1000f;

		setPosition(newPosX, newPosY, newPosZ);
		updateCollider();

		updateCollider();
		if (!status.isFlying()) {
			gravi();
		}
		status.setMoving(false);
	}

	public void correctVelocity() {
		if (velocity.x > maxVelocity.x) {
			velocity.setX(maxVelocity.getX());
		} else if (velocity.x < -maxVelocity.x) {
			velocity.setX(-maxVelocity.getX());
		}

		if (velocity.y > maxVelocity.y) {
			velocity.setY(maxVelocity.getY());
		} else if (velocity.y < -maxVelocity.y) {
			velocity.setY(-maxVelocity.getY());
		}

		if (velocity.z > maxVelocity.z) {
			velocity.setZ(maxVelocity.getZ());
		} else if (velocity.z < -maxVelocity.z) {
			velocity.setZ(-maxVelocity.getZ());
		}
		if (velocity.x > 0) {
			velocity.setX(velocity.x - velocity.x * Time.deltaTime / 1000f
					* getFriction());
		} else if (velocity.x < 0) {
			velocity.setX(velocity.x + Math.abs(velocity.x) * Time.deltaTime
					/ 1000f * getFriction());
		} else {
			velocity.setX(0);
		}
		if (status.isFlying()) {
			if (velocity.y > 0) {
				velocity.y -= velocity.y * Time.deltaTime / 1000f
						* getFriction();
			} else if (velocity.y < 0) {
				velocity.y += Math.abs(velocity.y) * Time.deltaTime / 1000f
						* getFriction();
			} else {
				velocity.setY(0);
			}
		}
		if (velocity.z > 0) {
			velocity.setZ(velocity.z - velocity.z * Time.deltaTime / 1000f
					* getFriction());
		} else if (velocity.z < 0) {
			velocity.setZ(velocity.z + Math.abs(velocity.z) * Time.deltaTime
					/ 1000f * getFriction());
		} else {
			velocity.setZ(0);
		}
	}

	public void gravi() {
		velocity.setY(velocity.getY() - 9.81f * Time.deltaTime / 1000f);
	}

	public void updateCollider() {
		collider.setPosition(position.getX() + width, position.getY() + height,
				position.getZ() + length);
	}

	public void updateStatus() {

	}
}
