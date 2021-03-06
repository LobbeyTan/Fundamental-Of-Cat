package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import audio.AudioPlayer;
import tileMap.TileMap;

public class ManaPotion extends MapObject {
	private BufferedImage manaPotion[];
	private boolean isEaten;

	private AudioPlayer soundEffect;

	public ManaPotion(TileMap tilemap) {
		super(tilemap);

		moveSpeed = 0;
		maxSpeed = 0;

		width = 75;
		height = 75;
		collisionWidth = 50;
		collisionHeight = 50;
		isEaten = false;

		try {

			BufferedImage spreedsheet = ImageIO
					.read(getClass().getResourceAsStream("/sprites/entities/ManaPotion.gif"));

			manaPotion = new BufferedImage[1];
			for (int i = 0; i < manaPotion.length; i++) {
				manaPotion[i] = spreedsheet.getSubimage(i * width, 0, width, height);
			}

			soundEffect = new AudioPlayer("/sfx/Drinking.mp3");

			animation = new Animation();
			animation.setFrames(manaPotion);
			animation.setDelay(300);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getNextPosition() {
		// Move left
		dx -= moveSpeed;
		if (dx < -maxSpeed) {
			dx = -maxSpeed;
		}

	}

	public void update() {
		// Update position
		getNextPosition();
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

	public void isEaten(boolean isEaten) {
		this.isEaten = isEaten;
	}

	public void addFire(Player player) {
		if (isEaten) {
			player.addFire(player.getMaxFire() / 5);
			soundEffect.play(false);
		}
	}

	public void setSpeed(double moveSpeed, double maxSpeed) {
		this.moveSpeed = moveSpeed;
		this.maxSpeed = maxSpeed;
	}

}
