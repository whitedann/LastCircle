package dan.Entities.Creatures;

import dan.Entities.Entity;
import dan.display.Animation;
import dan.display.Assets;
import dan.game.Handler;
import javafx.scene.shape.Circle;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static dan.Entities.Creatures.BlobType.*;


public class Blob extends Creature{

    private int timesHit = 0;
    private Entity target;
    private BlobType type;
    private Animation blobMove, blobHit, blobSpawning, deathAnimation;


    public Blob(Handler handler, float x, float y, float angle, BlobType type) {
        super(handler, x, y, angle,32,32);
        setTarget(handler.getPlayer());
        this.angle = 0;
        this.type = type;
        if(this.type == GREEN) {
            blobMove = new Animation(200, Assets.greenBlobMove);
            blobSpawning = new Animation(100, Assets.blobSpawning);
            blobHit = new Animation(50, Assets.blobDie);
            deathAnimation = new Animation(100, Assets.blobDying);
            setSpeed(2.0f);
        }
        else if(this.type == PINWHEEL){
            blobMove = new Animation(20, Assets.pinwheelMove);
            blobSpawning = new Animation(200, Assets.blobSpawning);
            blobHit = new Animation(50, Assets.blobDie);
            deathAnimation = new Animation(100, Assets.blobDying);
            setSpeed(1.0f);
        }
        else if(this.type == BIG){
            blobMove = new Animation(20, Assets.bigMove);
            blobSpawning = new Animation(50, Assets.bigSpawn);
            blobHit = new Animation(100, Assets.bigHit);
            deathAnimation = new Animation(100, Assets.bigDeath);
            setSpeed(0.5f);
        }
        else if(this.type == ARROW){
            blobMove =  new Animation(20, Assets.arrowMove);
            blobSpawning = new Animation(50, Assets.blobSpawning);
            blobHit = new Animation(50, Assets.blobDie);
            deathAnimation = new Animation(100, Assets.blobDying);
            setSpeed(1.5f);
        }
        blobMove.setCurrentFrameIndex(ThreadLocalRandom.current().nextInt(0, 3));
    }

    public void setTarget(Entity target){
        this.target = target;
    }

    @Override
    public void tick() {
        if(isSpawned()) {
            if(!isDead()) {
                if(type == BIG) {
                    this.bounds = new Circle(x, y, 46);
                }
                else
                    this.bounds = new Circle(x, y, 16);
                if (hitByBullet()) {
                    //TODO reset bounds when hit
                    timesHit += 1;
                    blobHit.tick();
                } else
                    blobMove.tick();
                getDirection();
                move();
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
        if(type == BIG)
            at.translate(-1 * 60, -1 * 60);
        else
            at.translate(-1*16, -1*16);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(getCurrentAnimationFrame(), at, null);
    }

    public void getDirection(){
        xMove = 0;
        yMove = 0;
        if(x < target.getX())
            xMove = translationalSpeed;
        else
            xMove = -translationalSpeed;
        if(y < target.getY())
            yMove = translationalSpeed;
        else
            yMove = -translationalSpeed;
    }

    public BlobType pickRandomType() {
        int pick = new Random().nextInt(BlobType.values().length);
        return BlobType.values()[pick];
    }

    public boolean isSpawned(){
        if(blobSpawning.getCurrentFrameIndex() == 5)
            return true;
        else
            return false;
    }

    public boolean isDead(){
        if(type == BIG) {
            if (timesHit > 50)
                return true;
            else
                return false;
        }
        else{
            if(timesHit > 1)
                return true;
            else
                return false;
        }
    }

    @Override
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
        else if(hitByBullet()) {
            return blobHit.getCurrentFrame();
        }
        else if(isDead()){
            return deathAnimation.getCurrentFrame();
        }
        else
            return blobMove.getCurrentFrame();
    }
}
