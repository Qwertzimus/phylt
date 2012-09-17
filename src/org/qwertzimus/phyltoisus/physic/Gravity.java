package org.qwertzimus.phyltoisus.physic;

import org.lwjgl.util.vector.Vector2f;

public class Gravity extends Force {
	private Gravity() { // Makes sure it's not accessible from the outside
		super(new Vector2f(0, -9.82f), true);
	}
	
	private static Gravity gravity = null;
	public static Gravity getGravity() {
		if (gravity == null)
			gravity = new Gravity();
		return gravity;
	}
}
