package gameState;

import static info.Info.health;
import static info.Info.damage;
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
import entity.Player;
import entity.enemies.level_1.Bat;
import entity.enemies.level_1.CloudMonster;
import entity.enemies.level_1.EvilBoss;
import entity.enemies.level_1.EvilDragon;
import entity.enemies.level_1.EvilTree;
import entity.enemies.level_1.Ghost;
import main.GamePanel;
import tileMap.Background;
import tileMap.TileMap;

public class Level1State extends GameState {

	private TileMap tileMap;
	private Background background;

	public static Player player;
	private EvilBoss boss;

	private LevelPortal portal;

	private ArrayList<ManaPotion> manaPotions;
	private ArrayList<HealthPotion> healthPotions;
	private ArrayList<Chest> chests;
	private ArrayList<Coins> coins;
	private ArrayList<Enemy> enemies;
	private ArrayList<Explosion> explosions;

	private HUD hud;

	private AudioPlayer bgMusic;
	private AudioPlayer earthquake;

	public Level1State(GameStateManager gsm) {
		currentLevel = 1;
		this.gsm = gsm;

		init();
	}

	@Override
	public void init() {
		tileMap = new TileMap(75);
		tileMap.loadTiles("/tilesets/Level_1.gif");
		tileMap.loadMap("/maps/Level_1.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);

		background = new Background("/backgrounds/Level_1BG.png", 0.1);

		player = new Player(tileMap);
		player.setPosition(100, 200);
		player.setHealth(health, health);
		player.setDamage(damage);
//		player.setPosition(7600, 725);

		enemies = new ArrayList<Enemy>();

		boss = new EvilBoss(tileMap, player);
		boss.setPosition(7650, 700);
		boss.getSpawnX(7650);
		enemies.add(boss);

		populateEnemies();
		populateCoins();
		populatePotions();

		explosions = new ArrayList<Explosion>();

		hud = new HUD(player, boss);

		bgMusic = new AudioPlayer("/musics/Halloween.mp3");
		bgMusic.play(true);

		earthquake = new AudioPlayer("/musics/Earthquake.mp3");

		portal = new LevelPortal(tileMap);
		portal.setPosition(8750, 200);
	}

	private void populateEnemies() {

		Point[] evilTreePoints = new Point[] { new Point(630, 400), new Point(930, 250), new Point(2100, 100),
				new Point(3150, 150), new Point(3350, 150), new Point(3550, 150), new Point(3752, 150),
				new Point(4550, 350), new Point(8000, 800), new Point(8370, 800),

		};

		Point[] batPoints = new Point[] { new Point(1450, 350), new Point(1200, 415), new Point(2000, 500),
				new Point(3000, 500), new Point(3400, 650), new Point(5600, 125), new Point(5640, 800),
				new Point(5740, 800), new Point(7870, 350), new Point(8000, 350), new Point(8250, 500),
				new Point(8400, 500), };

		Point[] ghostPoints = new Point[] { new Point(850, 550), new Point(2650, 250), new Point(3300, 650),
				new Point(6000, 200), new Point(6500, 700), new Point(6855, 800), };

		Point[] evilDragonPoints = new Point[] { new Point(260, 550), new Point(2050, 100), new Point(3850, 725),
				new Point(5705, 200), new Point(7800, 800), new Point(8200, 800)

		};

		Point[] cloudMonsterPoints = new Point[] { new Point(1750, 575), new Point(3000, 425), new Point(3500, 725),
				new Point(5000, 500), new Point(5555, 800), new Point(5755, 800), new Point(7600, 800), };

		for (int i = 0; i < evilTreePoints.length; i++) {
			EvilTree tree = new EvilTree(tileMap);
			tree.setPosition(evilTreePoints[i].x, evilTreePoints[i].y);
			tree.getSpawnX(evilTreePoints[i].x);
			enemies.add(tree);
		}

		for (int i = 0; i < batPoints.length; i++) {
			Bat bat = new Bat(tileMap);
			bat.setPosition(batPoints[i].x, batPoints[i].y);
			bat.getSpawnX(batPoints[i].x);
			enemies.add(bat);
		}

		for (int i = 0; i < ghostPoints.length; i++) {
			Ghost ghost = new Ghost(tileMap);
			ghost.setPosition(ghostPoints[i].x, ghostPoints[i].y);
			ghost.getSpawnX(ghostPoints[i].x);
			enemies.add(ghost);
		}

		for (int i = 0; i < evilDragonPoints.length; i++) {
			EvilDragon dragon = new EvilDragon(tileMap);
			dragon.setPosition(evilDragonPoints[i].x, evilDragonPoints[i].y);
			dragon.getSpawnX(evilDragonPoints[i].x);
			enemies.add(dragon);
		}

		for (int i = 0; i < cloudMonsterPoints.length; i++) {
			CloudMonster cloudMonster = new CloudMonster(tileMap);
			cloudMonster.setPosition(cloudMonsterPoints[i].x, cloudMonsterPoints[i].y);
			cloudMonster.getSpawnX(cloudMonsterPoints[i].x);
			enemies.add(cloudMonster);
		}

	}

	private void populateCoins() {
		coins = new ArrayList<Coins>();
		chests = new ArrayList<Chest>();

		Point[] coinPoints = new Point[] { new Point(630, 400), new Point(260, 500), new Point(935, 650),
				new Point(930, 240), new Point(1290, 350), new Point(1690, 275), new Point(2100, 125),
				new Point(2135, 500), new Point(3850, 725), new Point(3850, 675), new Point(3850, 600),
				new Point(3775, 725), new Point(3700, 725), new Point(4300, 175), new Point(4550, 350),
				new Point(4800, 300), new Point(5280, 325), new Point(5705, 200), new Point(6250, 400),
				new Point(6250, 500), new Point(6250, 600), new Point(6250, 700), new Point(6250, 800), };

		Point[] chestPoints = new Point[] { new Point(7000, 725), new Point(5300, 800), new Point(3270, 725), };

		for (int i = 0; i < coinPoints.length; i++) {
			Coins coin = new Coins(tileMap);
			coin.setPosition(coinPoints[i].x, coinPoints[i].y);
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

		Point[] HPPotionPoints = new Point[] { new Point(2650, 100), new Point(3750, 650), new Point(5700, 50),
				new Point(6650, 650), new Point(8555, 200), };

		Point[] APPotionPoints = new Point[] { new Point(3150, 500), new Point(4530, 175), new Point(7015, 650),
				new Point(8255, 200), };

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

		// Attack enemies
		player.checkAttack(enemies);

		// Update all the enemies
		for (int i = 0; i < enemies.size(); i++) {
			Enemy enemy = enemies.get(i);
			enemy.update();
			if (enemy.isDead()) {
				enemies.remove(i);
				i--;
				explosions.add(new Explosion(enemy.getX(), enemy.getY()));
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

		// When player isDead
		if (player.isDead()) {
			gsm.setState(GameStateManager.GAMEOVERSTATE);
			bgMusic.close();
		}

		// Update map
		if (boss.isDead()) {
			earthquake.play(false);
			tileMap.loadMap("/Maps/Level_1_1.map");
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

		portal.update();
		// When enter portal
		if (player.intersects(portal) && boss.isDead()) {
			portal.isEnter(true);
			gsm.setState(GameStateManager.LEVEL2STATE);
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

		// Draw explosions
		for (int i = 0; i < explosions.size(); i++) {
			explosions.get(i).setMapPosition((int) tileMap.getX(), (int) tileMap.getY());
			explosions.get(i).draw(graphic);
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

		// Draw chest
		for (int i = 0; i < chests.size(); i++) {
			chests.get(i).draw(graphic);
		}

		// Draw portal
		portal.draw(graphic);

	}

	public void display() {
		System.out.println("Player_X: " + player.getX() + "\tPlayer_Y: " + player.getY());
	}

	@Override
	public void keyPressed(int key) {
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
