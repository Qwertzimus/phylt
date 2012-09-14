package org.qwertzimus.phyltoisus.physic;

import org.lwjgl.Sys;

public class Time {
	public static long lastFrame;
	public static long lastFPS=getTime();
	public static int fps;
	public static int deltaTime;

	/*public static int getDelta() {
		/*long time = getTime();
		int delta = (int) (time - lastFrame);
		//lastFrame = time;
		
		return deltaTime;
	}*/



	public static long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}



	public static void updateFPS() {
		deltaTime=(int)(getTime()-lastFrame);
		if (getTime() - lastFPS > 1000) {
			// Display.setTitle("FPS: " + fps);
			System.out.println("FPS: " + fps);
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
		lastFrame=getTime();
	}
}
