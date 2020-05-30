package home;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entity.Animation;
import entity.MapObject;
import tileMap.TileMap;

public class HomeCat extends MapObject {
	// Animations
	private ArrayList<BufferedImage[]> sprites;

	private final int[] numFrames = { 1, 2, };

	// Animation actions
	private static final int IDLE = 0;
	private static final int WALKING = 1;

	public HomeCat(TileMap tileMap) {
		super(tileMap);

		moveSpeed = 1.5;
		maxSpeed = 2.0;

		stopSpeed = 1.0;
		fallSpeed = 0.375;
		maxFallSpeed = 5.0;

		width = 150;
		height = 150;
		collisionWidth = 50;
		collisionHeight = 50;

		facingRight = true;
		// Load sprites
		try {

			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/sprites/player/Cat.gif"));

			sprites = new ArrayList<BufferedImage[]>();

			for (int i = 0; i < numFrames.length; i++) {
				BufferedImage[] movement = new BufferedImage[numFrames[i]];
				for (int j = 0; j < numFrames[i]; j++) {
					movement[j] = spritesheet.getSubimage(j * width, (entity.world.cat.Creature.Cat - 1) * height, width, height);
				}
				sprites.add(movement);
			}

			animation = new Animation();
			currentAnimation = IDLE;
			animation.setFrames(sprites.get(IDLE));
			animation.setDelay(400);

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
			facingRight = false;
		} else if (right) {
			dx += moveSpeed;
			if (dx > maxSpeed) {
				dx = maxSpeed;
			}
			facingRight = true;
		} else {
			if (dx > 0) {
				dx -= stopSpeed;
				if (dx < 0) {
					dx = 0;
				}
			} else if (dx < 0) {
				dx += stopSpeed;
				if (dx > 0) {
					dx = 0;
				}
			}
		}

		// Falling
		if (falling) {
			dy += fallSpeed;
		}
	}

	public void update() {
		// Update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);

		if (left || right) {
			if (currentAnimation != WALKING) {
				currentAnimation = WALKING;
				animation.setFrames(sprites.get(WALKING));
				animation.setDelay(100);
				width = 150;
			}
		} else {
			if (currentAnimation != IDLE) {
				currentAnimation = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelay(400);
				width = 150;
			}
		}

		animation.update();
	}

	@Override
	public void draw(Graphics2D graphic) {
		setMapPosition();

		super.draw(graphic);

	}

	public void setTileMap(TileMap tileMap) {
		this.tileMap = tileMap;
	}

	public void setFalling(boolean falling) {
		this.falling = falling;
	}

	public void setSpeed(double moveSpeed, double maxSpeed) {
		this.moveSpeed = moveSpeed;
		this.maxSpeed = maxSpeed;
	}

}
