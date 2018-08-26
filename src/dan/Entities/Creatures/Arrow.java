package dan.Entities.Creatures;

import dan.display.Animation;
import dan.display.Assets;
import dan.game.Handler;
import javafx.scene.shape.Circle;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;


public class Arrow extends Creature{

    private Animation arrowMove, arrowDie, arrowSpawn;
    private int timesHit;

    public Arrow(Handler handler,float x, float y, int direction){
        super(handler, x, y, 0, 32, 32);
        setSpeed(5.0f);
        arrowMove = new Animation(100, Assets.arrowMove);
        arrowSpawn = new Animation(100, Assets.blobSpawning);
        arrowDie = new Animation(100, Assets.blobDie);

        //The "direction" parameter determines which way the arrow begins to move. down, left, right, up
        if(direction == 1) {
            yMove = 0;
            xMove = translationalSpeed;
        }
        else if(direction == 2) {
            xMove = -translationalSpeed;
            yMove = 0;
        }
        else if(direction == 3) {
            yMove = translationalSpeed;
            xMove = 0;
        }
        else if(direction == 4) {
            yMove = -translationalSpeed;
            xMove = 0;
        }
        timesHit = 0;
        phaseOff = false;
    }
    @Override
    public boolean finishedDying() {
        if(arrowDie.getCurrentFrameIndex() == 6)
            return true;
        else
            return false;
    }

    @Override
    public void tick() {
        if(isSpawned()) {
            if (!isDead()) {
                this.bounds = new Circle(x, y, 15);
                if (hitByBullet())
                    timesHit++;
                else
                    arrowMove.tick();
                changeDirection();
                move();
            }
            else{
                arrowDie.tick();
                this.bounds = new Circle(0, 0, 0);
            }
        }
        else {
            this.bounds = new Circle(0, 0, 0);
            arrowSpawn.tick();
        }
    }

    @Override
    public void render(Graphics g) {
        AffineTransform at = new AffineTransform();
        at.translate((int) (x - handler.getCamera().getxOffset()), (int) (y - handler.getCamera().getyOffset()));
        if(xMove < 0)
            at.rotate(Math.PI);
        if(yMove > 0)
            at.rotate(Math.PI/2);
        if(yMove < 0)
            at.rotate(3*Math.PI/2);
        at.translate(-1*16, -1*16);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(getCurrentAnimationFrame(), at, null);
    }

    @Override
    public BufferedImage getCurrentAnimationFrame(){
        if(!isSpawned())
            return arrowSpawn.getCurrentFrame();
        else if(isDead())
            return arrowDie.getCurrentFrame();
        else
            return arrowMove.getCurrentFrame();
    }

    public void changeDirection(){
        if(x < 128 || x > (handler.getWorld().getWidth() - 1) * 64 - 16)
            xMove = -xMove;
        if(y < 128 || y > (handler.getWorld().getHeight() - 1) * 64 - 16)
            yMove = - yMove;
    }

    public boolean isSpawned(){
        if(arrowSpawn.getCurrentFrameIndex() == 4)
            return true;
        else
            return false;
    }

    public boolean isDead(){
        if(timesHit > 0)
            return true;
        else
            return false;
    }
}
