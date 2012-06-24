package Mesh;

import java.util.*;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.*;

public class Mesh {
	public String name;
	public List<Vector3f> vertices = new ArrayList<Vector3f>();
	public List<Vector3f> normals = new ArrayList<Vector3f>();
	public List<Face> faces = new ArrayList<Face>();
	public Skeleton skeleton;
	public int displayListId = -1;
	public boolean isFinished;

	public Mesh() {
		skeleton = new Skeleton();
	}

	public List<Vector3f> getVertices() {
		return vertices;
	}

	public void setVertices(List<Vector3f> vertices) {
		this.vertices = vertices;
	}

	public List<Vector3f> getNormals() {
		return normals;
	}

	public void setNormals(List<Vector3f> normals) {
		this.normals = normals;
	}

	public List<Face> getFaces() {
		return faces;
	}

	public void setFaces(List<Face> faces) {
		this.faces = faces;
	}

	public Skeleton getSkeleton() {
		return skeleton;
	}

	public void setSkeleton(Skeleton skeleton) {
		this.skeleton = skeleton;
	}

	public void updateDisplayList() {
		isFinished = false;
		if (displayListId != -1) {
			GL11.glDeleteLists(displayListId, 1);
		}
		displayListId = GL11.glGenLists(1);
		GL11.glNewList(displayListId, GL11.GL_COMPILE);
		GL11.glBegin(GL11.GL_TRIANGLES);
		for (Face face : faces) {
			Vector3f n1 = normals.get((int) face.normal.x - 1);
			Vector3f v1 = vertices.get((int) face.vertex.x - 1);
			GL11.glNormal3f(n1.x, n1.y, n1.z);
			GL11.glVertex3f(v1.x, v1.y, v1.z);
			Vector3f n2 = normals.get((int) face.normal.y - 1);
			Vector3f v2 = vertices.get((int) face.vertex.y - 1);
			GL11.glNormal3f(n2.x, n2.y, n2.z);
			GL11.glVertex3f(v2.x, v2.y, v2.z);
			Vector3f n3 = normals.get((int) face.normal.z - 1);
			Vector3f v3 = vertices.get((int) face.vertex.z - 1);
			GL11.glNormal3f(n3.x, n3.y, n3.z);
			GL11.glVertex3f(v3.x, v3.y, v3.z);
		}
		GL11.glEnd();
		GL11.glEndList();
		isFinished = true;
	}

	public void render(Vector3f position,Vector3f rotation,Vector3f scale) {
		if (isFinished) {
			GL11.glPushMatrix();
			GL11.glRotatef(rotation.x, 1f, 0f, 0f);
			GL11.glRotatef(rotation.y, 0f, 1f, 0f);
			GL11.glRotatef(rotation.z, 0f, 0f, 1f);
			GL11.glTranslatef(position.x, position.y, position.z);
			GL11.glScalef(scale.x, scale.y, scale.z);
			GL11.glCallList(displayListId);
			GL11.glPopMatrix();
		}else{
			System.out.println("not finished");
		}
		
	}
}
