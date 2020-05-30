package home;

import static info.Info.tireness;
import static home.LivingRoom.bgMusic;
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

public class Bedroom extends GameState {
	private TileMap tileMap;
	private AudioPlayer sleeping;
	private Background background;
	private boolean isSleeping;
	// Draw button
	private boolean drawButton;
	private int currentChoice = 0;
	private String[] options = { " Sleep", " Back", };
	private Font font;

	private long startTime;
	private long currentTime;
	private long timePlayed;
	private boolean timerReset;

	private HomeCat cat;

	public Bedroom(GameStateManager gsm) {
		this.gsm = gsm;
		startTime = System.nanoTime() / 1000000000;

		try {
			font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/pdark.TTF"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		background = new Background("/backgrounds/home/bedroom/Bedroom.jpg", 1);
		background.setVector(0, 0);

		sleeping = new AudioPlayer("/musics/Sleeping.mp3");
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

		// Update timer
		currentTime = System.nanoTime() / 1000000000;
		if (timerReset) {
			startTime = currentTime;
			timerReset = false;
		}
		timePlayed = currentTime - startTime;

		if (isSleeping) {
			tireness -= timePlayed * 5;
			if (tireness <= 0) {
				isSleeping = false;
				sleeping.stop();
				bgMusic.play(true);
				cat.setPosition(715, 500);
			} else {
				isSleeping = true;
				startTime = currentTime;
			}
		}

		background.update();
		background.setRunnerPosition(tileMap.getX(), tileMap.getY());
		
		GameStateManager.cat.getInventory().update();
		if(!isSleeping)GameStateManager.cat.getInfo().update();

	}

	@Override
	public void draw(Graphics2D graphic) {
		// Draw tileMap
		tileMap.draw(graphic);

		// Draw background
		background.draw(graphic);

		if (!isSleeping) {
			// Draw player
			cat.draw(graphic);
		} else {
			graphic.setColor(Color.BLACK);
			graphic.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
			graphic.setColor(Color.WHITE);
			graphic.setFont(font.deriveFont(Font.ROMAN_BASELINE, 30));
			graphic.drawString("Tiredness", 300, 100);
			graphic.setColor(Color.darkGray);
			graphic.fillRoundRect(150, 150, 500, 50, 50, 50);
			graphic.setColor(Color.GRAY);
			graphic.fillRect(175, 160, 450, 30);
			graphic.setColor(Color.LIGHT_GRAY);
			graphic.fillRect(175, 160, (int) (450 * tireness / 100.0), 30);
		}

		// Draw button
		if (drawButton) {
			graphic.setFont(font.deriveFont(Font.TRUETYPE_FONT, 50));
			for (int i = 0; i < options.length; i++) {
				if (i == currentChoice) {
					graphic.setColor(Color.BLUE);

				} else {
					graphic.setColor(Color.RED);
				}

				graphic.fillRoundRect(590, 450 + i * 80, 200, 60, 50, 50);

				if (i == currentChoice) {
					graphic.setColor(Color.BLACK);

				} else {
					graphic.setColor(Color.ORANGE);
				}
				graphic.drawString(options[i], 590, 500 + i * 80);
			}
		}

		GameStateManager.cat.getInventory().draw(graphic);
		GameStateManager.cat.getInfo().draw(graphic);
		display();
	}

	public void select() {
		switch (currentChoice) {
		case 0:
			if (tireness >= 80) {
				sleeping.play(false);
				bgMusic.stop();
				isSleeping = true;
				timerReset = true;
			}
			break;
		case 1:
			gsm.setState(GameStateManager.LIVINGROOM);
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
		
		if (key == KeyEvent.VK_H && !isSleeping)
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
		if (key == KeyEvent.VK_H && !isSleeping) {
			select();
			drawButton = false;
		}
		if (key == KeyEvent.VK_A)
			cat.setLeft(false);
		if (key == KeyEvent.VK_D)
			cat.setRight(false);
	}

	public void display() {
		System.out.println("TimePlayed: " + timePlayed + "\nTireness: " + tireness + "\nIs sleeping: " + isSleeping
				+ "\nCat_X: " + cat.getX() + "\nCat_Y: " + cat.getY());
	}

	@Override
	public void keyTyped(int key) {
		// TODO Auto-generated method stub

	}

}
