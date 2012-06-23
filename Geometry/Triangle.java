package Geometry;

import org.lwjgl.util.vector.*;
import org.lwjgl.opengl.*;

public class Triangle extends Geometry {

	public Triangle() {
		super();
	}

	@Override
	public void draw() {
		GL11.glBegin(GL11.GL_TRIANGLES);
		for (int i = 0; i < vertexPositions.size(); i++) {
			GL11.glTexCoord2f(texturePositions.get(i).getX(), texturePositions
					.get(i).getY());
			GL11.glColor4f(colorValues.get(i).getX(),
					colorValues.get(i).getY(), colorValues.get(i).getZ(),
					colorValues.get(i).getW());
			GL11.glVertex3f(vertexPositions.get(i).getX(),
					vertexPositions.get(i).getY(), vertexPositions.get(i)
							.getZ());
		}
		GL11.glEnd();
	}
}
