
package entity.world.statics;

import entity.Entity;
import main.Handler;

public abstract class StaticEntity extends Entity {

	public StaticEntity(Handler handler, float x, float y, int width, int height, int health) {
		super(handler, x, y, width, height, health);
	}

}
