package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import audio.AudioPlayer;
import tileMap.TileMap;

public class Player extends MapObject {
	// Player
	private int health;
	private int maxHealth;
	private int fire;
	private int maxFire;
	private boolean dead;
	private boolean flinching;
	private long flinchTimer;

	// Fireball;
	private boolean firing;
	private int fireCost;
	private int fireBallDamage;
	private ArrayList<FireBall> fireBalls;

	// Scratch
	private boolean scratching;
	private int scratchDamage;
	private int scratchRange;

	// Gliding
	private boolean gliding;

	// Animations
	private ArrayList<BufferedImage[]> sprites;

	private final int[] numFrames = { 1, 2, 1, 2, 4, 2, 5 };

	// Animation actions
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int JUMPING = 2;
	private static final int FALLING = 3;
	private static final int GLIDING = 4;
	private static final int FIREBALL = 5;
	private static final int SCRATCHING = 6;

	private HashMap<String, AudioPlayer> sfx;

	public Player(TileMap tileMap) {
		super(tileMap);

		width = 75;
		height = 75;
		collisionWidth = 50;
		collisionHeight = 50;

		moveSpeed = 0.75;
		maxSpeed = 4;
		stopSpeed = 1.0;
		fallSpeed = 0.375;
		maxFallSpeed = 10.0;
		jumpStart = -12.0;
		stopJumpSpeed = 0.75;

		facingRight = true;

		health = maxHealth = 1000;
		fire = maxFire = 2500;

		fireCost = 200;
		fireBallDamage = 2;
		fireBalls = new ArrayList<FireBall>();

		scratchDamage = 3;
		scratchRange = 150;

		// load Sprite;
		try {

			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/sprites/player/CatSprites_" + entity.world.cat.Creature.Cat + ".png"));

			sprites = new ArrayList<BufferedImage[]>();

			for (int i = 0; i < numFrames.length; i++) {
				BufferedImage[] movement = new BufferedImage[numFrames[i]];
				for (int j = 0; j < numFrames[i]; j++) {

					if (i != 6) {
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

		sfx = new HashMap<String, AudioPlayer>();
		sfx.put("jump", new AudioPlayer("/sfx/jump.mp3"));
		sfx.put("scratch", new AudioPlayer("/sfx/scratch.mp3"));
	}

	public void checkAttack(ArrayList<Enemy> enemies) {
		// Check all enemies
		for (int i = 0; i < enemies.size(); i++) {
			Enemy enemy = enemies.get(i);

			// Scratch Attack
			if (scratching) {
				if (facingRight) {
					if (enemy.getX() > x && enemy.getX() < x + scratchRange && enemy.getY() > y - height / 2
							&& enemy.getY() < y + height / 2) {
						enemy.hit(scratchDamage);
					}
				} else {
					if (enemy.getX() < x && enemy.getX() > x - scratchRange && enemy.getY() > y - height / 2
							&& enemy.getY() < y + height / 2) {
						enemy.hit(scratchDamage);
					}
				}
			}

			// Fireballs attack
			for (int j = 0; j < fireBalls.size(); j++) {
				if (fireBalls.get(j).intersects(enemies.get(i))) {
					enemy.hit(fireBallDamage);
					fireBalls.get(j).setHit();
					break;
				}
			}

			// Check enemies collisions
			if (intersects(enemy)) {
				hit(enemy.getDamage());
			}
		}
	}

	public void fireballsRange() {
		for (int i = 0; i < fireBalls.size(); i++) {
			if (fireBalls.get(i).dx > 0) {
				if (fireBalls.get(i).getX() > this.getX() + 300) {
					fireBalls.remove(i);
					break;
				}
			} else if (fireBalls.get(i).dx < 0) {
				if (fireBalls.get(i).getX() < this.getX() - 300) {
					fireBalls.remove(i);
					break;
				}
			}
		}
	}

	public void hit(int damage) {
		if (flinching)
			return;
		health -= damage;
		if (health < 0)
			health = 0;
		if (health == 0) {
			dead = true;
		} else {
			flinching = true;
			flinchTimer = System.nanoTime();
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

		// Cannot move while attacking, except in air
		if ((currentAnimation == SCRATCHING || currentAnimation == FIREBALL) && !(jumping || falling)) {
			dx = 0;
		}

		// Jumping
		if (jumping && !falling) {
			sfx.get("jump").play(false);
			dy = jumpStart;
			falling = true;
		}

		// Falling
		if (falling) {
			if (dy > 0 && gliding) {
				dy += fallSpeed * 0.1;
			} else {
				dy += fallSpeed;
			}
			if (dy > 0) {
				jumping = false;
			}
			if (dy < 0 && !jumping) {
				dy += stopJumpSpeed;
			}
			if (dy > maxFallSpeed) {
				dy = maxFallSpeed;
			}
		}
	}

	public void update() {
		// Update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		fireballsRange();
		// Check death collision
		if (checkDeathTileCollision()) {
			hit(1);
		}

		// Check attack has stopped
		if (currentAnimation == SCRATCHING) {
			if (animation.hasPlayedOnce())
				scratching = false;
		}
		if (currentAnimation == FIREBALL) {
			if (animation.hasPlayedOnce())
				firing = false;
		}

		// FireBall attack
		fire += 1;
		if (fire > maxFire)
			fire = maxFire;
		if (firing && currentAnimation != FIREBALL) {
			if (fire > fireCost) {
				fire -= fireCost;
				FireBall fireball = new FireBall(tileMap, facingRight);
				if (facingRight) {
					fireball.setPosition(x + 20, y);
				} else {
					fireball.setPosition(x - 20, y);
				}

				fireBalls.add(fireball);
			}
		}

		// Update fireballs
		for (int i = 0; i < fireBalls.size(); i++) {
			fireBalls.get(i).update();
			if (fireBalls.get(i).shouldRemove()) {
				fireBalls.remove(i);
				i--;
			}
		}

		// Check done flinching;
		if (flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed > 1000) {
				flinching = false;
			}
		}

		// Set animation
		if (scratching) {
			if (currentAnimation != SCRATCHING) {
				sfx.get("scratch").play(false);
				currentAnimation = SCRATCHING;
				animation.setFrames(sprites.get(SCRATCHING));
				animation.setDelay(50);
				width = 150;
			}
		} else if (firing) {
			if (currentAnimation != FIREBALL) {
				currentAnimation = FIREBALL;
				animation.setFrames(sprites.get(FIREBALL));
				animation.setDelay(100);
				width = 75;
			}
		} else if (dy > 0) {
			if (gliding) {
				if (currentAnimation != GLIDING) {
					currentAnimation = GLIDING;
					animation.setFrames(sprites.get(GLIDING));
					animation.setDelay(100);
					width = 75;
				}
			} else if (currentAnimation != FALLING) {
				currentAnimation = FALLING;
				animation.setFrames(sprites.get(FALLING));
				animation.setDelay(100);
				width = 75;
			}
		} else if (dy < 0) {
			if (currentAnimation != JUMPING) {
				currentAnimation = JUMPING;
				animation.setFrames(sprites.get(JUMPING));
				animation.setDelay(-1);
				width = 75;
			}
		} else if (left || right) {
			if (currentAnimation != WALKING) {
				currentAnimation = WALKING;
				animation.setFrames(sprites.get(WALKING));
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

		animation.update();

		// Set direction
		if (currentAnimation != SCRATCHING && currentAnimation != FIREBALL) {
			if (right) {
				facingRight = true;
			}
			if (left) {
				facingRight = false;
			}
		}
	}

	@Override
	public void draw(Graphics2D graphic) {
		setMapPosition();

		// Draw fireballs
		for (int i = 0; i < fireBalls.size(); i++) {
			fireBalls.get(i).draw(graphic);
		}

		// Draw player;
		if (flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed / 100 % 2 == 0) {
				return;
			}
		}

		super.draw(graphic);

	}

	public void setTileMap(TileMap tileMap) {
		this.tileMap = tileMap;
	}

	public ArrayList<FireBall> getFireballs() {
		return this.fireBalls;
	}

	public void setFalling(boolean falling) {
		this.falling = falling;
	}

	public void setSpeed(double moveSpeed, double maxSpeed) {
		this.moveSpeed = moveSpeed;
		this.maxSpeed = maxSpeed;
	}

	public void setFiring() {
		firing = true;
	}

	public void setScratching() {
		scratching = true;
	}

	public void setGliding(boolean b) {
		gliding = b;
	}

	public void setFire(int fire, int maxFire) {
		this.fire = fire;
		this.maxFire = maxFire;
	}

	public void addFire(int fire) {
		this.fire += fire;
		if (this.fire > maxFire) {
			this.fire = maxFire;
		}
	}

	public void setDamage(int damage) {
		this.scratchDamage = damage;
		this.fireBallDamage = (int)(damage * 1.2);
	}

	public void setHealth(int health, int maxHealth) {
		this.health = health;
		this.maxHealth = maxHealth;
	}

	public void addHealth(int health) {
		this.health += health;
		if (this.health > maxHealth) {
			this.health = maxHealth;
		}
	}

	public int getHealth() {
		return health;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public int getFire() {
		return fire;
	}

	public int getMaxFire() {
		return maxFire;
	}

	public boolean isDead() {
		return dead;
	}

}
