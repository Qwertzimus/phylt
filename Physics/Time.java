package Physics;

import org.lwjgl.opengl.Display;

public class Time {
	public static long startTime=getTime();
	public static long lastFrameTime=getTime();
	public static int deltaTime;
	public static int fps=0;
	public static long lastFPSTime=getTime();
	public static void updateDeltaTime(){
		deltaTime=(int)(getTime()-lastFrameTime);
		lastFrameTime=getTime();
	}
	public static long getTime() {
    	return System.nanoTime() / 1000000;
    }
	public static void updateFPS(){
		if(getTime()-lastFPSTime>1000){
			Display.setTitle("FPS: "+fps);
			fps=0;
			lastFPSTime=getTime();
		}
		fps++;
	}
}
