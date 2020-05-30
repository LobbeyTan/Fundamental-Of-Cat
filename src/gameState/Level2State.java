package gameState;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import audio.AudioPlayer;
import entity.Chest;
import entity.Coins;
import entity.Enemy;
import entity.Explosion;
import entity.HUD;
import entity.HealthPotion;
import entity.LevelPortal;
import entity.ManaPotion;
import entity.MovingDragon;
import entity.Player;
import entity.enemies.level_2.FrozenDragon;
import entity.enemies.level_2.IceGolem;
import entity.enemies.level_2.Iceman;
import entity.enemies.level_2.LobsterBoss;
import entity.enemies.level_2.Penguin;
import main.GamePanel;
import tileMap.Background;
import tileMap.TileMap;

public class Level2State extends GameState {

	private TileMap tileMap;
	private Background background;

	private Player player;

	private MovingDragon dragon;
	private LevelPortal portal;

	private LobsterBoss boss;

	private ArrayList<ManaPotion> manaPotions;
	private ArrayList<HealthPotion> healthPotions;
	private ArrayList<Chest> chests;
	private ArrayList<Coins> coins;
	private ArrayList<Enemy> enemies;
	private ArrayList<Explosion> explosions;

	private HUD hud;

	private AudioPlayer bgMusic;

	public Level2State(GameStateManager gsm) {
		currentLevel = 2;
		this.gsm = gsm;

		init();
	}

