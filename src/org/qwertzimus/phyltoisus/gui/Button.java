package org.qwertzimus.phyltoisus.gui;

import org.lwjgl.util.vector.Vector2f;

public class Button implements GUIElement{
	public GUIElement parent;
	public GUIElement child;
	public Vector2f position;
	public Vector2f dimension;
	boolean isMouseOver;
	boolean isClicked;
	int textureId;
	public Button(){
		parent=null;
		child=null;
		position=new Vector2f();
		dimension=new Vector2f();
		isMouseOver=false;
		isClicked=false;
	}
	@Override
	public void setPosition(Vector2f position) {
		this.position=position;
		
	}

	@Override
	public void setPosition(int x, int y) {
		position.set(x,y);
		
	}

	@Override
	public void setDimension(Vector2f dimension) {
		this.dimension=dimension;
		
	}

	@Override
	public void setDimension(int x, int y) {
		dimension.set(x,y);
		
	}

	@Override
	public Vector2f getPosition() {
		return position;
	}

	@Override
	public Vector2f getDimension() {
		return dimension;
	}

	@Override
	public void setMouseOver(boolean isMouseOver) {
		this.isMouseOver=isMouseOver;
	}

	@Override
	public boolean isMouseOver() {
		return isMouseOver;
	}

	@Override
	public void setClicked(boolean isClicked) {
		this.isClicked=isClicked;
	}

	@Override
	public boolean isClicked() {
		return isClicked;
	}
	@Override
	public int getTextureId() {
		return textureId;
	}
	@Override
	public void setTextureId(int id) {
		textureId=id;
	}
	@Override
	public void setParent(GUIElement parent) {
		this.parent=parent;
		
	}
	@Override
	public GUIElement getParent() {
		return parent;
	}
	@Override
	public void setChild(GUIElement child) {
		this.child=child;
	}
	@Override
	public GUIElement getChild() {
		return child;
	}
	@Override
	public void draw() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setInForeground(boolean inForeground) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean isInForeground() {
		// TODO Auto-generated method stub
		return false;
	}

}
