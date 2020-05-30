package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import audio.AudioPlayer;
import tileMap.TileMap;

public class Coins extends MapObject {
	private BufferedImage[] coin;
	private boolean isCollected;

	private AudioPlayer soundEffect;

	public Coins(TileMap tilemap) {
		super(tilemap);

		moveSpeed = 0;
		maxSpeed = 0;

		width = 75;
		height = 75;
		collisionWidth = 50;
		collisionHeight = 50;
		isCollected = false;

		try {

			BufferedImage spreedsheet = ImageIO.read(getClass().getResourceAsStream("/sprites/entities/Coin.png"));

			coin = new BufferedImage[9];
			for (int i = 0; i < coin.length; i++) {
				coin[i] = spreedsheet.getSubimage(i * width, 0, width, height);
			}

			soundEffect = new AudioPlayer("/musics/PickedCoin.mp3");

			animation = new Animation();
			animation.setFrames(coin);
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

	public void isCollected(boolean isCollected) {
		this.isCollected = isCollected;
	}

	public void addCoins() {
		if (isCollected) {
			coinsCollected += 1;
			soundEffect.play(false);
		}
	}

	public void setSpeed(double moveSpeed, double maxSpeed) {
		this.moveSpeed = moveSpeed;
		this.maxSpeed = maxSpeed;
	}

}
