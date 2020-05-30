package gameState;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import audio.AudioPlayer;
import entity.Coins;
import entity.Enemy;
import entity.HUD;
import entity.HealthPotion;
import entity.Player;
import entity.enemies.runner.Bird1;
import entity.enemies.runner.Bird2;
import entity.enemies.runner.BlackSpider;
import entity.enemies.runner.Slugger;
import entity.enemies.runner.Snake;
import entity.enemies.runner.Squirrel;
import main.GamePanel;
import tileMap.Background;
import tileMap.TileMap;

public class Runner extends GameState {
	public static boolean isRunnerGame;

	private TileMap tileMap;
	private HUD hud;
	private AudioPlayer bgMusic;
	private Background background;
	private int currentBackground;

	private Player player;

	private ArrayList<Coins> coins;
	private ArrayList<HealthPotion> healthPotion;
	private ArrayList<Enemy> enemies;

	private long startTime;
	private long currentTime;
	private long timePlayed;

	private int backgroundTimer;
	private int coinsTimer;
	private int potionTimer;
	private int blackSpiderTimer;
	private int sluggerTimer;
	private int snakeTimer;
	private int bird1Timer;
	private int bird2Timer;
	private int squirrelTimer;

	private double moveScale;

	public Runner(GameStateManager gsm) {
		isRunnerGame = true;
		startTime = System.nanoTime() / 1000000000;

		backgroundTimer = 60;
		coinsTimer = 5;
		potionTimer = 30;
		blackSpiderTimer = 7;
		sluggerTimer = 5;
		snakeTimer = 12;
		bird1Timer = 15;
		bird2Timer = 35;
		squirrelTimer = 120;
		moveScale = 0;

		try {

		} catch (Exception e) {

		}

		coins = new ArrayList<Coins>();
		enemies = new ArrayList<Enemy>();
		healthPotion = new ArrayList<HealthPotion>();
		this.gsm = gsm;

		bgMusic = new AudioPlayer("/musics/EndlessRun.mp3");
		bgMusic.play(true);
		init();
	}

