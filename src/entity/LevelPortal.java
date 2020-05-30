package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import tileMap.TileMap;

public class LevelPortal extends MapObject {
	private BufferedImage[] coin;
	@SuppressWarnings("unused")
	private boolean isEnter;

	public LevelPortal(TileMap tilemap) {
		super(tilemap);

		width = 75;
		height = 133;
		collisionWidth = 50;
		collisionHeight = 80;
		isEnter = false;

		try {

			BufferedImage spreedsheet = ImageIO
					.read(getClass().getResourceAsStream("/sprites/entities/LevelPortal.gif"));

			coin = new BufferedImage[9];
			for (int i = 0; i < coin.length; i++) {
				coin[i] = spreedsheet.getSubimage(i * width, 0, width, height);
			}

			animation = new Animation();
			animation.setFrames(coin);
			animation.setDelay(300);

		} catch (Exception e) {
			e.printStackTrace();
		}

		facingRight = true;
	}

	public void update() {
		// Update position
		checkTileMapCollision();
		setPosition(xtemp, ytemp);

		// Update animation
		animation.update();
	}

	@Override
	public void draw(Graphics2D graphic) {
		setMapPosition();
		super.draw(graphic);
	}

	public void isEnter(boolean isEnter) {
		this.isEnter = isEnter;
	}

	public void setDirection(boolean facingRight) {
		this.facingRight = facingRight;
	}

}
