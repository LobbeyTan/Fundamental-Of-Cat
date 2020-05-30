
package entity.world.statics;

import java.awt.Graphics2D;

import gfx.Assets;
import main.Handler;
import tiles.Tile;

public class House extends StaticEntity {
	private boolean isDead = false;

	public House(Handler handler, float x, float y) {
		super(handler, x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT * 2, 1);
		bounds.x = -10;
		bounds.y = 40;
		// (int) (height / 1.5f);
		bounds.width = (width + 24) * 2;
		bounds.height = (int) (height - height / 1.6f + 10) * 2;
		this.health = 1;
	}

	@Override
	public void update() {

	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(Assets.house, (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), 160, 172, null);
	}

	@Override
	public void die() {
		isDead = true;
		System.out.println("Back home" + " Home_isDead: " + isDead);
	}
	
	public boolean isDead() {
		return isDead;
	}
	
	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

}
