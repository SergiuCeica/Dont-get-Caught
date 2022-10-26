package Graphics;

import java.awt.image.BufferedImage;
public class Assets
{

    public static BufferedImage grass;
    public static BufferedImage rock;
    public static BufferedImage player,player2,player3,player4,player5,player6,player7,player8,player9,player10,player11,player12,player13,player14,player15,player16,player17,player18,player19,player20,player21,player22;
    public static BufferedImage enemy,enemy2,enemy3,enemy4,enemy5,enemy6,enemy7,enemy8,enemy9,enemy10;
    public static BufferedImage bullet;
    public static BufferedImage ground;
    public static BufferedImage menuImage,nameImage,girlPlayer,girlPlayer2,girlPlayer3,girlPlayer4,girlPlayer5,girlPlayer6,girlPlayer7;

    public static void Init()
    {
        SpriteSheet sheet = new SpriteSheet(ImageLoader.LoadImage("C:\\Users\\x\\IdeaProjects\\JocPAOO - Copie\\resources\\textures\\iarba.png"));
        grass = sheet.crop(0, 0);
        sheet= new SpriteSheet(ImageLoader.LoadImage("C:\\Users\\x\\IdeaProjects\\JocPAOO - Copie\\resources\\textures\\rock.png"));
        rock = sheet.crop(0, 0);
        sheet= new SpriteSheet(ImageLoader.LoadImage("C:\\Users\\x\\IdeaProjects\\JocPAOO - Copie\\resources\\textures\\player.png"));
        player = sheet.cropV2(0, 0,50,75);
        sheet= new SpriteSheet(ImageLoader.LoadImage("C:\\Users\\x\\IdeaProjects\\JocPAOO - Copie\\resources\\textures\\ghost2.png"));
        enemy=sheet.cropV2(0,0,50,75);
        enemy2=sheet.cropV2(1,0,50,75);
        enemy3=sheet.cropV2(2,0,50,75);
        enemy4=sheet.cropV2(3,0,50,75);
        enemy5=sheet.cropV2(4,0,50,75);
        enemy6=sheet.cropV2(5,0,50,75);
        enemy7=sheet.cropV2(6,0,50,75);
        enemy8=sheet.cropV2(7,0,50,75);
        enemy9=sheet.cropV2(8,0,50,75);
        enemy10=sheet.cropV2(9,0,50,75);
        sheet= new SpriteSheet(ImageLoader.LoadImage("C:\\Users\\x\\IdeaProjects\\JocPAOO - Copie\\resources\\textures\\bullet3.png"));
        bullet=sheet.cropV2(0,0,40,40);
        sheet= new SpriteSheet(ImageLoader.LoadImage("C:\\Users\\x\\IdeaProjects\\JocPAOO - Copie\\resources\\textures\\ground.png"));
        ground=sheet.crop(0,0);
        sheet= new SpriteSheet(ImageLoader.LoadImage("C:\\Users\\x\\IdeaProjects\\JocPAOO - Copie\\resources\\textures\\player.png"));
        player2 = sheet.cropV2(1, 0,50,75);
        player3 = sheet.cropV2(2, 0,50,75);
        player4 = sheet.cropV2(3, 0,50,75);
        player5 = sheet.cropV2(4, 0,50,75);
        player6 = sheet.cropV2(5, 0,50,75);
        player7 = sheet.cropV2(6, 0,50,75);
        player8 = sheet.cropV2(7, 0,50,75);
        player9 = sheet.cropV2(8, 0,50,75);
        player10 = sheet.cropV2(9, 0,50,75);
        player11 = sheet.cropV2(10, 0,50,75);
        player12 = sheet.cropV2(11, 0,50,75);
        player13 = sheet.cropV2(12, 0,50,75);
        player14 = sheet.cropV2(13, 0,50,75);
        player15 = sheet.cropV2(14, 0,50,75);
        player16 = sheet.cropV2(15, 0,50,75);
        player17 = sheet.cropV2(16, 0,50,75);
        player18 = sheet.cropV2(17, 0,50,75);
        player19 = sheet.cropV2(18, 0,50,75);
        player20 = sheet.cropV2(19, 0,50,75);
        player21 = sheet.cropV2(20, 0,50,75);
        player22 = sheet.cropV2(21, 0,50,75);
        sheet= new SpriteSheet(ImageLoader.LoadImage("C:\\Users\\x\\IdeaProjects\\JocPAOO - Copie\\resources\\textures\\background.png"));
        menuImage=sheet.cropV2(0,0,1000,800);
        sheet= new SpriteSheet(ImageLoader.LoadImage("C:\\Users\\x\\IdeaProjects\\JocPAOO - Copie\\resources\\textures\\name.png"));
        nameImage=sheet.cropV2(0,0,1000,800);
        sheet= new SpriteSheet(ImageLoader.LoadImage("C:\\Users\\x\\IdeaProjects\\JocPAOO - Copie\\resources\\textures\\girl.png"));
        girlPlayer=sheet.cropV2(0,0,75,75);
        girlPlayer2=sheet.cropV2(1,0,75,75);
        girlPlayer3=sheet.cropV2(2,0,75,75);
        girlPlayer4=sheet.cropV2(3,0,75,75);
        girlPlayer5=sheet.cropV2(4,0,75,75);
        girlPlayer6=sheet.cropV2(5,0,75,75);
        girlPlayer7=sheet.cropV2(6,0,75,75);
    }
}
