package gameState;

import org.eclipse.jdt.annotation.Nullable;
import entity.world.cat.Cat;
import home.Bathroom;
import home.Bedroom;
import home.LivingRoom;
import main.Handler;
import world.World;

public class GameStateManager {

	private GameState[] gameStates;
	private World world;
	private Handler handler;
	public static int currentState;
	public static Cat cat;

	public static final int NUMGAMESTATES = 12;
	public static final int LIVINGROOM = 0;
	public static final int BATHROOM = 1;
	public static final int BEDROOM = 2;
	public static final int WORLDSTATE = 3;
	public static final int MENUSTATE = 4;
	public static final int HELPSTATE = 5;
	public static final int GAMEOVERSTATE = 6;
	public static final int WINSTATE = 7;
	public static final int LEVEL1STATE = 8;
	public static final int LEVEL2STATE = 9;
	public static final int RUNNERGAMESTATE = 10;
	public static final int ENDINGSTATE = 11;

	public GameStateManager(Handler handler) {
		this.handler = handler;
		cat = new Cat(this.handler, 100, 100);
		gameStates = new GameState[NUMGAMESTATES];

		currentState = LIVINGROOM;

		loadState(currentState);
	}

	private void loadState(int state) {
		if (state == LIVINGROOM) {
			gameStates[state] = new LivingRoom(this);
		}
		if (state == BATHROOM) {
			gameStates[state] = new Bathroom(this);
		}
		if (state == BEDROOM) {
			gameStates[state] = new Bedroom(this);
		}
		if (state == WORLDSTATE) {
			world = new World(handler, "resources/maps/World1.txt", this);
			gameStates[state] = world;
			handler.setWorld(world);
		}
		if (state == MENUSTATE) {
			gameStates[state] = new MenuState(this);
		}
		if (state == HELPSTATE) {
			gameStates[state] = new HelpState(this);
		}
		if (state == LEVEL1STATE) {
			gameStates[state] = new Level1State(this);
		}
		if (state == GAMEOVERSTATE) {
			gameStates[state] = new GameOverState(this);
		}
		if (state == WINSTATE) {
			gameStates[state] = new WinState(this);
		}
		if (state == LEVEL2STATE) {
			gameStates[state] = new Level2State(this);
		}
		if (state == RUNNERGAMESTATE) {
			gameStates[state] = new Runner(this);
		}
		if(state == ENDINGSTATE) {
			gameStates[state] = new EndingState(this);
		}
	}

	private void unloadState(int state) {
		gameStates[state] = null;
	}

	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
	}

	public void update() {
		try {
			gameStates[currentState].update();
		} catch (Exception e) {
			
		}
	}

	public void draw(java.awt.Graphics2D graphic) {
		try {
			gameStates[currentState].draw(graphic);
		} catch (Exception e) {
			
		}
	}

	public void keyTyped(@Nullable int key) {
		gameStates[currentState].keyTyped(key);
	}

	public void keyPressed(@Nullable int key) {
		gameStates[currentState].keyPressed(key);
	}

	public void keyReleased(@Nullable int key) {
		gameStates[currentState].keyReleased(key);
	}
}
