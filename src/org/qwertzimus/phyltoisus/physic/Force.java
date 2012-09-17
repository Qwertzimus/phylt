package org.qwertzimus.phyltoisus.physic;

import org.lwjgl.util.vector.Vector2f;

public class Force {
	private Vector2f direction;
	private boolean enabled;
	
	public Force(Vector2f direction, boolean enabled) {
		this.direction = direction;
		this.enabled = enabled;
	}
	
	/**
	 * Returns the direction of the force.
	 * The vector is relative to the position of the entity it's attached to.
	 * @return	Returns the direction as Vector2f
	 */
	public Vector2f getDirection() {
		return this.direction;
	}
	
	/**
	 * Sets the direction of the force.
	 * The vector has to be relative to the position of the entity it's attached to.
	 * @param direction	Vector2f for the direction
	 */
	public void setDirection(Vector2f direction) {
		this.direction = direction;
	}
	
	/**
	 * 
	 * @param radians	The direction it points to.
	 * @param power		Sets the power for the force. It's also know as the relative distance from the vector to the entity. 
	 */
	public void setDirection(float radians, float power) {
		this.direction = getVector2fByRadians(radians, power);
	}
	
	/**
	 * Checks if the force is enabled.
	 * @return	Returns a boolean with the value true if it's enabled or false if not.
	 */
	public boolean isEnabled() {
		return this.enabled;
	}
	
	/**
	 * Sets the force to be either enabled or disabled.
	 * @param enable	Boolean with the value true if the force is to be enabled or false if not to be enabled.
	 */
	public void setEnabled(boolean enable) {
		this.enabled = enable;
	}
	
	/**
	 * Tool to calculate radians to Vector2f 
	 * @param radians	The angle which will be converted to x and y.
	 * @param scale		The distance the vector have from the start point (use 1).
	 * @return
	 */
	public static Vector2f getVector2fByRadians(float radians, float distance) {
		float x = (float) Math.cos(radians) * distance;
		float y = (float) Math.sin(radians) * distance;
		return new Vector2f(x, y);
	}
}
