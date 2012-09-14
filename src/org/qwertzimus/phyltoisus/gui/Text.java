package org.qwertzimus.phyltoisus.gui;

/**
 * Beschreiben Sie hier die Klasse Text.
 * 
 * @author Sascha
 * @version 0.01
 */
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.*;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class Text {
	public static UnicodeFont font;

	public static void setupFont(int size) {
		java.awt.Font awtFont = new java.awt.Font("Times New Roman",
				java.awt.Font.BOLD, size);
		font = new UnicodeFont(awtFont);
		font.getEffects().add(new ColorEffect(java.awt.Color.white));
		font.addAsciiGlyphs();
		try {
			font.loadGlyphs();
		} catch (Exception ex) {
			System.out.println(ex);
		}
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	public static void setSize(int size) {
		font.setGlyphPageHeight(size);
		font.setGlyphPageWidth(size);
	}

	public static void drawTextOnDisplay(String s) {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluOrtho2D(0f, Display.getWidth(), Display.getHeight(), 0f);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();

		font.drawString(0, 0, s);

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluOrtho2D(0.0f, (float) Display.getWidth(), (float) 0,
				Display.getHeight());
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	public static void drawTextOnDisplay(String s, int x, int y) {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		GLU.gluOrtho2D(0f, Display.getWidth(), Display.getHeight(), 0f);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		font.drawString(x, y, s);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPopMatrix();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	public static void drawTextOnDisplay(String s, int size) {
		setSize(size);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		GLU.gluOrtho2D(0f, Display.getWidth(), Display.getHeight(), 0f);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();

		font.drawString(0, 0, s);

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPopMatrix();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	public static void drawTextOnDisplay(String s, int x, int y, int size) {
		setSize(size);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		GLU.gluOrtho2D(0f, Display.getWidth(), Display.getHeight(), 0f);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();

		font.drawString(x, Display.getHeight() - y - size, s);

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPopMatrix();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	public static void drawTextOnDisplay(String s, int x, int y, int offsetX,
			int offsetY, int size) {
		setSize(size);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		GLU.gluOrtho2D(0f, Display.getWidth(), Display.getHeight(), 0f);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glDisable(GL11.GL_LIGHTING);
		font.drawString(x - offsetX, Display.getHeight() - y - size - offsetY,
				s);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPopMatrix();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
}
