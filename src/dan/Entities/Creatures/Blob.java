package dan.Entities.Creatures;

import dan.Entities.Entity;
import dan.Utils.Utils;
import dan.display.Animation;
import dan.display.Assets;
import dan.game.Handler;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;


public class Blob extends Creature{

    private Entity target;
    private boolean isAlive;
    private Animation blobMove, blobDie;

    public Blob(Handler handler, float x, float y, float angle) {
        super(handler, x, y, angle,32,32);
        setTarget(handler.getPlayer());
        this.angle = 0;
        isAlive = true;
        blobMove = new Animation(50, Assets.blobMove);
        blobDie = new Animation(50, Assets.blobDie);
    }

    public void setTarget(Entity target){
        this.target = target;
    }

    @Override
    public void tick() {
        if(hitByPlayer())
            isAlive = false;
        getDirection();
        move();
        blobMove.tick();
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
            xMove = 1.0f;
        else
            xMove = -1.0f;
        if(y < target.getY())
            yMove = 1.0f;
        else
            yMove = -1.0f;
    }

    public boolean finishedDying(){
        if(blobDie.getCurrentFrameIndex() == 6)
            return true;
        else
            return false;
    }

    public BufferedImage getCurrentAnimationFrame(){
        if(!isAlive) {
            blobDie.tick();
            return blobDie.getCurrentFrame();
        }
        else
            return blobMove.getCurrentFrame();
    }
}
