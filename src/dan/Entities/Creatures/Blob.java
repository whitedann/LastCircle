package dan.Entities.Creatures;

import dan.Entities.Entity;
import dan.display.Animation;
import dan.display.Assets;
import dan.game.Handler;
import javafx.scene.shape.Circle;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;


public class Blob extends Creature{

    private int timesHit = 0;
    private Entity target;
    private Animation blobMove, blobHit, blobSpawning, deathAnimation;

    public Blob(Handler handler, float x, float y, float angle) {
        super(handler, x, y, angle,32,32);
        setTarget(handler.getPlayer());
        this.angle = 0;
        blobMove = new Animation(50, Assets.blobMove);
        blobHit = new Animation(50, Assets.blobDie);
        blobSpawning = new Animation(100, Assets.blobSpawning);
        deathAnimation = new Animation(100, Assets.blobDying);
    }

    public void setTarget(Entity target){
        this.target = target;
    }

    @Override
    public void tick() {
        if(isSpawned()) {
            if(!isDead()) {
                this.bounds = new Circle(x, y, width / 2);
                if (hitByPlayer()) {
                    //TODO reset bounds when hit
                    timesHit += 1;
                    blobHit.tick();
                } else {
                    getDirection();
                    move();
                    blobMove.tick();
                }
            }
            else{
                deathAnimation.tick();
                this.bounds = new Circle(0,0,0);
            }
        }
        else {
            this.bounds = new Circle(0,0,0);
            blobSpawning.tick();
        }
    }

    @Override
    public void render(Graphics g) {
        AffineTransform at = new AffineTransform();
        at.translate((int) (x - handler.getCamera().getxOffset()), (int) (y - handler.getCamera().getyOffset()));
        at.rotate(angle);
        at.translate(-1 * this.getWidth() / 2, -1 * this.getHeight() / 2);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(getCurrentAnimationFrame(), at, null);
    }

    public void getDirection(){
        xMove = 0;
        yMove = 0;
        if(x < target.getX())
            xMove = 1.5f;
        else
            xMove = -1.5f;
        if(y < target.getY())
            yMove = 1.5f;
        else
            yMove = -1.5f;
    }

    public boolean isSpawned(){
        if(blobSpawning.getCurrentFrameIndex() == 5)
            return true;
        else
            return false;
    }

    public boolean isDead(){
        if(timesHit > 4)
            return true;
        else
            return false;
    }

    public boolean finishedDying(){
        if(deathAnimation.getCurrentFrameIndex() == 7)
            return true;
        else
            return false;
    }

    public BufferedImage getCurrentAnimationFrame(){
        if(!isSpawned()){
            return blobSpawning.getCurrentFrame();
        }
        else if(timesHit > 0 && !isDead()) {
            return blobHit.getCurrentFrame();
        }
        else if(isDead()){
            return deathAnimation.getCurrentFrame();
        }
        else
            return blobMove.getCurrentFrame();
    }
}
