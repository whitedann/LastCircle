package dan.Entities.Creatures;

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
        super(handler, x, y, angle,8,8);
        this.master = handler.getPlayer();
        contagionMove = new Animation(100, Assets.contagionMove);
        this.x = (float) (master.getBounds().getCenterX() + 52*Math.cos(angle));
        this.y = (float) (master.getBounds().getCenterY() - 52*Math.sin(angle));
    }
    @Override
    public void tick() {
        contagionMove.tick();
        circleAroundMaster();
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

    public void circleAroundMaster(){
        double orbitSpeed = Math.PI / 200;
        double radian = orbitSpeed * timer - Math.PI/2 -angle;
        double orbitRadius = master.getBounds().getRadius() + 20;
        xMove = (float) (master.getxMove() + -1*orbitRadius*orbitSpeed*Math.cos(radian));
        yMove = (float) (master.getyMove() + -1*orbitRadius*orbitSpeed*Math.sin(radian));
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
