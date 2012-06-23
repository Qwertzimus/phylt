package Geometry;

import org.lwjgl.util.vector.*;
import java.util.*;

public class Prefab {
	List<Geometry> geometry;

	public Prefab() {
		geometry = new ArrayList<Geometry>();
	}

	public Prefab addGeometry(Geometry geo) {
		geometry.add(geo);
		geo.setParent(this);
		return this;
	}

	public Geometry getGeometry(int i) {
		return geometry.get(i);
	}

	public void removeGeometry(int i) {
		geometry.remove(i);
	}

	public void removeGeometry(Geometry geo) {
		geometry.remove(geo);
	}

	public void draw() {
		for (int i = 0; i < geometry.size(); i++) {
			geometry.get(i).draw();
		}
	}
}
