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

public class WinState extends GameState {
	private Background background;
	private AudioPlayer bgMusic;
	private BufferedImage scoreBoard;

	private Font font;

	public WinState(GameStateManager gsm) {
		this.gsm = gsm;

		try {
			background = new Background("/backgrounds/Level_1BG.gif", 1);
			background.setVector(-0.1, 0);

			scoreBoard = ImageIO.read(getClass().getResourceAsStream("/sprites/entities/ScoreBoard.png"));

			font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/pdark.ttf"));

		} catch (Exception e) {
			e.printStackTrace();
		}

		bgMusic = new AudioPlayer("/musics/Success.mp3");
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
		graphic.drawImage(scoreBoard, 0, -100, 800, 800, null);

		graphic.setFont(font.deriveFont(Font.TRUETYPE_FONT, 40));
		graphic.setColor(Color.BLACK);
		graphic.drawString("LEVEL " + GameState.currentLevel, 300, 100);
		graphic.drawString("COMPLETE!", 270, 150);

		graphic.drawString("X" + MapObject.coinsCollected, 370, 390);
		graphic.drawString("Enter", 340, 560);
	}

	public void select() {
		munny += MapObject.coinsCollected;
		MapObject.coinsCollected = 0;
		happiness += 30 + (int) (Math.random() * 15);
		tireness -= 10 + (int) (Math.random() * 10);
		gsm.setState(GameStateManager.MENUSTATE);
	}

	@Override
	public void keyPressed(int key) {
		if (key == KeyEvent.VK_ENTER) {
			select();
		}
	}

	@Override
	public void keyReleased(int key) {

	}

	@Override
	public void keyTyped(int key) {

	}

}
