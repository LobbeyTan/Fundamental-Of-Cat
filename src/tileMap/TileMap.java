package tileMap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileMap {

	// Position
	private double x;
	private double y;

	// Bounds
	private int xmin;
	private int ymin;
	private int xmax;
	private int ymax;

	private double tween; // Smoothly scroll the camera

	// Map
	private int[][] map;
	private int tileSize;
	private int numRows;
	private int numCols;
	private int width;
	private int height;

	// Tileset
	private BufferedImage tileset;
	private int numTilesAcross;
	private Tile[][] tiles;

	// Drawing tiles on screen
	private int rowOffset;
	private int colOffset;
	private int numRowsToDraw;
	private int numColsToDraw;

	public TileMap(int tileSize) {

		this.tileSize = tileSize;
		numRowsToDraw = GamePanel.HEIGHT / tileSize + 2;
		numColsToDraw = GamePanel.WIDTH / tileSize + 2;
		tween = 0.07;
	}

	public void loadTiles(String path) {
		try {

			tileset = ImageIO.read(getClass().getResourceAsStream(path));
			numTilesAcross = tileset.getWidth() / tileSize;
			tiles = new Tile[3][numTilesAcross];

			BufferedImage subImage;
			for (int col = 0; col < numTilesAcross; col++) {
				subImage = tileset.getSubimage(col * tileSize, 0, tileSize, tileSize);
				tiles[0][col] = new Tile(subImage, Tile.NORMAL);

				subImage = tileset.getSubimage(col * tileSize, tileSize, tileSize, tileSize);
				tiles[1][col] = new Tile(subImage, Tile.BLOCKED);

				subImage = tileset.getSubimage(col * tileSize, tileSize * 2, tileSize, tileSize);
				tiles[2][col] = new Tile(subImage, Tile.DEATH);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadMap(String path) {
		try {

			InputStream inputImage = getClass().getResourceAsStream(path);
			BufferedReader read = new BufferedReader(new InputStreamReader(inputImage));

			numCols = Integer.parseInt(read.readLine());
			numRows = Integer.parseInt(read.readLine());

			map = new int[numRows][numCols];

			width = numCols * tileSize;
			height = numRows * tileSize;

			xmin = GamePanel.WIDTH - width;
			xmax = 0;
			ymin = GamePanel.HEIGHT - height;
			ymax = 0;

			System.out.println(xmin);
			String delims = "\\s+";

			for (int row = 0; row < numRows; row++) {
				String line = read.readLine();
				String[] tokens = line.split(delims);

				for (int col = 0; col < numCols; col++) {
					map[row][col] = Integer.parseInt(tokens[col]);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics2D graphic) {
		for (int row = rowOffset; row < rowOffset + numRowsToDraw; row++) {

			if (row >= numRows)
				break;

			for (int col = colOffset; col < colOffset + numColsToDraw; col++) {

				if (col >= numCols)
					break;

				if (map[row][col] == 0)
					continue;

				int rc = map[row][col];
				int r = rc / numTilesAcross;
				int c = rc % numTilesAcross;

				graphic.drawImage(tiles[r][c].getImage(), (int) (x + col * tileSize), (int) (y + row * tileSize),
						(tileSize), (tileSize), null);
			}
		}
	}

	private void fixBounds() {
		if (x < xmin)
			x = xmin;
		if (y < ymin)
			y = ymin;
		if (x > xmax)
			x = xmax;
		if (y > ymax)
			y = ymax;
	}

	public void setTween(double n) {
		tween = n;
	}

	public void setPosition(double x, double y) {
		this.x += (x - this.x) * tween;
		this.y += (y - this.y) * tween;

		fixBounds();

		colOffset = (int) -this.x / tileSize;
		rowOffset = ((int) -this.y / tileSize);
	}

	public int getTileSize() {
		return tileSize;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getType(int row, int col) {
		int rc = map[row][col];
		int r = rc / numTilesAcross;
		int c = rc % numTilesAcross;

		return tiles[r][c].getType();
	}

	public Tile getTile(int row, int col) {
		return tiles[row][col];
	}
}
