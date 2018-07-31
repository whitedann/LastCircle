package dan.Entities.Creatures;

import dan.display.Animation;
import dan.display.Assets;
import dan.game.Handler;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Player extends Creature {

    private Animation playerFire, playerDie;
    private boolean playerFiring;
    public boolean playerKilled;
    private Shape laserRect;

    public Player(Handler handler, float x, float y, float angle) {
        super(handler, x, y, angle, Creature.DEFAULT_CREATURE_WIDTH,Creature.DEFAULT_CREATURE_HEIGHT);

        //Starting player stats
        this.setSpeed(2.0f);
        this.setRotationalSpeed(0.05f);

        //Player animations
        playerFire = new Animation(20, Assets.turretFire);
        playerDie = new Animation(100, Assets.playerDie);

        //Set up dummy laser shape
        laserRect = new Rectangle(0,0,1,1);
    }

    @Override
    public void tick() {
        getInput();
        move();
        handler.getCamera().centerOnEntity(this);
        if(entityEntityCollision())
            playerKilled = true;
        if(playerFiring)
            laserRect = placeBeamProjectile();

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
        at.translate(-1 * this.getWidth() / 2, -1 * this.getHeight() / 2);

        Graphics2D gd2 = (Graphics2D) g;
        gd2.drawImage(getCurrentAnimationFrame(), at, null);
        if(playerFire.getCurrentFrameIndex() == 7 || playerFire.getCurrentFrameIndex() == 6) {
            gd2.setColor(Color.BLUE);
            gd2.fill(laserRect);
        }
    }

    public Shape placeBeamProjectile(){
        //generates a beam from the tip of the character sprite out the given distance and rotates it according
        //to the player's orientation.
        Double anchorX = Double.valueOf(x - handler.getCamera().getxOffset());
        Double anchorY = Double.valueOf(y - handler.getCamera().getyOffset() - 1);
        Rectangle2D rect = new Rectangle2D.Double(anchorX + 32,  anchorY - 3, handler.getPlayer().getDistanceToNearestSolidTile()-32, 5);
        AffineTransform at = AffineTransform.getRotateInstance(angle,anchorX, anchorY);
        Shape rotatedRect = at.createTransformedShape(rect);
        return rotatedRect;
    }

    public Shape getLaserRect(){
        return laserRect;
    }

    private BufferedImage getCurrentAnimationFrame() {
        if(entityEntityCollision()) {
            playerDie.tick();
            return playerDie.getCurrentFrame();
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
}

