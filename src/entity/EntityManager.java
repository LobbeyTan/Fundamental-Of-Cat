
package entity;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import entity.world.cat.Cat;
import main.Handler;

public class EntityManager {

	private Handler handler;
	private Cat player;
	private ArrayList<Entity> entities;
	private Comparator<Entity> renderSorter = new Comparator<Entity>() {

		@Override
		public int compare(Entity a, Entity b) {
			if (a.getY() + a.getHeight() < b.getY() + b.getHeight())
				return -1;
			return 1;
		}

	};

	public EntityManager(Handler handler, Cat player) {
		this.handler = handler;
		this.player = player;
		entities = new ArrayList<Entity>();
		addEntity(player);
	}

	public void update() {
		Iterator<Entity> it = entities.iterator();
		while (it.hasNext()) {
			Entity e = it.next();
			e.update();
			if (!e.isActive())
				it.remove();
		}
		entities.sort(renderSorter);
	}

	public void draw(Graphics2D g) {
		for (Entity e : entities) {
			e.draw(g);
		}
		player.postRender(g);
	}

	public void addEntity(Entity e) {
		entities.add(e);
	}

	// Getter and Setter

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public Cat getPlayer() {
		return player;
	}

	public void setPlayer(Cat player) {
		this.player = player;
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}

	public void setEntities(ArrayList<Entity> entities) {
		this.entities = entities;
	}

}
