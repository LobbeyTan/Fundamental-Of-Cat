
package entity.world.statics;

import java.awt.Graphics2D;

import entity.Entity;
import main.Handler;

public class Dunguen extends Entity {
	private boolean isDead = false;

	public Dunguen(Handler handler, float x, float y) {
		super(handler, x, y, 64, 64, 1);
		bounds.x = 0;
		bounds.y = 0;
		bounds.width = 64;
		bounds.height = 64;
		this.health = 1;
	}

	@Override
	public void update() {

	}

	@Override
	public void draw(Graphics2D g) {

	}

	@Override
	public void die() {
		isDead = true;
		System.out.println("Go to dungeon" + " Dungeon_isDead: " + isDead);
	}
	
	public boolean isDead()	{
		return this.isDead;
	}
	
	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

}
