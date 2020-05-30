package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import tileMap.TileMap;

public class MovingDragon extends MapObject {
	private BufferedImage[] tiles;

	private boolean isIntersect;
	private boolean hasReachDestination;

	private int xDestination;
	private int yDestination;

	public MovingDragon(TileMap tilemap) {
		super(tilemap);

		moveSpeed = 0;
		maxSpeed = 0;

		width = 300;
		height = 300;
		collisionWidth = 50;
		collisionHeight = 50;

		facingRight = true;
		hasReachDestination = false;

		try {

			BufferedImage spreedsheet = ImageIO.read(getClass().getResourceAsStream("/sprites/entities/Dragon.gif"));

			tiles = new BufferedImage[3];
			for (int i = 0; i < tiles.length; i++) {
				tiles[i] = spreedsheet.getSubimage(i * width, 0, width, height);
			}

			animation = new Animation();
			animation.setFrames(tiles);
			animation.setDelay(300);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getNextPosition() {
		if (isIntersect) {
			if (this.x < xDestination) { // Move right
				dx += moveSpeed;
				if (dx > maxSpeed)
					dx = maxSpeed;
			}
			if (this.x > xDestination) { // Move left
				dx += -moveSpeed;
				if (dx < -maxSpeed)
					dx = -maxSpeed;
			}
			if (this.y < yDestination) { // Move down
				dy += moveSpeed;
				if (dy > maxSpeed)
					dy = maxSpeed;
			}
			if (this.dy > yDestination) { // Move up
				dy += -moveSpeed;
				if (dy > -maxSpeed)
					dy = -maxSpeed;
			}
		}
	}

	public void checkOnRide(Player player) {
		if (this.intersects(player) && !hasReachDestination) {
			player.moveSpeed = 0;
			player.y = this.y - collisionHeight;
			player.x = this.x;
			player.animation.setFrame(3);
			player.animation.setDelay(1000);
			isIntersect = true;
		}

		falling = false;
		fallSpeed = 0;
		maxFallSpeed = 0;

		if (this.x / xDestination >= 1.0) {
			this.dx = 0;
			this.x = xDestination;
		}

		if (this.y / yDestination >= 1.0) {
			this.dy = 0;
			this.y = yDestination;
		}

		if ((int) this.x == xDestination && (int) this.y == yDestination) {
			this.moveSpeed = 0;
			player.moveSpeed = 0.75;
			isIntersect = false;
			hasReachDestination = true;
		}

		if (hasReachDestination) {
			animation.setDelay(400);
		}
	}

	public void update() {
		// Update position
		if (!hasReachDestination) {
			getNextPosition();
			checkTileMapCollision();
			setPosition(xtemp, ytemp);

//			display();
		}

		// Update animation
		animation.update();
	}

	@Override
	public void draw(Graphics2D graphic) {
		setMapPosition();
		super.draw(graphic);
	}

	public void setProperty(int xDestination, int yDestination, double moveSpeed, double maxSpeed) {
		this.xDestination = xDestination;
		this.yDestination = yDestination;
		this.moveSpeed = moveSpeed;
		this.maxSpeed = maxSpeed;
	}

	public boolean isIntersect() {
		return this.isIntersect;
	}

	public boolean hasReachDestination() {
		return this.hasReachDestination;
	}

	public void display() {
		System.out.println("xDestination: " + xDestination + " yDestination: " + yDestination + " Dragon_X: "
				+ this.getX() + " Dragon_Y: " + this.getY() + " Reach destination: " + hasReachDestination
				+ " Falling: " + falling);
	}

}
