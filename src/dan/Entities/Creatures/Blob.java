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

    private Entity target;
    private Animation blobMove, blobDie, blobSpawning;

    public Blob(Handler handler, float x, float y, float angle) {
        super(handler, x, y, angle,32,32);
        setTarget(handler.getPlayer());
        isAlive = true;
        this.angle = 0;
        blobMove = new Animation(50, Assets.blobMove);
        blobDie = new Animation(50, Assets.blobDie);
        blobSpawning = new Animation(100, Assets.blobSpawning);
    }

    public void setTarget(Entity target){
        this.target = target;
    }

    @Override
    public void tick() {
        if(isSpawned()) {
            this.bounds = new Circle(x, y, width/2);
            if (hitByPlayer()) {
                isAlive = false;
                blobDie.tick();
            }
            else {
                getDirection();
                move();
                blobMove.tick();
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

    public boolean finishedDying(){
        if(blobDie.getCurrentFrameIndex() == 3)
            return true;
        else
            return false;
    }

    public BufferedImage getCurrentAnimationFrame(){
        if(!isSpawned()){
            return blobSpawning.getCurrentFrame();
        }
        else if(!isAlive && !finishedDying()) {
            return blobDie.getCurrentFrame();
        }
        else
            return blobMove.getCurrentFrame();
    }
}
