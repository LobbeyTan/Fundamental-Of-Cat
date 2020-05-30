package items;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;

import main.Handler;

public class ItemManager {

	private Handler handler;
	private ArrayList<Item> items;

	public ItemManager(Handler handler) {
		this.handler = handler;
		items = new ArrayList<Item>();

	}

	public void update() {
		Iterator<Item> it = items.iterator();
		while (it.hasNext()) {
			Item i = it.next();
			i.update();
			if (i.isPickedUp()) {
				it.remove();
			}
		}
	}

	public void draw(Graphics2D g) {
		for (Item i : items)
			i.draw(g);
	}

	public void addItem(Item i) {
		i.setHandler(handler);
		items.add(i);
	}

	// Getter and Setter

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

}
