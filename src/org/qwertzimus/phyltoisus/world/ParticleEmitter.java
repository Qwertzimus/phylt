package org.qwertzimus.phyltoisus.world;

import java.nio.FloatBuffer;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.GL11;

public class ParticleEmitter {
	List<Particle> particles;
	int vertexBufferId,textureBufferId;
	FloatBuffer vertices,textures;
	boolean recreateBuffers;
	public ParticleEmitter(){
		vertexBufferId=-1;
		textureBufferId=-1;
	}
	public synchronized void addParticle(Particle particle){
		particles.add(particle);
	}
	public void init(){
		if(vertexBufferId==-1){
			vertexBufferId=ARBVertexBufferObject.glGenBuffersARB();
		}
		if(textureBufferId==-1){
			textureBufferId=ARBVertexBufferObject.glGenBuffersARB();
		}
	}
	
	public synchronized void updateBuffer(){
		if(vertices==null||recreateBuffers){
			vertices=BufferUtils.createFloatBuffer(particles.size()*2);
		}
		if(textures==null||recreateBuffers){
			textures=BufferUtils.createFloatBuffer(particles.size()*2);
		}
		vertices.clear();
		textures.clear();
		
		for(int i=0;i<particles.size();i++){
			vertices.put(particles.get(i).getPosition().x);
			vertices.put(particles.get(i).getPosition().y);
			textures.put(new float[]{0,1,0,0,1,0,1,1});
			
		}
	
	}
	public void rebindBuffer(){
		
		vertices.clear();
		textures.clear();
		//TODO
		ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, vertexBufferId);
		
		
	}
	public void draw(){
		
		ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, vertexBufferId);
		GL11.glVertexPointer(2, GL11.GL_FLOAT, 0, 0);
		ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, textureBufferId);
		GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, 0);
		
		GL11.glDrawArrays(GL11.GL_QUADS,0, particles.size());
	}
}
