package entity.enemies.level_1;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import audio.AudioPlayer;
import entity.Animation;
import entity.Enemy;
import entity.Player;
import tileMap.TileMap;

public class EvilBoss extends Enemy {
	// Player
	private Player player;

	// Magic Attack
	private boolean spelling;
	private int magicDamage;
	private int magicRange;

	// Animations
	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = { 4, 6 };

	// Animation actions
	private static final int MOVEMENT = 0;
	private static final int SPELLING = 1;

	// Magic attack sound
	private AudioPlayer magicAttack;

	// Static movement
	private int xSpawn;
	private int moveRange;

	// Set cooldown
	private long startTime;
	private long currentTime;
	private long elapsed;
	private long cooldown;
	private boolean isCoolingDown;

	public EvilBoss(TileMap tileMap, Player player) {
		super(tileMap);
		this.player = player;

		startTime = System.currentTimeMillis();
		cooldown = 1000;
		isCoolingDown = false;

		width = 100;
		height = 150;
		collisionWidth = 75;
		collisionHeight = 150;

		moveSpeed = 0.5 + (Math.random() * 1);
		maxSpeed = 2.5;
		stopSpeed = 1.0;

		right = true;
		facingRight = true;

		health = maxHealth = 40;

		damage = 3;
		magicDamage = 5;
		magicRange = 150;

		moveRange = 250;
		// load Sprite;
		try {

			BufferedImage spritesheet = ImageIO
					.read(getClass().getResourceAsStream("/sprites/enemies/level_1/EvilBoss.png"));

			sprites = new ArrayList<BufferedImage[]>();

			for (int i = 0; i < numFrames.length; i++) {
				BufferedImage[] movement = new BufferedImage[numFrames[i]];
				for (int j = 0; j < numFrames[i]; j++) {

					if (i != 1) {
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
		currentAnimation = MOVEMENT;
		animation.setFrames(sprites.get(MOVEMENT));
		animation.setDelay(200);

		magicAttack = new AudioPlayer("/musics/MagicAttack.mp3");
	}

	public void checkAttack() {
		// Magic Attack
		if (spelling) {
			if (facingRight) {
				if (player.getX() > x && player.getX() < x + magicRange && player.getY() > y - height / 2
						&& player.getY() < y + height / 2) {
					player.hit(magicDamage);
				}
			} else {
				if (player.getX() < x && player.getX() > x - magicRange && player.getY() > y - height / 2
						&& player.getY() < y + height / 2) {
					player.hit(magicDamage);
				}
			}
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

		if (currentAnimation == SPELLING) {
			dx = 0.001;
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
		if (currentAnimation == SPELLING) {
			if (animation.hasPlayedOnce())
				spelling = false;
			startTime = currentTime;
		}

		// Check attack range

		if (!spelling && !isCoolingDown) {
			if (facingRight) {
				if (player.getX() > x && player.getX() < x + magicRange && player.getY() > y - height / 2
						&& player.getY() < y + height / 2) {
					spelling = true;
				}
			} else {
				if (player.getX() < x && player.getX() > x - magicRange && player.getY() > y - height / 2
						&& player.getY() < y + height / 2) {
					spelling = true;
				}
			}
		}

		System.out.println(elapsed);

		// check flinching
		if (flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed > 400) {
				flinching = false;
			}
		}

		// Set animation
		if (spelling) {
			if (currentAnimation != SPELLING) {
				magicAttack.play(false);
				currentAnimation = SPELLING;
				animation.setFrames(sprites.get(SPELLING));
				animation.setDelay(70);
				width = 200;
				spelling = false;
			}
		}

		else {
			if (currentAnimation != MOVEMENT) {
				currentAnimation = MOVEMENT;
				animation.setFrames(sprites.get(MOVEMENT));
				animation.setDelay(400);
				width = 100;
			}
		}

		// Update animation
		animation.update();

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

		// Set direction
		if (currentAnimation != SPELLING) {
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
