package org.qwertzimus.phyltoisus.base;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.qwertzimus.phyltoisus.gui.GUIElement;
import org.qwertzimus.phyltoisus.gui.Slot;

public class Inventory implements GUIElement{
	List<Slot> slots;
	boolean inForeground;
	public Inventory() {
		slots = new ArrayList<Slot>();
		init(5,5);
	}

	public void init(int lines, int colums) {
		for (int i = 0; i < lines; i++) {
			for (int k = 0; k < colums; k++) {
				Slot s = new Slot();
				s.setPosition(k*60, i*60);
				s.setDimension(40, 40);
				s.register();
				slots.add(s);
			}
		}
	}

	public List<Slot> getSlots() {
		return slots;
	}

	public boolean isInForeground() {
		return inForeground;
	}

	public void setSlots(List<Slot> slots) {
		this.slots = slots;
	}

	public void setInForeground(boolean b){
		for (Slot s : slots) {
			s.setInForeground(b);
		}
		inForeground=b;
	}
	
	public void draw() {
		for (Slot s : slots) {
			s.draw();
		}
	}

	@Override
	public void setParent(GUIElement parent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public GUIElement getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setChild(GUIElement child) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public GUIElement getChild() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPosition(Vector2f position) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPosition(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDimension(Vector2f dimension) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDimension(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Vector2f getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector2f getDimension() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMouseOver(boolean isMouseOver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isMouseOver() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setClicked(boolean isClicked) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isClicked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getTextureId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setTextureId(int id) {
		// TODO Auto-generated method stub
		
	}
}
