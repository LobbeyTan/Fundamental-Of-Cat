package entity.enemies.level_1;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import entity.Animation;
import entity.Enemy;
import tileMap.TileMap;

public class Bat extends Enemy {
	private BufferedImage[] sprites;
	private int xSpawn;
	private int moveRange;

	public Bat(TileMap tileMap) {
		super(tileMap);

		moveSpeed = 1.5 + (Math.random() * 1);
		maxSpeed = 2.0;

		width = 75;
		height = 75;
		collisionWidth = 50;
		collisionHeight = 50;

		health = maxHealth = 5;
		damage = 2;

		moveRange = 250;

		// Load sprites
		try {

			BufferedImage spreedsheet = ImageIO
					.read(getClass().getResourceAsStream("/sprites/enemies/level_1/Bat.gif"));

			sprites = new BufferedImage[4];
			for (int i = 0; i < sprites.length; i++) {
				sprites[i] = spreedsheet.getSubimage(i * width, 0, width, height);
			}

			animation = new Animation();
			animation.setFrames(sprites);
			animation.setDelay(300);

			right = true;
			facingRight = false;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void getNextPosition() {
		// Movement
		if (left) {
			dx -= moveSpeed;
			if (dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		} else if (right) {
			dx += moveSpeed;
			if (dx > maxSpeed) {
				dx = maxSpeed;
			}
		}
	}

	@Override
	public void update() {
		// Update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);

		// check flinching
		if (flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed > 400) {
				flinching = false;
			}
		}

		// If hit wall, then go other direction
		if (right && dx == 0) {
			right = false;
			left = true;
			facingRight = true;
		} else if (left && dx == 0) {
			right = true;
			left = false;
			facingRight = false;
		}

		// Move in moveRange
		if (this.getX() >= xSpawn + moveRange) {
			right = false;
			left = true;
			facingRight = true;
		} else if (this.getX() <= xSpawn - moveRange) {
			right = true;
			left = false;
			facingRight = false;
		}

		// Update animation
		animation.update();
	}

	@Override
	public void draw(Graphics2D graphic) {

		setMapPosition();

		if (flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed / 100 % 2 == 0) {
				return;
			}
		}

		super.draw(graphic);
	}

	public void getSpawnX(double x) {
		xSpawn = (int) x;
	}
}
