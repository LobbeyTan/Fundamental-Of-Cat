
package entity.world.statics;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import gfx.Assets;
import items.Item;
import main.Handler;

import static info.Info.happiness;
import static info.Info.hunger;
import static items.Item.ITEMHEIGHT;
import static items.Item.ITEMWIDTH;

public class Apple extends StaticEntity {
	public Apple(Handler handler, float x, float y) {
		super(handler, x, y, 32, 40, 9999);
		bounds = new Rectangle((int) x, (int) y, ITEMWIDTH, ITEMHEIGHT);
	}

	@Override
	public void update() {
		if (handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0f, 0f).intersects(bounds)) {
			Item.setPickedUp(true);
//            handler.getWorld().getEntityManager().getPlayer().getInventory().addItem(appleItem); //add item see here
			setActive(false);
			happiness += 10;
			hunger -= 10;
		}
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(Assets.apple, (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), 32, 32, null);
	}

	public void consume() {
		health += 1;
		hunger -= 10;
	}

	@Override
	public void die() {
		throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
																		// Tools | Templates.
	}

}
