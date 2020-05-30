package gameState;

public abstract class GameState {
	public static int currentLevel;

	protected GameStateManager gsm;

	public abstract void init();

	public abstract void update();

	public abstract void draw(java.awt.Graphics2D graphic);

	public abstract void keyTyped(int key);

	public abstract void keyPressed(int key);

	public abstract void keyReleased(int key);

}
