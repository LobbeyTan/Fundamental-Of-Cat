
package entity.world.statics;

import java.awt.Graphics2D;

import gfx.Assets;
import main.Handler;
import tiles.Tile;

public class Shop extends StaticEntity {

	public Shop(Handler handler, float x, float y) {
		super(handler, x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT * 2, 99999);
		bounds.x = -10;
		bounds.y = 40;
		// (int) (height / 1.5f);
		bounds.width = (width + 24) * 2 + 64;
		bounds.height = (int) (height - height / 1.6f + 10) * 2 + 64;
	}

	@Override
	public void update() {

	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(Assets.shop, (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), 237, 240, null);
	}

	@Override
	public void die() {

	}

}
