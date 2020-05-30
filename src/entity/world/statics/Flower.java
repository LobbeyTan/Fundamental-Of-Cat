
package entity.world.statics;

import java.awt.Graphics2D;

import gfx.Assets;
import main.Handler;

public class Flower extends StaticEntity {

	public Flower(Handler handler, float x, float y) {
		super(handler, x, y, 46, 16, 9999);
		bounds.x = 0;
		bounds.y = 0;
		bounds.width = 189;
		bounds.height = 50;
	}

	@Override
	public void update() {

	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(Assets.flower, (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), 189, 69, null);

	}

	@Override
	public void die() {

	}

}
