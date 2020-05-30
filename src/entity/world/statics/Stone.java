
package entity.world.statics;

import java.awt.Graphics2D;

import gfx.Assets;
import main.Handler;
import tiles.Tile;

public class Stone extends StaticEntity {

	public Stone(Handler handler, float x, float y) {
		super(handler, x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT, 99999);

		bounds.x = -12;
		bounds.y = (int) (height / 2f) - 10;
		bounds.width = width + 10;
		bounds.height = (int) (height - height / 2f);
	}

	@Override
	public void update() {

	}

	@Override
	public void die() {

	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(Assets.stone, (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
	}

}
