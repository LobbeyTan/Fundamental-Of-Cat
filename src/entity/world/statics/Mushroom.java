
package entity.world.statics;

import static info.Info.hunger;
import static items.Item.ITEMHEIGHT;
import static items.Item.ITEMWIDTH;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import gfx.Assets;
import items.Item;
import main.Handler;

public class Mushroom extends StaticEntity {

	public Mushroom(Handler handler, float x, float y) {
		super(handler, x, y, 32, 40, 9999);
		bounds = new Rectangle((int) x, (int) y, ITEMWIDTH, ITEMHEIGHT);
	}

	@Override
	public void update() {
		if (handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0f, 0f).intersects(bounds)) {
			Item.setPickedUp(true);
			hunger -= 10;
//            handler.getWorld().getEntityManager().getPlayer().getInventory().addItem(mushroomItem); //add item see here
			setActive(false);
		}
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(Assets.mushroom, (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), 64, 64, null);
	}

	@Override
	public void die() {

	}

}
