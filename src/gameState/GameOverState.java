package gameState;

import static info.Info.munny;
import static info.Info.happiness;
import static info.Info.tireness;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import audio.AudioPlayer;
import entity.MapObject;
import tileMap.Background;

public class GameOverState extends GameState {
	private BufferedImage coin;
	private Background background;
	private AudioPlayer bgMusic;

	private int currentSelection = 0;

	private String[] options = { "Yes", "No" };

	private Font titleFont;
	private Font font;

	public GameOverState(GameStateManager gsm) {
		this.gsm = gsm;

		try {
			background = new Background("/backgrounds/GameOverBG.png", 100);
			background.setVector(-0.1, 0);

			font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/game_over.ttf"));
			titleFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/ARCADE.TTF"));

			coin = ImageIO.read(getClass().getResourceAsStream("/hud/Coin.png"));

		} catch (Exception e) {
			e.printStackTrace();
		}

		bgMusic = new AudioPlayer("/musics/GameOver.mp3");
		bgMusic.play(false);
	}

	@Override
	public void init() {

	}

	@Override
	public void update() {
		background.update();
	}

	@Override
	public void draw(Graphics2D graphic) {
		background.draw(graphic);

		// Draw title
		graphic.setColor(Color.RED);
		graphic.setFont(titleFont.deriveFont(Font.BOLD, 150));
		graphic.drawString("Game Over", 30, 150);

		// Draw text box
		graphic.setColor(Color.WHITE);
		graphic.setFont(font.deriveFont(Font.PLAIN, 100));
		graphic.drawString("PLAY AGAIN ?", 270, 350);

		graphic.drawImage(coin, 370, 150, 75, 75, null);
		graphic.drawString("X" + MapObject.coinsCollected, 385, 250);

		// Draw options
		for (int i = 0; i < options.length; i++) {
			if (i == currentSelection) {
				graphic.setColor(Color.MAGENTA);
				graphic.setFont(font.deriveFont(Font.PLAIN, 100));

			} else {
				graphic.setColor(Color.WHITE);
			}
			graphic.drawString(options[i], 290 + i * 150, 425);
		}
	}

	public void select() {
		munny += MapObject.coinsCollected;
		MapObject.coinsCollected = 0;
		happiness += 25 + (int) (Math.random() * 10);
		tireness += 15 + (int) (Math.random() * 10);
		bgMusic.stop();
		
		if (Runner.isRunnerGame) {
			switch (currentSelection) {
			case 0:
				gsm.setState(GameStateManager.RUNNERGAMESTATE);
				break;
			case 1:
				gsm.setState(GameStateManager.WORLDSTATE);
				GameStateManager.cat.setX(1455);
				GameStateManager.cat.setY(2239);
				GameStateManager.cat.reset();
				break;
			}
		} else {
			switch (currentSelection) {
			case 0:
				gsm.setState(GameStateManager.LEVEL1STATE);
				break;
			case 1:
				gsm.setState(GameStateManager.MENUSTATE);
				break;
			}
		}
	}

	@Override
	public void keyPressed(int key) {
		if (key == KeyEvent.VK_ENTER) {
			select();
		}
		if (key == KeyEvent.VK_A) {
			currentSelection--;
			if (currentSelection == -1) {
				currentSelection = options.length - 1;
			}
		}
		if (key == KeyEvent.VK_D) {
			currentSelection++;
			if (currentSelection == options.length) {
				currentSelection = 0;
			}
		}
	}

	@Override
	public void keyReleased(int key) {

	}

	@Override
	public void keyTyped(int key) {
		// TODO Auto-generated method stub

	}
}
