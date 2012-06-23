package Physics;

import org.lwjgl.util.vector.*;

public class BoxCollider {
	private Vector3f position;
	private Vector3f size;

	public BoxCollider() {
		position=new Vector3f();
		size=new Vector3f();
	}

	public BoxCollider(Vector3f position, Vector3f size) {
		setPosition(position);
		this.size = new Vector3f(size);
	}

	public boolean intersects(BoxCollider col) {
		return !(maxX() < col.minX() || minX() > col.maxX())
				&& !(maxY() < col.minY() || minY() > col.maxY())
				&& !(maxZ() < col.minZ() || minZ() > col.maxZ());
	}

	public boolean contains(Vector3f point) {
		return !(maxX() < point.x || minX() > point.x)
				&& !(maxY() < point.y || minY() > point.y)
				&& !(maxZ() < point.z || minZ() > point.z);
	}

	public float minX() {
		return (getPosition().x - size.x);
	}

	public float minY() {
		return (getPosition().y - size.y);
	}

	public float minZ() {
		return (getPosition().z - size.z);
	}

	public float maxX() {
		return (getPosition().x + size.x);
	}

	public float maxY() {
		return (getPosition().y + size.y);
	}

	public float maxZ() {
		return (getPosition().z + size.z);
	}

	public Vector3f closestPointOnColliderToPoint(Vector3f p) {
		Vector3f r = new Vector3f(p);
		if (p.x < minX()) {
			r.x = minX();
		}
		if (p.x > maxX()) {
			r.x = maxX();
		}
		if (p.y < minY()) {
			r.y = minY();
		}
		if (p.y > maxY()) {
			r.y = maxY();
		}
		if (p.z < minZ()) {
			r.z = minZ();
		}
		if (p.z > maxZ()) {
			r.z = maxZ();
		}
		return r;
	}

	public Vector3f centerPointForNormal(Vector3f normal) {
		if (normal.x == 1 && normal.y == 0 && normal.z == 0) {
			return new Vector3f(getPosition().x + size.x, getPosition().y,
					getPosition().z);
		}
		if (normal.x == -1 && normal.y == 0 && normal.z == 0) {
			return new Vector3f(getPosition().x - size.x, getPosition().y,
					getPosition().z);
		}
		if (normal.x == 0 && normal.y == 0 && normal.z == 1) {
			return new Vector3f(getPosition().x, getPosition().y,
					getPosition().z + size.z);
		}
		if (normal.x == 0 && normal.y == 0 && normal.z == -1) {
			return new Vector3f(getPosition().x, getPosition().y,
					getPosition().z - size.z);
		}
		if (normal.x == 0 && normal.y == 1 && normal.z == 0) {
			return new Vector3f(getPosition().x, getPosition().y + size.y,
					getPosition().z);
		}
		if (normal.x == 0 && normal.y == -1 && normal.z == 0) {
			return new Vector3f(getPosition().x, getPosition().y - size.y,
					getPosition().z);
		}

		return new Vector3f();
	}

	public Vector3f getSize() {
		return size;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position.set(position);
	}

	public void setPosition(float x, float y, float z) {
		this.position.set(x, y, z);
	}

	public void setSize(Vector3f size) {
		this.size.set(size);
	}

	public void setSize(float x, float y, float z) {
		size.setX(x);
		size.setY(y);
		size.setZ(z);
	}
}
