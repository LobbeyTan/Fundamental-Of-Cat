
package tiles;

import gfx.Assets;

public class PathTile31 extends Tile {
	public PathTile31(int id) {
		super(Assets.path[30], id);
	}

	@Override
	public boolean isSolid() {
		return true;
	}
}
