package Mesh;

import java.io.*;
import java.util.*;
import org.lwjgl.util.vector.*;

public class OBJLoader {

	public static Mesh loadMesh(File f) throws IOException,
			FileNotFoundException {
		Mesh mesh = new Mesh();
		BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
		String line;
		List<Face> faces = new ArrayList<Face>();
		List<Vector3f> vertices = new ArrayList<Vector3f>();
		List<Vector3f> normals = new ArrayList<Vector3f>();

		while ((line = bufferedReader.readLine()) != null) {
			if (line.startsWith("v ")) {
				Vector3f vertex = new Vector3f();
				vertex.x = Float.valueOf(line.split("v ")[1].split(" ")[0]);
				vertex.y = Float.valueOf(line.split("v ")[1].split(" ")[1]);
				vertex.z = Float.valueOf(line.split("v ")[1].split(" ")[2]);
				vertices.add(vertex);
			} else if (line.startsWith("vn ")) {
				System.out.println("0");
				Vector3f normal = new Vector3f();
				normal.x = Float.valueOf(line.split("vn ")[1].split(" ")[0]);
				normal.y = Float.valueOf(line.split("vn ")[1].split(" ")[1]);
				normal.z = Float.valueOf(line.split("vn ")[1].split(" ")[2]);
				normals.add(normal);
				System.out.println("1");

				System.out.println("2");
			} else if (line.startsWith("f ")) {
				Face face = new Face();
				face.vertex.x = Integer
						.valueOf((line.split("f ")[1].split(" ")[0].split("/")[0]));
				face.vertex.y = Integer
						.valueOf((line.split("f ")[1].split(" ")[1].split("/")[0]));
				face.vertex.z = Integer
						.valueOf((line.split("f ")[1].split(" ")[2].split("/")[0]));

				face.normal.x = Integer
						.valueOf((line.split("f ")[1].split(" ")[0].split("/")[2]));
				face.normal.y = Integer
						.valueOf((line.split("f ")[1].split(" ")[1].split("/")[2]));
				face.normal.z = Integer
						.valueOf((line.split("f ")[1].split(" ")[2].split("/")[2]));

				faces.add(face);
			}
		}
		mesh.vertices = vertices;
		mesh.normals = normals;
		mesh.faces = faces;
		System.out.println("loaded");
		return mesh;
	}
}
