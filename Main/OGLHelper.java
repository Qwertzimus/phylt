package Main;

import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.GLU;

import java.nio.*;
import org.lwjgl.BufferUtils;

public class OGLHelper {

	public static FloatBuffer projectionMatrixSave;
	public static FloatBuffer modelViewMatrixSave;

	public static void renderTextures2D(boolean b) {
		if (b) {
			GL11.glEnable(GL11.GL_TEXTURE_2D);
		} else {
			GL11.glDisable(GL11.GL_TEXTURE_2D);
		}
	}

	public static void renderLight(boolean b) {
		if (b) {
			GL11.glEnable(GL11.GL_LIGHTING);
		} else {
			GL11.glDisable(GL11.GL_LIGHTING);
		}
	}

	public static void renderColorMaterial(boolean b) {
		if (b) {
			GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		} else {
			GL11.glDisable(GL11.GL_COLOR_MATERIAL);
		}
	}

	public static void storeProjectionMatrix() {
		try {
			projectionMatrixSave.clear();
		} catch (Exception e) {
			projectionMatrixSave = BufferUtils.createFloatBuffer(16);
		}
		GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projectionMatrixSave);
		projectionMatrixSave.rewind();
	}

	public static void restoreProjectionMatrix() {
		// GL11.gl
	}

	public static void storeModelViewMatrix() {
		try {
			modelViewMatrixSave.clear();
		} catch (Exception e) {
			modelViewMatrixSave = BufferUtils.createFloatBuffer(16);
		}
		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelViewMatrixSave);
		modelViewMatrixSave.rewind();
	}

	public static void restoreModelViewMatrix() {
		GL11.glRenderMode(GL11.GL_MODELVIEW);
		GL11.glPopMatrix();
	}

	public static void storeMatrix() {
		GL11.glPushMatrix();
	}

	public static void restoreMatrix() {
		GL11.glPopMatrix();
	}

	public static void changeRenderMode2D() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
	}

	public static void changeRenderMode3D() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluPerspective(90f,
				(float) (Display.getWidth() / Display.getHeight()), 0.01f, 100f);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
	}
}
