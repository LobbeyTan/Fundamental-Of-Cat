package world;

import static info.Info.*;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import audio.AudioPlayer;
import entity.EntityManager;
import entity.world.cat.Cat;
import entity.world.statics.Apple;
import entity.world.statics.Castle;
import entity.world.statics.Coin;
import entity.world.statics.CutTree;
import entity.world.statics.Dunguen;
import entity.world.statics.Flag;
import entity.world.statics.Flower;
import entity.world.statics.Fountain;
import entity.world.statics.House;
import entity.world.statics.Log;
import entity.world.statics.Mushroom;
import entity.world.statics.Pond;
import entity.world.statics.Ship;
import entity.world.statics.Shop;
import entity.world.statics.Skeleton;
import entity.world.statics.Statue;
import entity.world.statics.Stone;
import entity.world.statics.Tree;
import entity.world.statics.Village;
import gameState.GameState;
import gameState.GameStateManager;
import items.ItemManager;
import main.Handler;
import tiles.Tile;
import utils.Utils;

public class World extends GameState {
	private Handler handler;
	private int width, height;
	private int[][] tiles;
	
	// Change state purpose
	private House house;
	private Dunguen dungeun;
	private Ship ship;

	// Cat
	private static Cat cat;
	// Item
	private ItemManager itemManager;
	// Entities
	private EntityManager entityManager;

	private AudioPlayer bgMusic;

	public World(Handler handler, String path, GameStateManager gsm) {
		this.gsm = gsm;
		this.handler = handler;

		init();

		loadWorld(path);
		

		bgMusic = new AudioPlayer("/musics/World.mp3");
		bgMusic.play(true);
	}

