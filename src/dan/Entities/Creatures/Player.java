package dan.Entities.Creatures;

import dan.Entities.Bullet;
import dan.display.Animation;
import dan.display.Assets;
import dan.game.Handler;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Creature {

    public Animation playerFire, playerDie, animateRocket;
    private boolean playerFiring;
    public boolean playerKilled;
    private Shape laserRect;
    private int beamWidth = 1;
    private ArrayList<Bullet> bullets;

    public Player(Handler handler, float x, float y, float angle) {
        super(handler, x, y, angle, Creature.DEFAULT_CREATURE_WIDTH,Creature.DEFAULT_CREATURE_HEIGHT);

        //Starting player stats
        this.setSpeed(2.0f);
        this.setRotationalSpeed(0.05f);

        //Player animations
        playerFire = new Animation(100, Assets.turretFire);
        playerDie = new Animation(50, Assets.playerDie);
        animateRocket = new Animation(50, Assets.animateRocket);

        //Set up dummy laser shape
        laserRect = new Rectangle(0,0,1,1);

        bullets = new ArrayList<>();
    }

    @Override
    public void tick() {
        getInput();
        if(finishedFiring()) {
            //laserRect = placeBeamProjectile();
            bullets.add(new Bullet(handler, (int) (x - handler.getCamera().getxOffset()),
                    (int)(y - handler.getCamera().getyOffset()), angle));
        }
        else
            laserRect = new Rectangle(0, 0, 1, 1);
        for(Bullet e : this.bullets) {
            if (!e.insideSolidTile())
                e.tick();
        }
        move();
        handler.getCamera().centerOnEntity(this);
        if(entityEntityCollision()) {
            playerKilled = true;
            playerFiring = false;
            xMove = 0;
            yMove = 0;
        }
    }

    public void levelUp(int score){
        int playerLevel = score / 1000;
        if(beamWidth < 10)
            beamWidth = 1 + 1 * playerLevel;
    }

    public void getInput() {
        xMove = 0;
        yMove = 0;
        thetaMove = 0;
        playerFiring = false;

        if (handler.getKeyManager().rotateR)
            thetaMove = -rotationalSpeed;
        if (handler.getKeyManager().rotateL)
            thetaMove = rotationalSpeed;
        if (handler.getKeyManager().up)
            yMove = -translationalSpeed;
        if (handler.getKeyManager().down)
            yMove = translationalSpeed;
        if (handler.getKeyManager().left)
            xMove = -translationalSpeed;
        if (handler.getKeyManager().right)
            xMove = translationalSpeed;
        if (handler.getKeyManager().fire)
            playerFiring = true;
    }

    @Override
    public void render(Graphics g) {

        AffineTransform at = new AffineTransform();
        //transforms happen in reverse order
        //3rd transform: moves player position to (x,y), accounting for the offset from the game camera.
        at.translate((int) (x - handler.getCamera().getxOffset()), (int) (y - handler.getCamera().getyOffset()));

        //2nd transform: rotates image by (angle) in radians
        at.rotate(angle);

        //1st transform: moves pivot point to center of player (default dimensions are 64x64).
        at.translate(-1 * this.getBounds().getRadius(), -1 * this.getBounds().getRadius());

        Graphics2D gd2 = (Graphics2D) g;
        gd2.drawImage(getCurrentAnimationFrame(), at, null);
        /*
        if(finishedFiring()) {
            gd2.setColor(Color.CYAN);
            gd2.fill(laserRect);
        }
        */
        gd2.setColor(Color.CYAN);
        for(Bullet e: this.bullets)
            e.render(g);
        drawRocketAnimation(gd2);
    }

    public Shape placeBeamProjectile(){
        //generates a beam from the tip of the character sprite out the given distance and rotates it according
        //to the player's orientation.
        int distance = getDistanceToNearestEntity();
        if(distance >= 200)
            distance = 200;
        Double anchorX = Double.valueOf(x - handler.getCamera().getxOffset());
        Double anchorY = Double.valueOf(y - handler.getCamera().getyOffset());
        Rectangle2D rect = new Rectangle2D.Double(anchorX,  anchorY, distance, beamWidth + 2);
        AffineTransform at = AffineTransform.getRotateInstance(angle,anchorX, anchorY);
        Shape rotatedRect = at.createTransformedShape(rect);
        return rotatedRect;
    }

    public Shape getLaserRect(){
        return laserRect;
    }

    public boolean finishedFiring(){
        if(handler.getKeyManager().fire)
            return true;
        else return false;
        /*
        if(playerFire.getCurrentFrameIndex() == 7)
            return true;
        else
            return false;
            */
    }

    public boolean finishedDying(){
        if(playerDie.getCurrentFrameIndex() == 10)
            return true;
        else
            return false;
    }

    public void drawRocketAnimation(Graphics2D g){
        //No movement, no animation
        if(yMove == 0 && xMove == 0)
            return;
        animateRocket.tick();
        AffineTransform at = new AffineTransform();

        //  Transformations occur in reverse order.
        //  3. Moves draw location 'at' to player's location, accounting for camera offset
        at.translate((int) (x - handler.getCamera().getxOffset()), (int) (y - handler.getCamera().getyOffset()));

        //  2. Rotates the image according to movment direction
        if(yMove < 0) {
            if(xMove < 0)
                at.rotate(7*Math.PI/4);
            else if(xMove > 0)
                at.rotate(Math.PI/4);
            else
                at.rotate(0);
        }
        else if(yMove > 0){
            if(xMove < 0)
                at.rotate(5*Math.PI/4);
            else if (xMove > 0)
                at.rotate(3*Math.PI/4);
            else
                at.rotate(Math.PI);
        }
        else if(yMove == 0){
            if(xMove < 0)
                at.rotate(3*Math.PI/2);
            if(xMove > 0)
                at.rotate(Math.PI/2);
        }


        //  1. Sets the pivot point to the center of the image (the rocket animation is 96x96 pixels)
        at.translate(-48,-48);

        //  Finally, draw the image.
        g.drawImage(animateRocket.getCurrentFrame(), at, null);
    }

    public BufferedImage getCurrentAnimationFrame() {
        if(playerKilled) {
            if(playerDie.getCurrentFrameIndex() == 10)
                return Assets.playerDie[10];
            else {
                playerDie.tick();
                return playerDie.getCurrentFrame();
            }
        }
        if (playerFiring) {
            playerFire.tick();
            return playerFire.getCurrentFrame();
        }
        else {
            playerFire.setCurrentFrameIndex(0);
            return Assets.turretE;
        }
    }

    /*
    public void removeBullets(){
        for(Bullet e : this.bullets){
            if(e.getShape().)
        }
    }
   */
    public ArrayList<Bullet> getBullets(){
        return this.bullets;
    }
}

