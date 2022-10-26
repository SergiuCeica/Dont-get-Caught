package Tiles;

import Graphics.Assets;

public class RockTile extends Tile{
    public RockTile(int id)
    {
        super(Assets.rock, id);
        this.collision=true;
    }

    @Override
    public boolean IsSolid() {
        return true;
    }
}
