
package tiles;

import gfx.Assets;

public class PathTile34 extends Tile {
	public PathTile34(int id) {
		super(Assets.path[33], id);
	}

	@Override
	public boolean isSolid() {
		return true;
	}
}
