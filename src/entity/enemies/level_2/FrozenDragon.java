package entity.enemies.level_2;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import entity.Animation;
import entity.Enemy;
import entity.Player;
import tileMap.TileMap;

public class FrozenDragon extends Enemy {
	// Player
	private Player player;

	// Magic Attack
	private boolean charging;
	private boolean hasCharged;
	private boolean attacking;
	private int attackDamage;
	private int attackRange;

	// Animations
	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = { 5, 4, 10, 5 };

	// Animation actions
	private static final int IDLE = 0;
	private static final int MOVING = 1;
	private static final int CHARGING = 2;
	private static final int ATTACKING = 3;

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

	public FrozenDragon(TileMap tileMap, Player player) {
		super(tileMap);
		this.player = player;

		startTime = System.currentTimeMillis();
		cooldown = 800;
		isCoolingDown = false;

		width = 150;
		height = 100;
		collisionWidth = 80;
		collisionHeight = 80;

		moveSpeed = 0.1;
		maxSpeed = 0.5 + (Math.random() * 1);
		fallSpeed = 0.5;
		maxFallSpeed = 2.5;

		stopSpeed = 1.0;

		health = maxHealth = 25;

		damage = 3;
		attackDamage = 10;
		attackRange = 180;

		moveRange = 500;
		// load Sprite;
		try {

			BufferedImage spritesheet = ImageIO
					.read(getClass().getResourceAsStream("/sprites/enemies/level_2/FrozenDragon.gif"));

			sprites = new ArrayList<BufferedImage[]>();

			for (int i = 0; i < numFrames.length; i++) {
				BufferedImage[] movement = new BufferedImage[numFrames[i]];
				for (int j = 0; j < numFrames[i]; j++) {
					if (i < 3) {
						movement[j] = spritesheet.getSubimage(j * width, i * height, width, height);
					} else {
						movement[j] = spritesheet.getSubimage(j * width * 2, i * height, width * 2, height);
					}

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
			if (!facingRight) {
				if (player.getX() > x && player.getX() < x + attackRange && player.getY() > y - height / 2
						&& player.getY() < y + height / 2) {
					player.hit(attackDamage);
					player.setSpeed(0, 0);
				}
			} else {
				if (player.getX() < x && player.getX() > x - attackRange && player.getY() > y - height / 2
						&& player.getY() < y + height / 2) {
					player.hit(attackDamage);
					player.setSpeed(0, 0);
				}
			}
		} else {
			player.setSpeed(0.75, 4);
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
			facingRight = true;
		}

		// If hit wall, then go other direction
		if (right && dx == 0) {
			direction(true, false);
			facingRight = true;
		} else if (left && dx == 0) {
			direction(false, true);
			facingRight = false;
		}

		// Move in moveRange
		if (this.x < leftDestination) {
			direction(false, true);
			facingRight = false;
			inMoveRange = false;
		} else if (this.x > rightDestination) {
			direction(true, false);
			facingRight = true;
			inMoveRange = false;
		} else {
			inMoveRange = true;
		}

		if (inMoveRange && player.getX() != this.getX()) {
			isTrigger = true;
			if (player.getX() >= leftDestination && player.getX() <= xSpawn && player.getX() < this.getX()) {
				direction(true, false);
				facingRight = true;
			} else if (player.getX() >= leftDestination && player.getX() <= xSpawn && player.getX() > this.getX()) {
				direction(false, true);
				facingRight = false;
			} else if (player.getX() >= xSpawn && player.getX() <= rightDestination && player.getX() > this.getX()) {
				direction(false, true);
				facingRight = false;
			} else if (player.getX() >= xSpawn && player.getX() <= rightDestination && player.getX() < this.getX()) {
				direction(true, false);
				facingRight = true;
			} else {
				isTrigger = false;
			}
		}

		if (!isTrigger && !inPosition) {
			if (this.getX() <= xSpawn) {
				direction(false, true);
				facingRight = false;
				if (this.getX() / (double) xSpawn >= 1.0) {
					this.x = xSpawn;
				}
			} else if (this.getX() >= xSpawn) {
				direction(true, false);
				facingRight = true;
				if (this.getX() / (double) xSpawn <= 1.0) {
					this.x = xSpawn;
				}
			}
		}

		// Falling
		if (falling) {
			dy += fallSpeed;
		}

		if (attacking) {
			collisionWidth = 180;
		} else {
			collisionWidth = 80;
		}

		if (currentAnimation == ATTACKING || currentAnimation == CHARGING) {

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

		// Check charging has stopped
		if (currentAnimation == CHARGING) {
			if (animation.hasPlayedOnce()) {
				charging = false;
				hasCharged = true;
			}
			startTime = currentTime;
		}
		// Check attack has stopped
		if (currentAnimation == ATTACKING) {
			if (animation.hasPlayedOnce()) {
				attacking = false;
				hasCharged = false;
			}
		}

		// Check attack range
		if (!charging && !isCoolingDown) {
			if (!facingRight) {
				if (player.getX() > x && player.getX() < x + attackRange && player.getY() > y - height / 2
						&& player.getY() < y + height / 2) {
					charging = true;
					hasCharged = false;
				}
			} else {
				if (player.getX() < x && player.getX() > x - attackRange && player.getY() > y - height / 2
						&& player.getY() < y + height / 2) {
					charging = true;
					hasCharged = false;
				}
			}
		}
		if (!charging && hasCharged) {
			attacking = true;
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
				animation.setDelay(300);
				width = 300;
				attacking = false;
			}
		} else if (charging) {
			if (currentAnimation != CHARGING) {
				currentAnimation = CHARGING;
				animation.setFrames(sprites.get(CHARGING));
				animation.setDelay(200);
				width = 150;
				charging = false;
			}
		} else if (!inPosition) {
			if (currentAnimation != MOVING) {
				currentAnimation = MOVING;
				animation.setFrames(sprites.get(MOVING));
				animation.setDelay(400);
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

		// Update animation
		animation.update();

		// Set direction
		if (currentAnimation != ATTACKING) {
			if (right) {
				facingRight = false;
			} else {
				facingRight = true;
			}
		}
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

}
