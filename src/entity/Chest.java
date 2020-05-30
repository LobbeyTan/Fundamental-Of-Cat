package entity;

import static info.Info.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import audio.AudioPlayer;
import tileMap.TileMap;

public class Chest extends MapObject {
	private boolean isCollected;
	private boolean hasCollectedOnce;
	// Animations
	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = { 1, 3 };

	// Animation actions
	private static final int IDLE = 0;
	private static final int COLLECTION = 1;

	private AudioPlayer soundEffect;

	public Chest(TileMap tilemap) {
		super(tilemap);

		fallSpeed = 0.5;
		maxFallSpeed = 25.0;

		width = 75;
		height = 75;
		collisionWidth = 50;
		collisionHeight = 50;
		hasCollectedOnce = false;
		isCollected = false;

		try {

			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/sprites/entities/Chest.gif"));

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

		soundEffect = new AudioPlayer("/musics/ChestSound.mp3");

		animation = new Animation();
		currentAnimation = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(400);
	}

	private void getNextPosition() {
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

		if (currentAnimation == COLLECTION) {
			if (animation.hasPlayedOnce())
				return;
		}

		// Set animation
		if (isCollected) {
			if (currentAnimation != COLLECTION) {
				currentAnimation = COLLECTION;
				animation.setFrames(sprites.get(COLLECTION));
				animation.setDelay(70);
				width = 75;
				isCollected = true;
				hasCollectedOnce = true;
			}
		}

		else {
			if (currentAnimation != IDLE) {
				currentAnimation = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelay(400);
				width = 75;
			}
		}

		// Update animation
		animation.update();
	}

	@Override
	public void draw(Graphics2D graphic) {
		setMapPosition();
		super.draw(graphic);
	}

	public void isCollected(boolean isCollected) {
		this.isCollected = isCollected;
	}

	public void addCoins() {
		if (isCollected && !hasCollectedOnce) {
			coinsCollected += (int) (Math.random() * 10 + (happiness / 10 - tireness / 15 + level * 2));
			System.out.println(true);
			soundEffect.play(false);
		}
	}

}
