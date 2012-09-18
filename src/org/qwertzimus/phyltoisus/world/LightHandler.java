package org.qwertzimus.phyltoisus.world;

import java.util.ArrayList;
import java.util.List;

import org.qwertzimus.phyltoisus.base.LightSource;

public class LightHandler extends Thread{
	private List<LightSource> lights;
	
	private boolean isRunning;
	public LightHandler(){
		lights=new ArrayList<LightSource>();
	}
	
	public LightHandler(List<LightSource> ls){
		lights=ls;
	}
	public void run(){
		isRunning=true;
		while(isRunning){
			try{
				for(LightSource ls:lights){
					ls.updateAffectedBlocks();
				}
				sleep(1);
			}catch(Exception ex){
				System.out.println("LightHandler:"+ex);
			}
		}
	}
	
	
	public List<LightSource> getLights() {
		return lights;
	}

	public void setLights(List<LightSource> lights) {
		this.lights = lights;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public synchronized void addLight(LightSource ls){
		lights.add(ls);
	}
	
	public synchronized void removeLight(LightSource ls){
		lights.remove(ls);
	}
}
