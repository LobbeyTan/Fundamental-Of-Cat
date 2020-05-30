package entity.world.statics;

import java.awt.Graphics2D;

import gfx.Assets;
import main.Handler;

public class Village extends StaticEntity {

	public Village(Handler handler, float x, float y) {
		super(handler, x, y, 32, 43, 9999);
		bounds.x = -12;
		bounds.y = (int) (height / 2f) - 10;
		bounds.width = (width + 10 * 2) + 32;
		bounds.height = (128) - 30;
	}

	@Override
	public void update() {

	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(Assets.village, (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), 96, 129, null);

	}

	@Override
	public void die() {

	}

}
