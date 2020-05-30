
package inventory;

import static info.Info.munny;
import static items.Item.appleItem;
import static items.Item.baconItem;
import static items.Item.carrotItem;
import static items.Item.cheeseItem;
import static items.Item.chickenItem;
import static items.Item.eggItem;
import static items.Item.fishItem;
import static items.Item.lemonItem;
import static items.Item.meatItem;
import static items.Item.mushroomItem;
import static items.Item.potatoItem;
import static items.Item.prawnItem;
import static items.Item.sushiItem;
import static items.Item.swordItem;
import static items.Item.watermelonItem;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import gfx.Assets;
import gfx.Text;
import items.Item;
import main.Handler;

public class Inventory {
	private boolean openInventory;

	private Handler handler;
	private boolean active = false;
	private boolean up;
	private boolean down;
	private boolean select;
	private ArrayList<Item> inventoryItems;
	private int invX = 64, invY = 48, invWidth = 672, invHeight = 542, invListCenterX = invX + 171,
			invListCenterY = invY + invHeight / 2 + 5, invListSpacing = 42;

	private int invImageX = 570, invImageY = 90, invImageWidth = 90, invImageHeight = 90;

	private int invCountX = 565, invCountY = 225;

	private int selectedItem = 0;

	public static boolean flyAbility;

	public Inventory(Handler handler) {
		this.handler = handler;

		init();
	}

	public void init() {
		inventoryItems = new ArrayList<Item>();

		inventoryItems.add(appleItem);
		inventoryItems.add(mushroomItem);
		inventoryItems.add(cheeseItem);
		inventoryItems.add(lemonItem);
		inventoryItems.add(carrotItem);
		inventoryItems.add(sushiItem);
		inventoryItems.add(meatItem);
		inventoryItems.add(potatoItem);
		inventoryItems.add(eggItem);
		inventoryItems.add(baconItem);
		inventoryItems.add(fishItem);
		inventoryItems.add(chickenItem);
		inventoryItems.add(watermelonItem);
		inventoryItems.add(prawnItem);
		inventoryItems.add(swordItem);
	}

	public void update() {
		if (openInventory) {
			active = !active;
			openInventory = false;
		}

		if (!active)
			return;

		if (up) {
			selectedItem--;
			up = false;
		}

		if (down) {
			selectedItem++;
			down = false;
		}

		if (selectedItem < 0)
			selectedItem = inventoryItems.size() - 1;
		else if (selectedItem >= inventoryItems.size())
			selectedItem = 0;

		if (select) {
			if (munny < inventoryItems.get(selectedItem).getMunny()) {
				select = false;
				return;
			}else {
				inventoryItems.get(selectedItem).consume();
				select = false;
			}
		}
	}

	public void draw(Graphics2D g) {
		if (!active)
			return;

		g.drawImage(Assets.inventoryScreen, invX, invY, invWidth, invHeight, null);

		int len = inventoryItems.size();

		for (int i = -5; i < 6; i++) {
			if (selectedItem + i < 0 || selectedItem + i >= len)
				continue;
			if (i == 0) {
				Text.drawString(g, "> " + inventoryItems.get(selectedItem + i).getName() + " <", invListCenterX,
						invListCenterY + i * invListSpacing, true, Color.YELLOW, Assets.font30);
			} else
				Text.drawString(g, inventoryItems.get(selectedItem + i).getName(), invListCenterX,
						invListCenterY + i * invListSpacing, true, Color.WHITE, Assets.font30);

		}

		Item item = inventoryItems.get(selectedItem);
		g.drawImage(item.getTexture(), invImageX, invImageY, invImageWidth, invImageHeight, null);

		Text.drawString(g, "" + munny, invCountX, invCountY, true, Color.WHITE, Assets.font40);
		Text.drawString(g, item.getPrice(), 490, 300, false, Color.white, Assets.font30);
		Text.drawString(g, item.getHunger(), 490, 400, false, Color.white, Assets.font30);
		Text.drawString(g, item.getHappiness(), 490, 450, false, Color.white, Assets.font30);
//        Text.drawString(g, item.getDamage(), 490, 500, false, Color.white, Assets.font30);

		if (munny < inventoryItems.get(selectedItem).getMunny()) {
			Text.drawString(g, "Insufficient Munny", 370, 320, true, Color.red, Assets.font40);
		}

		if (inventoryItems.get(selectedItem) == swordItem) {
			Text.drawString(g, "Level + 1", 490, 500, false, Color.white, Assets.font30);
		}

	}

	// Inventory Method

	public void addItem(Item item) {
		inventoryItems.add(item);
	}

	public void removeItem(Item item) {
		inventoryItems.remove(item);
	}

	// Getter and Setter

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public boolean isActive() {
		return active;
	}

	public boolean isOpen() {
		return openInventory;
	}

	public void setUp(boolean b) {
		this.up = b;
	}

	public void setDown(boolean b) {
		this.down = b;
	}

	public void setOpenInventory(boolean b) {
		this.openInventory = b;
	}

	public void setSelect(boolean b) {
		this.select = b;
	}

}
