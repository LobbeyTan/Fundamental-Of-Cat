
package tiles;

import gfx.Assets;

public class PathTile32 extends Tile {
	public PathTile32(int id) {
		super(Assets.path[31], id);
	}

	@Override
	public boolean isSolid() {
		return true;
	}
}
