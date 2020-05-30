package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JPanel;

import org.eclipse.jdt.annotation.Nullable;

import gameState.GameStateManager;
import gfx.Assets;
import gfx.GameCamera;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener {
	// Dimension
	public static final int WIDTH = 800;
	public static final int HEIGHT = 640;
	public static final int SCALE = 1;

	// Game Thread
	private Thread thread;
	private boolean running;

	// Image
	private BufferedImage image;
	private Graphics2D graphic;

	// GameStateManager
	private GameStateManager gsm;

	// Camera
	private GameCamera gameCamera;

	// Handler
	private Handler handler;

	public GamePanel() {
		super();
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setFocusable(true);
		requestFocus();
	}

	@Override
	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}

	private void init() {
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

		graphic = (Graphics2D) image.getGraphics();

		running = true;

		Assets.init();
		handler = new Handler(this);
		gsm = new GameStateManager(handler);
		gameCamera = new GameCamera(handler, 0, 0);
	}

	@Override
	public void run() {
		init();

		long lastTime = System.nanoTime();
		double delta = 0.0;
		double nanoTime = 1000000000.0 / 60.0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;

		// Game Loops
		while (running) {

			long currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / nanoTime;
			lastTime = currentTime;

			if (delta >= 1.0) {
				update();
				updates++;
				delta--;
			}

			draw();
			drawToScreen();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("UPS: " + updates + "\t" + "FPS: " + frames);
				updates = 0;
				frames = 0;
			}
		}

		stop();
	}

	private void update() {
		gsm.update();
	}

	private void draw() {
		gsm.draw(graphic);
	}

	private void drawToScreen() {
		Graphics panelGraphics = getGraphics();
		panelGraphics.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		panelGraphics.dispose();
	}

	public synchronized void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		if (!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException ex) {
			Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public GameCamera getGameCamera() {
		return gameCamera;
	}

	@Override
	public int getWidth() {
		return WIDTH;
	}

	@Override
	public int getHeight() {
		return HEIGHT;
	}

	@Override
	public void keyTyped(KeyEvent key) {

	}

	@Override
	public void keyPressed(@Nullable KeyEvent key) {
		gsm.keyPressed(key.getKeyCode());
	}

	@Override
	public void keyReleased(@Nullable KeyEvent key) {
		gsm.keyReleased(key.getKeyCode());
	}
}
