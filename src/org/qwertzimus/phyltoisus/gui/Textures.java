package org.qwertzimus.phyltoisus.gui;

/**
 * Beschreiben Sie hier die Klasse TextureLoader.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.BufferedImageUtil;
import org.newdawn.slick.util.ResourceLoader;

public class Textures {
	public static boolean canModify;
	public static HashMap<String, HashMap<String, Animation>> animations;
	public static List<Animation> animationsToUpdate = new ArrayList<Animation>();
	public static Texture texture[][], texture1, texture2, slotTexture,
			slotTextureMarked,slotTextureLocked;
	public static float[][] tBlock, tItem;

	public static void loadTextureCoordinates() {
		tBlock = new float[256][8];
		// air
		tBlock[0] = new float[] { (0.0f + 13.0f) / 16.0f,
				(1.0f + 15.0f) / 16.0f, (0.0f + 13.0f) / 16.0f,
				(0.0f + 15.0f) / 16.0f, (1.0f + 13.0f) / 16.0f,
				(0.0f + 15.0f) / 16.0f, (1.0f + 13.0f) / 16.0f,
				(1.0f + 15.0f) / 16.0f };
		// stone2
		tBlock[1] = new float[] { (0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (0.0f + 0.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f, (0.0f + 0.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f };
		// stone1
		tBlock[2] = new float[] { (0.0f + 1.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(0.0f + 1.0f) / 16.0f, (0.0f + 0.0f) / 16.0f,
				(1.0f + 1.0f) / 16.0f, (0.0f + 0.0f) / 16.0f,
				(1.0f + 1.0f) / 16.0f, (1.0f + 0.0f) / 16.0f };
		// dirt1
		tBlock[3] = new float[] { (0.0f + 2.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(0.0f + 2.0f) / 16.0f, (0.0f + 0.0f) / 16.0f,
				(1.0f + 2.0f) / 16.0f, (0.0f + 0.0f) / 16.0f,
				(1.0f + 2.0f) / 16.0f, (1.0f + 0.0f) / 16.0f };
		// wooden planks
		tBlock[4] = new float[] { (0.0f + 4.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(0.0f + 4.0f) / 16.0f, (0.0f + 0.0f) / 16.0f,
				(1.0f + 4.0f) / 16.0f, (0.0f + 0.0f) / 16.0f,
				(1.0f + 4.0f) / 16.0f, (1.0f + 0.0f) / 16.0f };
		// stone3
		tBlock[5] = new float[] { (0.0f + 0.0f) / 16.0f, (1.0f + 1.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (0.0f + 1.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f, (0.0f + 1.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f, (1.0f + 1.0f) / 16.0f };
		// sand1
		tBlock[6] = new float[] { (0.0f + 2.0f) / 16.0f, (1.0f + 1.0f) / 16.0f,
				(0.0f + 2.0f) / 16.0f, (0.0f + 1.0f) / 16.0f,
				(1.0f + 2.0f) / 16.0f, (0.0f + 1.0f) / 16.0f,
				(1.0f + 2.0f) / 16.0f, (1.0f + 1.0f) / 16.0f };
		// sandstone
		tBlock[7] = new float[] { (0.0f + 0.0f) / 16.0f,
				(1.0f + 12.0f) / 16.0f, (0.0f + 0.0f) / 16.0f,
				(0.0f + 12.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(0.0f + 12.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(1.0f + 12.0f) / 16.0f };
		// torch
		tBlock[8] = new float[] { (0.0f + 0.0f) / 16.0f, (1.0f + 5.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (0.0f + 5.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f, (0.0f + 5.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f, (1.0f + 5.0f) / 16.0f };
		// snow
		tBlock[9] = new float[] { (0.0f + 1.0f) / 16.0f, (1.0f + 3.0f) / 16.0f,
				(0.0f + 1.0f) / 16.0f, (0.0f + 3.0f) / 16.0f,
				(1.0f + 1.0f) / 16.0f, (0.0f + 3.0f) / 16.0f,
				(1.0f + 1.0f) / 16.0f, (1.0f + 3.0f) / 16.0f };
		// wool white
		tBlock[10] = new float[] { (0.0f + 0.0f) / 16.0f,
				(1.0f + 4.0f) / 16.0f, (0.0f + 0.0f) / 16.0f,
				(0.0f + 4.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(0.0f + 4.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(1.0f + 4.0f) / 16.0f };
		// obsidian
		tBlock[11] = new float[] { (0.0f + 5.0f) / 16.0f,
				(1.0f + 2.0f) / 16.0f, (0.0f + 5.0f) / 16.0f,
				(0.0f + 2.0f) / 16.0f, (1.0f + 5.0f) / 16.0f,
				(0.0f + 2.0f) / 16.0f, (1.0f + 5.0f) / 16.0f,
				(1.0f + 2.0f) / 16.0f };
		// ore gold
		tBlock[12] = new float[] { (0.0f + 0.0f) / 16.0f,
				(1.0f + 2.0f) / 16.0f, (0.0f + 0.0f) / 16.0f,
				(0.0f + 2.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(0.0f + 2.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(1.0f + 2.0f) / 16.0f };
		// ore iron
		tBlock[13] = new float[] { (0.0f + 1.0f) / 16.0f,
				(1.0f + 2.0f) / 16.0f, (0.0f + 1.0f) / 16.0f,
				(0.0f + 2.0f) / 16.0f, (1.0f + 1.0f) / 16.0f,
				(0.0f + 2.0f) / 16.0f, (1.0f + 1.0f) / 16.0f,
				(1.0f + 2.0f) / 16.0f };
		// ore coal
		tBlock[14] = new float[] { (0.0f + 2.0f) / 16.0f,
				(1.0f + 2.0f) / 16.0f, (0.0f + 2.0f) / 16.0f,
				(0.0f + 2.0f) / 16.0f, (1.0f + 2.0f) / 16.0f,
				(0.0f + 2.0f) / 16.0f, (1.0f + 2.0f) / 16.0f,
				(1.0f + 2.0f) / 16.0f };
		// stone4
		tBlock[15] = new float[] { (0.0f + 6.0f) / 16.0f,
				(1.0f + 3.0f) / 16.0f, (0.0f + 6.0f) / 16.0f,
				(0.0f + 3.0f) / 16.0f, (1.0f + 6.0f) / 16.0f,
				(0.0f + 3.0f) / 16.0f, (1.0f + 6.0f) / 16.0f,
				(1.0f + 3.0f) / 16.0f };
		// wood
		tBlock[16] = new float[] { (0.0f + 4.0f) / 16.0f,
				(1.0f + 1.0f) / 16.0f, (0.0f + 4.0f) / 16.0f,
				(0.0f + 1.0f) / 16.0f, (1.0f + 4.0f) / 16.0f,
				(0.0f + 1.0f) / 16.0f, (1.0f + 4.0f) / 16.0f,
				(1.0f + 1.0f) / 16.0f };
		// cactus
		tBlock[17] = new float[] { (0.0f + 6.0f) / 16.0f,
				(1.0f + 4.0f) / 16.0f, (0.0f + 6.0f) / 16.0f,
				(0.0f + 4.0f) / 16.0f, (1.0f + 6.0f) / 16.0f,
				(0.0f + 4.0f) / 16.0f, (1.0f + 6.0f) / 16.0f,
				(1.0f + 4.0f) / 16.0f };
		// ore Diamond
		tBlock[18] = new float[] { (0.0f + 2.0f) / 16.0f,
				(1.0f + 3.0f) / 16.0f, (0.0f + 2.0f) / 16.0f,
				(0.0f + 3.0f) / 16.0f, (1.0f + 2.0f) / 16.0f,
				(0.0f + 3.0f) / 16.0f, (1.0f + 2.0f) / 16.0f,
				(1.0f + 3.0f) / 16.0f };
		// ore Wire
		tBlock[19] = new float[] { (0.0f + 3.0f) / 16.0f,
				(1.0f + 3.0f) / 16.0f, (0.0f + 3.0f) / 16.0f,
				(0.0f + 3.0f) / 16.0f, (1.0f + 3.0f) / 16.0f,
				(0.0f + 3.0f) / 16.0f, (1.0f + 3.0f) / 16.0f,
				(1.0f + 3.0f) / 16.0f };
		// ore blue
		tBlock[20] = new float[] { (0.0f + 10.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f, (0.0f + 10.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 10.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 10.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f };
		// wool white
		tBlock[21] = new float[] { (0.0f + 5.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f, (0.0f + 5.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 5.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 5.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f };
		// wool red
		tBlock[22] = new float[] { (0.0f + 8.0f) / 16.0f,
				(1.0f + 1.0f) / 16.0f, (0.0f + 8.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f };
		// wool dark green
		tBlock[23] = new float[] { (0.0f + 9.0f) / 16.0f,
				(1.0f + 1.0f) / 16.0f, (0.0f + 9.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f };
		// wool brown
		tBlock[24] = new float[] { (0.0f + 10.0f) / 16.0f,
				(1.0f + 1.0f) / 16.0f, (0.0f + 10.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f };
		// wool dark blue
		tBlock[25] = new float[] { (0.0f + 11.0f) / 16.0f,
				(1.0f + 1.0f) / 16.0f, (0.0f + 11.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f };
		// wool dark purple
		tBlock[26] = new float[] { (0.0f + 12.0f) / 16.0f,
				(1.0f + 1.0f) / 16.0f, (0.0f + 12.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f };
		// wool cyan blue
		tBlock[27] = new float[] { (0.0f + 13.0f) / 16.0f,
				(1.0f + 1.0f) / 16.0f, (0.0f + 13.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f };
		// wool light blue
		tBlock[28] = new float[] { (0.0f + 14.0f) / 16.0f,
				(1.0f + 1.0f) / 16.0f, (0.0f + 14.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f };
		// wool grey
		tBlock[29] = new float[] { (0.0f + 15.0f) / 16.0f,
				(1.0f + 1.0f) / 16.0f, (0.0f + 15.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f };
		// wool pink
		tBlock[30] = new float[] { (0.0f + 8.0f) / 16.0f,
				(1.0f + 2.0f) / 16.0f, (0.0f + 8.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f };
		// wool light green
		tBlock[31] = new float[] { (0.0f + 9.0f) / 16.0f,
				(1.0f + 2.0f) / 16.0f, (0.0f + 9.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f };
		// wool yellow
		tBlock[32] = new float[] { (0.0f + 10.0f) / 16.0f,
				(1.0f + 2.0f) / 16.0f, (0.0f + 10.0f) / 16.0f,
				(0.0f + 2.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f };
		// wool light blue
		tBlock[33] = new float[] { (0.0f + 11.0f) / 16.0f,
				(1.0f + 2.0f) / 16.0f, (0.0f + 11.0f) / 16.0f,
				(0.0f + 2.0f) / 16.0f, (1.0f + 11.0f) / 16.0f,
				(0.0f + 2.0f) / 16.0f, (1.0f + 11.0f) / 16.0f,
				(1.0f + 2.0f) / 16.0f };
		// wool light purple
		tBlock[34] = new float[] { (0.0f + 12.0f) / 16.0f,
				(1.0f + 2.0f) / 16.0f, (0.0f + 12.0f) / 16.0f,
				(0.0f + 2.0f) / 16.0f, (1.0f + 12.0f) / 16.0f,
				(0.0f + 2.0f) / 16.0f, (1.0f + 12.0f) / 16.0f,
				(1.0f + 2.0f) / 16.0f };
		// wool orange
		tBlock[35] = new float[] { (0.0f + 13.0f) / 16.0f,
				(1.0f + 2.0f) / 16.0f, (0.0f + 13.0f) / 16.0f,
				(0.0f + 2.0f) / 16.0f, (1.0f + 13.0f) / 16.0f,
				(0.0f + 2.0f) / 16.0f, (1.0f + 13.0f) / 16.0f,
				(1.0f + 2.0f) / 16.0f };
		//
		tBlock[36] = new float[] { (0.0f + 0.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f, (0.0f + 0.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f };
		//
		tBlock[37] = new float[] { (0.0f + 0.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f, (0.0f + 0.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f };
		//
		tBlock[38] = new float[] { (0.0f + 0.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f, (0.0f + 0.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f };
		//
		tBlock[39] = new float[] { (0.0f + 0.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f, (0.0f + 0.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f };
		//
		tBlock[40] = new float[] { (0.0f + 0.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f, (0.0f + 0.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f };
		//
		tBlock[41] = new float[] { (0.0f + 0.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f, (0.0f + 0.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f };
		//
		tBlock[42] = new float[] { (0.0f + 0.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f, (0.0f + 0.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f };
		//
		tBlock[43] = new float[] { (0.0f + 0.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f, (0.0f + 0.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f };
		//
		tBlock[44] = new float[] { (0.0f + 0.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f, (0.0f + 0.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f };
		//
		tBlock[45] = new float[] { (0.0f + 0.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f, (0.0f + 0.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f };
		//
		tBlock[46] = new float[] { (0.0f + 0.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f, (0.0f + 0.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f };
		//
		tBlock[47] = new float[] { (0.0f + 0.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f, (0.0f + 0.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f };
		//
		tBlock[48] = new float[] { (0.0f + 0.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f, (0.0f + 0.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f };
		//
		tBlock[49] = new float[] { (0.0f + 0.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f, (0.0f + 0.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f };
		//
		tBlock[50] = new float[] { (0.0f + 0.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f, (0.0f + 0.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(0.0f + 0.0f) / 16.0f, (1.0f + 0.0f) / 16.0f,
				(1.0f + 0.0f) / 16.0f };
		loadItemTextureCoordinates();
	}

	public static void loadItemTextureCoordinates() {
		int counter = 0;
		tItem = new float[256][8];
		for (int k = 0; k < 16; k++) {
			for (int i = 0; i < 16; i++) {
				tItem[counter] = new float[] { (0.0f + i) / 16.0f,
						(1.0f + 0.0f + k) / 16.0f, (0.0f + 0.0f + i) / 16.0f,
						(0.0f + 0.0f + k) / 16.0f, (1.0f + 0.0f + i) / 16.0f,
						(0.0f + 0.0f + k) / 16.0f, (1.0f + 0.0f + i) / 16.0f,
						(1.0f + 0.0f + k) / 16.0f };
				counter++;
			}

		}
	}

	public static float[] getBlockTextureCoordinatesFromID(int id) {
		switch (id) {
		case 1:
			return tBlock[3];
		case 2:
			return tBlock[2];
		case 3:
			return tBlock[5];
		case 4:
			return tBlock[4];
		case 5:
			return tBlock[16];
		case 6:
			return tBlock[1];
		case 7:
			return tBlock[6];
		case 8:
			return tBlock[14];
		case 9:
			return tBlock[13];
		case 10:
			return tBlock[12];
		case 11:
			return tBlock[18];
		case 12:
			return tBlock[19];
		case 13:
			return tBlock[13];
		case 14:
			return tBlock[8];
		case 15:
			return tBlock[11];
		case 16:
			return tBlock[7];
		case 17:
			return tBlock[15];
		case 18:
			return tBlock[18];
		case 19:
			return tBlock[19];
		case 20:
			return tBlock[20];
		case 21:
			return tBlock[21];
		case 22:
			return tBlock[22];
		case 23:
			return tBlock[23];
		case 24:
			return tBlock[24];
		case 25:
			return tBlock[25];
		case 26:
			return tBlock[26];
		case 27:
			return tBlock[27];
		case 28:
			return tBlock[28];
		case 29:
			return tBlock[29];
		case 30:
			return tBlock[30];
		case 31:
			return tBlock[31];
		}
		return tBlock[0];
	}

	public static float[] getItemTextureCoordinatesFromID(int id) {
		if (id < 256) {
			return getBlockTextureCoordinatesFromID(id);
		} else {

			switch (id) {
			case 256:
				return tItem[1];
			case 257:
				return tItem[2];
			case 258:
				return tItem[3];
			case 259:
				return tItem[4];
			case 260:
				return tItem[5];
			case 261:
				return tItem[6];
			case 262:
				return tItem[7];
			case 263:
				return tItem[8];
			case 264:
				return tItem[9];
			case 265:
				return tItem[10];
			case 266:
				return tItem[11];
			case 267:
				return tItem[12];
			case 268:
				return tItem[13];
			case 269:
				return tItem[14];
			case 270:
				return tItem[15];
			case 271:
				return tItem[16];
			case 272:
				return tItem[17];
			case 273:
				return tItem[18];
			case 274:
				return tItem[19];
			case 275:
				return tItem[20];
			case 276:
				return tItem[21];
			case 277:
				return tItem[22];
			case 278:
				return tItem[23];
			case 279:
				return tItem[24];
			case 280:
				return tItem[25];
			case 281:
				return tItem[26];
			case 282:
				return tItem[27];
			case 283:
				return tItem[28];
			case 284:
				return tBlock[29];
			case 285:
				return tItem[30];
			case 286:
				return tItem[31];
			}
			return null;
		}
	}

	public static void loadTextures() {
		animations = new HashMap<String, HashMap<String, Animation>>();
		texture = new Texture[1][1];
		try {
			texture1 = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("images/World/terrain.png"),
					GL11.GL_LINEAR);
			texture2 = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("images/World/items.png"),
					GL11.GL_LINEAR);
			texture[0][0] = TextureLoader.getTexture("PNG",
					ResourceLoader.getResourceAsStream("images/World/t.png"),
					GL11.GL_LINEAR);
			slotTextureMarked = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("images/World/slotTextureMarked.png"),
					GL11.GL_LINEAR);
			slotTexture=TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("images/World/slotTexture.png"),
					GL11.GL_LINEAR);
			slotTextureLocked=TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("images/World/slotTextureLocked.png"),
					GL11.GL_LINEAR);
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public static Texture getTexture(BufferedImage bufferedImage) {
		try {
			Texture texture = BufferedImageUtil.getTexture("", bufferedImage);
			return texture;
		} catch (Exception e) {
			System.out.println("getTexture: "+e);
			return null;
		}

	}

	public static void loadAnimations(String path) {
		File dir = new File(path);
		String name = "";
		int lastindex = path.split("/").length - 1;
		name = path.split("/")[lastindex];
		HashMap<String, Animation> anims = new HashMap<String, Animation>();

		String[] animationPath = new String[1];

		System.out.println(dir);
		animationPath = dir.list(new FilenameFilter() {
			public boolean accept(File d, String name) {
				return name.endsWith(".gif");

			}
		});
		for (int k = 0; k < animationPath.length; k++) {
			try {
				System.out.println(path + animationPath[k]);
				final Animation a = new Animation();
				final String s = path + animationPath[k];
				
				new Thread() {
					public void run() {
						a.prepareAnimation(s);
//						a.loadAnimation();
					}
				}.start();
				int i = animationPath[k].split("/").length - 1;
				anims.put(animationPath[k].split("/")[i], a);
					
			} catch (Exception ex) {
				System.out.println("Texture Loading Error: " + ex);
			}
		}
		animations.put(name, anims);
	}

	public synchronized static void update(String name, String names[]) {

		for (int i = 0; i < names.length; i++) {
			if (animations.get(name) != null) {
				if (animations.get(name).get(names[i]) != null) {
					
					if (!animations.get(name).get(names[i]).isLoaded) {
						System.out.println("blub");
						animations.get(name).get(names[i]).loadAnimation();

					}
				}
			}
		}
	}

	public synchronized static boolean addAnimationToUpdate(Animation a) {
		return animationsToUpdate.add(a);
	}
}
