
package tiles;

import gfx.Assets;

public class PathTile28 extends Tile {

	public PathTile28(int id) {
		super(Assets.path[27], id);
	}

	@Override
	public boolean isSolid() {
		return true;
	}
}
