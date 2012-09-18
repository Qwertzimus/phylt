package org.qwertzimus.phyltoisus.base;

import org.lwjgl.util.vector.Vector2f;
import org.qwertzimus.phyltoisus.world.Block;

public interface LightSource {
	void setLightIntensity(float lightIntensity);
	float getLightIntensity();
	void setPosition(float x,float y);
	void setPosition(Vector2f position);
	Vector2f getPosition();
	void updateBlocks();
	void updateAffectedBlocks();
	int getRange();
	void setRange(int range);
	boolean hasChanged();
	boolean forceUpdate();
	void setForceUpdate(boolean forceUpdate);
	void setHasChanged(boolean hasChanged);
	float getLightValue(Block b);
	void addBlock(Block b);
	void removeBlock(Block b);
}