	@Override
	public void init() {
		tileMap = new TileMap(75);
		tileMap.loadTiles("/tilesets/Runner.gif");
		tileMap.loadMap("/maps/Runner.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);

		currentBackground = 1;
		background = new Background("/backgrounds/runner/BG_" + currentBackground + ".png", 30 + moveScale / 10);
		background.setVector(0, 0);

		player = new Player(tileMap);
		player.setPosition(65, 650);
		player.setSpeed(0.1, 0.2);
		player.setHealth(5, 5);

		hud = new HUD(player, this);
	}

	public void populateEntities() {
		if (timePlayed / coinsTimer >= 1) {
			Coins coin = new Coins(tileMap);
			coin.setPosition(player.getX() + 800, player.getY() - (int) (10 + Math.random() * 50));
			coin.setSpeed(1.5, 4 + moveScale / 10);
			coins.add(coin);
			coinsTimer += 5;
		}

		if (timePlayed / potionTimer >= 1) {
			HealthPotion potion = new HealthPotion(tileMap);
			potion.setPosition(player.getX() + 800, player.getY() - (int) (10 + Math.random() * 50));
			potion.setSpeed(2, 3 + moveScale / 10);
			healthPotion.add(potion);
			potionTimer += 40;
		}
	}

	public void populateEnemies() {
		if (timePlayed / blackSpiderTimer >= 1) {
			BlackSpider spider = new BlackSpider(tileMap);
			spider.setPosition(player.getX() + (int) (800 + Math.random() * 100), player.getY());
			spider.setSpeed(1.75, 6 + moveScale / 10);
			enemies.add(spider);
			blackSpiderTimer += 7;
		}

		if (timePlayed / sluggerTimer >= 1) {
			Slugger slugger = new Slugger(tileMap);
			slugger.setPosition(player.getX() + (int) (800 + Math.random() * 75), player.getY());
			slugger.setSpeed(1.5, 5 + moveScale / 10);
			enemies.add(slugger);
			sluggerTimer += 5;
		}

		if (timePlayed / snakeTimer >= 1) {
			Snake snake = new Snake(tileMap);
			snake.setPosition(player.getX() + (int) (800 + Math.random() * 125), player.getY());
			snake.setSpeed(2.0, 7 + moveScale / 10);
			enemies.add(snake);
			snakeTimer += 12;
		}

		if (timePlayed / bird1Timer >= 1) {
			Bird1 bird = new Bird1(tileMap);
			bird.setPosition(player.getX() + 800, player.getY() - (int) (10 + Math.random() * 40));
			bird.setSpeed(2.2, 10 + moveScale / 10);
			enemies.add(bird);
			bird1Timer += 14;
		}

		if (timePlayed / bird2Timer >= 1) {
			Bird2 bird = new Bird2(tileMap);
			bird.setPosition(player.getX() + 800, player.getY() - (int) (10 + Math.random() * 40));
			bird.setSpeed(2.4, 11 + moveScale / 10);
			enemies.add(bird);
			bird2Timer += 15;
		}

		if (timePlayed / squirrelTimer >= 1) {
			Squirrel squirrel = new Squirrel(tileMap);
			squirrel.setPosition(player.getX() + (int) (800 + Math.random() * 150), player.getY());
			squirrel.setSpeed(2.0, 8 + moveScale / 10);
			enemies.add(squirrel);
			squirrelTimer += 10;
		}
	}

	@Override
	public void update() {
		player.setRight(true);
		populateEntities();
		populateEnemies();

		// Update timer
		currentTime = System.nanoTime() / 1000000000;
		timePlayed = currentTime - startTime;

		// Update moveScale
		moveScale = timePlayed / 5;

		// Set background
		if (timePlayed / backgroundTimer >= 1) {
			currentBackground++;
			if (currentBackground > 6)
				currentBackground = 1;
			background = new Background("/backgrounds/runner/BG_" + currentBackground + ".png", 30 + moveScale);
			backgroundTimer += 60;
		}
		background.update();
		background.setRunnerPosition(tileMap.getX(), tileMap.getY());

		// Update player
		player.update();
		tileMap.setPosition(-player.getX() + 100, GamePanel.HEIGHT / 2 - player.getY());

		// Attack enemies
		player.checkAttack(enemies);

		// Update all the enemies
		for (int i = 0; i < enemies.size(); i++) {
			Enemy enemy = enemies.get(i);
			enemy.update();
			if (enemies.get(i).getX() <= player.getX() - 30) {
				enemies.remove(i);
				i--;
			}
		}

		// Update coins
		for (int i = 0; i < coins.size(); i++) {
			Coins coin = coins.get(i);
			coin.update();
			if (player.intersects(coins.get(i))) {
				coins.get(i).isCollected(true);
				coins.get(i).addCoins();
				coins.remove(i);
				i--;
			} else if (!player.intersects(coins.get(i)) && coins.get(i).getX() <= player.getX() - 30) {
				coins.remove(i);
				i--;
			}
		}

		// Update potions
		for (int i = 0; i < healthPotion.size(); i++) {
			HealthPotion potion = healthPotion.get(i);
			potion.update();
			if (player.intersects(potion)) {
				potion.isEaten(true);
				potion.addHealth(player);
				healthPotion.remove(i);
				i--;
			} else if (!player.intersects(potion) && healthPotion.get(i).getX() <= player.getX() - 30) {
				healthPotion.remove(i);
				i--;
			}
		}

		// When player isDead
		if (player.isDead()) {
			gsm.setState(GameStateManager.GAMEOVERSTATE);
			bgMusic.close();
		}

		display();
	}

	@Override
	public void draw(Graphics2D graphic) {
		// Clear screen
		background.draw(graphic);

		// Draw tileMap
		tileMap.draw(graphic);

		// Draw player
		player.draw(graphic);

		// Draw enemies
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(graphic);
		}

		// Draw coins
		for (int i = 0; i < coins.size(); i++) {
			coins.get(i).draw(graphic);
		}

		// Draw healthPotion
		for (int i = 0; i < healthPotion.size(); i++) {
			healthPotion.get(i).draw(graphic);
		}

		// Draw HUD
		hud.drawRunnerState(graphic);
	}

	public long getTime() {
		return this.timePlayed;
	}

	public void display() {
		System.out.println("Player_X: " + player.getX() + "\tPlayer_Y: " + player.getY());
		System.out.println("Tilemap_X: " + tileMap.getX() + "\tTilemap_Y: " + tileMap.getY());
		System.out.println("TimePlayed: " + timePlayed);
		System.out.println("MoveScale: " + moveScale);
		System.out.println("Health: " + player.getHealth());
	}

	@Override
	public void keyPressed(int key) {
		if (key == KeyEvent.VK_SPACE)
			player.setUp(true);
		if (key == KeyEvent.VK_SPACE)
			player.setJumping(true);
	}

	@Override
	public void keyReleased(int key) {
		if (key == KeyEvent.VK_SPACE)
			player.setUp(false);
		if (key == KeyEvent.VK_SPACE)
			player.setJumping(false);
	}

	@Override
	public void keyTyped(int key) {
		// TODO Auto-generated method stub

	}

}
