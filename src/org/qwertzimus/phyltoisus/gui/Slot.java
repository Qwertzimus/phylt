package org.qwertzimus.phyltoisus.gui;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

public class Slot implements GUIElement {
	protected Vector2f position, dimension;
	protected GUIElement parent;
	protected GUIElement child;
	protected boolean isMouseOver, isClicked, inForeground;
	protected int textureId;

	public Slot() {
		position = new Vector2f();
		dimension = new Vector2f();
		textureId = Textures.slotTexture.getTextureID();
	}

	@Override
	public void setParent(GUIElement parent) {
		this.parent = parent;
	}

	@Override
	public GUIElement getParent() {
		return parent;
	}

	@Override
	public void setChild(GUIElement child) {
		this.child = child;
	}

	@Override
	public GUIElement getChild() {
		return child;
	}

	@Override
	public void setPosition(Vector2f position) {
		this.position.set(position);
	}

	@Override
	public void setPosition(int x, int y) {
		this.position.set(x, y);
	}

	@Override
	public void setDimension(Vector2f dimension) {
		this.dimension.set(dimension);
	}

	@Override
	public void setDimension(int x, int y) {
		this.dimension.set(x, y);
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
		this.isMouseOver = isMouseOver;
	}

	@Override
	public boolean isMouseOver() {
		return isMouseOver;
	}

	@Override
	public void setClicked(boolean isClicked) {
		this.isClicked = isClicked;
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
		this.textureId = id;
	}

	public void register() {
		GUI.guiElements.add(this);
	}

	public void draw() {
		if (isInForeground()) {
			GL11.glPushMatrix();
			if (!isClicked) {
				textureId = (isMouseOver) ? Textures.slotTextureMarked
						.getTextureID() : Textures.slotTexture.getTextureID();
			}else{
				textureId=Textures.slotTextureLocked.getTextureID();
			}
			System.out.println(textureId);
			// GL11.glColor3f(1f, 0f, 0f);
			GL11.glLoadIdentity();
			if (parent != null) {
				GL11.glTranslatef(parent.getPosition().x, getPosition().y, 0);
			}
			GL11.glTranslatef(position.x, position.y, 0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);

			GL11.glTexParameteri(GL11.GL_TEXTURE_2D,
					GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D,
					GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0f, 1f);
			GL11.glVertex2f(0f, dimension.y);
			GL11.glTexCoord2f(0f, 0f);
			GL11.glVertex2f(0f, 0f);
			GL11.glTexCoord2f(1f, 0f);
			GL11.glVertex2f(dimension.x, 0f);
			GL11.glTexCoord2f(1f, 1f);
			GL11.glVertex2f(dimension.x, dimension.y);
			GL11.glEnd();
			GL11.glPopMatrix();
		}
	}

	@Override
	public void setInForeground(boolean inForeground) {
		this.inForeground = inForeground;
	}

	@Override
	public boolean isInForeground() {
		return inForeground;
	}

}
