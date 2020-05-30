package home;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import audio.AudioPlayer;
import gameState.GameState;
import gameState.GameStateManager;
import main.GamePanel;
import tileMap.Background;
import tileMap.TileMap;

public class LivingRoom extends GameState {
	public static AudioPlayer bgMusic = new AudioPlayer("/musics/Home.mp3");

	private TileMap tileMap;

	private Background background;
	// Draw button
	private boolean drawButton;
	private int currentChoice = 0;
	private String[] options = { " Bathroom", " Bedroom ", " Outing ", };
	private Font font;

	private HomeCat cat;

	public LivingRoom(GameStateManager gsm) {
		this.gsm = gsm;

		try {
			font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/pdark.TTF"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		background = new Background("/backgrounds/home/livingroom/LivingRoom.jpg", 1);
		background.setVector(0, 0);

		if (!bgMusic.isPlaying()) {
			bgMusic.play(true);
		}

		init();
	}

	@Override
	public void init() {
		tileMap = new TileMap(75);
		tileMap.loadTiles("/tilesets/Home.gif");
		tileMap.loadMap("/maps/Home.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);

		cat = new HomeCat(tileMap);
		cat.setPosition(65, 100);
	}

	@Override
	public void update() {
		// Update player
		cat.update();
		tileMap.setPosition(GamePanel.WIDTH / 2 - cat.getX(), GamePanel.HEIGHT / 2 - cat.getY());

		background.update();
		background.setRunnerPosition(tileMap.getX(), tileMap.getY());

		GameStateManager.cat.getInventory().update();
		GameStateManager.cat.getInfo().update();
	}

	@Override
	public void draw(Graphics2D graphic) {
		// Draw background
		background.draw(graphic);

		// Draw tileMap
		tileMap.draw(graphic);

		// Draw player
		cat.draw(graphic);

		// Draw button
		if (drawButton) {
			graphic.setFont(font.deriveFont(Font.TRUETYPE_FONT, 30));
			for (int i = 0; i < options.length; i++) {
				if (i == currentChoice) {
					graphic.setColor(Color.BLUE);

				} else {
					graphic.setColor(Color.RED);
				}

				graphic.fillRoundRect(550, 400 + i * 80, 230, 60, 50, 50);

				if (i == currentChoice) {
					graphic.setColor(Color.BLACK);

				} else {
					graphic.setColor(Color.ORANGE);
				}
				graphic.drawString(options[i], 550, 440 + i * 80);
			}
		}
		
		GameStateManager.cat.getInventory().draw(graphic);
		GameStateManager.cat.getInfo().draw(graphic);

	}

	public void select() {
		switch (currentChoice) {
		case 0:
			gsm.setState(GameStateManager.BATHROOM);
			break;
		case 1:
			gsm.setState(GameStateManager.BEDROOM);
			break;
		case 2:
			gsm.setState(GameStateManager.WORLDSTATE);
			GameStateManager.cat.setX(120);
			GameStateManager.cat.setY(200);
			GameStateManager.cat.reset();
			bgMusic.stop();
			break;
		}
	}

	@Override
	public void keyPressed(int key) {
		if (key == KeyEvent.VK_E && !GameStateManager.cat.getInfo().isActive()) GameStateManager.cat.getInventory().setOpenInventory(true);
		if (key == KeyEvent.VK_I && !GameStateManager.cat.getInventory().isActive()) GameStateManager.cat.getInfo().setOpenInfo(true);
		
		if(GameStateManager.cat.getInventory().isActive()) {
			if (key == KeyEvent.VK_W)
				GameStateManager.cat.getInventory().setUp(true);
			if (key == KeyEvent.VK_S)
				GameStateManager.cat.getInventory().setDown(true);
			if (key == KeyEvent.VK_J)
				GameStateManager.cat.getInventory().setSelect(true);
		}
		
		if (key == KeyEvent.VK_H)
			drawButton = true;
		if (key == KeyEvent.VK_A)
			cat.setLeft(true);
		if (key == KeyEvent.VK_D)
			cat.setRight(true);
		
		if(!GameStateManager.cat.getInventory().isActive() && drawButton == true) {
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
	}

	@Override
	public void keyReleased(int key) {
		if (key == KeyEvent.VK_H) {
			select();
			drawButton = false;
		}
		if (key == KeyEvent.VK_A)
			cat.setLeft(false);
		if (key == KeyEvent.VK_D)
			cat.setRight(false);
	}

	@Override
	public void keyTyped(int key) {
		// TODO Auto-generated method stub

	}

}
