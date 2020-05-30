
package tiles;

import gfx.Assets;

public class PathTile26 extends Tile {

	public PathTile26(int id) {
		super(Assets.path[25], id);
	}

	@Override
	public boolean isSolid() {
		return true;
	}
}
