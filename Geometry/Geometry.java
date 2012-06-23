package Geometry;

import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.*;
import java.util.*;


public class Geometry {
	Vector3f position;
	Vector3f rotation;
	Prefab parent;
	List<Vector3f> vertexPositions;
	List<Vector2f> texturePositions;
	List<Vector4f> colorValues;

	public Geometry() {
		position = new Vector3f();
		rotation = new Vector3f();
		vertexPositions = new ArrayList<Vector3f>();
		texturePositions = new ArrayList<Vector2f>();
		colorValues = new ArrayList<Vector4f>();
	}

	public void setPosition(float x, float y, float z) {
		position.set(x, y, z);
		Vector3f nVec3f=new Vector3f();
		for(int i=0;i<vertexPositions.size();i++){
			Vector3f.add(vertexPositions.get(i), new Vector3f(x,y,z), nVec3f);
			vertexPositions.set(i, nVec3f);
		}
	}

	public void setPosition(Vector3f position) {
		this.position = position;
		Vector3f nVec3f=new Vector3f();
		for(int i=0;i<vertexPositions.size();i++){
			Vector3f.add(vertexPositions.get(i), position, nVec3f);
			vertexPositions.set(i, nVec3f);
		}
	}

	public void setPositionWithoutChange(float x, float y, float z) {
		position.set(x, y, z);
	}

	public void setPositionWithoutChange(Vector3f position) {
		this.position = position;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setRotation(float x, float y, float z) {
		rotation.set(x, y, z);
	}

	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public void setParent(Prefab p) {
		parent = p;
	}

	public Prefab getParent() {
		return parent;
	}

	public int addVertexPosition(Vector3f vertexPosition) {
		vertexPositions.add(vertexPosition);
		return vertexPositions.size() - 1;
	}

	public void removeVertexPosition(Vector3f vertexPosition) {
		vertexPositions.remove(vertexPosition);
	}

	public void removeVertexPosition(int i) {
		vertexPositions.remove(i);
	}

	public List<Vector3f> getVertexPositions() {
		return vertexPositions;
	}

	public int addTexturePosition(Vector2f texturePosition) {
		texturePositions.add(texturePosition);
		return texturePositions.size() - 1;
	}

	public void removeTexturePosition(Vector2f texturePosition) {
		texturePositions.remove(texturePosition);
	}

	public void removeTexturePosition(int i) {
		texturePositions.remove(i);
	}

	public List<Vector2f> getTexturePositions() {
		return texturePositions;
	}

	public int addColorValue(Vector4f colorValue) {
		colorValues.add(colorValue);
		return colorValues.size() - 1;
	}

	public void removeColorValue(Vector4f colorValue) {
		colorValues.remove(colorValue);
	}

	public void removeColorValue(int i) {
		colorValues.remove(i);
	}

	public List<Vector4f> getColorValues() {
		return colorValues;
	}

	public void draw() {

	}
}
