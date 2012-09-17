package org.qwertzimus.phyltoisus.base;

import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.GL11;

public class Player extends Entity {
	Inventory inventory;

	public Player() {
		super();
		inventory = new Inventory();
	}

	@Override
	public void draw() {
		//System.out.println(this.position.x + ", " + this.position.y);
		if (isReady) {
			GL11.glPushMatrix();
			GL11.glTranslatef((int) position.x, (int) position.y, 0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
			/*
			 * GL11.glVertexPointer(2,0,vertices);
			 * GL11.glTexCoordPointer(2,0,textures);
			 */
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D,
					GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D,
					GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

			ARBVertexBufferObject.glBindBufferARB(
					ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, vertexBufferId);
			GL11.glVertexPointer(2, GL11.GL_FLOAT, 0, 0);
			ARBVertexBufferObject.glBindBufferARB(
					ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, 0);
			ARBVertexBufferObject.glBindBufferARB(
					ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, textureBufferId);
			GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, 0);
			if (inChunk != null) {
				ARBShaderObjects.glUniform1fARB(
						Main.baseLightValueLoc,
						inChunk.getBlock((getBlockX() - getChunkX()) / 16,
								(getBlockY() - getChunkY()) / 16, 0)
								.getLightValue());
				
				ARBShaderObjects.glUniform1fARB(
						Main.baseLightValueLoc,
						0.9f);

			}
			GL11.glDrawArrays(GL11.GL_QUADS, 0, 4);
			ARBVertexBufferObject.glBindBufferARB(
					ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, 0);
			ARBShaderObjects.glUniform1fARB(Main.baseLightValueLoc, 0);
			GL11.glPopMatrix();
		}
	}
}
