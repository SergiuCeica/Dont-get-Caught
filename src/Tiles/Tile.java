package Tiles;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile
{
    private static final int NO_TILES   = 32;
    public static Tile[] tiles          = new Tile[NO_TILES];

    public static Tile grassTile        = new GrassTile(0);
    public static Tile rockTile     = new RockTile(1);
    public static Tile groundTile     = new GroundTile(2);

    public static final int TILE_WIDTH  = 50;
    public static final int TILE_HEIGHT = 50;

    protected BufferedImage img;
    protected final int id;
    public boolean collision=false;

    public Tile(BufferedImage image, int idd)
    {
        img = image;
        id = idd;
        tiles[id] = this;
    }

    public void Update()
    {

    }
    public void Draw(Graphics g, int x, int y)
    {
        g.drawImage(img, x, y, TILE_WIDTH, TILE_HEIGHT, null);
    }

    public boolean IsSolid()
    {
        return false;
    }
    public int GetId()
    {
        return id;
    }
}
