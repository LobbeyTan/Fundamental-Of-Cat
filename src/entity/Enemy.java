package entity;

import tileMap.TileMap;

public class Enemy extends MapObject {
	protected int health;
	protected int maxHealth;
	protected boolean dead;
	protected int damage;

	protected boolean flinching;
	protected long flinchTimer;

	public Enemy(TileMap tileMap) {
		super(tileMap);
	}

	public void hit(int damage) {
		if (dead || flinching) {
			return;
		}

		health -= damage;
		if (health < 0)
			health = 0;
		if (health == 0) {
			dead = true;
		} else {
			flinching = true;
			flinchTimer = System.nanoTime();
		}

	}

	public void update() {
		if (checkDeathTileCollision()) {
			hit(1);
		}
	}

	public boolean isDead() {
		return dead;
	}

	public int getDamage() {
		return damage;
	}

	public int getHealth() {
		return health;
	}

	public int getMaxHealth() {
		return maxHealth;
	}
}
