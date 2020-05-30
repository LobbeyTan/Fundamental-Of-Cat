
package tiles;

import gfx.Assets;

public class PathTile27 extends Tile {

	public PathTile27(int id) {
		super(Assets.path[26], id);
	}

	@Override
	public boolean isSolid() {
		return true;
	}
}
