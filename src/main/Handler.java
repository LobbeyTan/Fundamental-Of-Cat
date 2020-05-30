
package main;

import gfx.GameCamera;
import world.World;

public class Handler {

	private GamePanel game;
	private World world;

	public Handler(GamePanel gamePanel) {
		this.game = gamePanel;
	}

	public GameCamera getGameCamera() {
		return game.getGameCamera();
	}

	public int getWidth() {
		return game.getWidth();
	}

	public int getHeight() {
		return game.getHeight();
	}

	public GamePanel getGame() {
		return game;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

}
