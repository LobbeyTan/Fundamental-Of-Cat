
package ui;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import main.Handler;

public class UIManager {

	private Handler handler;
	private ArrayList<UIObject> objects;

	public UIManager(Handler handler) {
		this.handler = handler;
		objects = new ArrayList<UIObject>();
	}

	public void update() {
		for (UIObject o : objects)
			o.update();
	}

	public void draw(Graphics2D g) {
		for (UIObject o : objects)
			o.draw(g);
	}

	public void onMouseMove(MouseEvent e) {
		for (UIObject o : objects)
			o.onMouseMove(e);
	}

	public void onMouseRelease(MouseEvent e) {
		for (UIObject o : objects)
			o.onMouseRelease(e);
	}

	public void addObject(UIObject o) {
		objects.add(o);
	}

	public void removeObject(UIObject o) {
		objects.remove(o);
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public ArrayList<UIObject> getObjects() {
		return objects;
	}

	public void setObjects(ArrayList<UIObject> objects) {
		this.objects = objects;
	}

}
