
package entity.world.statics;

import java.awt.Graphics2D;

import gfx.Animation;
import gfx.Assets;
import main.Handler;
import tiles.Tile;

public class Fountain extends StaticEntity {

	private Animation animFountain;

	public Fountain(Handler handler, float x, float y) {
		super(handler, x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT * 2, 99999);
		bounds.x = -30;
		bounds.y = 20;
		// (int) (height / 1.5f);
		bounds.width = (width + 24) * 2;
		bounds.height = (int) (height - height / 1.6f) * 2;

		animFountain = new Animation(350, Assets.fountain);
	}

	@Override
	public void update() {
		animFountain.update();
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(animFountain.getCurrentFrame(), (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), 144, 141, null);
	}

	@Override
	public void die() {

	}

}
