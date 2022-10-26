package Entities;

import Graphics.Assets;
import Tiles.Tile;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.util.LinkedList;
import java.util.Queue;
import java.awt.image.BufferedImage;

public class Enemy extends Entity{
    private double speed=1.2;
    public int health;
    private int nrF;
    public Enemy( int idd, int xx, int yy) {
        super(Assets.enemy, idd, xx, yy);
        solidArea=new Rectangle();
        solidArea.x=(int)this.x+16;
        solidArea.y=(int)this.y+16;
        solidArea.width=TILE_WIDTH-30;
        solidArea.height=TILE_HEIGHT-30;
        health=100;
        nrF=0;
    }
    public void move(Entity tinta,Tile[][] map) {
        char c = ' ';
        double cX = this.getX();
        double cY = this.getY();
        double diffX ;
        double diffY ;
        float distance;
        float minim = 1000;
        boolean check;
        diffX =Math.abs(cX - tinta.getX());
        diffY = Math.abs(cY + 50 - tinta.getY());
        distance = (float) Math.sqrt(diffX * diffX + diffY * diffY);
        if (distance < minim) {
            if(map[(int)diffX/50][(int)diffY/50].IsSolid()==false) {
                minim = distance;
                c = 'D';
            }
        }
        diffX = Math.abs(cX - tinta.getX());
        diffY = Math.abs(cY - 50 - tinta.getY());
        distance = (float) Math.sqrt(diffX * diffX + diffY * diffY);
        if (distance < minim) {
            if(map[(int)diffX/50][(int)diffY/50].IsSolid()==false) {
                minim = distance;
                c = 'U';
            }
        }
        diffX = Math.abs(cX + 50 - tinta.getX());
        diffY = Math.abs(cY - tinta.getY());
        distance = (float) Math.sqrt(diffX * diffX + diffY * diffY);
        if (distance < minim) {
            if(map[(int)diffX/50][(int)diffY/50].IsSolid()==false) {
                minim = distance;
                c = 'R';
            }
        }
        diffX = Math.abs(cX - 50 - tinta.getX());
        diffY = Math.abs(cY - tinta.getY());
        distance = (float) Math.sqrt(diffX * diffX + diffY * diffY);
        if (distance < minim) {
            if(map[(int)diffX/50][(int)diffY/50].IsSolid()==false) {
                minim = distance;
                c = 'L';
            }
        }
        if (c == 'U') {
            this.setX(cX);
            this.setY(cY - speed);
            check=this.checkCollision(map);
        } else if (c == 'D') {
            this.setX(cX);
            this.setY(cY + speed);
            check=this.checkCollision(map);
        } else if (c == 'R') {
            this.setX(cX + speed);
            this.setY(cY);
            check=this.checkCollision(map);
        } else if (c == 'L') {
            this.setX(cX - speed);
            this.setY(cY);
            check=this.checkCollision(map);
        }
        anim();
    }
    public boolean checkCollisionEn(Enemy enemy){
        double pxss=this.getX();
        double xss=enemy.getX();
        double pyss=this.getY();
        double yss=enemy.getY();
        double pxdj=this.getX()+30;
        double xdj=enemy.getX()+30;
        double pydj=this.getY()+30;
        double ydj=enemy.getY()+30;
        double pxsj=this.getX();
        double xsj=enemy.getX();
        double pysj=this.getY()+30;
        double ysj=enemy.getY()+30;
        double pxds=this.getX()+30;
        double xds=enemy.getX()+30;
        double pyds= this.getY();
        double yds=enemy.getY();
        if(pxss>xss && pxss<xdj && pyss>yss && pyss<ydj){
            this.setX(this.getX()+1);
            this.setY(this.getY()+1);
            return true;
        }
        if(pxdj>xss && pxdj<xdj && pydj>yss && pydj<ydj){
            this.setX(this.getX()-1);
            this.setY(this.getY()-1);
            return true;
        }
        if(pxds>xss && pxds<xdj && pyds>yss && pyds<ydj){
            this.setX(this.getX()-1);
            this.setY(this.getY()+1);
            return true;
        }
        if(pxsj>xss && pxsj<xdj && pysj>yss && pysj<ydj) {
            this.setX(this.getX()+1);
            this.setY(this.getY()-1);
            return true;
        }
        return false;
    }
    @Override
    public void Draw(Graphics g,double x, double y){
        g.drawImage(img, (int)x, (int)y, null);
    }
    public void drawInMenu(Graphics g,double x,double y){g.drawImage(img,(int)x,(int)y,70,110,null);}
    public void anim(){
        if(nrF==7){
            if(this.img==Assets.enemy) {this.img=Assets.enemy2;}
            else if(this.img==Assets.enemy2) {this.img=Assets.enemy3;}
            else if(this.img==Assets.enemy3) {this.img=Assets.enemy4;}
            else if(this.img==Assets.enemy4) {this.img=Assets.enemy5;}
            else if(this.img==Assets.enemy5) {this.img=Assets.enemy6;}
            else if(this.img==Assets.enemy6) {this.img=Assets.enemy7;}
            else if(this.img==Assets.enemy7) {this.img=Assets.enemy8;}
            else if(this.img==Assets.enemy8) {this.img=Assets.enemy9;}
            else if(this.img==Assets.enemy9) {this.img=Assets.enemy10;}
            else if(this.img==Assets.enemy10) {this.img=Assets.enemy;}
            nrF=0;
        }
        nrF++;
    }
    public void flip(){
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-img.getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        img = op.filter(img, null);
    }

}
