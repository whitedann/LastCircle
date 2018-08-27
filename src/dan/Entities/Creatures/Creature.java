package dan.Entities.Creatures;

import dan.Entities.Bullet;
import dan.Entities.Entity;
import dan.Tile.Tile;
import dan.Utils.Utils;
import dan.game.Handler;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.Iterator;

public abstract class Creature extends Entity {

    public static final float DEFAULT_SPEED = 3.0f,
                            DEFAULT_ROTATIONAL_SPEED = .1f;
    public static final int DEFAULT_CREATURE_WIDTH = 64,
                            DEFAULT_CREATURE_HEIGHT = 64;

    protected float translationalSpeed, rotationalSpeed;
    protected float xMove, yMove, thetaMove;

    public Creature(Handler handler, float x, float y, float angle, int width, int height) {
        super(handler, x, y, angle, width, height);

        this.translationalSpeed = DEFAULT_SPEED;
        this.rotationalSpeed = DEFAULT_ROTATIONAL_SPEED;

        this.bounds.setCenterX(x);
        this.bounds.setCenterY(y);
        this.xMove = 0;
        this.yMove = 0;
        this.thetaMove = 0;
    }

    public void move(){
        moveX();
        moveY();
        moveTheta();
    }

    public void moveTheta(){
        angle -= thetaMove;
    }

    public void moveX() {
        if(xMove > 0){//Moving right
            int tx = (int) ((x + xMove + Tile.TILE_WIDTH) / Tile.TILE_WIDTH);
            int ty = (int) ((y) / Tile.TILE_HEIGHT);
            if(!collisionWithTile(tx, ty) && !collisionWithTile(tx,(ty+1)) && !collisionWithTile(tx,(ty-1))) {
                x += xMove;
                this.bounds.setCenterX(x);
            }
            if(entityEntityCollision()){
                x -= xMove;
                this.bounds.setCenterX(x);
            }
        }
        if(xMove < 0) {//Moving left
            int tx = (int) ((x + xMove - Tile.TILE_WIDTH) / Tile.TILE_WIDTH);
            int ty = (int) ((y) / Tile.TILE_HEIGHT);
            if (!collisionWithTile(tx, ty) && !collisionWithTile(tx,(ty+1)) && !collisionWithTile(tx, (ty-1))) {
                x += xMove;
                this.bounds.setCenterX(x);
            }
            if(entityEntityCollision()){
                x -= xMove;
                this.bounds.setCenterX(x);
            }
        }
    }

    public void moveY(){
        if(yMove > 0){//Moving down
           int tx = (int) ((x) / Tile.TILE_WIDTH);
           int ty = (int) ((y + yMove + Tile.TILE_HEIGHT) / Tile.TILE_HEIGHT);
           if(!collisionWithTile(tx-1,ty) && !collisionWithTile(tx, ty) && !collisionWithTile(tx+1, ty)) {
               y += yMove;
               this.bounds.setCenterY(y);
           }
           if(entityEntityCollision()){
               y -= yMove;
               this.bounds.setCenterY(y);
           }
        }
        if(yMove < 0){//Moving up
           int tx = (int) ((x) / Tile.TILE_WIDTH);
           int ty = (int) ((y + yMove - Tile.TILE_HEIGHT) / Tile.TILE_HEIGHT);
           if(!collisionWithTile(tx-1,ty) && !collisionWithTile(tx, ty) && !collisionWithTile(tx+1, ty)) {
               y += yMove;
               this.bounds.setCenterY(y);
           }
           if(entityEntityCollision()){
               y -= yMove;
               this.bounds.setCenterY(y);
           }
        }
    }

    public boolean collisionWithTile(int i, int j){
        //takes coordinates (in tile indices, not pixels) and returns true if tile at that index is
        //solid and overlapping the player hit box.
        if(handler.getWorld().getTile(i,j).isSolid() && circleIntersectsTile(i,j))
            return true;
        else
            return false;
    }

    public void setSpeed(float speed) {
        this.translationalSpeed  = speed;
    }

    public void setRotationalSpeed(float speed){
        this.rotationalSpeed = speed;
    }

    public void setxMove(float xMove) {
        this.xMove = xMove;
    }

    public void setyMove(float yMove) {
        this.yMove = yMove;
    }

    public float getxMove() {
        return xMove;
    }

    public float getyMove() {
        return yMove;
    }

