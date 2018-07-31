package dan.Entities.Creatures;

import dan.Entities.Entity;
import dan.Tile.Tile;
import dan.game.Handler;

import java.awt.*;

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
            int tx = (int) ((x + xMove + bounds.getRadius() *2) / Tile.TILE_WIDTH);
            int ty = (int) ((y + bounds.getRadius()) / Tile.TILE_HEIGHT);
            if(!collisionWithTile(tx,ty) && !collisionWithTile(tx,(ty+1)) && !collisionWithTile(tx,(ty-1))) {
                x += xMove;
                this.bounds.setCenterX(x);
            }
            if(entityEntityCollision()){
                x -= xMove;
                this.bounds.setCenterX(x);
            }
        }
        if(xMove < 0) {//Moving left
            int tx = (int) ((x + xMove - bounds.getRadius() * 2) / Tile.TILE_WIDTH);
            int ty = (int) ((y + bounds.getRadius()) / Tile.TILE_HEIGHT);
            if (!collisionWithTile(tx,ty) && !collisionWithTile(tx,(ty+1)) && !collisionWithTile(tx, (ty-1))) {
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
           int tx = (int) ((x + bounds.getRadius()) / Tile.TILE_WIDTH);
           int ty = (int) ((y + yMove + bounds.getRadius() *2) / Tile.TILE_HEIGHT);
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
           int tx = (int) ((x + bounds.getRadius()) / Tile.TILE_WIDTH);
           int ty = (int) ((y + yMove - bounds.getRadius() * 2) / Tile.TILE_HEIGHT);
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
        if(handler.getWorld().getTile(i,j).isSolid() && circleIntersectsTile(i * Tile.TILE_WIDTH,j * Tile.TILE_HEIGHT))
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
       //takes coordinates (in pixels) and returns true if bounds of circular entity
       //overlaps the tile containing given coordinates.
       double circleDistanceX = Math.abs(bounds.getCenterX() - handler.getWorld().getTileCenterX(x));
       double circleDistanceY = Math.abs(bounds.getCenterY() - handler.getWorld().getTileCenterY(y));
       if (circleDistanceX > bounds.getRadius() + Tile.TILE_WIDTH / 2)
           return false;
       if (circleDistanceY > bounds.getRadius() + Tile.TILE_HEIGHT / 2)
           return false;
       if (circleDistanceX <= Tile.TILE_WIDTH / 2)
           return true;
       if (circleDistanceY <= Tile.TILE_HEIGHT / 2)
           return true;
       double sqrdCornerDistance = (Math.pow(circleDistanceX - Tile.TILE_WIDTH / 2, 2) + Math.pow(circleDistanceY - Tile.TILE_HEIGHT / 2, 2));
       return (sqrdCornerDistance <= Math.pow(bounds.getRadius(), 2));
     }

     public boolean pointInsideTile(Point point, int x, int y) {
        //Returns true if the given point is inside the tile containing the point (x, y)
         int xmin = handler.getWorld().getTileCenterX(x) - Tile.TILE_WIDTH / 2;
         int xmax = handler.getWorld().getTileCenterX(x) + Tile.TILE_WIDTH / 2;
         int ymin = handler.getWorld().getTileCenterY(y) + Tile.TILE_HEIGHT / 2;
         int ymax = handler.getWorld().getTileCenterY(y) - Tile.TILE_HEIGHT / 2;

         if(point.getX() > xmax)
             return false;
         if(point.getX() < xmin)
             return false;
         if(point.getY() > ymin)
             return false;
         if(point.getY() < ymax)
             return false;
         return true;
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
        //Utility used to draw line between position of creature (x, y)
         //to edge of nearest solid Tile.
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

}
