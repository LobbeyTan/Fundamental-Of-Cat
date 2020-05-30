
package entity.world.statics;

import java.awt.Graphics2D;

import gfx.Assets;
import main.Handler;
import tiles.Tile;

public class Tree extends StaticEntity {

	public Tree(Handler handler, float x, float y) {
		super(handler, x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT * 2, 9999);
		bounds.x = 10;
		bounds.y = (int) (height / 1.5f) - 10;
		bounds.width = width + 15;
		bounds.height = (int) (height - height / 1.5f) + 10;
	}

	@Override
	public void update() {

	}

	@Override
	public void die() {

	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(Assets.tree, (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), 128, 128, null);
	}

}