	@Override
	public void init() {
		cat = GameStateManager.cat;
		house = new House(handler, 78, 64);
		dungeun = new Dunguen(handler, 2176, 192);
		ship = new Ship(handler, 1408, 2084);
		
		entityManager = new EntityManager(handler, cat);
		itemManager = new ItemManager(handler);
		entityManager.addEntity(house);
		entityManager.addEntity(ship);
		entityManager.addEntity(dungeun);
		entityManager.addEntity(new Tree(handler, 400, 250));
		entityManager.addEntity(new Stone(handler, 200, 450));
		entityManager.addEntity(new Tree(handler, 700, 150));
		entityManager.addEntity(new Stone(handler, 800, 1150));
		entityManager.addEntity(new Tree(handler, 1000, 50));
		entityManager.addEntity(new Stone(handler, 900, 1550));
		entityManager.addEntity(new Fountain(handler, 1270, 886));
		entityManager.addEntity(new Mushroom(handler, 1132, 814));
		entityManager.addEntity(new Tree(handler, 192, 512));
		entityManager.addEntity(new Tree(handler, 192, 712));
		entityManager.addEntity(new Tree(handler, 192, 950));
		entityManager.addEntity(new Tree(handler, 192, 1150));
		entityManager.addEntity(new Tree(handler, 192, 1300));
		entityManager.addEntity(new Tree(handler, 192, 1500));
		entityManager.addEntity(new Tree(handler, 192, 1700));
		entityManager.addEntity(new Tree(handler, 392, 1700));
		entityManager.addEntity(new Tree(handler, 592, 1700));
		entityManager.addEntity(new Tree(handler, 792, 1700));
		entityManager.addEntity(new Tree(handler, 992, 1700));
		entityManager.addEntity(new Tree(handler, 1092, 1500));
		entityManager.addEntity(new Tree(handler, 1132, 1032));
		entityManager.addEntity(new Apple(handler, 192 + 100, 512 + 100));
		entityManager.addEntity(new Apple(handler, 192 + 100, 1150 + 100));
		entityManager.addEntity(new Apple(handler, 192 + 100, 950 + 100));
		entityManager.addEntity(new Mushroom(handler, 1478, 1032));
		entityManager.addEntity(new Tree(handler, 192, 712));
		entityManager.addEntity(new Castle(handler, 1245, 1));
		entityManager.addEntity(new Statue(handler, 1180, 65));
		entityManager.addEntity(new Statue(handler, 1437, 65));
		entityManager.addEntity(new CutTree(handler, 1180, 265));
		entityManager.addEntity(new CutTree(handler, 1430, 265));
		entityManager.addEntity(new Pond(handler, 1626, 1236));
		entityManager.addEntity(new Skeleton(handler, 2132, 276));
		entityManager.addEntity(new Skeleton(handler, 2260, 276));
		entityManager.addEntity(new Village(handler, 384, 1216));
		entityManager.addEntity(new Village(handler, 984, 1016));
		entityManager.addEntity(new Village(handler, 684, 1416));
		entityManager.addEntity(new Village(handler, 384, 1516));
		entityManager.addEntity(new Village(handler, 1084, 1316));
		entityManager.addEntity(new Skeleton(handler, 2260, 276));
		entityManager.addEntity(new Coin(handler, 320 + (int) (Math.random() * 1920), (int) (320 + Math.random() * 1500)));
		entityManager.addEntity(new Coin(handler, 320 + (int) (Math.random() * 1920), (int) (320 + Math.random() * 1500)));
		entityManager.addEntity(new Coin(handler, 320 + (int) (Math.random() * 1920), (int) (320 + Math.random() * 1500)));
		entityManager.addEntity(new Coin(handler, 320 + (int) (Math.random() * 1920), (int) (320 + Math.random() * 1500)));
		entityManager.addEntity(new Coin(handler, 320 + (int) (Math.random() * 1920), (int) (320 + Math.random() * 1500)));
		entityManager.addEntity(new Coin(handler, 320 + (int) (Math.random() * 1920), (int) (320 + Math.random() * 1500)));
		entityManager.addEntity(new Coin(handler, 320 + (int) (Math.random() * 1920), (int) (320 + Math.random() * 1500)));
		entityManager.addEntity(new Coin(handler, 320 + (int) (Math.random() * 1920), (int) (320 + Math.random() * 1500)));
		entityManager.addEntity(new Coin(handler, 320 + (int) (Math.random() * 1920), (int) (320 + Math.random() * 1500)));
		entityManager.addEntity(new Coin(handler, 320 + (int) (Math.random() * 1920), (int) (320 + Math.random() * 1500)));
		entityManager.addEntity(new Log(handler, 384, 1216 - 155));
		entityManager.addEntity(new Log(handler, 984 + 100, 1016 + 155));
		entityManager.addEntity(new Log(handler, 584, 1400));
		entityManager.addEntity(new Log(handler, 484, 1656));
		entityManager.addEntity(new Log(handler, 934, 1316));
		entityManager.addEntity(new Shop(handler, 584, 456));
		entityManager.addEntity(new Flower(handler, 600, 570));
		entityManager.addEntity(new Flag(handler, 1586, 412));
		entityManager.addEntity(new Flag(handler, 1986, 412));
		entityManager.addEntity(new Flag(handler, 1786, 612));
		entityManager.addEntity(new Flag(handler, 2186, 612));
		entityManager.addEntity(new Flower(handler, 1500, 1984));
		entityManager.addEntity(new Flower(handler, 1700, 1984));
		entityManager.addEntity(new Flower(handler, 1900, 1984));
		entityManager.addEntity(new Flower(handler, 700, 1984));
		entityManager.addEntity(new Flower(handler, 980, 1984));
	}

	@Override
	public void update() {
		if (dungeun.isDead() && tireness < 80) {
			gsm.setState(GameStateManager.MENUSTATE);
			GameStateManager.cat.reset();
			bgMusic.close();
		}
		if (ship.isDead() && tireness < 80) {
			gsm.setState(GameStateManager.RUNNERGAMESTATE);
			bgMusic.close();
			GameStateManager.cat.reset();
		}
		if (house.isDead()) {
			gsm.setState(GameStateManager.LIVINGROOM);
			bgMusic.close();
			GameStateManager.cat.reset();
		}
		
		if(happiness <= 0 || TTL <= 0) {
			gsm.setState(GameStateManager.ENDINGSTATE);
			bgMusic.close();
		}
		
		dungeun.setHealth(1);dungeun.setDead(false);
		ship.setHealth(1);ship.setDead(false);
		house.setHealth(1);house.setDead(false);

		itemManager.update();
		entityManager.update();
		cat.update();
		
		System.out.println("Dunguen_isDead: " + dungeun.isDead());
		System.out.println("Ship_isDead: " + ship.isDead());
		System.out.println("House_isDead: " + house.isDead());
		System.out.println("Set Attack: " + cat.isAtatck());
	}

