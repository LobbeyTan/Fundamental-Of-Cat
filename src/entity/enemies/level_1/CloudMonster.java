package entity.enemies.level_1;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import entity.Animation;
import entity.Enemy;
import tileMap.TileMap;

public class CloudMonster extends Enemy {
	private BufferedImage[] sprites;
	private int xSpawn;
	private int moveRange;

	public CloudMonster(TileMap tileMap) {
		super(tileMap);

		moveSpeed = 0.75 + (Math.random() * 1);
		maxSpeed = 1.5;

		width = 75;
		height = 75;
		collisionWidth = 50;
		collisionHeight = 50;

		health = maxHealth = 4;
		damage = 3;

		moveRange = 150;

		// Load sprites
		try {

			BufferedImage spritesheet = ImageIO
					.read(getClass().getResourceAsStream("/sprites/enemies/level_1/CloudMonster.gif"));

			sprites = new BufferedImage[4];
			for (int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
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

		// Move in moveRange
		if (this.getX() >= xSpawn + moveRange) {
			right = false;
			left = true;
			facingRight = false;
		} else if (this.getX() <= xSpawn - moveRange) {
			right = true;
			left = false;
			facingRight = true;
		}

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
