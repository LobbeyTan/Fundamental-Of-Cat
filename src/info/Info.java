
package info;

import java.awt.Color;
import java.awt.Graphics2D;
import java.text.SimpleDateFormat;
import java.util.Date;

import gfx.Assets;
import gfx.Text;
import main.Handler;

public class Info {

	private Handler handler;
	private boolean active = false;
	private boolean openInfo;
	private int invX = 64, invY = 48, invWidth = 672, invHeight = 542;
	public static String name = "Cat";
	public static int age, happiness = 100, tireness = 0, hunger = 0, level = 1, TTL = 9999, damage = 3, munny = 0,
			health = 15;
	private long lastTime, timer, TOB = System.currentTimeMillis();
	SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy");
	Date resultdate = new Date(TOB);

	public Info(Handler handler) {
		this.handler = handler;
		timer = 0;
		lastTime = System.currentTimeMillis();
	}

	public void update() {
		if (openInfo) {
			active = !active;
			openInfo = false;
		}

		timer += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();

		if (timer > 1000) {
			happiness--;
			tireness++;
			hunger++;
			TTL--;
			timer = 0;
		}

		if (timer > 10000 && tireness >= 100) {
			TTL -= 2;
		}

		if (timer > 10000 && hunger >= 100) {
			TTL -= 5;
		}

		if (hunger >= 100)
			hunger = 100;

		if (tireness >= 100)
			tireness = 100;

		if (happiness >= 300)
			happiness = 300;

		if (hunger < 0)
			hunger = 0;

		if (tireness < 0)
			tireness = 0;

		if (happiness <= 0) {
			happiness = 0;
			// RUN AWAY
		}

		if (TTL <= 0) {
			TTL = 0;
			// DIE
		}
	}

	public void draw(Graphics2D g) {

		if (!active) {
			return;
		}

		g.drawImage(Assets.infoScreen, invX, invY, invWidth, invHeight, null);

		Text.drawString(g, "Name           : " + name, 200, 140, false, Color.BLACK, Assets.font30);
		Text.drawString(g, "Happy          : " + happiness, 200, 190, false, Color.BLACK, Assets.font30);
		Text.drawString(g, "Tireness       : " + tireness, 200, 240, false, Color.BLACK, Assets.font30);
		Text.drawString(g, "Hunger         : " + hunger, 200, 290, false, Color.BLACK, Assets.font30);
		Text.drawString(g, "Level           : " + level, 200, 340, false, Color.BLACK, Assets.font30);
		Text.drawString(g, "Time to live   : " + TTL, 200, 390, false, Color.BLACK, Assets.font30);
		Text.drawString(g, "Damage         : " + damage, 200, 440, false, Color.BLACK, Assets.font30);
		Text.drawString(g, "Health          : " + health, 200, 490, false, Color.black, Assets.font30);
		Text.drawString(g, "Time of Birth : ", 200, 540, false, Color.BLACK, Assets.font30);
		Text.drawString(g, sdf.format(resultdate), 480, 540, false, Color.black, Assets.font30);
	}

	// Getter and Setter

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public boolean isActive() {
		return active;
	}

	public boolean isOpen() {
		return openInfo;
	}

	public void setOpenInfo(boolean b) {
		this.openInfo = b;
	}

}
