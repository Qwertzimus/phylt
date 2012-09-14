package org.qwertzimus.phyltoisus.gui;

import org.lwjgl.util.vector.*;
public interface GUIElement {
	void setParent(GUIElement parent);
	GUIElement getParent();
	void setChild(GUIElement child);
	GUIElement getChild();
	void setPosition(Vector2f position);
	void setPosition(int x,int y);
	void setDimension(Vector2f dimension);
	void setDimension(int x,int y);
	Vector2f getPosition();
	Vector2f getDimension();
	void setInForeground(boolean inForeground);
	boolean isInForeground();
	void setMouseOver(boolean isMouseOver);
	boolean isMouseOver();
	void setClicked(boolean isClicked);
	boolean isClicked();
	int getTextureId();
	void setTextureId(int id);
	void draw();
}
