
package entity.world.statics;

import java.awt.Graphics2D;

import gfx.Assets;
import main.Handler;

public class Skeleton extends StaticEntity {

	public Skeleton(Handler handler, float x, float y) {
		super(handler, x, y, 13, 12, 9999);
		bounds.x = -12;
		bounds.y = (int) (height / 2f) - 10;
		bounds.width = width + 10;
		bounds.height = (int) (height - height / 2f);
	}

	@Override
	public void update() {

	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(Assets.skeleton, (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), 39, 36, null);

	}

	@Override
	public void die() {

	}

}