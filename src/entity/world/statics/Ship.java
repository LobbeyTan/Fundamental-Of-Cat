
package entity.world.statics;

import java.awt.Graphics2D;

import entity.Entity;
import gfx.Assets;
import main.Handler;

public class Ship extends Entity {
	private boolean isDead;

	public Ship(Handler handler, float x, float y) {
		super(handler, x, y, 32, 47, 1);
		bounds.x = -15;
		bounds.y = (int) (height / 1.5f) + 190;
		bounds.width = width + 47;
		bounds.height = (int) (height - height / 1.5f) + 42;
		this.health = 1;
	}

	@Override
	public void update() {

	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(Assets.ship, (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), 432, 320, null);
	}

	@Override
	public void die() {
		isDead = true;
		System.out.println("Go to ship game" + "Ship_isDead: " + isDead);
	}
	
	public boolean isDead() {
		return this.isDead;
	}
	
	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

}