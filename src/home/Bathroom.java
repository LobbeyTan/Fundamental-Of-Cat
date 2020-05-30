package home;

import static info.Info.tireness;
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

public class Bathroom extends GameState {
	private TileMap tileMap;
	private AudioPlayer bathing;
	private Background background;
	private int backgroundNumber;
	private int currentBackground;
	private boolean isBathing;
	// Draw button
	private boolean drawButton;
	private int currentChoice = 0;
	private String[] options = { " Bath", " Back", };
	private Font font;

	private long startTime;
	private long currentTime;
	private long timePlayed;
	private boolean timerReset;

	private int backgroundTimer;

	private HomeCat cat;

	public Bathroom(GameStateManager gsm) {
		this.gsm = gsm;
		startTime = System.nanoTime() / 1000000000;

		try {
			font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/pdark.TTF"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		backgroundTimer = 1;
		backgroundNumber = 4;
		currentBackground = 0;
		background = new Background("/backgrounds/home/bathroom/Bathroom_" + currentBackground + ".jpg", 1);
		background.setVector(0, 0);

		bathing = new AudioPlayer("/musics/Bathing.mp3");
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

		// Set background
		if (timePlayed / backgroundTimer >= 1 && isBathing) {
			currentBackground++;
			if (currentBackground >= backgroundNumber) {
				currentBackground = 0;
				backgroundTimer = 1;
				isBathing = false;
				bathing.stop();
				cat.setPosition(300, 500);
				tireness -= 10 + Math.random() * 10;
			}
			background = new Background("/backgrounds/home/bathroom/Bathroom_" + currentBackground + ".jpg", 1);
			backgroundTimer += 1;
		}

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
		if (!isBathing)
			cat.draw(graphic);

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
			bathing.play(false);
			isBathing = true;
			timerReset = true;
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
		
		if (key == KeyEvent.VK_H && !isBathing)
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
		if (key == KeyEvent.VK_H && !isBathing) {
			select();
			drawButton = false;
		}
		if (key == KeyEvent.VK_A)
			cat.setLeft(false);
		if (key == KeyEvent.VK_D)
			cat.setRight(false);
	}

	public void display() {
		System.out.println(
				"TimePlayed: " + timePlayed + "\nCurrentBackground: " + currentBackground + "\nIs bathing: " + isBathing
						+ "\nIs playing: " + bathing.isPlaying() + "\nCat_X: " + cat.getX() + "\nCat_Y: " + cat.getY());
	}

	@Override
	public void keyTyped(int key) {
		// TODO Auto-generated method stub

	}

}
