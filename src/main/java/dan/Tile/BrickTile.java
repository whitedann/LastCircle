package dan.Tile;

import dan.display.Assets;

public class BrickTile extends Tile{

    public BrickTile(int id) {
        super(Assets.brick, id);
    }

    @Override
    public boolean isSolid(){
        return true;
    }
}