	@Override
	public void draw(Graphics2D g) {
		int xStart = (int) Math.max(0, handler.getGameCamera().getxOffset() / Tile.TILEWIDTH);
		int xEnd = (int) Math.min(width,
				(handler.getGameCamera().getxOffset() + handler.getWidth()) / Tile.TILEWIDTH + 1);
		int yStart = (int) Math.max(0, handler.getGameCamera().getyOffset() / Tile.TILEHEIGHT);
		int yEnd = (int) Math.min(height,
				(handler.getGameCamera().getyOffset() + handler.getHeight()) / Tile.TILEHEIGHT + 1);
		for (int y = yStart; y < yEnd; y++) {
			for (int x = xStart; x < xEnd; x++) {
				getTile(x, y).render(g, (int) (x * Tile.TILEWIDTH - handler.getGameCamera().getxOffset()),
						(int) (y * Tile.TILEHEIGHT - handler.getGameCamera().getyOffset()));
			}
		}

		// Entities
		entityManager.draw(g);

		// Items
		itemManager.draw(g);
	}

	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) {
			return Tile.grassTile;
		}

		Tile t = Tile.tiles[tiles[x][y]];
		if (t == null)
			return Tile.grassTile;
		return t;
	}

	private void loadWorld(String path) {
		String file = Utils.loadFileAsString(path);
		String[] tokens = file.split("\\s+");
		width = Utils.parseInt(tokens[0]);
		height = Utils.parseInt(tokens[1]);
//        spawnX = Utils.parseInt(tokens[2]);
//        spawnY = Utils.parseInt(tokens[3]);

		tiles = new int[width][height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tiles[x][y] = Utils.parseInt(tokens[(x + y * width) + 2]);
			}
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public ItemManager getItemManager() {
		return itemManager;
	}

	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}

	@Override
	public void keyPressed(int key) {
		if (key == KeyEvent.VK_E && !GameStateManager.cat.getInfo().isActive()) GameStateManager.cat.getInventory().setOpenInventory(true);
		if (key == KeyEvent.VK_I && !GameStateManager.cat.getInventory().isActive()) GameStateManager.cat.getInfo().setOpenInfo(true);

		if(!cat.getInventory().isActive() && !cat.getInfo().isActive()) {
			if (key == KeyEvent.VK_A)
				cat.setLeft(true);
			if (key == KeyEvent.VK_D)
				cat.setRight(true);
			if (key == KeyEvent.VK_W)
				cat.setUp(true);
			if (key == KeyEvent.VK_S)
				cat.setDown(true);
			if (key == KeyEvent.VK_J)
				cat.setAttack(true);
		}
		
		if(cat.getInventory().isActive()) {
			if (key == KeyEvent.VK_W)
				cat.getInventory().setUp(true);
			if (key == KeyEvent.VK_S)
				cat.getInventory().setDown(true);
			if (key == KeyEvent.VK_J)
				cat.getInventory().setSelect(true);
		}
	}

	@Override
	public void keyReleased(int key) {
		if (key == KeyEvent.VK_A)
			cat.setLeft(false);
		if (key == KeyEvent.VK_D)
			cat.setRight(false);
		if (key == KeyEvent.VK_W)
			cat.setUp(false);
		if (key == KeyEvent.VK_S)
			cat.setDown(false);
		if (key == KeyEvent.VK_J)
			cat.setAttack(false);
	}

	@Override
	public void keyTyped(int key) {

	}

}
