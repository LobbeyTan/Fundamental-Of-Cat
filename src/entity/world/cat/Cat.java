package entity.world.cat;

import static info.Info.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import entity.Entity;
import gfx.Animation;
import gfx.Assets;
import gfx.Text;
import info.Info;
import inventory.Inventory;
import main.Handler;
import tiles.Tile;

public class Cat extends Creature {

	// Animations
	private Animation animDown, animUp, animLeft, animRight;
	private BufferedImage Stand;

	// Attack Timer
	private long lastAttackTimer, attackCooldown = 800, attackTimer = attackCooldown;

	// Inventory
	private Inventory inventory;
	private Info info;

	public Cat(Handler handler, float x, float y) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, 99999);
		// Cat's Collision bounds size
		bounds.x = 16;
		bounds.y = 32;
		bounds.width = 32;
		bounds.height = 32;

		Cat = 1;
		// Animations
		if (Cat == 1) {
			animDown = new Animation(350, Assets.whitecat_down);
			animUp = new Animation(350, Assets.whitecat_up);
			animLeft = new Animation(350, Assets.whitecat_left);
			animRight = new Animation(350, Assets.whitecat_right);
			Stand = Assets.whitecat;
		} else if (Cat == 2) {
			animDown = new Animation(350, Assets.blackcat_down);
			animUp = new Animation(350, Assets.blackcat_up);
			animLeft = new Animation(350, Assets.blackcat_left);
			animRight = new Animation(350, Assets.blackcat_right);
			Stand = Assets.blackcat;
		} else if (Cat == 3) {
			animDown = new Animation(350, Assets.yellowcat_down);
			animUp = new Animation(350, Assets.yellowcat_up);
			animLeft = new Animation(350, Assets.yellowcat_left);
			animRight = new Animation(350, Assets.yellowcat_right);
			Stand = Assets.yellowcat;
		}

		inventory = new Inventory(handler);
		info = new Info(handler);
	}

	@Override
	public void update() {
		// Animations
		animDown.update();
		animUp.update();
		animLeft.update();
		animRight.update();

		// Movement
		getInput();
		move();
		handler.getGameCamera().centerOnEntity(this);
//        System.out.println("Left: " + moveLeft + " Right: " + moveRight + " Up: " + moveUp + " Down: " + moveDown);
		// Attack
		checkAttack();

		// Inventory
		inventory.update();

		// Info
		info.update();

//		System.out.println("Cat_X: " + this.getX() + "Cat_Y: " + this.getY());

//        System.out.println("OpenInventory: " + inventory.isOpen());
//        System.out.println("Inventory_active: " + isActive());
//        System.out.println("OpenInfo: " + info.isOpen());
//        System.out.println("Info_active: " + isActive());
	}

	private void checkAttack() {
		attackTimer += System.currentTimeMillis() - lastAttackTimer;
		lastAttackTimer = System.currentTimeMillis();
		if (attackTimer < attackCooldown)
			return;

		if (inventory.isActive())
			return;

		if (info.isActive())
			return;

		Rectangle cb = getCollisionBounds(0, 0);
		Rectangle ar = new Rectangle();
		int arSize = Tile.TILEWIDTH;
		ar.width = arSize;
		ar.height = arSize;

		// Attack Bounds
		if (attack) {
			ar.x = cb.x - 20;
			ar.y = cb.y - 20;
		} else {
			return;
		}

		attackTimer = 0;

		for (Entity e : handler.getWorld().getEntityManager().getEntities()) {
			if (e.equals(this))
				continue;
			if (e.getCollisionBounds(0, 0).intersects(ar)) {
				e.hurt(1);
				return;
			}
		}
	}

	private void getInput() {
		xMove = 0;
		yMove = 0;

		if (inventory.isActive())
			return;

		if (info.isActive())
			return;

		if (moveUp)
			yMove = -speed / 2;
		if (moveDown)
			yMove = speed / 2;
		if (moveLeft)
			xMove = -speed / 2;
		if (moveRight)
			xMove = speed / 2;
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), width + 20, height + 20, null);

	}

	public void postRender(Graphics2D g) {
		if (tireness >= 80 && !inventory.isActive() && !info.isActive()) {
			Text.drawString(g, "Your Cat is too tired!", 350, 200, true, Color.red, Assets.font40);
			Text.drawString(g, "Let your Cat sleep!", 365, 300, true, Color.red, Assets.font40);
		}else if(hunger >= 80 && !inventory.isActive() && !info.isActive()) {
			Text.drawString(g, "Your Cat is too hungry!", 350, 200, true, Color.red, Assets.font40);
			Text.drawString(g, "Let your Cat eat!", 365, 300, true, Color.red, Assets.font40);
		}else if(happiness <= 20 && !inventory.isActive() && !info.isActive()) {
			Text.drawString(g, "Your Cat is not happy!", 350, 200, true, Color.red, Assets.font40);
			Text.drawString(g, "Play with your Cat!", 365, 300, true, Color.red, Assets.font40);
		}
		inventory.draw(g);
		info.draw(g);
	}

	private BufferedImage getCurrentAnimationFrame() {
		if (xMove < 0) {
			return animLeft.getCurrentFrame();
		} else if (xMove > 0) {
			return animRight.getCurrentFrame();
		} else if (yMove < 0) {
			return animUp.getCurrentFrame();
		} else if (yMove > 0) {
			return animDown.getCurrentFrame();
		} else
			return Stand;
	}

	@Override
	public void die() {
		System.out.println("You Lose!");
	}
	
	public void reset() {
		this.moveLeft = false;
		this.moveRight = false;
		this.moveUp = false;
		this.moveDown = false;
		this.attack = false;
		this.info.setOpenInfo(false);
		this.inventory.setOpenInventory(false);
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}
	
	public boolean isAtatck() {
		return this.attack;
	}

	public void setLeft(boolean b) {
		this.moveLeft = b;
	}

	public void setRight(boolean b) {
		this.moveRight = b;
	}

	public void setUp(boolean b) {
		this.moveUp = b;
	}

	public void setDown(boolean b) {
		this.moveDown = b;
	}

	public void setAttack(boolean b) {
		this.attack = b;
	}
}
