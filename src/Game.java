import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.image.BufferStrategy;

import DataBase.DataBase;
import Entities.Bullet;
import Entities.Enemy;
import Entities.Entity;
import Entities.Player;
import Graphics.Assets;
import Tiles.Tile;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;
import java.util.Random;
import java.sql.*;
import java.util.concurrent.TimeUnit;


public class Game implements Runnable {
    private GameWindow      wnd;
    private boolean         runState,menuState,scoreState,gameState,nameState,genderState,finalState;
    private Thread          gameThread;
    private BufferStrategy  bs;
    private Graphics        g;
    private Tile[][] harta=new Tile[25][20];
    private  Player  player=Player.getInstance(1,0,400);
    private Enemy[] inamici=new Enemy[100];
    private int wave,bltNr,nrFrame;
    private Bullet[] b;
    private PointerInfo mouseInfo;
    private Point mouse;
    private Canvas canvas;
    private Point mouseEvent;
    private int ok,spriteCounter,spriteNumber,spriteAnim;
    private Enemy en;
    private Font font;
    private  DataBase dataBase;
    private Scanner scn;
    private String nameText="Nume: ";
    private String gender;
    private int score,getData;
    private String date[],genders[];
    private int  scores[];
    private int spawner,lastSpawn,activeEnemies;
    private int spawnScore[];

