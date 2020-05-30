package tileMap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Background {

	private BufferedImage image;

	private double x;
	private double y;
	private double dx;
	private double dy;

	private double moveScale;

	public Background(String path, double ms) {
		try {
			image = ImageIO.read(getClass().getResourceAsStream(path));

			moveScale = ms;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setPosition(double x, double y) {
		this.x = (x * moveScale) % GamePanel.WIDTH;
		this.y = (y * moveScale) % GamePanel.HEIGHT;
	}

	public void setRunnerPosition(double x, double y) {
		this.x = (x * moveScale) % GamePanel.WIDTH;
		this.y = 0 % GamePanel.HEIGHT;
	}

	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}

	public void update() {
		x += dx;
		y += dy;
	}

	public void draw(Graphics2D graphic) {
		graphic.drawImage(image, (int) x, (int) y, GamePanel.WIDTH, GamePanel.HEIGHT, null);

		if (x < 0) {
			graphic.drawImage(image, (int) x + GamePanel.WIDTH, (int) y, GamePanel.WIDTH, GamePanel.HEIGHT, null);
		}

		if (x > 0) {
			graphic.drawImage(image, (int) x - GamePanel.WIDTH, (int) y, GamePanel.WIDTH, GamePanel.HEIGHT, null);
		}
	}
}
