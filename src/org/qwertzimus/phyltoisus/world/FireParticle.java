package org.qwertzimus.phyltoisus.world;

import org.qwertzimus.phyltoisus.physic.Time;

public class FireParticle extends Particle{
	public FireParticle(){
		super();
	}
	public FireParticle(long lifeTime){
		super(lifeTime);
	}
	@Override
	public void update() {
		position.x+=Time.deltaTime*velocity.x;
		position.y+=Time.deltaTime*velocity.y;
	}
	
}
