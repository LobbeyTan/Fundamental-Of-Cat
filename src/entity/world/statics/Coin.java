
package entity.world.statics;

import static info.Info.munny;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import audio.AudioPlayer;
import gfx.Animation;
import gfx.Assets;
import items.Item;
import main.Handler;
import tiles.Tile;

public class Coin extends StaticEntity {
	private AudioPlayer pickedCoin;
	private Animation animCoin;

	public Coin(Handler handler, float x, float y) {
		super(handler, x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT * 2, 1);
		bounds = new Rectangle(0, 0, 16, 16);

		animCoin = new Animation(350, Assets.coin);
		pickedCoin = new AudioPlayer("/musics/PickedCoin.mp3");
	}

	@Override
	public void update() {
		animCoin.update();
		if (handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0f, 0f).intersects(bounds)) {
			Item.setPickedUp(true);
			munny++;
		}
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(animCoin.getCurrentFrame(), (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), 32, 32, null);

	}

	@Override
	public void die() {
		super.setActive(false);
		pickedCoin.play(false);
		munny++;
	}

}
