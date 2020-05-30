package entity.enemies.level_2;

import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import entity.Animation;
import entity.Enemy;
import entity.Player;
import tileMap.TileMap;

public class IceGolem extends Enemy {
	// Player
	private Player player;

	// Magic Attack
	private boolean attacking;
	private int attackDamage;
	private int attackRange;

	// Animations
	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = { 6, 7, 9 };

	// Animation actions
	private static final int IDLE = 0;
	private static final int ATTACKING = 1;
	private static final int MOVING = 2;

	// Static movement
	private int xSpawn;
	private int moveRange;
	private boolean inMoveRange;
	private boolean inPosition;
	private boolean isTrigger;

	// Set cooldown
	private long startTime;
	private long currentTime;
	private long elapsed;
	private long cooldown;
	private boolean isCoolingDown;

	public IceGolem(TileMap tileMap, Player player) {
		super(tileMap);
		this.player = player;

		startTime = System.currentTimeMillis();
		cooldown = 300;
		isCoolingDown = false;

		width = 100;
		height = 100;
		collisionWidth = 75;
		collisionHeight = 75;

		moveSpeed = 0.5;
		maxSpeed = 2.5 + (Math.random() * 1);
		fallSpeed = 0.5;
		maxFallSpeed = 2.5;

		stopSpeed = 1.0;

		health = maxHealth = 15;

		damage = 3;
		attackDamage = 5;
		attackRange = 100;

		moveRange = 200;
		// load Sprite;
		try {

			BufferedImage spritesheet = ImageIO
					.read(getClass().getResourceAsStream("/sprites/enemies/level_2/Icegolem.gif"));

			sprites = new ArrayList<BufferedImage[]>();

			for (int i = 0; i < numFrames.length; i++) {
				BufferedImage[] movement = new BufferedImage[numFrames[i]];
				for (int j = 0; j < numFrames[i]; j++) {
					movement[j] = spritesheet.getSubimage(j * width, i * height, width, height);
				}
				sprites.add(movement);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		animation = new Animation();
		currentAnimation = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(400);

	}

	public void checkAttack() {
		// Attack
		if (attacking) {
			if (facingRight) {
				if (player.getX() > x && player.getX() < x + attackRange && player.getY() > y - height / 2
						&& player.getY() < y + height / 2) {
					player.hit(attackDamage);
				}
			} else {
				if (player.getX() < x && player.getX() > x - attackRange && player.getY() > y - height / 2
						&& player.getY() < y + height / 2) {
					player.hit(attackDamage);
				}
			}
		}
	}

	private void direction(boolean left, boolean right) {
		this.left = left;
		this.right = right;
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
		} else {
			dx = 0;
		}
	}

	private void getNextPosition() {
		double leftDestination = xSpawn - moveRange;
		double rightDestination = xSpawn + moveRange;

		// Initial static state
		if (this.getX() == xSpawn) {
			inPosition = true;
		} else {
			inPosition = false;
		}
		if (inPosition) {
			direction(false, false);
			facingRight = false;
		}

		// If hit wall, then go other direction
		if (right && dx == 0) {
			direction(true, false);
			facingRight = false;
		} else if (left && dx == 0) {
			direction(false, true);
			facingRight = true;
		}

		// Move in moveRange
		if (this.x < leftDestination) {
			direction(false, true);
			facingRight = true;
			inMoveRange = false;
		} else if (this.x > rightDestination) {
			direction(true, false);
			facingRight = false;
			inMoveRange = false;
		} else {
			inMoveRange = true;
		}

		if (inMoveRange) {
			isTrigger = true;
			if (player.getX() >= leftDestination && player.getX() <= xSpawn && player.getX() < this.getX()) {
				direction(true, false);
				facingRight = false;
			} else if (player.getX() >= leftDestination && player.getX() <= xSpawn && player.getX() > this.getX()) {
				direction(false, true);
				facingRight = true;
			} else if (player.getX() >= xSpawn && player.getX() <= rightDestination && player.getX() > this.getX()) {
				direction(false, true);
				facingRight = true;
			} else if (player.getX() >= xSpawn && player.getX() <= rightDestination && player.getX() < this.getX()) {
				direction(true, false);
				facingRight = false;
			} else {
				isTrigger = false;
			}
		}

		if (!isTrigger && !inPosition) {
			if (this.getX() <= xSpawn) {
				direction(false, true);
				facingRight = true;
				if (this.getX() / (double) xSpawn >= 1.0) {
					this.x = xSpawn;
				}
			} else if (this.getX() >= xSpawn) {
				direction(true, false);
				facingRight = false;
				if (this.getX() / (double) xSpawn <= 1.0) {
					this.x = xSpawn;
				}
			}
		}

		// Falling
		if (falling) {
			dy += fallSpeed;
		}

		if (currentAnimation == ATTACKING) {
			dx = 0.00001;
		}

	}

	@Override
	public void update() {
		// Update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		checkAttack();

		currentTime = System.currentTimeMillis();
		elapsed = currentTime - startTime;

		if (elapsed <= cooldown) {
			isCoolingDown = true;
		} else {
			isCoolingDown = false;
		}

		// Check attack has stopped
		if (currentAnimation == ATTACKING) {
			if (animation.hasPlayedOnce())
				attacking = false;
			startTime = currentTime;
		}

		// Check attack range

		if (!attacking && !isCoolingDown) {
			if (facingRight) {
				if (player.getX() > x && player.getX() < x + attackRange && player.getY() > y - height / 2
						&& player.getY() < y + height / 2) {
					attacking = true;
				}
			} else {
				if (player.getX() < x && player.getX() > x - attackRange && player.getY() > y - height / 2
						&& player.getY() < y + height / 2) {
					attacking = true;
				}
			}
		}

		// check flinching
		if (flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed > 400) {
				flinching = false;
			}
		}

		// Set animation
		if (attacking) {
			if (currentAnimation != ATTACKING) {
				currentAnimation = ATTACKING;
				animation.setFrames(sprites.get(ATTACKING));
				animation.setDelay(70);
				width = 100;
				attacking = false;
			}
		} else if (!inPosition) {
			if (currentAnimation != MOVING) {
				currentAnimation = MOVING;
				animation.setFrames(sprites.get(MOVING));
				animation.setDelay(100);
				width = 75;
			}
		} else {
			if (currentAnimation != IDLE) {
				currentAnimation = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelay(400);
				width = 75;
			}
		}

		// Update animation
		animation.update();

		// Set direction
		if (currentAnimation != ATTACKING) {
			if (right) {
				facingRight = true;
			} else {
				facingRight = false;
			}
		}

//		display();
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
		this.xSpawn = (int) x;
	}

	public void display() {
		System.out
				.println("CooldownTimer: " + elapsed + "\nGolem_X: " + this.x + "\nGolem_Y: " + this.y + "\nGolem_dx: "
						+ this.dx + "\nGolem_dy: " + this.dy + "\nIstrigger: " + isTrigger + "\nWall collision: "
						+ isCollision + "\nFacing right: " + facingRight + "\nLeft: " + left + "\nRight: " + right);
	}
}