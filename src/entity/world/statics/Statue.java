
package entity.world.statics;

import java.awt.Graphics2D;

import entity.Entity;
import gfx.Assets;
import main.Handler;

public class Statue extends Entity {

	public Statue(Handler handler, float x, float y) {
		super(handler, x, y, 32, 47, 9999);
		bounds.x = -15;
		bounds.y = (int) (height / 1.5f) - 10;
		bounds.width = width + 47;
		bounds.height = (int) (height - height / 1.5f) + 42;
	}

	@Override
	public void update() {

	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(Assets.statue, (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), 64, 94, null);
	}

	@Override
	public void die() {

	}

}
