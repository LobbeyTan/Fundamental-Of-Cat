package gameState;

import static info.Info.TTL;
import static info.Info.happiness;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import audio.AudioPlayer;
import tileMap.Background;

public class EndingState extends GameState {
	private BufferedImage coffin;
	private Background background;
	private AudioPlayer bgMusic;

	private boolean isLeaving;
	private boolean isDead;
	
	private Font font;

	public EndingState(GameStateManager gsm) {
		this.gsm = gsm;

		try {
			font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/game_over.ttf"));
			coffin = ImageIO.read(getClass().getResourceAsStream("/backgrounds/Coffin.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		init();
	}

	@Override
	public void init() {
		if(happiness <= 0) {
			background = new Background("/backgrounds/Leaving.jpg", 0);
			isLeaving = true;
		}else if(TTL <= 0) {
			background = new Background("/backgrounds/Black.jpg", 0); 
			isDead = true;
		}else {
			return;
		}
		
		bgMusic = new AudioPlayer("/musics/Ending.mp3");
		bgMusic.play(false);
	}

	@Override
	public void update() {
		background.update();
	}

	@Override
	public void draw(Graphics2D graphic) {
		background.draw(graphic);
		
		if(isLeaving) {
			graphic.setFont(font.deriveFont(Font.TRUETYPE_FONT, 80));
			graphic.setColor(Color.RED);
			graphic.drawString("YOUR CAT IS LEAVING", 400, 300);
		}else if(isDead) {
			graphic.setColor(Color.RED);
			graphic.drawString("YOUR CAT IS DEAD", 250, 300);
			graphic.drawImage(coffin, 325, 300, 150, 150, null);
		}else {
			return;
		}
		graphic.setColor(Color.LIGHT_GRAY);
		graphic.setFont(font.deriveFont(Font.TRUETYPE_FONT, 80));
		graphic.drawString("BE A BETTER OWNER NEXT TIME", 150, 620);
		graphic.drawString("Press ENTER to EXIT", 250, 550);
	}



	@Override
	public void keyPressed(int key) {
		if (key == KeyEvent.VK_ENTER) {
			System.exit(0);
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
