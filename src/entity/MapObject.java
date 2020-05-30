package entity;

import main.GamePanel;
import tileMap.Tile;
import tileMap.TileMap;

import java.awt.Rectangle;

public abstract class MapObject {
	public static int coinsCollected = 0;
	// tile stuff
	protected TileMap tileMap;
	protected int tileSize;
	protected double xmap;
	protected double ymap;

	// position and vector
	protected double x;
	protected double y;
	protected double dx;
	protected double dy;

	// dimensions
	protected int width;
	protected int height;

	// collision box
	protected int collisionWidth;
	protected int collisionHeight;

	// collision
	protected int currentRow;
	protected int currentCol;
	protected double xDestination;
	protected double yDestination;
	protected double xtemp;
	protected double ytemp;
	protected boolean topLeft;
	protected boolean topRight;
	protected boolean bottomLeft;
	protected boolean bottomRight;
	protected boolean isCollision;

	// animation
	protected Animation animation;
	protected int currentAnimation;
	protected int previousAnimation;
	protected boolean facingRight;

	// movement
	protected boolean left;
	protected boolean right;
	protected boolean up;
	protected boolean down;
	protected boolean jumping;
	protected boolean falling;

	// movement attributes
	protected double moveSpeed;
	protected double maxSpeed;
	protected double stopSpeed;
	protected double fallSpeed;
	protected double maxFallSpeed;
	protected double jumpStart;
	protected double stopJumpSpeed;

	// constructor
	public MapObject(TileMap tilemap) {
		tileMap = tilemap;
		tileSize = tilemap.getTileSize();
	}

	public boolean intersects(MapObject other) {
		Rectangle r1 = getRectangle();
		Rectangle r2 = other.getRectangle();
		return r1.intersects(r2);
	}

	public Rectangle getRectangle() {
		return new Rectangle((int) x - collisionWidth, (int) y - collisionHeight, collisionWidth, collisionHeight);
	}

	public void calculateCorners(double x, double y, int tileType) {

		int leftTile = (int) (x - collisionWidth / 2) / tileSize;
		int rightTile = (int) (x + collisionWidth / 2 - 1) / tileSize;
		int topTile = (int) (y - collisionHeight / 2) / tileSize;
		int bottomTile = (int) (y + collisionHeight / 2 - 1) / tileSize;

		int tl = tileMap.getType(topTile, leftTile);
		int tr = tileMap.getType(topTile, rightTile);
		int bl = tileMap.getType(bottomTile, leftTile);
		int br = tileMap.getType(bottomTile, rightTile);

		topLeft = tl == tileType;
		topRight = tr == tileType;
		bottomLeft = bl == tileType;
		bottomRight = br == tileType;

	}

	public void checkTileMapCollision() {

		currentCol = (int) x / tileSize;
		currentRow = (int) y / tileSize;

		xDestination = x + dx;
		yDestination = y + dy;

		xtemp = x;
		ytemp = y;

		calculateCorners(x, yDestination, Tile.BLOCKED);
		if (dy < 0) {
			if (topLeft || topRight) {
				dy = 0;
				ytemp = currentRow * tileSize + collisionHeight / 2;
			} else {
				ytemp += dy;
			}
		}
		if (dy > 0) {
			if (bottomLeft || bottomRight) {
				dy = 0;
				falling = false;
				ytemp = (currentRow + 1) * tileSize - collisionHeight / 2;
			} else {
				ytemp += dy;
			}
		}

		calculateCorners(xDestination, y, Tile.BLOCKED);
		if (dx < 0) {
			if (topLeft || bottomLeft) {
				dx = 0;
				isCollision = true;
				xtemp = currentCol * tileSize + collisionWidth / 2;
			} else {
				xtemp += dx;
			}
		}
		if (dx > 0) {
			if (topRight || bottomRight) {
				dx = 0;
				isCollision = true;
				xtemp = (currentCol + 1) * tileSize - collisionWidth / 2;
			} else {
				xtemp += dx;
			}
		}

		if (!falling) {
			calculateCorners(x, yDestination + 1, Tile.BLOCKED);
			if (!bottomLeft && !bottomRight) {
				falling = true;
			}
		}

	}

	public boolean checkDeathTileCollision() {
		currentCol = (int) x / tileSize;
		currentRow = (int) y / tileSize;

		xDestination = x + dx;
		yDestination = y + dy;

		xtemp = x;
		ytemp = y;

		calculateCorners(x, yDestination, Tile.DEATH);
		if (dy < 0) {
			if (topLeft || topRight) {
				dy = 0;
				ytemp = currentRow * tileSize + collisionHeight / 2;
				return true;
			} else {
				ytemp += dy;
			}
		}
		if (dy > 0) {
			if (bottomLeft || bottomRight) {
				dy = 0;
				falling = false;
				ytemp = (currentRow + 1) * tileSize - collisionHeight / 2;
				return true;
			} else {
				ytemp += dy;
			}
		}

		calculateCorners(xDestination, y, Tile.DEATH);
		if (dx < 0) {
			if (topLeft || bottomLeft) {
				dx = 0;
				xtemp = currentCol * tileSize + collisionWidth / 2;
				return true;
			} else {
				xtemp += dx;
			}
		}
		if (dx > 0) {
			if (topRight || bottomRight) {
				dx = 0;
				xtemp = (currentCol + 1) * tileSize - collisionWidth / 2;
				return true;
			} else {
				xtemp += dx;
			}
		}

		return false;
	}

	public boolean outOfScreen() {
		return x + xmap + width < 0 || x + xmap - width > GamePanel.WIDTH || y + ymap + height < 0
				|| y + ymap - height > GamePanel.HEIGHT;
	}

	public void draw(java.awt.Graphics2D g) {
		if (facingRight) {
			g.drawImage(animation.getImage(), (int) (x + xmap - width / 2), (int) (y + ymap - height / 2), null);
		} else {
			g.drawImage(animation.getImage(), (int) (x + xmap - width / 2 + width), (int) (y + ymap - height / 2),
					-width, height, null);
		}
	}

	public int getX() {
		return (int) x;
	}

	public int getY() {
		return (int) y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getCollisionWidth() {
		return collisionWidth;
	}

	public int getCollisionHeight() {
		return collisionHeight;
	}

	public void setLeft(boolean b) {
		left = b;
	}

	public void setRight(boolean b) {
		right = b;
	}

	public void setUp(boolean b) {
		up = b;
	}

	public void setDown(boolean b) {
		down = b;
	}

	public void setJumping(boolean b) {
		jumping = b;
	}

	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}

	public void setMapPosition() {
		xmap = tileMap.getX();
		ymap = tileMap.getY();
	}

}
