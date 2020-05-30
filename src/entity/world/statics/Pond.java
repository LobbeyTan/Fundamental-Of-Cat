package entity.world.statics;

import java.awt.Graphics2D;

import entity.Entity;
import gfx.Assets;
import main.Handler;

public class Pond extends Entity {

	public Pond(Handler handler, float x, float y) {
		super(handler, x, y, 83, 84, 9999);
		bounds.x = 10;
		bounds.y = (int) (height / 1.5f) - 74;
		bounds.width = width * 4 - 32;
		bounds.height = (int) (height - height / 1.5f) * 12;
	}

	@Override
	public void update() {

	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(Assets.pond, (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), 332, 336, null);

	}

	@Override
	public void die() {

	}

}
