package Main;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.*;
import java.nio.*;

public class Camera {
	Vector3f position;
	Vector3f rotation;
	IntBuffer viewport;
	IntBuffer selectionBuffer;
	FloatBuffer modelviewMatrix;
	FloatBuffer projectionMatrix;
	FloatBuffer winZ;
	FloatBuffer pos;

	public Camera() {
		position = new Vector3f();
		rotation = new Vector3f();
	}

	public void setPosition(float x, float y, float z) {
		position.set(x, y, z);
	}

	public void setPosition(Vector3f vec3f) {
		position = vec3f;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setRotation(float x, float y, float z) {
		rotation.set(x, y, z);
	}

	public void setRotation(Vector3f vec3f) {
		rotation = vec3f;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public Vector3f getLookAtPosition() {

		try {
			viewport.clear();
			modelviewMatrix.clear();
			projectionMatrix.clear();
			winZ.clear();
			pos.clear();
		} catch (Exception ex) {
			viewport = BufferUtils.createIntBuffer(16);
			modelviewMatrix = BufferUtils.createFloatBuffer(16);
			projectionMatrix = BufferUtils.createFloatBuffer(16);
			winZ = BufferUtils.createFloatBuffer(1);
			pos = BufferUtils.createFloatBuffer(3);
		}
		float winX, winY;

		winX = Display.getWidth() / 2;
		winY = Display.getHeight() / 2;
		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelviewMatrix);
		GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projectionMatrix);
		GL11.glGetInteger(GL11.GL_VIEWPORT, viewport);

		GL11.glReadPixels((int) winX, (int) winY, 1, 1,
				GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, winZ);
		GLU.gluUnProject(winX, winY, winZ.get(0), modelviewMatrix,
				projectionMatrix, viewport, pos);
		viewport.rewind();
		modelviewMatrix.rewind();
		projectionMatrix.rewind();
		winZ.rewind();
		pos.rewind();
		Vector3f point = new Vector3f(pos.get(0), pos.get(1), pos.get(2));
		float length = Vector3f.dot(
				point,
				new Vector3f((float) Math.floor(pos.get(0)), (float) Math
						.floor(pos.get(1)), (float) Math.floor(pos.get(2))));
		if (length <= 0) {
			length = 0.00001f;
		}
		return point;
	}

	
	public void usePerspective() {
		GL11.glLoadIdentity();
		GL11.glRotatef(rotation.getX(), 1f, 0f, 0f);
		GL11.glRotatef(rotation.getY(), 0f, 1f, 0f);
		GL11.glRotatef(rotation.getZ(), 0f, 0f, 1f);
		GL11.glTranslatef(-position.getX(), -position.getY(), -position.getZ());
	}
	
}
