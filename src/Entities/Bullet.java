package Entities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Bullet {
    private double x,y;
    private BufferedImage img;
    private double speedX,speedY;
    private double mx,my,m;
    private Point e;
    public Bullet(BufferedImage img, double x, double y, Point e){
        this.x=x;
        this.y=y;
        this.img=img;
        this.speedX=this.speedY=50;
        this.e=e;
        try {
            my = e.getY() - this.y;
            mx = e.getX() - this.x;
        }catch (Exception ex){
            System.out.println("Mouse not on screen");
        }
        this.m=my/mx;
    }
    public void tick(){
        if((mx/speedX<1 && mx/speedX>0) || (mx/speedX>-1 && mx/speedX<0)) speedX=5;
        else speedX=50;
        if((my/speedY<1 && my/speedY>0) || (my/speedY>-1 && my/speedY<0)) speedY=5;
        else speedY=50;
        x+=mx/speedX;
        y+=my/speedY;
    }
    public void Draw(Graphics g){
        g.drawImage(img,(int)x,(int)y,40,40,null);
    }
    public double getX(){
        return this.x;
    }
    public double getY(){
        return this.y;
    }

}
