
package tiles;

import gfx.Assets;

public class PathTile35 extends Tile {
	public PathTile35(int id) {
		super(Assets.path[34], id);

	}

	@Override
	public boolean isSolid() {
		return true;
	}
}
