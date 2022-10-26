package Entities;
import Graphics.Assets;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;

public class Player extends Entity{
    public Bullet blt;
    public int c;
    public String gender;
    private static Player player;
    private Player(int id,int x,int y){
        super(Assets.player, id,x,y);
        c=0;
    }
    public static Player getInstance(int id,int x,int y){
        if(player==null) {
            player=new Player(id,x,y);
        }
        return player;
    }

    public Bullet shoot(Point e){
        blt = new Bullet(Assets.bullet, this.x, this.y, e);
        return blt;
    }
    public void checkBullets(){
        if (blt != null) {
            if (blt.getX() < 0 || blt.getX() > 1200) {
                blt = null;
                return;
            }
            if (blt.getY() < 0 || blt.getY() > 1500) {
                blt = null;
                return;
            }
        }
        return;
    }
    @Override
    public void Draw(Graphics g,double x,double y){
        if(gender=="male") {
            if (c == 0) this.img = Assets.player;
            else if (c == 1) this.img = Assets.player2;
            else if (c == 2) this.img = Assets.player3;
            else if (c == 3) this.img = Assets.player4;
            else if (c == 4) this.img = Assets.player5;
            else if (c == 5) this.img = Assets.player6;
            else if (c == 6) this.img = Assets.player7;
            else if (c == 7) this.img = Assets.player8;
            else if (c == 8) this.img = Assets.player9;
            else if (c == 9) this.img = Assets.player10;
            else if (c == 10) this.img = Assets.player11;
            else if (c == 11) this.img = Assets.player12;
            else if (c == 12) this.img = Assets.player13;
            else if (c == 13) this.img = Assets.player14;
            else if (c == 14) this.img = Assets.player15;
            else if (c == 15) this.img = Assets.player16;
            else if (c == 16) this.img = Assets.player17;
            else if (c == 17) this.img = Assets.player18;
            else if (c == 18) this.img = Assets.player19;
            else if (c == 19) this.img = Assets.player20;
            else if (c == 20) this.img = Assets.player21;
            else if (c == 21) this.img = Assets.player22;
            else if (c == 22) this.img = Assets.player;
            g.drawImage(img, (int)x, (int)y, 50, 75, null);
        }else if(gender=="female"){
            if (c == 0) this.img = Assets.girlPlayer;
            else if (c == 1) this.img = Assets.girlPlayer2;
            else if (c == 2) this.img = Assets.girlPlayer3;
            else if (c == 3) this.img = Assets.girlPlayer4;
            else if (c == 4) this.img = Assets.girlPlayer5;
            else if (c == 5) this.img = Assets.girlPlayer6;
            else if (c == 6) this.img = Assets.girlPlayer7;
            else if (c == 7) this.img = Assets.girlPlayer;
            g.drawImage(this.img, (int)x, (int)y, 75, 75, null);
        }

    }
    public void drawInMenu(Graphics g,double x,double y){
        if(c==0) this.img=Assets.player;
        else if(c==1) this.img=Assets.player2;
        else if(c==2) this.img=Assets.player3;
        else if(c==3) this.img=Assets.player4;
        else if(c==4) this.img=Assets.player5;
        else if(c==5) this.img=Assets.player6;
        else if(c==6) this.img=Assets.player7;
        else if(c==7) this.img=Assets.player8;
        else if(c==8) this.img=Assets.player9;
        else if(c==9) this.img=Assets.player10;
        else if(c==10) this.img=Assets.player11;
        else if(c==11) this.img=Assets.player12;
        else if(c==12) this.img=Assets.player13;
        else if(c==13) this.img=Assets.player14;
        else if(c==14) this.img=Assets.player15;
        else if(c==15) this.img=Assets.player16;
        else if(c==16) this.img=Assets.player17;
        else if(c==17) this.img=Assets.player18;
        else if(c==18) this.img=Assets.player19;
        else if(c==19) this.img=Assets.player20;
        else if(c==20) this.img=Assets.player21;
        else if(c==21) this.img=Assets.player22;
        else if(c==22) this.img=Assets.player;
        g.drawImage(img, (int)x, (int)y, 90, 140, null);
    }
    public void  setImg(BufferedImage image){
        this.img=image;
    }
    public BufferedImage getImg(){
        return this.img;
    }
    public void Draw2(Graphics g,double x,double y,double angle){
        //g.drawImage(img, (int)x, (int)y, TILE_WIDTH, TILE_HEIGHT, null);
        Graphics2D g2=(Graphics2D) g;
        g2.setRenderingHint(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        int cx = img.getWidth() / 2;
        int cy = img.getHeight() / 2;
        AffineTransform oldAT = g2.getTransform();
        g2.translate(cx+(int)this.x, cy+(int)this.y);
        g2.rotate(angle);
        g2.translate(-cx, -cy);
        g2.drawImage(img, 0, 0, null);
        g2.setTransform(oldAT);
    }
    public void flip(){
            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-img.getWidth(null), 0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            img = op.filter(img, null);
    }

}
