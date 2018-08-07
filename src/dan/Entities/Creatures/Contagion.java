package dan.Entities.Creatures;

import dan.Entities.Entity;
import dan.display.Animation;
import dan.display.Assets;
import dan.game.Handler;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Contagion extends Creature{

    private double timer = 0;
    private Animation contagionMove;
    private Creature master;

    public Contagion(Handler handler, float x, float y, float angle){
        super(handler, x, y, angle,9,9);
        this.angle = 0;
        this.master = handler.getPlayer();
        contagionMove = new Animation(100, Assets.contagionMove);
    }
    @Override
    public void tick() {
        contagionMove.tick();
        circleAroundPlayer();
        move();
        timer++;
    }

    @Override
    public void render(Graphics g) {
        AffineTransform at = new AffineTransform();
        at.translate((int) (x - handler.getCamera().getxOffset()), (int) (y - handler.getCamera().getyOffset()));
        at.translate(-1 * this.getWidth() / 2, -1 * this.getHeight() / 2);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(getCurrentAnimationFrame(), at, null);
    }

    public void circleAroundPlayer(){
        xMove = 0;
        yMove = 0;
        double orbitSpeed = Math.PI / 60 ;
        double radian = 10 * orbitSpeed * timer;
        xMove = (int) (10*Math.sin(radian));
        yMove = (int) (10*Math.cos(radian));

    }

    public boolean finishedDying(){
        if(hitByPlayer())
            return true;
        return false;
    }

    private BufferedImage getCurrentAnimationFrame(){
        return contagionMove.getCurrentFrame();
    }
}
