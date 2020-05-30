package entity.enemies.runner;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import entity.Animation;
import entity.Enemy;
import tileMap.TileMap;

public class Snake extends Enemy {
	private BufferedImage[] sprite;

	public Snake(TileMap tileMap) {
		super(tileMap);

		moveSpeed = 0;
		maxSpeed = 0;
		fallSpeed = 0.5;
		maxFallSpeed = 25.0;

		width = 75;
		height = 75;
		collisionWidth = 50;
		collisionHeight = 50;

		damage = 1;

		// Load sprites
		try {

			BufferedImage spreedsheet = ImageIO
					.read(getClass().getResourceAsStream("/sprites/enemies/runner/Snake.gif"));

			sprite = new BufferedImage[12];
			for (int i = 0; i < sprite.length; i++) {
				sprite[i] = spreedsheet.getSubimage(i * width, 0, width, height);
			}

			animation = new Animation();
			animation.setFrames(sprite);
			animation.setDelay(300);

			left = true;
			facingRight = true;

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

		// Update animation
		animation.update();
	}

	@Override
	public void draw(Graphics2D graphic) {
		setMapPosition();
		super.draw(graphic);
	}

	public void setSpeed(double moveSpeed, double maxSpeed) {
		this.moveSpeed = moveSpeed;
		this.maxSpeed = maxSpeed;
	}
}
