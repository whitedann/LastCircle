package dan.display;

import dan.Tile.Tile;

import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class Assets {

    private static final int WIDTH = 64, HEIGHT = 64;
    public static BufferedImage turretE, brokenBrick, grid, border, start1, brick, controlsButton, exitButton,
      exitButtonSelected, controlButtonSelected, surivalModeButton, survivalModeButtonSelected, menuArrow;

    public static BufferedImage[] turretFire;
    public static BufferedImage[] blobMove;
    public static BufferedImage[] playerDie;
    public static BufferedImage[] blobDie;
    public static BufferedImage[] blobSpawning;
    public static BufferedImage[] blobDying;
    public static BufferedImage[] animateRocket;
    public static BufferedImage[] contagionMove;
    public static BufferedImage[] greenBlobMove;
    public static BufferedImage[] redBlobMove;
    public static BufferedImage[] pinwheelMove;
    public static BufferedImage[] bigMove;
    public static BufferedImage[] bigSpawn;
    public static BufferedImage[] bigHit;
    public static BufferedImage[] bigDeath;

    public static void init(){
       start1 = ImageLoader.loadImage("/textures/start1.png");
       controlsButton = ImageLoader.loadImage("/textures/controlsButton.png");
       exitButton = ImageLoader.loadImage("/textures/exitButton.png");
       exitButtonSelected = ImageLoader.loadImage("/textures/exitButtonSelected.png");
       controlButtonSelected = ImageLoader.loadImage("/textures/controlButtonSelected.png");
       surivalModeButton = ImageLoader.loadImage("/textures/survivalModeButton.png");
       survivalModeButtonSelected = ImageLoader.loadImage("/textures/survivalModeButtonSelected.png");
       menuArrow =  ImageLoader.loadImage("/textures/menuArrow.png");

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

       blobDying = new BufferedImage[8];
       blobDying[0] = blobSheet.crop(2*WIDTH, 3*WIDTH/2, WIDTH/2, HEIGHT/2);
       blobDying[1] = blobSheet.crop(3*WIDTH/2,3*HEIGHT/2, WIDTH/2, HEIGHT/2);
       blobDying[2] = blobSheet.crop(2*WIDTH, 3*HEIGHT/2, WIDTH/2, HEIGHT/2);
       blobDying[3] = blobSheet.crop(5*WIDTH/2, 3*HEIGHT/2, WIDTH/2, HEIGHT/2);
       blobDying[4] = blobSheet.crop(0, 2*HEIGHT, HEIGHT/2, HEIGHT/2);
       blobDying[5] = blobSheet.crop(WIDTH/2, 2*HEIGHT, HEIGHT/2, HEIGHT/2);
       blobDying[6] = blobSheet.crop(WIDTH, 2*HEIGHT, HEIGHT/2, HEIGHT/2);
       blobDying[7] = blobSheet.crop(3*WIDTH/2, 2*HEIGHT, HEIGHT/2, HEIGHT/2);


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

       SpriteSheet greenBlobSheet = new SpriteSheet(ImageLoader.loadImage("/textures/greenBlob.png"));
       greenBlobMove = new BufferedImage[11];
       greenBlobMove[0] =  greenBlobSheet.crop(0, 0, WIDTH/2, HEIGHT/2);
       greenBlobMove[1] = greenBlobSheet.crop(WIDTH/2, 0, WIDTH/2, HEIGHT/2);
       greenBlobMove[2] = greenBlobSheet.crop(WIDTH, 0, WIDTH/2, HEIGHT/2);
       greenBlobMove[3] = greenBlobSheet.crop((3/2)*WIDTH, 0, WIDTH/2, HEIGHT/2);
       greenBlobMove[4] = greenBlobSheet.crop(2*WIDTH, 0, WIDTH/2, HEIGHT/2);
       greenBlobMove[5] = greenBlobSheet.crop( (5/2)*WIDTH, 0, WIDTH/2, HEIGHT/2);
       greenBlobMove[6] = greenBlobSheet.crop( 3*WIDTH, 0, WIDTH/2, HEIGHT/2);
       greenBlobMove[7] = greenBlobSheet.crop((7/2)*WIDTH, 0, WIDTH/2, HEIGHT/2);
       greenBlobMove[8] = greenBlobSheet.crop(0,HEIGHT/2, WIDTH/2, HEIGHT/2);
       greenBlobMove[9] = greenBlobSheet.crop(WIDTH/2,HEIGHT/2, WIDTH/2, HEIGHT/2);
       greenBlobMove[10] = greenBlobSheet.crop(WIDTH, HEIGHT/2, WIDTH/2, HEIGHT/2);

       SpriteSheet redBlobSheet = new SpriteSheet(ImageLoader.loadImage("/textures/redBlob.png"));
       redBlobMove = new BufferedImage[11];
       redBlobMove[0] =  redBlobSheet.crop(0, 0, WIDTH/2, HEIGHT/2);
       redBlobMove[1] = redBlobSheet.crop(WIDTH/2, 0, WIDTH/2, HEIGHT/2);
       redBlobMove[2] = redBlobSheet.crop(WIDTH, 0, WIDTH/2, HEIGHT/2);
       redBlobMove[3] = redBlobSheet.crop((3/2)*WIDTH, 0, WIDTH/2, HEIGHT/2);
       redBlobMove[4] = redBlobSheet.crop(2*WIDTH, 0, WIDTH/2, HEIGHT/2);
       redBlobMove[5] = redBlobSheet.crop( (5/2)*WIDTH, 0, WIDTH/2, HEIGHT/2);
       redBlobMove[6] = redBlobSheet.crop( 3*WIDTH, 0, WIDTH/2, HEIGHT/2);
       redBlobMove[7] = redBlobSheet.crop((7/2)*WIDTH, 0, WIDTH/2, HEIGHT/2);
       redBlobMove[8] = redBlobSheet.crop(0,HEIGHT/2, WIDTH/2, HEIGHT/2);
       redBlobMove[9] = redBlobSheet.crop(WIDTH/2,HEIGHT/2, WIDTH/2, HEIGHT/2);
       redBlobMove[10] = redBlobSheet.crop(WIDTH, HEIGHT/2, WIDTH/2, HEIGHT/2);

       SpriteSheet pinwheelSheet = new SpriteSheet(ImageLoader.loadImage("/textures/pinwheelBlob.png"));
       pinwheelMove = new BufferedImage[24];
       pinwheelMove[0] =  pinwheelSheet.crop(0, 0, WIDTH/2, HEIGHT/2);
       pinwheelMove[1] = pinwheelSheet.crop(WIDTH/2, 0, WIDTH/2, HEIGHT/2);
       pinwheelMove[2] = pinwheelSheet.crop(WIDTH, 0, WIDTH/2, HEIGHT/2);
       pinwheelMove[3] = pinwheelSheet.crop((3/2)*WIDTH, 0, WIDTH/2, HEIGHT/2);
       pinwheelMove[4] = pinwheelSheet.crop(2*WIDTH, 0, WIDTH/2, HEIGHT/2);
       pinwheelMove[5] = pinwheelSheet.crop( (5/2)*WIDTH, 0, WIDTH/2, HEIGHT/2);
       pinwheelMove[6] = pinwheelSheet.crop( 3*WIDTH, 0, WIDTH/2, HEIGHT/2);
       pinwheelMove[7] = pinwheelSheet.crop((7/2)*WIDTH, 0, WIDTH/2, HEIGHT/2);
       pinwheelMove[8] = pinwheelSheet.crop(0,HEIGHT/2, WIDTH/2, HEIGHT/2);
       pinwheelMove[9] = pinwheelSheet.crop(WIDTH/2,HEIGHT/2, WIDTH/2, HEIGHT/2);
       pinwheelMove[10] = pinwheelSheet.crop(WIDTH, HEIGHT/2, WIDTH/2, HEIGHT/2);
       pinwheelMove[11] =  pinwheelSheet.crop((3/2)*WIDTH, HEIGHT/2, WIDTH/2, HEIGHT/2);
       pinwheelMove[12] = pinwheelSheet.crop(2*WIDTH, HEIGHT/2, WIDTH/2, HEIGHT/2);
       pinwheelMove[13] = pinwheelSheet.crop((5/2)*WIDTH, HEIGHT/2, WIDTH/2, HEIGHT/2);
       pinwheelMove[14] = pinwheelSheet.crop(3*WIDTH, HEIGHT/2, WIDTH/2, HEIGHT/2);
       pinwheelMove[15] = pinwheelSheet.crop((7/2)*WIDTH, HEIGHT/2, WIDTH/2, HEIGHT/2);
       pinwheelMove[16] = pinwheelSheet.crop( 0, HEIGHT, WIDTH/2, HEIGHT/2);
       pinwheelMove[17] = pinwheelSheet.crop( WIDTH/2, HEIGHT, WIDTH/2, HEIGHT/2);
       pinwheelMove[18] = pinwheelSheet.crop(WIDTH, HEIGHT, WIDTH/2, HEIGHT/2);
       pinwheelMove[19] = pinwheelSheet.crop((3/2)*WIDTH,HEIGHT, WIDTH/2, HEIGHT/2);
       pinwheelMove[20] = pinwheelSheet.crop(2*WIDTH,HEIGHT, WIDTH/2, HEIGHT/2);
       pinwheelMove[21] = pinwheelSheet.crop((5/2)*WIDTH, HEIGHT, WIDTH/2, HEIGHT/2);
       pinwheelMove[22] = pinwheelSheet.crop(3*WIDTH, HEIGHT, WIDTH/2, HEIGHT/2);
       pinwheelMove[23] = pinwheelSheet.crop((5/2)*WIDTH, HEIGHT, WIDTH/2, HEIGHT/2);

       SpriteSheet bigSheet = new SpriteSheet(ImageLoader.loadImage("/textures/BigCircle.png"));
       bigMove =  new BufferedImage[16];
       bigMove[0] = bigSheet.crop(0 ,0,120,120);
       bigMove[1] = bigSheet.crop(120, 0, 120,120);
       bigMove[2] = bigSheet.crop(120*2, 0, 120,120);
       bigMove[3] = bigSheet.crop(120*3, 0, 120,120);
       bigMove[4] = bigSheet.crop(0, 120,120,120);
       bigMove[5] = bigSheet.crop(120, 120, 120, 120);
       bigMove[6] = bigSheet.crop( 120*2, 120, 120, 120);
       bigMove[7] = bigSheet.crop( 120*3,120, 120,120);
       bigMove[8] = bigSheet.crop( 0, 120*2, 120, 120);
       bigMove[9] = bigSheet.crop( 120, 120*2, 120,120);
       bigMove[10] = bigSheet.crop( 120*2, 120*2, 120,120);
       bigMove[11] = bigSheet.crop( 120*3,120*2, 120,120);
       bigMove[12] = bigSheet.crop( 0, 120*3, 120,120);
       bigMove[13] = bigSheet.crop(120, 120*3, 120, 120);
       bigMove[14] = bigSheet.crop(120*2, 120*2, 120, 120);
       bigMove[15] = bigSheet.crop(120*3, 120*2, 120, 120);

       SpriteSheet bigSpawnSheet = new SpriteSheet(ImageLoader.loadImage("/textures/bigspawn.png"));
       bigSpawn = new BufferedImage[8];
       bigSpawn[0] = bigSpawnSheet.crop(0,0,120,120);
       bigSpawn[1] = bigSpawnSheet.crop(120, 0,120,120);
       bigSpawn[2] = bigSpawnSheet.crop(120*2, 0, 120, 120);
       bigSpawn[3] = bigSpawnSheet.crop(120*3, 0, 120, 120);
       bigSpawn[4] = bigSpawnSheet.crop(0, 120, 120, 120);
       bigSpawn[5] = bigSpawnSheet.crop(120, 120, 120,120);
       bigSpawn[6] = bigSpawnSheet.crop( 120*2, 120, 120,120);
       bigSpawn[7] = bigSpawnSheet.crop( 120*3,120, 120, 120);

       SpriteSheet bigHitSheet = new SpriteSheet(ImageLoader.loadImage("/textures/bighit.png"));
       bigHit = new BufferedImage[2];
       bigHit[0] = bigHitSheet.crop(0,0,120,120);
       bigHit[1] = bigHitSheet.crop(120, 0, 120,120);

       SpriteSheet bigDeathSheet = new SpriteSheet(ImageLoader.loadImage("/textures/bigDeath.png"));
       bigDeath = new BufferedImage[8];
       bigDeath[0] = bigDeathSheet.crop(0,0,120, 120);
       bigDeath[1] = bigDeathSheet.crop(120, 0, 120, 120);
       bigDeath[2] = bigDeathSheet.crop(120*2, 0, 120,120);
       bigDeath[3] = bigDeathSheet.crop(120*3, 0, 120, 120);
       bigDeath[4] = bigDeathSheet.crop(0, 120, 120,120);
       bigDeath[5] = bigDeathSheet.crop(120, 120, 120,120);
       bigDeath[6] = bigDeathSheet.crop( 120*2, 120, 120,120);
       bigDeath[7] = bigDeathSheet.crop( 120*3, 120, 120, 120);


       SpriteSheet brickSheet = new SpriteSheet(ImageLoader.loadImage("/textures/bricks.png"));
       brick = brickSheet.crop(0,0,WIDTH, HEIGHT);
       brokenBrick = brickSheet.crop(WIDTH, 0, WIDTH, HEIGHT);

       grid = ImageLoader.loadImage("/textures/iGrid2.png");
       border = ImageLoader.loadImage("/textures/border.png");

    }
}
