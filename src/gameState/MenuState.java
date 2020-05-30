package gameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import audio.AudioPlayer;
import tileMap.Background;

public class MenuState extends GameState {

	private Background background;
	private AudioPlayer bgMusic;
	private AudioPlayer selectSound;
	private int currentChoice = 0;
	private String[] options = { " Play", " Help", " Quit" };

	private Color titleColor;
	private Font titleFont;
	private Font font;

	public MenuState(GameStateManager gsm) {
		this.gsm = gsm;

		titleColor = new Color(128, 0, 0);

		try {
			titleFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/pcsenior.ttf"));
			font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/ARCADECLASSIC.TTF"));
			background = new Background("/backgrounds/MenuBG.jpg", 0.5);
			background.setVector(-0.1, 0);

		} catch (Exception e) {
			e.printStackTrace();
		}

		bgMusic = new AudioPlayer("/musics/MainMenu.mp3");
		selectSound = new AudioPlayer("/musics/MenuSelect.mp3");
		bgMusic.play(true);
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
		graphic.setColor(titleColor);
		graphic.setFont(titleFont.deriveFont(Font.HANGING_BASELINE, 75));
		graphic.drawString("Fairy Cat", 70, 120);

		// Draw menu options
		graphic.setFont(font.deriveFont(Font.TRUETYPE_FONT, 50));
		for (int i = 0; i < options.length; i++) {
			graphic.setColor(Color.BLACK);
			graphic.fillRect(340, 250 + i * 60, 125, 65);
			if (i == currentChoice) {
				graphic.setColor(Color.WHITE);

			} else {
				graphic.setColor(Color.RED);
			}

			graphic.fillRect(340, 250 + i * 60, 120, 60);

			if (i == currentChoice) {
				graphic.setColor(Color.BLACK);

			} else {
				graphic.setColor(Color.ORANGE);
			}
			graphic.drawString(options[i], 340, 300 + i * 60);
		}
	}

	public void select() {
		switch (currentChoice) {
		case 0:
			gsm.setState(GameStateManager.LEVEL1STATE);
			break;
		case 1:
			gsm.setState(GameStateManager.HELPSTATE);
			break;
		case 2:
			gsm.setState(GameStateManager.WORLDSTATE);
			GameStateManager.cat.setX(2175);
			GameStateManager.cat.setY(235);
			GameStateManager.cat.reset();
			break;
		}
	}

	@Override
	public void keyPressed(int key) {
		if (key == KeyEvent.VK_ENTER) {
			bgMusic.close();
			selectSound.play(false);
			select();
		}
		if (key == KeyEvent.VK_W) {
			currentChoice--;
			if (currentChoice == -1) {
				currentChoice = options.length - 1;
			}
		}
		if (key == KeyEvent.VK_S) {
			currentChoice++;
			if (currentChoice == options.length) {
				currentChoice = 0;
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
