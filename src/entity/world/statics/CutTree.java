
package entity.world.statics;

import java.awt.Graphics2D;

import entity.Entity;
import gfx.Assets;
import main.Handler;

public class CutTree extends Entity {

	public CutTree(Handler handler, float x, float y) {
		super(handler, x, y, 31, 26, 9999);
		bounds.x = -10;
		bounds.y = (int) (height / 1.5f) - 10;
		bounds.width = width + 35;
		bounds.height = (int) (height - height / 1.5f) + 10;
	}

	@Override
	public void update() {

	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(Assets.cutTree, (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), 62, 52, null);

	}

	@Override
	public void die() {

	}
}
