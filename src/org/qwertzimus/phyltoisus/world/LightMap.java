package org.qwertzimus.phyltoisus.world;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBVertexBufferObject;

public class LightMap {
	FloatBuffer lightValues;
	int lightBufferId;
	boolean rebind,warning;
	Chunk chunk;

	public LightMap(Chunk chunk) {
		lightBufferId = -1;
		this.chunk = chunk;
		rebind = false;
	}

	public void init() {
		if (lightBufferId == -1) {
			lightBufferId = ARBVertexBufferObject.glGenBuffersARB();
		}
	}

	
	public boolean warning() {
		return warning;
	}

	public void setWarning(boolean warning) {
		this.warning = warning;
	}

	public synchronized FloatBuffer getLightValues() {
		return lightValues;
	}

	public synchronized void setLightValues(FloatBuffer lightValues) {
		this.lightValues = lightValues;
	}

	public boolean rebind() {
		return rebind;
	}

	public synchronized void setRebind(boolean rebind) {
		this.rebind = rebind;
	}

	public int getLightBufferId() {
		return lightBufferId;
	}

	public void setLightBufferId(int lightBufferId) {
		this.lightBufferId = lightBufferId;
	}

	public Chunk getChunk() {
		return chunk;
	}

	public void setChunk(Chunk chunk) {
		this.chunk = chunk;
	}

	public synchronized void updateLightBuffer() {
			
//		if(warning==false)
		{
		rebind=false;
		Block[][][] block=chunk.getBlocks();
		
		try {
			lightValues.clear();
			lightValues.mark();
		} catch (Exception ex) {
			lightValues = BufferUtils.createFloatBuffer(16 * 16 * 5 * 4);
		}
		for (int o = 0; o < 5; o++) {
			for (int i = 0; i < 16; i++) {
				for (int k = 0; k < 16; k++) {
					lightValues.put(new float[] {
							block[k][i][o].getLightValue(),
							block[k][i][o].getLightValue(),
							block[k][i][o].getLightValue(),
							block[k][i][o].getLightValue() });
				}
			}
		}
		lightValues.rewind();
		rebind=true;
		}
	}

	// only main Thread
	public synchronized void bindBuffer() {
		if (rebind) {
			warning=true;
			ARBVertexBufferObject.glBindBufferARB(
					ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, lightBufferId);
			ARBVertexBufferObject.glBufferDataARB(
					ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, lightValues,
					ARBVertexBufferObject.GL_DYNAMIC_DRAW_ARB);
//			lightValues.clear();
			/*
			 * ARBVertexProgram.glVertexAttribPointerARB(2, 1, GL11.GL_FLOAT,
			 * false, 0, 0);
			 */
			ARBVertexBufferObject.glBindBufferARB(
					ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, 0);
			rebind = false;
			warning=false;
		}
	}
}