	@Override
	public void init() {
		tileMap = new TileMap(75);
		tileMap.loadTiles("/tilesets/Level_2.gif");
		tileMap.loadMap("/maps/Level_2.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);

		background = new Background("/backgrounds/Level_2BG.png", 0.1);

		player = Level1State.player;
		player.setPosition(100, 0);
		player.setRight(false);
		player.setTileMap(tileMap);
//		player.setPosition(10000, 800);

		enemies = new ArrayList<Enemy>();

		boss = new LobsterBoss(tileMap, player);
		boss.setPosition(10000, 600);
		boss.getSpawnX(10000);
		enemies.add(boss);

		populateEnemies();
		populateCoins();
		populatePotions();

		explosions = new ArrayList<Explosion>();

		hud = new HUD(player, boss);

		bgMusic = new AudioPlayer("/musics/Winter.mp3");
		bgMusic.play(true);

		dragon = new MovingDragon(tileMap);
		dragon.setPosition(400, 300);
		dragon.setProperty(1200, 950, 0.5, 1);

		portal = new LevelPortal(tileMap);
		portal.setPosition(9250, 920);
		portal.setDirection(false);

	}

	private void populateEnemies() {

		Point[] iceGolemPoints = new Point[] { new Point(2900, 700), new Point(2500, 500), new Point(2300, 500),
				new Point(1800, 375), new Point(1650, 375), new Point(3850, 150), new Point(7700, 600),
				new Point(7600, 600), };

		Point[] icemanPoints = new Point[] { new Point(2450, 800), new Point(2650, 800), new Point(3700, 375),
				new Point(4000, 375), new Point(4500, 200), new Point(4650, 750), new Point(4300, 750),
				new Point(3800, 750), };

		Point[] penguinPoints = new Point[] { new Point(1650, 900), new Point(1850, 900), new Point(2050, 900),
				new Point(6115, 720), new Point(6320, 720), new Point(6500, 720), new Point(6715, 720), };

		Point[] frozenDragonPoints = new Point[] { new Point(3350, 200), new Point(4750, 200), new Point(3550, 750),
				new Point(6500, 600), new Point(9300, 600), };
		for (int i = 0; i < iceGolemPoints.length; i++) {
			IceGolem golem = new IceGolem(tileMap, player);
			golem.setPosition(iceGolemPoints[i].x, iceGolemPoints[i].y);
			golem.getSpawnX(iceGolemPoints[i].x);
			enemies.add(golem);
		}

		for (int i = 0; i < icemanPoints.length; i++) {
			Iceman iceman = new Iceman(tileMap, player);
			iceman.setPosition(icemanPoints[i].x, icemanPoints[i].y);
			iceman.getSpawnX(icemanPoints[i].x);
			enemies.add(iceman);
		}

		for (int i = 0; i < penguinPoints.length; i++) {
			Penguin penguin = new Penguin(tileMap, player);
			penguin.setPosition(penguinPoints[i].x, penguinPoints[i].y);
			penguin.getSpawnX(penguinPoints[i].x);
			enemies.add(penguin);
		}

		for (int i = 0; i < frozenDragonPoints.length; i++) {
			FrozenDragon dragon = new FrozenDragon(tileMap, player);
			dragon.setPosition(frozenDragonPoints[i].x, frozenDragonPoints[i].y);
			dragon.getSpawnX(frozenDragonPoints[i].x);
			enemies.add(dragon);
		}
	}

	private void populateCoins() {
		coins = new ArrayList<Coins>();
		chests = new ArrayList<Chest>();

		Point[] points = new Point[] { new Point(1700, 900), new Point(1900, 900), new Point(2950, 875),
				new Point(2900, 575), new Point(2395, 500), new Point(1500, 325), new Point(1880, 325),
				new Point(2400, 250), new Point(2200, 200), new Point(2600, 275), new Point(2750, 200),
				new Point(3850, 175), new Point(4400, 275), new Point(4800, 200), new Point(5100, 275),
				new Point(5500, 575), new Point(5400, 800), new Point(5200, 800), new Point(4750, 800),
				new Point(4350, 800), new Point(3950, 800), new Point(6600, 615), new Point(7235, 725),
				new Point(8095, 650), new Point(8400, 650), new Point(8700, 650), };

		Point[] chestPoints = new Point[] { new Point(11000, 1025), new Point(6950, 775), new Point(5300, 800),
				new Point(3850, 425), new Point(3200, 650), };

		for (int i = 0; i < points.length; i++) {
			Coins coin = new Coins(tileMap);
			coin.setPosition(points[i].x, points[i].y);
			coins.add(coin);
		}

		for (int i = 0; i < chestPoints.length; i++) {
			Chest chest = new Chest(tileMap);
			chest.setPosition(chestPoints[i].x, chestPoints[i].y);
			chests.add(chest);
		}
	}

	private void populatePotions() {
		healthPotions = new ArrayList<HealthPotion>();
		manaPotions = new ArrayList<ManaPotion>();

		Point[] HPPotionPoints = new Point[] { new Point(1800, 900), new Point(2900, 275), new Point(3850, 100),
				new Point(4500, 750), new Point(3650, 750), new Point(6215, 750), new Point(9200, 500), };

		Point[] APPotionPoints = new Point[] { new Point(2850, 875), new Point(4950, 275), new Point(4850, 750),
				new Point(3200, 500), new Point(9400, 500), };

		for (int i = 0; i < HPPotionPoints.length; i++) {
			HealthPotion potion = new HealthPotion(tileMap);
			potion.setPosition(HPPotionPoints[i].x, HPPotionPoints[i].y);
			healthPotions.add(potion);
		}

		for (int i = 0; i < APPotionPoints.length; i++) {
			ManaPotion potion = new ManaPotion(tileMap);
			potion.setPosition(APPotionPoints[i].x, APPotionPoints[i].y);
			manaPotions.add(potion);
		}

	}

	@Override
	public void update() {
		// Update player
		player.update();
		tileMap.setPosition(GamePanel.WIDTH / 2 - player.getX(), GamePanel.HEIGHT / 2 - player.getY());

		// Set background
		background.setPosition(tileMap.getX(), tileMap.getY());

		// Update MovingDragon
		dragon.update();
		dragon.checkOnRide(player);

		// Attack enemies
		player.checkAttack(enemies);

		// Update boss
		boss.update();

		// Update all the enemies
		for (int i = 1; i < enemies.size(); i++) {
			Enemy enemy = enemies.get(i);
			enemy.update();
			if (enemy.isDead()) {
				enemies.remove(i);
				i--;
				explosions.add(new Explosion(enemy.getX(), enemy.getY()));
			}
		}

		// Update chest
		for (int i = 0; i < chests.size(); i++) {
			Chest c = chests.get(i);
			c.update();
			if (player.intersects(chests.get(i))) {
				chests.get(i).isCollected(true);
				chests.get(i).addCoins();
			}
		}

		// Update health potion
		for (int i = 0; i < healthPotions.size(); i++) {
			HealthPotion potion = healthPotions.get(i);
			potion.update();
			if (player.intersects(potion)) {
				potion.isEaten(true);
				potion.addHealth(player);
				healthPotions.remove(i);
				i--;
			}
		}

		// Update health potion
		for (int i = 0; i < manaPotions.size(); i++) {
			ManaPotion potion = manaPotions.get(i);
			potion.update();
			if (player.intersects(potion)) {
				potion.isEaten(true);
				potion.addFire(player);
				manaPotions.remove(i);
				i--;
			}
		}

		// Update all the coins
		for (int i = 0; i < coins.size(); i++) {
			Coins c = coins.get(i);
			c.update();
			if (player.intersects(coins.get(i))) {
				coins.get(i).isCollected(true);
				coins.get(i).addCoins();
				coins.remove(i);
				i--;
			}
		}

		// Update all the explosions
		for (int i = 0; i < explosions.size(); i++) {
			explosions.get(i).update();
			if (explosions.get(i).shouldRemove()) {
				explosions.remove(i);
				i--;
			}
		}

		// Update map
		if (boss.isDead()) {
			tileMap.loadMap("/Maps/Level_2_1.map");
		}

		// When player isDead
		if (player.isDead()) {
			gsm.setState(GameStateManager.GAMEOVERSTATE);
			bgMusic.close();
		}

		portal.update();
		// When enter portal
		if (player.intersects(portal) && boss.isDead()) {
			portal.isEnter(true);
			gsm.setState(GameStateManager.WINSTATE);
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

		// Draw movingTile
		dragon.draw(graphic);

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

		// Draw explosions
		for (int i = 0; i < explosions.size(); i++) {
			explosions.get(i).setMapPosition((int) tileMap.getX(), (int) tileMap.getY());
			explosions.get(i).draw(graphic);
		}

		// Draw chest
		for (int i = 0; i < chests.size(); i++) {
			chests.get(i).draw(graphic);
		}

		// Draw health potions
		for (int i = 0; i < healthPotions.size(); i++) {
			healthPotions.get(i).draw(graphic);
		}

		// Draw mana potions
		for (int i = 0; i < manaPotions.size(); i++) {
			manaPotions.get(i).draw(graphic);
		}

		// Draw HUD
		hud.draw(graphic);

		// Draw portal
		if (boss.isDead()) {
			portal.draw(graphic);
		}
	}

	public void display() {
		System.out.println("Player_X: " + player.getX() + "\tPlayer_Y: " + player.getY());
	}

	@Override
	public void keyPressed(int key) {
		if (!dragon.isIntersect()) {
			if (key == KeyEvent.VK_A)
				player.setLeft(true);
			if (key == KeyEvent.VK_D)
				player.setRight(true);
			if (key == KeyEvent.VK_W)
				player.setUp(true);
			if (key == KeyEvent.VK_S)
				player.setDown(true);
			if (key == KeyEvent.VK_W)
				player.setJumping(true);
			if (key == KeyEvent.VK_J)
				player.setGliding(true);
			if (key == KeyEvent.VK_K)
				player.setScratching();
			if (key == KeyEvent.VK_L)
				player.setFiring();
		}

	}

	@Override
	public void keyReleased(int key) {
		if (key == KeyEvent.VK_A)
			player.setLeft(false);
		if (key == KeyEvent.VK_D)
			player.setRight(false);
		if (key == KeyEvent.VK_W)
			player.setUp(false);
		if (key == KeyEvent.VK_S)
			player.setDown(false);
		if (key == KeyEvent.VK_W)
			player.setJumping(false);
		if (key == KeyEvent.VK_J)
			player.setGliding(false);
	}

	@Override
	public void keyTyped(int key) {
		// TODO Auto-generated method stub

	}
}
