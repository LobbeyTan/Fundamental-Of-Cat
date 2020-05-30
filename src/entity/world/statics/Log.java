
package entity.world.statics;

import java.awt.Graphics2D;

import gfx.Assets;
import main.Handler;

public class Log extends StaticEntity {

	public Log(Handler handler, float x, float y) {
		super(handler, x, y, 46, 16, 9999);
		bounds.x = -12;
		bounds.y = (int) (height / 2f) - 10;
		bounds.width = width + 70;
		bounds.height = (int) (height - height / 2f);
	}

	@Override
	public void update() {

	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(Assets.log, (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), 132, 48, null);

	}

	@Override
	public void die() {

	}

}