
package tiles;

import gfx.Assets;

public class PathTile30 extends Tile {
	public PathTile30(int id) {
		super(Assets.path[29], id);
	}

	@Override
	public boolean isSolid() {
		return true;
	}
}
