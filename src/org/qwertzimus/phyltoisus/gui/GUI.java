package org.qwertzimus.phyltoisus.gui;

import java.util.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.AWTGLCanvas;
import org.lwjgl.opengl.Display;

public class GUI {
	public static List<GUIElement> guiElements = new ArrayList<GUIElement>();
	public static GUIElement lastSelectedGUIElement;
	public static GUIElement selectedGUIElement;

	public static void updateStats() {
		for (GUIElement guiE : guiElements) {
			if (guiE.isInForeground()) {
				if (guiE.getPosition().x <= Mouse.getX()
						&& guiE.getPosition().x + guiE.getDimension().x > Mouse
								.getX()
						&& guiE.getPosition().y <= Mouse.getY()
						&& guiE.getPosition().y + guiE.getDimension().y > Mouse
								.getY()) {
					guiE.setMouseOver(true);
					if (Mouse.isButtonDown(0)) {
						if (selectedGUIElement == null) {
							guiE.setClicked(true);
							selectedGUIElement = guiE;
							lastSelectedGUIElement = guiE;
						} else {

							if (!guiE.equals(selectedGUIElement)) {
								guiE.setClicked(false);
							}
						}
					} else {
						guiE.setClicked(false);
					}
				} else {
					guiE.setMouseOver(false);
					// guiE.setClicked(false);
				}
			}
		}
		if (selectedGUIElement != null) {
			if (!Mouse.isButtonDown(0)) {
				selectedGUIElement.setClicked(false);
				selectedGUIElement = null;
			}
		}
	}
}
