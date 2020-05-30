package gameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import tileMap.Background;

public class HelpState extends GameState {
	private Background background;

	private BufferedImage[] inputKeys;
	private String[] text = { "Jump", "To the left", "To the right", "Gliding while jump", "Scratch attack",
			"Fireball attack" };

	private Font font;

	public HelpState(GameStateManager gsm) {
		this.gsm = gsm;
		inputKeys = new BufferedImage[6];

		font = new Font("Arial", Font.PLAIN, 25);

		try {

			background = new Background("/backgrounds/MenuBG.gif", 1);
			background.setVector(-0.1, 0);

			for (int i = 0; i < 6; i++) {
				inputKeys[i] = ImageIO.read(getClass().getResourceAsStream("/inputKeys/" + i + ".png"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init() {

	}

	@Override
	public void update() {
		background.update();
	}

	@Override
	public void draw(Graphics2D graphic) {
		background.draw(graphic);

		// Draw title
//		graphic.setColor(titleColor);
//		graphic.setFont(titleFont);
//		graphic.drawString("Help",300, 120);

		// Draw text box
		graphic.setColor(Color.CYAN);
		graphic.fillRoundRect(200, 180, 400, 400, 50, 50);
		graphic.setColor(Color.BLACK);
		graphic.setFont(font);
		for (int i = 0; i < 6; i++) {
			graphic.drawImage(inputKeys[i], 220, 220 + i * 55, null);
			graphic.drawString(text[i], 270, 245 + i * 55);
		}

		graphic.drawString("Press Enter to back...", 350, 575);

	}

	public void select() {
		gsm.setState(GameStateManager.MENUSTATE);
	}

	@Override
	public void keyPressed(int key) {
		if (key == KeyEvent.VK_ENTER) {
			select();
		}
	}

	@Override
	public void keyReleased(int key) {

	}

	@Override
	public void keyTyped(int key) {
		// TODO Auto-generated method stub

	}
}
