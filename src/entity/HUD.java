package entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import gameState.Runner;

public class HUD {
	private Player player;
	private Enemy boss;
	private Runner runner;

	private BufferedImage health;
	private BufferedImage fire;
	private BufferedImage bar;
	private BufferedImage coin;
	private BufferedImage emptyHeart;
	private BufferedImage heart;

	private Font font;

	public HUD(Player player, Enemy boss) {
		this.player = player;
		this.boss = boss;

		try {

			bar = ImageIO.read(getClass().getResourceAsStream("/hud/HUD.png"));
			health = ImageIO.read(getClass().getResourceAsStream("/hud/Health.png"));
			fire = ImageIO.read(getClass().getResourceAsStream("/hud/Fire.png"));
			coin = ImageIO.read(getClass().getResourceAsStream("/hud/Coin.png"));

			font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/ARCADECLASSIC.TTF"));

		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	public HUD(Player player, Runner runner) {
		this.player = player;
		this.runner = runner;

		try {

			emptyHeart = ImageIO.read(getClass().getResourceAsStream("/hud/EmptyHeart.png"));
			heart = ImageIO.read(getClass().getResourceAsStream("/hud/Heart.png"));
			coin = ImageIO.read(getClass().getResourceAsStream("/hud/Coin.png"));
			font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/ARCADECLASSIC.TTF"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void drawRunnerState(Graphics2D graphic) {
		for (int i = 0; i < player.getMaxHealth(); i++) {
			graphic.drawImage(emptyHeart, 10 + i * 60, 30, null);
		}
		for (int i = 0; i < player.getHealth(); i++) {
			graphic.drawImage(heart, 10 + i * 60, 30, null);
		}

		graphic.setColor(Color.BLACK);
		graphic.setFont(font.deriveFont(Font.TRUETYPE_FONT, 150));
		switch ((int) runner.getTime()) {
		case 1:
			graphic.drawString("3", 100, 180);
			break;
		case 2:
			graphic.drawString("2", 100, 180);
			break;
		case 3:
			graphic.drawString("1", 100, 180);
			break;
		}

		graphic.drawImage(coin, 600, 25, 75, 75, null);
		graphic.setFont(font.deriveFont(Font.BOLD, 60));
		graphic.drawString("X " + MapObject.coinsCollected, 670, 80);
	}

	public void draw(Graphics2D graphic) {
		if (player.getX() > boss.getX() - 1000) {
			if (!boss.isDead()) {
				graphic.setColor(Color.BLACK);
				graphic.fillRect(340, 40, (int) (boss.getMaxHealth() * (300.0 / boss.getMaxHealth()) + 20), 50);
			}
			graphic.setColor(Color.RED);
			graphic.fillRect(350, 50, (int) (boss.getHealth() * (300.0 / boss.getMaxHealth())), 30);

		}

		graphic.setColor(Color.RED);
		graphic.fillRect(50, 50, (int) (player.getHealth() * (250.0 / player.getMaxHealth())), 30);
		graphic.setColor(Color.ORANGE);
		graphic.fillRect(50, 100, (int) (player.getFire() * (250.0 / player.getMaxFire())), 30);
		graphic.setColor(Color.BLACK);
		graphic.setFont(font.deriveFont(Font.TRUETYPE_FONT, 30));
		graphic.drawString("X" + MapObject.coinsCollected, 750, 50);

		graphic.drawImage(bar, 30, 50, 290, 100, null);
		graphic.drawImage(bar, 30, 0, 290, 100, null);
		graphic.drawImage(health, 45, 35, null);
		graphic.drawImage(fire, 45, 85, null);
		graphic.drawImage(coin, 720, 25, 32, 32, null);
	}
}
