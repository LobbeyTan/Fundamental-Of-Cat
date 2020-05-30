
package entity.world.statics;

import java.awt.Graphics2D;

import entity.Entity;
import gfx.Assets;
import main.Handler;

public class Castle extends Entity {

	public Castle(Handler handler, float x, float y) {
		super(handler, x, y, 64, 57, 1);
		bounds.x = -10;
		bounds.y = (int) (height / 1.5f) - 10;
		bounds.width = width * 3 + 25;
		bounds.height = height * 2;
	}

	@Override
	public void update() {

	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(Assets.castle, (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), 192, 171, null);
	}

	@Override
	public void die() {
		System.out.println("Go to castle game");
	}

}
