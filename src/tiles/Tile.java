
package tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author lian
 */
public class Tile {

	// STATIC STUFF HERE

	public static Tile[] tiles = new Tile[256];
	public static Tile grassTile = new GrassTile(0);
	public static Tile pathTile1 = new PathTile1(1);
	public static Tile pathTile2 = new PathTile2(2);
	public static Tile pathTile3 = new PathTile3(3);
	public static Tile pathTile4 = new PathTile4(4);
	public static Tile pathTile5 = new PathTile5(5);
	public static Tile pathTile6 = new PathTile6(6);
	public static Tile pathTile7 = new PathTile7(7);
	public static Tile pathTile8 = new PathTile8(8);
	public static Tile pathTile9 = new PathTile9(9);
	public static Tile pathTile10 = new PathTile10(10);
	public static Tile pathTile11 = new PathTile11(11);
	public static Tile pathTile12 = new PathTile12(12);
	public static Tile pathTile13 = new PathTile13(13);
	public static Tile pathTile14 = new PathTile14(14);
	public static Tile pathTile15 = new PathTile15(15);
	public static Tile pathTile16 = new PathTile16(16);
	public static Tile pathTile17 = new PathTile17(17);
	public static Tile pathTile18 = new PathTile18(18);
	public static Tile pathTile19 = new PathTile19(19);
	public static Tile pathTile20 = new PathTile20(20);
	public static Tile pathTile21 = new PathTile21(21);
	public static Tile pathTile22 = new PathTile22(22);
	public static Tile pathTile23 = new PathTile23(23);
	public static Tile pathTile24 = new PathTile24(24);
	public static Tile pathTile25 = new PathTile25(25);
	public static Tile pathTile26 = new PathTile26(26);
	public static Tile pathTile27 = new PathTile27(27);
	public static Tile pathTile28 = new PathTile28(28);
	public static Tile pathTile29 = new PathTile29(29);
	public static Tile pathTile30 = new PathTile30(30);
	public static Tile pathTile31 = new PathTile31(31);
	public static Tile blackTile = new BlackTile(32);
	public static Tile pathTile32 = new PathTile32(33);
	public static Tile pathTile33 = new PathTile33(34);
	public static Tile pathTile34 = new PathTile34(35);
	public static Tile pathTile35 = new PathTile35(36);

//    public static Tile dirtTile = new DirtTile(1);
//    public static Tile rockTile = new RockTile(2);

	// CLASS
	public static final int TILEWIDTH = 64, TILEHEIGHT = 64;
	protected BufferedImage texture;
	protected final int id;

	public Tile(BufferedImage texture, int id) {
		this.texture = texture;
		this.id = id;

		tiles[id] = this;
	}

	public void tick() {

	}

	public void render(Graphics g, int x, int y) {
		g.drawImage(texture, x, y, TILEWIDTH, TILEHEIGHT, null);
	}

	public boolean isSolid() {
		return false;
	}

	public int getId() {
		return id;
	}
}