    public Game(String title, int width, int height) {
        wnd = new GameWindow(title, width, height);
        runState = false;
    }
    private void InitGame() {
        wnd = new GameWindow("Shooter", 1000, 800);
        wnd.BuildGameWindow();
        Assets.Init();
    }
    public void run() {
        InitGame();
        long oldTime = System.nanoTime();
        long curentTime;
        en=new Enemy(1,-100,640);
        getData=1;
        en.flip();
        wave=1;
        date=genders=new String[6];
        activeEnemies=0;
        scores=new int[6];
        score=0;
        spawner=lastSpawn=0;
        bltNr=nrFrame=spriteCounter=spriteAnim=0;
        spriteNumber=1;
        b=new Bullet[30];
        canvas=GameWindow.canvas;
        final int framesPerSecond   = 60;
        final double timeFrame      = 1000000000 / framesPerSecond;
        gameState=nameState=scoreState=genderState=finalState=false;
        menuState=true;
        dataBase=new DataBase();
        try {
            InputStream inputStream = new BufferedInputStream(
                    new FileInputStream("BatBoo.ttf"));

            font = Font.createFont(Font.TRUETYPE_FONT, inputStream);

        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        while (runState == true)
        {
            curentTime = System.nanoTime();
            if((curentTime - oldTime) > timeFrame)
            {
                if(gameState==true) {
                    Update();
                    try {
                        Draw();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }else if(menuState==true){
                    Update();
                    drawMenu();
                }else if(nameState==true){
                    Update();
                    drawName();
                }else if(genderState==true){
                    Update();
                    drawGender();
                }else if(scoreState==true){
                    Update();
                    try {
                        drawScore();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }else if(finalState==true){
                    Update();
                    try {
                        drawFinal();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                oldTime = curentTime;
            }
        }


    }
    public synchronized void StartGame() {
        if(runState == false)
        {

            runState = true;
            gameThread = new Thread(this);
            gameThread.start();

        }
        else
        {
            return;
        }
    }
    public synchronized void StopGame() throws SQLException {
        if(runState == true) {
            if(score>0) {
                dataBase.updateDB(nameText, gender, score);
            }
            runState = false;
            /*try
            {
                gameThread.join();
            }
            catch(InterruptedException ex)
            {
                ex.printStackTrace();
            }*/
        }
        else
        {
            return;
        }
    }
    private void Update() {
        wnd.GetCanvas().setFocusable(false);
        wnd.GetCanvas().setFocusable(true);
        mouseInfo=MouseInfo.getPointerInfo();
        mouse=mouseInfo.getLocation();

    }
    private void Draw() throws SQLException {
        bs = wnd.GetCanvas().getBufferStrategy();
        if(bs == null) {
            try {
                wnd.GetCanvas().createBufferStrategy(3);
                return;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        g = bs.getDrawGraphics();
        g.clearRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());
        try {
            File myObj = new File("C:\\Users\\x\\IdeaProjects\\JocPAOO\\resources\\textures\\harta_background.csv");
            Scanner myReader = new Scanner(myObj);
            int j=0;
            myReader.close();
            myObj= new File("C:\\Users\\x\\IdeaProjects\\JocPAOO - Copie\\resources\\textures\\harta3_background.csv");
            myReader=new Scanner(myObj);
            j=0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] tiles=data.split(",");
                for(int i=0;i<25;i++){
                    if(Integer.parseInt(tiles[i])==2) {
                        Tile.grassTile.Draw(g, i * Tile.TILE_WIDTH, j * Tile.TILE_HEIGHT);
                        Tile.rockTile.Draw(g, i * Tile.TILE_WIDTH, j * Tile.TILE_HEIGHT);
                        harta[i][j]=Tile.rockTile;
                    }else if(Integer.parseInt(tiles[i])==6){
                        Tile.grassTile.Draw(g, i * Tile.TILE_WIDTH, j * Tile.TILE_HEIGHT);
                        harta[i][j]=Tile.grassTile;
                    }else if(Integer.parseInt(tiles[i])==2) {
                        Tile.groundTile.Draw(g, i * Tile.TILE_WIDTH, j * Tile.TILE_HEIGHT);
                        harta[i][j] = Tile.groundTile;
                    }
                }
                j++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
            if(runState==true && gameState==true) {
                if(spawner==lastSpawn) {
                    spawnEnemies(5);
                    spawner=lastSpawn+activeEnemies/2;
                    System.out.println(spawner + " "+activeEnemies);
                }
            }
            lastSpawn=score;
            drawWave();
        nrFrame++;
        if(nrFrame==50) {
            ok=1;
            try {
                mouseEvent = wnd.GetCanvas().getMousePosition();
            }catch (Exception e){
                System.out.println("Mouse not on screen");
            }

            if (ok == 1) {
                b[bltNr] = player.shoot(mouseEvent);
                bltNr++;
                if (bltNr == 30) bltNr = 0;
                ok = 0;
                mouseEvent = null;
            }
            nrFrame = 0;
        }
        if(wnd.keyH.upPressed || wnd.keyH.downPressed || wnd.keyH.rightPressed || wnd.keyH.leftPressed) {
            if (wnd.keyH.upPressed) {
                player.Draw(g, player.getX(), player.getY() - player.getSpeed());
                if (player.checkCollision(harta) == true) {
                    player.setY(player.getY() - player.getSpeed());
                }
            }
            if (wnd.keyH.downPressed) {
                player.Draw(g, player.getX(), player.getY() + player.getSpeed());
                if (player.checkCollision(harta) == true) {
                    player.setY(player.getY() + player.getSpeed());
                }
            }
            if (wnd.keyH.leftPressed) {
                player.Draw(g, player.getX() - player.getSpeed(), player.getY());
                if (player.checkCollision(harta) == true) {
                    player.setX(player.getX() - player.getSpeed());
                }
            }
            if (wnd.keyH.rightPressed) {
                player.Draw(g, player.getX() + player.getSpeed(), player.getY());
                if (player.checkCollision(harta) == true) {
                    player.setX(player.getX() + player.getSpeed());
                }
            }
            spriteAnim++;
            if(spriteAnim>2){
                if(player.gender=="male") {
                    if (player.c == 0) player.c = 1;
                    else if (player.c == 1) player.c = 2;
                    else if (player.c == 2) player.c = 3;
                    else if (player.c == 3) player.c = 4;
                    else if (player.c == 4) player.c = 5;
                    else if (player.c == 5) player.c = 6;
                    else if (player.c == 6) player.c = 7;
                    else if (player.c == 7) player.c = 8;
                    else if (player.c == 8) player.c = 9;
                    else if (player.c == 9) player.c = 10;
                    else if (player.c == 10) player.c = 11;
                    else if (player.c == 11) player.c = 12;
                    else if (player.c == 12) player.c = 13;
                    else if (player.c == 13) player.c = 14;
                    else if (player.c == 14) player.c = 15;
                    else if (player.c == 15) player.c = 16;
                    else if (player.c == 16) player.c = 17;
                    else if (player.c == 17) player.c = 18;
                    else if (player.c == 18) player.c = 19;
                    else if (player.c == 19) player.c = 20;
                    else if (player.c == 20) player.c = 21;
                    else if (player.c == 21) player.c = 22;
                    else if (player.c == 22) player.c = 0;
                }else if(player.gender=="female"){
                    if (player.c == 0) player.c = 1;
                    else if (player.c == 1) player.c = 2;
                    else if (player.c == 2) player.c = 3;
                    else if (player.c == 3) player.c = 4;
                    else if (player.c == 4) player.c = 5;
                    else if (player.c == 5) player.c = 6;
                    else if (player.c == 6) player.c = 7;
                    else if (player.c == 7) player.c = 0;
                }
                spriteAnim=0;
            }
        }
        checkDie();
        player.Draw(g,player.getX(),player.getY());
        if(b!=null) {
            for(int i=0;i<30;i++) {
                if(b[i]!=null) {
                    b[i].Draw(g);
                    b[i].tick();
                    player.checkBullets();
                }
            }
        }
        checkHit();
        for (int i=0;i<20;i++){
            if(inamici[i]!=null) {
                inamici[i].move(player,harta);
            }
        }
        g.setFont(font.deriveFont(Font.PLAIN, 55f));
        g.setColor(Color.darkGray);
        g.drawString("Score: "+score, 100, 700);
        g.setColor(Color.white);
        g.drawString("Score: "+score, 100 - 5, 700 - 5);
        if(wnd.keyH.caracter==27){
            gameState=false;
            finalState=true;
          //  StopGame();
        }
        bs.show();
        g.dispose();
    }
    private void drawMenu(){
        bs = wnd.GetCanvas().getBufferStrategy();
        if(bs == null) {
            try {
                wnd.GetCanvas().createBufferStrategy(3);
                return;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        g = bs.getDrawGraphics();
        g.clearRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());
        g.drawImage(Assets.menuImage, 0, 0, 1000, 800, null);
        player.setY(640);
        player.drawInMenu(g, player.getX(), player.getY());
        player.setX(player.getX()+2);
        if(player.getX()>1000) player.setX(-60);
        spriteAnim++;
        if(spriteAnim>2){
            if(player.c==0) player.c=1;
            else if(player.c==1) player.c=2;
            else if(player.c==2) player.c=3;
            else if(player.c==3) player.c=4;
            else if(player.c==4) player.c=5;
            else if(player.c==5) player.c=6;
            else if(player.c==6) player.c=7;
            else if(player.c==7) player.c=8;
            else if(player.c==8) player.c=9;
            else if(player.c==9) player.c=10;
            else if(player.c==10) player.c=11;
            else if(player.c==11) player.c=12;
            else if(player.c==12) player.c=13;
            else if(player.c==13) player.c=14;
            else if(player.c==14) player.c=15;
            else if(player.c==15) player.c=16;
            else if(player.c==16) player.c=17;
            else if(player.c==17) player.c=18;
            else if(player.c==18) player.c=19;
            else if(player.c==19) player.c=20;
            else if(player.c==20) player.c=21;
            else if(player.c==21) player.c=22;
            else if(player.c==22) player.c=0;
            spriteAnim=0;
        }
        en.drawInMenu(g,en.getX(),640);
        en.anim();
        en.setX(en.getX()+2);
        if(en.getX()>1000) en.setX(-60);
        g.setFont(font.deriveFont(Font.PLAIN, 55f));
        g.setColor(Color.darkGray);
        String text = "Start Game";
        g.drawString(text, 340, 400);
        g.setColor(Color.white);
        g.drawString(text, 340 - 5, 400 - 5);
        text = "HighScores";
        g.setColor(Color.darkGray);
        g.drawString(text, 360, 500);
        g.setColor(Color.white);
        g.drawString(text, 360 - 5, 500 - 5);
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                   if(e.getX()>343 && e.getX()<674 && e.getY()>341 && e.getY()<395 && menuState==true) {
                        menuState=false;
                        nameState=true;
                        player.setY(400);
                        player.setX(500);
                    }else if(e.getX()>357 && e.getX()<666 && e.getY()>441 && e.getY()<493 && menuState==true) {
                       menuState=false;
                       scoreState=true;
                   }
            }
        });
        bs.show();
        g.dispose();
    }
    private void drawName(){
        bs = wnd.GetCanvas().getBufferStrategy();
        if(bs == null) {
            try {
                wnd.GetCanvas().createBufferStrategy(3);
                return;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        g = bs.getDrawGraphics();
        g.clearRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());
        g.drawImage(Assets.nameImage, 0, 0, 1000, 800, null);
        g.setFont(font.deriveFont(Font.PLAIN, 55f));
        g.setColor(Color.darkGray);
        g.drawString(nameText, 340, 400);
        g.setColor(Color.white);
        g.drawString(nameText, 340 - 5, 400 - 5);
        if((wnd.keyH.caracter>='a' && wnd.keyH.caracter<='z' || wnd.keyH.caracter>='A' && wnd.keyH.caracter<='Z') && nameState==true) {
            nameText+=wnd.keyH.caracter;
            wnd.keyH.caracter='|';
        }
        if (wnd.keyH.caracter==8 && nameText!="Nume: " && nameState==true)
        {
            nameText = nameText.substring(0,nameText.length()-1);
            wnd.keyH.caracter='|';
        }
        if(wnd.keyH.caracter==48 && nameState==true){
            nameState=false;
            genderState=true;
        }
        bs.show();
        g.dispose();
    }
    private void drawGender(){
        bs = wnd.GetCanvas().getBufferStrategy();
        if(bs == null) {
            try {
                wnd.GetCanvas().createBufferStrategy(3);
                return;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        g = bs.getDrawGraphics();
        g.clearRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());
        g.drawImage(Assets.nameImage, 0, 0, 1000, 800, null);
        g.setFont(font.deriveFont(Font.PLAIN, 55f));
        g.setColor(Color.darkGray);
        g.drawString("Select your gender:", 200, 200);
        g.setColor(Color.white);
        g.drawString("Select your gender:", 200 - 5, 200 - 5);
        g.drawImage(Assets.player,250,350,100,150,null);
        g.drawImage(Assets.girlPlayer,550,350,150,150,null);
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getX()>250 && e.getX()<350 && e.getY()>350 && e.getY()<500 ) {
                    gender="male";
                    player.gender=gender;
                }else if(e.getX()>550 && e.getX()<700 && e.getY()>350 && e.getY()<500){
                    gender="female";
                    player.gender=gender;
                }
            }
        });
        g.setColor(Color.darkGray);
        g.drawString("Gender: "+gender, 300, 700);
        g.setColor(Color.white);
        g.drawString("Gender: "+gender, 300 - 5, 700 - 5);
        if(wnd.keyH.caracter==49 && genderState==true && player.gender!=null){
            genderState=false;
            gameState=true;
        }
        bs.show();
        g.dispose();
    }
    private void drawScore() throws SQLException {
        bs = wnd.GetCanvas().getBufferStrategy();
        if(bs == null) {
            try {
                wnd.GetCanvas().createBufferStrategy(3);
                return;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        g = bs.getDrawGraphics();
        g.clearRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());
        g.drawImage(Assets.nameImage, 0, 0, 1000, 800, null);
        int x=100;
        int y=200;
        if(getData==1) {
            dataBase.getData();
            date=dataBase.getColumnOne();
            scores=dataBase.getColumnTwo();
            genders=dataBase.getColumnThree();
            getData=0;
        }
        for(int i=0;i<5;i++){
            if(date[i]!=null) {
                g.setFont(font.deriveFont(Font.PLAIN, 44f));
                g.setColor(Color.darkGray);
                String text = date[i];
                g.drawString(text, x, y);
                g.setColor(Color.white);
                g.drawString(text, x - 5, y - 5);
                y=y+100;
            }
        }
        x=500;
        y=200;
        for(int i=0;i<5;i++){
            if(scores[i]!=0) {
                g.setFont(font.deriveFont(Font.PLAIN, 44f));
                g.setColor(Color.darkGray);
                int text = scores[i];
                g.drawString(Integer.toString(text), x, y);
                g.setColor(Color.white);
                g.drawString(Integer.toString(text), x - 5, y - 5);
                y=y+100;
            }
        }
        x=700;
        y=200;
        for(int i=0;i<5;i++){
            if(genders[i]!=null) {
                g.setFont(font.deriveFont(Font.PLAIN, 44f));
                g.setColor(Color.darkGray);
                String text = genders[i];
                g.drawString(text, x, y);
                g.setColor(Color.white);
                g.drawString(text, x - 5, y - 5);
                y=y+100;
            }
        }
        g.setColor(Color.darkGray);
        String text = "BACK";
        g.drawString(text, 50, 700);
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getX()>50 && e.getX()<177 && e.getY()>656 && e.getY()<700 ) {
                    scoreState=false;
                    menuState=true;
                    en.setX(-100);
                }
            }
        });
        bs.show();
        g.dispose();
    }
    private void drawFinal() throws SQLException {
        bs = wnd.GetCanvas().getBufferStrategy();
        if(bs == null) {
            try {
                wnd.GetCanvas().createBufferStrategy(3);
                return;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        g = bs.getDrawGraphics();
        g.clearRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());
        g.drawImage(Assets.nameImage, 0, 0, 1000, 800, null);
        if(score>0) {
            dataBase.updateDB(nameText, gender, score);
        }
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getX()>347 && e.getX()<653 && e.getY()>342 && e.getY()<397 ) {
                    score=0;
                    nameText="Nume: ";
                    spawner=0;
                    lastSpawn=0;
                    for(int i=0;i<activeEnemies;i++) inamici[i]=null;
                    activeEnemies=0;
                    en.setX(-100);
                    finalState=false;
                    menuState=true;
                }
            }
        });
        g.setFont(font.deriveFont(Font.PLAIN, 55f));
        g.setColor(Color.darkGray);
        g.drawString("Score: "+score, 370, 200);
        g.setColor(Color.white);
        g.drawString("Score: "+score, 370 - 5, 200 - 5);
        g.setColor(Color.darkGray);
        g.drawString("Main Menu", 350, 400);
        g.setColor(Color.white);
        g.drawString("Main Menu", 350 - 5, 400 - 5);
        bs.show();
        g.dispose();
    }
    void spawnEnemies(int size){
        Random rnd=new Random();
        int x,y;
        System.out.println("Wave incoming");
        for(int i=0;i< inamici.length && size>0;i++) {

            x = rnd.nextInt(1000);
            y = rnd.nextInt(800);
            /*if(harta[x/50][y/50].IsSolid()==true){
                while(harta[x/50][y/50].IsSolid()==true){
                    x = rnd.nextInt(1000);
                    y = rnd.nextInt(800);
                }
            }*/
            if((x>300 && x<700) || (y>200 && y<650) || (x> player.getX() && x< player.getX()) || (y> player.getY() && y<player.getY()) ){
                while((x>300 && x<700) || (y>200 && y<650) || (x> player.getX() && x< player.getX()) || (y> player.getY() && y<player.getY())) {
                    x = rnd.nextInt(1100);
                    y = rnd.nextInt(900);
                }
            }
            if(inamici[i]==null) {
                inamici[i] = new Enemy(1, x, y);
                System.out.println("Am spawnat inamicul "+i);
                activeEnemies++;
                System.out.println(activeEnemies);
                size--;
            }
        }
    }
    void drawWave(){
        for(int i=0;i</*inamici.length*/activeEnemies*2;i++){
            for(int j=0;j< /*inamici.length*/activeEnemies*2;j++) {
                if(i!=j){
                    if(inamici[i]!=null && inamici[j]!=null) {
                        inamici[i].checkCollisionEn(inamici[j]);
                    }
                }
                if(inamici[i]!=null) {
                    inamici[i].Draw(g, inamici[i].getX(), inamici[i].getY());
                }
            }
            g.setColor(Color.red);
            if(inamici[i]!=null) {
                g.drawRect(inamici[i].solidArea.x + 8, inamici[i].solidArea.y + 8, inamici[i].solidArea.width, inamici[i].solidArea.height);
            }
        }
    }
    void checkHit(){
        for(int i=0;i<bltNr;i++){
            if(b!=null){
                for(int j=0;j<activeEnemies*2;j++) {
                    if (inamici[j] != null && b[i]!=null){
                        if (b[i].getX() > inamici[j].getX() && b[i].getX() < inamici[j].getX() + 60 && b[i].getY() > inamici[j].getY() && b[i].getY() < inamici[j].getY()+70)  {
                            System.out.println("Atins inamicul "+j);
                            score++;
                            inamici[j]=null;
                            activeEnemies--;
                            b[i]=null;
                        }
                    }
                }
            }
        }
    }
    void checkDie(){
        for(int i=0;i<activeEnemies;i++){
            if(inamici[i]!=null){
                if(inamici[i].getX()>player.getX() && inamici[i].getX()< player.getX()+50 && inamici[i].getY()> player.getY() && inamici[i].getY()< player.getY()+70){
                    gameState=false;
                    finalState=true;
                }else if(inamici[i].getX()+50>player.getX() && inamici[i].getX()+50< player.getX()+50 && inamici[i].getY()+70>player.getY() && inamici[i].getY()+70< player.getY()+70){
                    gameState=false;
                    finalState=true;
                }else if(inamici[i].getX()>player.getX() && inamici[i].getX()< player.getX()+50 && inamici[i].getY()+70>player.getY() && inamici[i].getY()+70< player.getY()+70){
                    gameState=false;
                    finalState=true;
                }
            }
        }
    }

}

