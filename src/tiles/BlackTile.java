
package tiles;

import gfx.Assets;

public class BlackTile extends Tile {

	public BlackTile(int id) {
		super(Assets.blackTile, id);
	}

	@Override
	public boolean isSolid() {
		return true;
	}
}
