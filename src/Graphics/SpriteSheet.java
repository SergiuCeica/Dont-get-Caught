package Graphics;

import java.awt.image.BufferedImage;

public class SpriteSheet
{
    private BufferedImage       spriteSheet;
    private static final int    tileWidth   = 50;
    private static final int    tileHeight  = 50;

    public SpriteSheet(BufferedImage buffImg)
    {
        spriteSheet = buffImg;
    }
    public BufferedImage crop(int x, int y)
    {
        return spriteSheet.getSubimage(x * tileWidth, y * tileHeight, tileWidth, tileHeight);
    }
    public BufferedImage cropV2(int x,int y,int w,int h){
        return spriteSheet.getSubimage(x*w,y*h,w,h);
    }
}
