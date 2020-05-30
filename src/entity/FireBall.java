package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import audio.AudioPlayer;
import tileMap.TileMap;

public class FireBall extends MapObject {
	private boolean hit;
	private boolean remove;
	private BufferedImage[] sprites;
	private BufferedImage[] hitSprites;
	private AudioPlayer fireball;

	public FireBall(TileMap tileMap, boolean right) {
		super(tileMap);

		facingRight = right;

		moveSpeed = 9.5;

		if (right) {
			dx = moveSpeed;
		} else {
			dx = -moveSpeed;
		}

		width = 75;
		height = 75;
		collisionWidth = 35;
		collisionHeight = 35;

		// Load sprites
		try {

			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/sprites/player/fireball.gif"));

			sprites = new BufferedImage[4];
			for (int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
			}

			hitSprites = new BufferedImage[3];
			for (int i = 0; i < hitSprites.length; i++) {
				hitSprites[i] = spritesheet.getSubimage(i * width, height, width, height);
			}

			animation = new Animation();
			animation.setFrames(sprites);
			animation.setDelay(70);

			fireball = new AudioPlayer("/musics/Fireball.mp3");
			fireball.play(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setHit() {
		if (hit)
			return;

		hit = true;
		animation.setFrames(hitSprites);
		animation.setDelay(70);
		dx = 0;
	}

	public boolean shouldRemove() {
		return remove;
	}

	public void update() {
		checkTileMapCollision();
		setPosition(xtemp, ytemp);

		if (dx == 0 && !hit) {
			setHit();
		}

		animation.update();

		if (hit && animation.hasPlayedOnce()) {
			remove = true;
		}
	}

	@Override
	public void draw(Graphics2D graphic) {
		setMapPosition();

		super.draw(graphic);
	}
}
