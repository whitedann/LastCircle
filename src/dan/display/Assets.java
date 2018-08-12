package dan.display;

import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class Assets {

    private static final int WIDTH = 64, HEIGHT = 64;
    public static BufferedImage turretE, brokenBrick, grid, border, start1, start2, brick;

    public static BufferedImage[] turretFire;
    public static BufferedImage[] blobMove;
    public static BufferedImage[] playerDie;
    public static BufferedImage[] blobDie;
    public static BufferedImage[] blobSpawning;
    public static BufferedImage[] animateRocket;
    public static BufferedImage[] contagionMove;

    public static void init(){
       start1 = ImageLoader.loadImage("/textures/start1.png");
       start2 = ImageLoader.loadImage("/textures/start2.png");

       SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/turret.png"));

       turretFire = new BufferedImage[8];
       turretFire[0] = sheet.crop(0,0, WIDTH, HEIGHT);
       turretFire[1] = sheet.crop(WIDTH,0, WIDTH, HEIGHT);
       turretFire[2] = sheet.crop(WIDTH*2,0, WIDTH, HEIGHT);
       turretFire[3] = sheet.crop(WIDTH * 3,0, WIDTH, HEIGHT);
       turretFire[4] = sheet.crop(0,HEIGHT, WIDTH, HEIGHT);
       turretFire[5] = sheet.crop(WIDTH ,HEIGHT, WIDTH, HEIGHT);
       turretFire[6] = sheet.crop(WIDTH * 2,HEIGHT, WIDTH, HEIGHT);
       turretFire[7] = sheet.crop(WIDTH* 3,HEIGHT, WIDTH, HEIGHT);
       turretE = sheet.crop(0,0,WIDTH, HEIGHT);

       SpriteSheet blobSheet = new SpriteSheet(ImageLoader.loadImage("/textures/enemies.png"));

       blobMove = new BufferedImage[11];
       blobMove[0] =  blobSheet.crop(0, 0, WIDTH/2, HEIGHT/2);
       blobMove[1] = blobSheet.crop(WIDTH/2, 0, WIDTH/2, HEIGHT/2);
       blobMove[2] = blobSheet.crop(WIDTH, 0, WIDTH/2, HEIGHT/2);
       blobMove[3] = blobSheet.crop((3/2)*WIDTH, 0, WIDTH/2, HEIGHT/2);
       blobMove[4] = blobSheet.crop(2*WIDTH, 0, WIDTH/2, HEIGHT/2);
       blobMove[5] = blobSheet.crop( (5/2)*WIDTH, 0, WIDTH/2, HEIGHT/2);
       blobMove[6] = blobSheet.crop( 3*WIDTH, 0, WIDTH/2, HEIGHT/2);
       blobMove[7] = blobSheet.crop((7/2)*WIDTH, 0, WIDTH/2, HEIGHT/2);
       blobMove[8] = blobSheet.crop(0,HEIGHT/2, WIDTH/2, HEIGHT/2);
       blobMove[9] = blobSheet.crop(WIDTH/2,HEIGHT/2, WIDTH/2, HEIGHT/2);
       blobMove[10] = blobSheet.crop(WIDTH, HEIGHT/2, WIDTH/2, HEIGHT/2);

       SpriteSheet playerDieSheet = new SpriteSheet(ImageLoader.loadImage("/textures/turretExplosion.png"));

       playerDie = new BufferedImage[11];
       playerDie[0] = playerDieSheet.crop(0, 0, WIDTH, HEIGHT);
       playerDie[1] = playerDieSheet.crop(WIDTH, 0, WIDTH, HEIGHT);
       playerDie[2] = playerDieSheet.crop(WIDTH *2, 0, WIDTH, HEIGHT);
       playerDie[3] = playerDieSheet.crop(3*WIDTH, 0, WIDTH, HEIGHT);
       playerDie[4] = playerDieSheet.crop(0, WIDTH, WIDTH, HEIGHT);
       playerDie[5] = playerDieSheet.crop(WIDTH, WIDTH, WIDTH, HEIGHT);
       playerDie[6] = playerDieSheet.crop(2*WIDTH, WIDTH, WIDTH, HEIGHT);
       playerDie[7] = playerDieSheet.crop(3*WIDTH, WIDTH, WIDTH, HEIGHT);
       playerDie[8] = playerDieSheet.crop(0,HEIGHT*2, WIDTH, HEIGHT);
       playerDie[9] = playerDieSheet.crop(WIDTH,HEIGHT*2, WIDTH, HEIGHT);
       playerDie[10] = playerDieSheet.crop(2*WIDTH, 2*HEIGHT, WIDTH, HEIGHT);

       blobDie = new BufferedImage[7];
       blobDie[0] = blobSheet.crop(0, 3*HEIGHT/2, WIDTH/2, HEIGHT/2);
       blobDie[1] = blobSheet.crop(WIDTH/2, 3*HEIGHT/2, WIDTH/2, HEIGHT/2);
       blobDie[2] = blobSheet.crop(WIDTH, 3*HEIGHT/2, WIDTH/2, HEIGHT/2);
       blobDie[3] = blobSheet.crop(3*WIDTH/2, 3*HEIGHT/2, WIDTH/2, HEIGHT/2);
       blobDie[4] = blobSheet.crop(2*WIDTH, 3*HEIGHT/2, WIDTH/2, HEIGHT/2);
       blobDie[5] = blobSheet.crop(5*WIDTH/2, 3*HEIGHT/2, WIDTH/2, HEIGHT/2);
       blobDie[6] = blobSheet.crop(3*WIDTH, 3*HEIGHT/2, WIDTH/2, HEIGHT/2);

       blobSpawning = new BufferedImage[6];
       blobSpawning[0] = blobSheet.crop(0, 5*HEIGHT/2, WIDTH/2, HEIGHT/2);
       blobSpawning[1] = blobSheet.crop(WIDTH/2, 5*HEIGHT/2, WIDTH/2, HEIGHT/2);
       blobSpawning[2] = blobSheet.crop(WIDTH, 5*HEIGHT/2, WIDTH/2, HEIGHT/2);
       blobSpawning[3] = blobSheet.crop(3*WIDTH/2, 5*HEIGHT/2, WIDTH/2, HEIGHT/2);
       blobSpawning[4] = blobSheet.crop(2*WIDTH, 5*HEIGHT/2, WIDTH/2, HEIGHT/2);
       blobSpawning[5] = blobSheet.crop(5*WIDTH/2, 5*HEIGHT/2, WIDTH/2, HEIGHT/2);

       SpriteSheet rocketSheet = new SpriteSheet(ImageLoader.loadImage("/textures/rocketAnimations.png"));
       animateRocket = new BufferedImage[4];
       animateRocket[0] = rocketSheet.crop(0,0,96,96);
       animateRocket[1] = rocketSheet.crop(96,0,96,96);
       animateRocket[2] = rocketSheet.crop(2*96,0,96,96);
       animateRocket[3] = rocketSheet.crop(3*96,0,96,96);

       SpriteSheet contagionSheet = new SpriteSheet(ImageLoader.loadImage("/textures/contagion.png"));
       contagionMove = new BufferedImage[12];
       contagionMove[0] = contagionSheet.crop(0,0,8,8);
       contagionMove[1] = contagionSheet.crop(8,0,8,8);
       contagionMove[2] = contagionSheet.crop(16,0,8,8);
       contagionMove[3] = contagionSheet.crop(24,0,8,8);
       contagionMove[4] = contagionSheet.crop(0,8,8,8);
       contagionMove[5] = contagionSheet.crop(8,8,8,8);
       contagionMove[6] = contagionSheet.crop(16,8,8,8);
       contagionMove[7] = contagionSheet.crop(24,8,8,8);
       contagionMove[8] = contagionSheet.crop(0,16,8,8);
       contagionMove[9] = contagionSheet.crop(8,16,8,8);
       contagionMove[10] = contagionSheet.crop(16,16,8,8);
       contagionMove[11] = contagionSheet.crop(24,16,8,8);

       SpriteSheet brickSheet = new SpriteSheet(ImageLoader.loadImage("/textures/bricks.png"));
       brick = brickSheet.crop(0,0,WIDTH, HEIGHT);
       brokenBrick = brickSheet.crop(WIDTH, 0, WIDTH, HEIGHT);

       grid = ImageLoader.loadImage("/textures/iGrid.png");
       border = ImageLoader.loadImage("/textures/border.png");

    }
}
