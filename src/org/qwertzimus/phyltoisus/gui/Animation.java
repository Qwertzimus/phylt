package org.qwertzimus.phyltoisus.gui;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.opengl.Texture;

public class Animation {
	public List<Texture> frame;
	public List<Integer> duration;
	public GifDecoder g;
	public boolean isReady;
	public boolean isLoaded;
	public int size;

	public Animation() {
		g = new GifDecoder();
		frame = new ArrayList<Texture>();
		duration = new ArrayList<Integer>();
		isReady = false;
		isLoaded = false;
		size = -1;
	}

	public synchronized void loadAnimation() {
		boolean b = false;
		for (int i = 0; i < size; i++) {
			addFrame(Textures.getTexture(g.getFrame(i)));
			addDuration(g.getDelay(i));
		}
		isLoaded = true;
	}

	public synchronized void addFrame(Texture f) {
		frame.add(f);
	}

	public synchronized void addDuration(int d) {
		duration.add(d);
	}

	public void prepareAnimation(String path) {
		isLoaded=false;
		System.out.println(g.read(path));
		size = g.getFrameCount();

		isReady = true;
	}

	public Texture getFrame(int i) {
		return frame.get(i);
	}

	public int getDuration(int i) {
		return duration.get(i);
	}

	public int size() {
		return size;
	}
}
