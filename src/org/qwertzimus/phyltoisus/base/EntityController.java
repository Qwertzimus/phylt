package org.qwertzimus.phyltoisus.base;

import java.util.Random;

import org.lwjgl.util.vector.Vector2f;
import org.qwertzimus.phyltoisus.physic.Time;
import org.qwertzimus.phyltoisus.world.World;

public class EntityController extends Thread {
	public static int AISleeptime = 1;
	boolean isRunning, isOk;
	long lastRandomActionTime = -1;
	int entityMode = 0;
	Entity ent;
	Entity target;

	public EntityController(Entity entity) {
		ent = entity;
	}

	public static int getAISleeptime() {
		return AISleeptime;
	}

	public static void setAISleeptime(int aISleeptime) {
		AISleeptime = aISleeptime;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public Entity getEnt() {
		return ent;
	}

	public void setEnt(Entity ent) {
		this.ent = ent;
	}

	public int getEntityMode() {
		return entityMode;
	}

	public void setEntityMode(int entityMode) {
		this.entityMode = entityMode;
	}

	public Entity getTarget() {
		return target;
	}

	public void setTarget(Entity target) {
		this.target = target;
	}

	public long getLastRandomActionTime() {
		return lastRandomActionTime;
	}

	public void setLastRandomActionTime(long lastRandomActionTime) {
		this.lastRandomActionTime = lastRandomActionTime;
	}

	public boolean isOk() {
		return isOk;
	}

	public synchronized void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	public void run() {
		isRunning = true;
		int i=0;
		while (isRunning) {
			if (isOk()) {
				if (ent != null) {
					Vector2f entityPosition = ent.getPosition();
					if (target != null) {
						Vector2f targetPosition = target.getPosition();
						if (entityPosition.x < targetPosition.x) {
							if (entityPosition.x + ent.getVelocity().x
									* Time.deltaTime / 1000 > targetPosition.x
									+ target.getVelocity().x * Time.deltaTime
									/ 1000) {
								ent.moveLeft();
							} else {
								ent.moveRight();
							}
						} else if (entityPosition.x > targetPosition.x) {
							if (entityPosition.x + ent.getVelocity().x
									* Time.deltaTime / 1000 < targetPosition.x
									+ target.getVelocity().x * Time.deltaTime
									/ 1000) {
								ent.moveRight();
							} else {
								ent.moveLeft();
							}
						}
						if (entityPosition.y > targetPosition.y) {
							if (entityPosition.y + ent.getVelocity().y
									* Time.deltaTime / 1000 > targetPosition.y
									+ target.getVelocity().y * Time.deltaTime
									/ 1000) {
								ent.moveDown();
							} else {
								ent.moveUp();
							}
						} else if (entityPosition.y < targetPosition.y) {
							if (entityPosition.y + ent.getVelocity().y
									* Time.deltaTime / 1000 < targetPosition.y
									+ target.getVelocity().y * Time.deltaTime
									/ 1000) {
								ent.moveUp();
							} else {
								ent.moveDown();
							}
						}

					} else {
						// TODO-correct time setting

						if (lastRandomActionTime > Time.getTime() - 2000) {
							
							
							if (i == 0) {
								ent.moveDown();
							} else if (i == 1) {
								ent.moveUp();
							} else if (i == 2) {
								ent.moveLeft();
							} else if (i == 3) {
								ent.moveRight();
							}
						} else {
							lastRandomActionTime = Time.getTime();
							 i = new Random().nextInt(4);
						}

					}
				}
				setOk(false);
			}
			try {
				sleep(AISleeptime);
			} catch (Exception ex) {

			}
		}
	}
}
