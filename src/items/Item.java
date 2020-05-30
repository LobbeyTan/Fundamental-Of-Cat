
package items;

import static info.Info.damage;
import static info.Info.happiness;
import static info.Info.health;
import static info.Info.hunger;
import static info.Info.level;
import static info.Info.munny;
import static info.Info.TTL;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import gfx.Assets;
import main.Handler;

public class Item {

	// Handler

	public static Item[] items = new Item[256];
	public static Item appleItem = new Item(Assets.apple, "Apple", 5, 2, 10, 0, 0, 0);
	public static Item mushroomItem = new Item(Assets.mushroom, "Mushroom", 5, 2, 10, 0, 0, 0);
	public static Item cheeseItem = new Item(Assets.cheese, "Cheese", 10, 2, 20, 0, 0, 0);
	public static Item lemonItem = new Item(Assets.lemon, "Lemon", 5, 2, 10, 0, 0, 0);
	public static Item carrotItem = new Item(Assets.carrot, "Carrot", 10, 0, 20, 0, 0, 0);
	public static Item meatItem = new Item(Assets.meat, "Meat", 20, 2, 40, 0, 0, 0);
	public static Item drinkItem = new Item(Assets.drink, "Drink", 5, 2, 10, 0, 0, 0);
	public static Item pizzaItem = new Item(Assets.pizza, "Pizza", 20, 2, 40, 0, 0, 0);
	public static Item icecreamItem = new Item(Assets.icecream, "Ice Cream", 5, 2, 10, 0, 0, 0);
	public static Item sushiItem = new Item(Assets.sushi, "Sushi", 10, 2, 20, 0, 0, 0);
	public static Item potatoItem = new Item(Assets.potato, "Potato", 5, 2, 10, 0, 0, 0);
	public static Item eggItem = new Item(Assets.egg, "Egg", 5, 2, 10, 0, 0, 0);
	public static Item baconItem = new Item(Assets.bacon, "Bacon", 15, 2, 30, 0, 0, 0);
	public static Item fishItem = new Item(Assets.fish, "Fish", 20, 2, 40, 0, 0, 0);
	public static Item chickenItem = new Item(Assets.chicken, "Chicken", 20, 2, 40, 0, 0, 0);
	public static Item watermelonItem = new Item(Assets.watermelon, "Watermelon", 10, 2, 20, 0, 0, 0);
	public static Item prawnItem = new Item(Assets.prawn, "Prawn", 15, 2, 30, 0, 0, 0);
	public static Item swordItem = new Item(Assets.sword, "Level Up", 0, 0, 100, 1 + (int) (Math.random() * 2), 1,
			5 + (int) (Math.random() * 5));

	// CLASS

	// Item Size
	public static final int ITEMWIDTH = 32, ITEMHEIGHT = 32;

	protected Handler handler;
	protected BufferedImage texture;
	protected String name;

	protected Rectangle bounds;

	protected int x, y, count, a, b, c, d, e, f;
	protected static boolean pickedUp = false;

	public Item(BufferedImage texture, String name, int hunger, int happiness, int munny, int damage, int level,
			int health) {
		this.texture = texture;
		this.name = name;
		a = hunger;
		b = happiness;
		c = munny;
		d = damage;
		e = level;
		f = health;

		bounds = new Rectangle(x, y, ITEMWIDTH, ITEMHEIGHT);

	}

	public void consume() {
		hunger -= a;
		happiness += b;
		munny -= c;
		damage += d;
		level += e;
		health += f;
		TTL += c;
	}

	public String getPrice() {
		return "Price : " + c;
	}

	public String getHunger() {
		return "Hunger : -" + a;
	}

	public String getHappiness() {
		return "Happy : +" + b;
	}

	public String getDamage() {
		return "Damage : +" + d;
	}

	public void update() {
		if (handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0f, 0f).intersects(bounds)) {
			pickedUp = true;
			handler.getWorld().getEntityManager().getPlayer().getInventory().addItem(this);
		}

	}

	public void draw(Graphics2D g) {
		if (handler == null)
			return;
		render(g, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()));
	}

	public void render(Graphics g, int x, int y) {
		g.drawImage(texture, x, y, ITEMWIDTH, ITEMHEIGHT, null);
	}

	// Getter Setter

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public BufferedImage getTexture() {
		return texture;
	}

	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public boolean isPickedUp() {
		return pickedUp;
	}

	public static void setPickedUp(boolean pickedUp) {
		Item.pickedUp = pickedUp;
	}

	public int getMunny() {
		return c;
	}
}
