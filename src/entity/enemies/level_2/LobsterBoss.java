package entity.enemies.level_2;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import entity.Animation;
import entity.Enemy;
import entity.FireBall;
import entity.Player;
import tileMap.TileMap;

public class LobsterBoss extends Enemy {
	// Player
	private Player player;

	// Properties
	private boolean isSmashing;
	private boolean isPunching;
	private boolean isBlocking;
	private boolean isFlashing;
	private boolean isDead;
	private boolean isRemoved;

	private int smashDamage;
	private int smashRange;
	private int punchDamage;
	private int punchRange;
	private int flashRange;
	private int blockRange;

	// Animations
	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = { 5, 8, 8, 1, 18, 12, 14 };

	// Animation actions
	private static final int IDLE = 0;
	private static final int MOVING = 1;
	private static final int FLASHING = 2;
	private static final int BLOCKING = 3;
	private static final int SMASHING = 4;
	private static final int PUNCHING = 5;
	private static final int DEAD = 6;

	// Static movement
	private int xSpawn;
	private int moveRange;
	private boolean inMoveRange;
	private boolean inPosition;
	private boolean isTrigger;

	// Set cooldown
	private long startTime;
	private long currentTime;
	private long timePlayed;
	private boolean timerStart;
	private int flashingCooldown;
	private int blockingCooldown;
	private int smashingCooldown;
	private int punchingCooldown;

