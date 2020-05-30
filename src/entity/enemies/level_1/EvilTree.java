package entity.enemies.level_1;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import entity.Animation;
import entity.Enemy;
import tileMap.TileMap;

public class EvilTree extends Enemy {
	private BufferedImage[] sprites;
	private int xSpawn;
	private int moveRange;

	public EvilTree(TileMap tileMap) {
		super(tileMap);

		moveSpeed = 0.5 + (Math.random() * 1);
		maxSpeed = 1.0;
		fallSpeed = 0.5;
		maxFallSpeed = 25.0;

		width = 75;
		height = 75;
		collisionWidth = 50;
		collisionHeight = 50;

		health = maxHealth = 7;
		damage = 2;

		moveRange = 75;

		// Load sprites
		try {

			BufferedImage spritesheet = ImageIO
					.read(getClass().getResourceAsStream("/sprites/enemies/level_1/EvilTree.gif"));

			sprites = new BufferedImage[6];
			for (int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
			}

			animation = new Animation();
			animation.setFrames(sprites);
			animation.setDelay(300);

			right = true;
			facingRight = true;

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

		// Falling
		if (falling) {
			dy += fallSpeed;
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
			facingRight = false;
		} else if (left && dx == 0) {
			right = true;
			left = false;
			facingRight = true;
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
