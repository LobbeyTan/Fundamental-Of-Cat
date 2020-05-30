
package tiles;

import gfx.Assets;

public class PathTile33 extends Tile {
	public PathTile33(int id) {
		super(Assets.path[32], id);
	}

	@Override
	public boolean isSolid() {
		return true;
	}
}
