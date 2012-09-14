package org.qwertzimus.phyltoisus.base;

import org.lwjgl.util.vector.Vector2f;

public interface LightSource {
	void setLightIntensity(float lightIntensity);
	float getLightIntensity();
	void setPosition(float x,float y);
	void setPosition(Vector2f position);
	Vector2f getPosition();
	void updateBlocks();
	int getRange();
	void setRange(int range);
	boolean hasChanged();
	boolean forceUpdate();
	void setForceUpdate(boolean forceUpdate);
	void setHasChanged(boolean hasChanged);
}