    public float getSpeed(){
        return this.translationalSpeed;
    }

    public boolean circleIntersectsTile(int x, int y){
       //takes coordinates (in index) and returns true if bounds of circular entity
       //overlaps the tile containing given coordinates.
       double circleDistanceX = Math.abs(bounds.getCenterX() - handler.getWorld().getTileCenterXFromIndex(x));
       double circleDistanceY = Math.abs(bounds.getCenterY() - handler.getWorld().getTileCenterYFromIndex(y));
       if (circleDistanceX > bounds.getRadius() + Tile.TILE_WIDTH / 2)
           return false;
       if (circleDistanceY > bounds.getRadius() + Tile.TILE_HEIGHT / 2)
           return false;
       if (circleDistanceX <= Tile.TILE_WIDTH / 2)
           return true;
       if (circleDistanceY <= Tile.TILE_HEIGHT / 2)
           return true;
       double sqrdCornerDistance = (Math.pow(circleDistanceX - Tile.TILE_WIDTH / 2, 2) + Math.pow(circleDistanceY - Tile.TILE_HEIGHT / 2, 2));

       if(sqrdCornerDistance <= Math.pow(bounds.getRadius()-1,2)){
           return true;
       }
       else return false;
     }

     public boolean collisionWithCircleEntity(Entity entity){
         double r = Math.sqrt(
                 Math.pow(entity.getBounds().getCenterX() - bounds.getCenterX(), 2)
                 + Math.pow(entity.getBounds().getCenterY() - bounds.getCenterY(), 2));
         if(r <= entity.getBounds().getRadius() + bounds.getRadius())
             return true;
         else
             return false;
     }

     public int getDistanceToNearestSolidTile(){
         // Utility used to draw line between position of creature (x, y)
         // to edge of nearest solid Tile.
        int r = 0;
        int i = (int) x;
        int j = (int) y;
        double normalizedAngle = angle % (2*Math.PI);
        while(!handler.getWorld().getTile(i / Tile.TILE_WIDTH,j / Tile.TILE_HEIGHT).isSolid()){
            r++;
            i = (int) (r * Math.cos(normalizedAngle) + x);
            j = (int) (r * Math.sin(normalizedAngle) + y);
        }
        return r;
     }

     public int getDistanceToNearestEntity(){
         // Utility to draw line between position of creature (x, y)
         // to the edge of nearest Entity
         int r = 1;
         int i, j;
         boolean hitAnEntity = false;
         int maxDistance = getDistanceToNearestSolidTile();
         double normalizedAngle = angle % (2*Math.PI);
         while(!hitAnEntity){
             if(r > maxDistance)
                 return r - 32;
             r++;
             i = (int) (r * Math.cos(normalizedAngle) + x);
             j = (int) (r * Math.sin(normalizedAngle) + y);
             for (Entity e : this.handler.getCreatures()){
                 if(e.entityContainsPoint(i,j))
                     hitAnEntity = true;
             }
         }
         return r - 16;
     }

     public abstract boolean finishedDying();

     public abstract BufferedImage getCurrentAnimationFrame();

     public boolean hitByPlayer(){
        if(handler.getPlayer().finishedFiring()) {
            Ellipse2D hitbox = new Ellipse2D.Double(x - handler.getCamera().getxOffset() - this.bounds.getRadius(),
                    y - handler.getCamera().getyOffset() - this.bounds.getRadius(), bounds.getRadius()*2,bounds.getRadius()*2);
            for(Bullet e : this.handler.getPlayer().getBullets()) {
                if (Utils.testIntersection(hitbox, e.getShape())) {
                    System.out.println("hit");
                    return true;
                }
                else
                    return false;
            }
        }
        return false;
    }

    public boolean hitByBullet(){
        Ellipse2D hitbox = new Ellipse2D.Double(x - handler.getCamera().getxOffset() - this.bounds.getRadius(),
                y - handler.getCamera().getyOffset() - this.bounds.getRadius(), bounds.getRadius()*2,bounds.getRadius()*2);
        Iterator<Bullet> iter = handler.getPlayer().getBullets().iterator();
        Area bulletArea = new Area();
        while(iter.hasNext()) {
            Bullet e = iter.next();
            Area singleBullet = new Area(e.getShape());
            if(Utils.testIntersection(singleBullet, hitbox))
                return true;
            else
                continue;
        }
        return false;
    }

}