	public LobsterBoss(TileMap tileMap, Player player) {
		super(tileMap);
		this.player = player;

		width = 315;
		height = 200;
		collisionWidth = 150;
		collisionHeight = 50;

		moveSpeed = 0.5;
		maxSpeed = 2 + (Math.random() * 1);
		fallSpeed = 0.5;
		maxFallSpeed = 2.5;

		stopSpeed = 1.0;

		health = maxHealth = 55;

		flashingCooldown = 1200;
		blockingCooldown = 300;
		smashingCooldown = 1000;
		punchingCooldown = 400;

		damage = 3;
		smashDamage = 8;
		punchDamage = 5;
		smashRange = 315;
		punchRange = 280;
		flashRange = 600;
		blockRange = 300;

		moveRange = 1000;
		// load Sprite;
		try {

			BufferedImage spritesheet = ImageIO
					.read(getClass().getResourceAsStream("/sprites/enemies/level_2/LobsterBoss.gif"));

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

	private void checkAttack() {
		// Attack
		if (isSmashing) {
			if (facingRight) {
				if (player.getX() > x && player.getX() < x + smashRange && player.getY() > y - height / 2
						&& player.getY() < y + height / 2) {
					player.hit(smashDamage);
				}
			} else {
				if (player.getX() < x && player.getX() > x - smashRange && player.getY() > y - height / 2
						&& player.getY() < y + height / 2) {
					player.hit(smashDamage);
				}
			}
		}
		if (isPunching) {
			if (facingRight) {
				if (player.getX() > x && player.getX() < x + punchRange && player.getY() > y - height / 2
						&& player.getY() < y + height / 2) {
					player.hit(punchDamage);
				}
			} else {
				if (player.getX() < x && player.getX() > x - smashRange && player.getY() > y - height / 2
						&& player.getY() < y + height / 2) {
					player.hit(punchDamage);
				}
			}
		}

	}

	private void smashing() {
		boolean isCoolingDown = false;

		if (timePlayed <= smashingCooldown) {
			isCoolingDown = true;
		}

		if (isPunching || isFlashing || isBlocking || isDead) {
			return;
		}

		if (!isSmashing && !isCoolingDown) {
			if (facingRight) {
				if (player.getX() > x && player.getX() < x + smashRange && player.getY() > y - height / 2
						&& player.getY() < y + height / 2) {
					isSmashing = true;
				}
			} else {
				if (player.getX() < x && player.getX() > x - smashRange && player.getY() > y - height / 2
						&& player.getY() < y + height / 2) {
					isSmashing = true;
				}
			}
		}

		if (currentAnimation == SMASHING) {
			if (animation.hasPlayedOnce()) {
				isSmashing = false;
				smashingCooldown += 1000 + (int) (Math.random() * 500);
			}
		}

	}

	private void punching() {
		boolean isCoolingDown = false;

		if (timePlayed <= punchingCooldown) {
			isCoolingDown = true;
		}

		if (isSmashing || isFlashing || isBlocking || isDead) {
			return;
		}

		if (!isPunching && !isCoolingDown) {
			if (facingRight) {
				if (player.getX() > x && player.getX() < x + punchRange && player.getY() > y - height / 2
						&& player.getY() < y + height / 2) {
					isPunching = true;
				}
			} else {
				if (player.getX() < x && player.getX() > x - punchRange && player.getY() > y - height / 2
						&& player.getY() < y + height / 2) {
					isPunching = true;
				}
			}
		}

		if (currentAnimation == PUNCHING) {
			if (animation.hasPlayedOnce()) {
				isPunching = false;
				punchingCooldown += 600 + (int) (Math.random() * 300);
			}
		}
	}

	private void flashing() {
		boolean isCoolingDown = false;

		if (timePlayed <= flashingCooldown) {
			isCoolingDown = true;
		}

		if (isSmashing || isPunching || isBlocking || isDead) {
			return;
		}

		if (!isFlashing && !isCoolingDown) {
			if (facingRight) {
				if (player.getX() > x && player.getX() < x + flashRange && player.getY() > y - height / 2
						&& player.getY() < y + height / 2) {
					isFlashing = true;
				}
			} else {
				if (player.getX() < x && player.getX() > x - flashRange && player.getY() > y - height / 2
						&& player.getY() < y + height / 2) {
					isFlashing = true;
				}
			}
		}

		if (currentAnimation == FLASHING) {
			if (animation.hasPlayedOnce()) {
				isFlashing = false;
				if (facingRight) {
					this.x = player.getX() - (50 + (Math.random() * 50));
				} else {
					this.x = player.getX() + (50 + (Math.random() * 50));
				}
				flashingCooldown += 1000;
			}
		}
	}

	public void blocking() {
		ArrayList<FireBall> fireballs = player.getFireballs();

		boolean isCoolingDown = false;
		isBlocking = false;
		if (timePlayed <= blockingCooldown) {
			isCoolingDown = true;
		}

		if (isSmashing || isPunching || isBlocking || isDead) {
			return;
		}

		if (!isBlocking && !isCoolingDown) {
			for (int i = 0; i < fireballs.size(); i++) {
				if (facingRight) {
					if (fireballs.get(i).getX() > x && fireballs.get(i).getX() < x + blockRange
							&& fireballs.get(i).getY() > y - height / 2 && fireballs.get(i).getY() < y + height / 2) {
						isBlocking = true;
					}
				} else {
					if (fireballs.get(i).getX() < x && fireballs.get(i).getX() > x - blockRange
							&& fireballs.get(i).getY() > y - height / 2 && fireballs.get(i).getY() < y + height / 2) {
						isBlocking = true;
					}
				}
			}
		}
		if (currentAnimation == BLOCKING) {
			if (animation.hasPlayedOnce()) {
				isBlocking = false;
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
		boolean bossNearPlayer = false;
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

		// Check boss and player distance
		if (player.getX() < x && player.getX() + 100 >= x) {
			bossNearPlayer = true;
			dx = 0.00001;
		} else if (player.getX() > x && player.getX() - 100 <= x) {
			bossNearPlayer = true;
			dx = 0.00001;
		} else {
			bossNearPlayer = false;
		}

		if (inMoveRange && !bossNearPlayer) {
			isTrigger = true;
			if (player.getX() >= leftDestination && player.getX() <= xSpawn && player.getX() <= this.getX()) {
				direction(true, false);
				facingRight = false;
			} else if (player.getX() >= leftDestination && player.getX() <= xSpawn && player.getX() > this.getX()) {
				direction(false, true);
				facingRight = true;
			} else if (player.getX() >= xSpawn && player.getX() <= rightDestination && player.getX() > this.getX()) {
				direction(false, true);
				facingRight = true;
			} else if (player.getX() >= xSpawn && player.getX() <= rightDestination && player.getX() <= this.getX()) {
				direction(true, false);
				facingRight = false;
			} else {
				isTrigger = false;
			}
		}

		if (isTrigger && !timerStart) {
			startTime = System.currentTimeMillis();
			timerStart = true;
		}

		if (!isTrigger && !inPosition && !bossNearPlayer) {
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

		if (currentAnimation == SMASHING) {
			if (right) {
				direction(false, true);
				facingRight = true;
				dx += 3;
			} else if (left) {
				direction(true, false);
				facingRight = false;
				dx -= 3;
			}
		}

		if (currentAnimation == PUNCHING) {
			if (right) {
				direction(false, true);
				facingRight = true;
				dx += 2;
			} else if (left) {
				direction(true, false);
				facingRight = false;
				dx -= 2;
			}
		}
		if (currentAnimation == BLOCKING) {
			dx = 0.00001;
			hit(-8);
			if (health > maxHealth) {
				health = maxHealth;
			}
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
		timePlayed = (currentTime - startTime) / 10;

		if (isDead()) {
			isDead = true;
			isSmashing = false;
			isPunching = false;
			isFlashing = false;
			isBlocking = false;
			dx = 0.000001;
		}

		if (currentAnimation == DEAD) {
			if (animation.hasPlayedOnce())
				isRemoved = true;
		}

		// Update attack
		smashing();
		punching();
		flashing();
		blocking();

		// check flinching
		if (flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed > 400) {
				flinching = false;
			}
		}

		// Set animation
		if (isSmashing) {
			if (currentAnimation != SMASHING) {
				currentAnimation = SMASHING;
				animation.setFrames(sprites.get(SMASHING));
				animation.setDelay(250);
				width = 315;
				isSmashing = false;
			}
		} else if (isPunching) {
			if (currentAnimation != PUNCHING) {
				currentAnimation = PUNCHING;
				animation.setFrames(sprites.get(PUNCHING));
				animation.setDelay(250);
				width = 315;
				isPunching = false;
			}
		} else if (isFlashing) {
			if (currentAnimation != FLASHING) {
				currentAnimation = FLASHING;
				animation.setFrames(sprites.get(FLASHING));
				animation.setDelay(200);
				width = 315;
				isFlashing = false;
			}
		} else if (isBlocking) {
			if (currentAnimation != BLOCKING) {
				currentAnimation = BLOCKING;
				animation.setFrames(sprites.get(BLOCKING));
				animation.setDelay(1000);
				width = 315;
				isBlocking = false;
			}
		} else if (isDead) {
			if (currentAnimation != DEAD) {
				currentAnimation = DEAD;
				animation.setFrames(sprites.get(DEAD));
				animation.setDelay(250);
				width = 315;
			}
		} else if (!inPosition) {
			if (currentAnimation != MOVING) {
				currentAnimation = MOVING;
				animation.setFrames(sprites.get(MOVING));
				animation.setDelay(100);
				width = 315;
			}
		} else {
			if (currentAnimation != IDLE) {
				currentAnimation = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelay(400);
				width = 315;
			}
		}

		// Update animation
		animation.update();

		// Set direction
		if (currentAnimation != SMASHING || currentAnimation != PUNCHING || currentAnimation != BLOCKING) {
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

		if (flinching && !isBlocking) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed / 100 % 2 == 0) {
				return;
			}
		}

		if (!isRemoved) {
			super.draw(graphic);
		}

	}

	public void getSpawnX(double x) {
		this.xSpawn = (int) x;
	}

	public void display() {
		System.out.println("Blocking: " + isBlocking + " Smashing: " + isSmashing + " Punching: " + isPunching
				+ " Flashing: " + isFlashing + "\nDead: " + isDead + "\nHealth: " + health + "\nTimePlayed: "
				+ timePlayed + "\nBlockingCD: " + blockingCooldown + " SmashingCD: " + smashingCooldown
				+ " PunchingCD: " + punchingCooldown + " FlashingCD: " + flashingCooldown + "\nCurrentAnimation: "
				+ currentAnimation + "\nLobster_X: " + x + " Lobstser_Y: " + y + "\nIsTrigger: " + isTrigger);
	}
}
