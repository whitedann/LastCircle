package dan.Entities;

import dan.Tile.Tile;
import dan.game.Handler;
import javafx.scene.shape.Circle;
import java.awt.*;

public abstract class Entity {

    //location given as float (not int) to avoid calculation errors
    protected Handler handler;
    protected float x, y, angle;
    protected int width, height;
    protected Circle bounds;
    protected boolean phaseOff;


    public Entity(Handler handler, float x, float y, float angle, int width, int height){
        this.handler = handler;
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.angle = angle;
        this.phaseOff = true;
        this.bounds = new Circle(0,0,width/2);
    }

    public boolean entityEntityCollision(){
        int cellIndexX = this.getCellIndexX();
        int cellIndexY = this.getCellIndexY();
        for(int x = cellIndexX - 1; x <= cellIndexX + 1; x++) {
            for (int y = cellIndexY - 1; y <= cellIndexY + 1; y++) {
                for (Entity e : handler.getWorldTracker().getEntitiesInThisCell(x, y)) {
                    if (!collisionWithCircleEntity(e) || e == this || !this.phaseOff)
                        continue;
                    else
                        return true;
                }
            }
        }
        return false;
    }

    public boolean entityContainsPoint(int i, int j){
        if(bounds.contains(i, j))
            return true;
        return false;
    }

    public Circle getBounds(){
        return this.bounds;
    }

    public abstract void tick();

    public abstract void render(Graphics g);

    public void setX(int x){
        this.x = x;
        this.bounds.setCenterX(x);
    }

    public void setY(int y){
        this.y = y;
        this.bounds.setCenterY(y);
    }

    //Change to use window size, not just random-looking integrers
    public boolean isWithinMaxRenderDistance(){
        if(this.getX() > handler.getPlayer().getX() + 530)
            return false;
        else if(this.getX() < handler.getPlayer().getX() - 530)
            return false;
        else if(this.getY() > handler.getPlayer().getY() + 360)
            return false;
        else if(this.getY() < handler.getPlayer().getY() - 360)
            return false;
        else
            return true;
    }

    public int getCellIndexX(){
        return (int) (this.getX() / Tile.TILE_WIDTH);
    }

    public int getCellIndexY(){
        return (int) (this.getY() / Tile.TILE_HEIGHT);
    }

    public float getX(){
        return this.x;
    }

    public float getY(){
        return this.y;
    }

    public void setBounds(Circle s){
        this.bounds = s;
    }

    public boolean collisionWithCircleEntity(Entity entity){
        double r = Math.sqrt(
                Math.pow(entity.getBounds().getCenterX() - bounds.getCenterX() , 2)
                        + Math.pow(entity.getBounds().getCenterY() - bounds.getCenterY(), 2));
        if(r <= entity.getBounds().getRadius() + bounds.getRadius())
            return true;
        else
            return false;
    }


}
