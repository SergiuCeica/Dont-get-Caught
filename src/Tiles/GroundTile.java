package Tiles;

import Graphics.Assets;

import java.awt.image.BufferedImage;

public class GroundTile extends Tile{
    public GroundTile(int id)
    {
        super(Assets.ground, id);
        this.collision=true;
    }

    @Override
    public boolean IsSolid() {
        return true;
    }
}
