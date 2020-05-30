
package entity.world.statics;

import java.awt.Graphics2D;

import gfx.Animation;
import gfx.Assets;
import main.Handler;
import tiles.Tile;

public class Flag extends StaticEntity {

	private Animation animFlag;

	public Flag(Handler handler, float x, float y) {
		super(handler, x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT * 2, 99999);
		bounds.x = 0;
		bounds.y = 70;
		// (int) (height / 1.5f);
		bounds.width = 15;
		bounds.height = 35;

		animFlag = new Animation(350, Assets.flag);
	}

	@Override
	public void update() {
		animFlag.update();
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(animFlag.getCurrentFrame(), (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), 96, 96, null);
	}

	@Override
	public void die() {

	}

}
