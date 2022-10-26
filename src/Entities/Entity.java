package Entities;

import Tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {
    public static Entity player;
    public static final int TILE_WIDTH  = 50;
    public static final int TILE_HEIGHT = 50;
    private static final int NO_TILES   = 32;
    public static Entity[] entities = new Entity[NO_TILES];
    protected BufferedImage img;
    protected final int id;
    protected double speed,x,y;

    public Rectangle solidArea;
    public boolean collissionOn=false;


    public Entity(BufferedImage image,int idd,int xx,int yy){
        img = image;
        id = idd;
        speed=2;
        x=xx;
        y=yy;
        solidArea=new Rectangle();
        solidArea.x=(int)this.x+8;
        solidArea.y=(int)this.y+8;
        solidArea.width=TILE_WIDTH-16;
        solidArea.height=TILE_HEIGHT-16;
        //entities[id] = this;
    }
    public void Update(){

    }
    public double getX(){
        return this.x;
    }
    public double getY(){
        return this.y;
    }
    public double getSpeed(){
        return this.speed;
    }
    public void setX(double val){
        this.x=val;
        this.solidArea.x=(int)val+8;
    }
    public void setY(double val){
        this.y=val;
        this.solidArea.y=(int)val+8;
    }
    public boolean checkCollision(Tile[][] map){
        double pxss=this.getX();
        int xss=this.solidArea.x;
        double pyss=this.getY();
        int yss=this.solidArea.y;
        double pxdj=this.getX()+50;
        int xdj=this.solidArea.x+30;
        double pydj=this.getY()+50;
        int ydj=this.solidArea.y+30;
        double pxsj=this.getX();
        int xsj=this.solidArea.x;
        double pysj=this.getY()+50;
        int ysj=this.solidArea.y+30;
        double pxds=this.getX()+50;
        int xds=this.solidArea.x+30;
        double pyds= this.getY();
        int yds=this.solidArea.y;

        if(map[xss/50][yss/50].IsSolid()==true){
            if(map[xsj/50][ysj/50].IsSolid()==true)  this.setX(pxss+1);
            else if(map[xds/50][yds/50].IsSolid()==true) this.setY(pyss+1);
            else{
                this.setX(pxss+1);
                this.setY(pyss+1);
            }
            return false;
        }

        if(map[xdj/50][ydj/50].IsSolid()==true){
            if(map[xsj/50][ysj/50].IsSolid()==true) this.setY(pyss-1);
            else if(map[xds/50][yds/50].IsSolid()==true) this.setX(pxss-1);
            else{
                this.setX(pxss-1);
                this.setY(pyss-1);
            }
            return false;
        }
        if(map[xds/50][yds/50].IsSolid()==true){
            this.setX(pxss-1);
            this.setY(pyss+1);
            return false;
        }
        if(map[xsj/50][ysj/50].IsSolid()==true){
            this.setX(pxss+1);
            this.setY(pyss-1);
            return false;
        }
        return true;
    }

    public abstract void Draw(Graphics g, double x, double y);
}
