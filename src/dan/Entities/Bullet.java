package dan.Entities;

import dan.Tile.Tile;
import dan.game.Handler;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class Bullet {

    private int anchorX, anchorY, x, y;
    private float angle;
    private Shape shape;
    private Handler handler;

    public Bullet(Handler handler, int startingX, int startingY, float startingAngle) {
        this.anchorX = startingX;
        this.anchorY = startingY;
        this.angle = startingAngle;
        this.handler = handler;
        this.x = startingX;
        this.y = startingY;
        AffineTransform at = AffineTransform.getRotateInstance(angle, startingX, startingY);
        shape = at.createTransformedShape(new Rectangle2D.Double(startingX, startingY, 20, 3));
    }

    public void tick(){
        anchorX += (10*Math.cos(angle) - handler.getPlayer().getxMove());
        anchorY += (10*Math.sin(angle) - handler.getPlayer().getyMove());
        shape = new Rectangle2D.Double(anchorX + 30, anchorY, 7, 3);
        AffineTransform at = AffineTransform.getRotateInstance(angle, anchorX, anchorY);
        shape = at.createTransformedShape(shape);
    }

    public void render(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.fill(shape);
    }

    public boolean insideSolidTile(){
        int xPos = (int) (shape.getBounds().getX() + handler.getCamera().getxOffset());
        int yPos = (int) (shape.getBounds().getY() + handler.getCamera().getyOffset());
        if(handler.getWorld().getTile(xPos / Tile.TILE_WIDTH, yPos / Tile.TILE_HEIGHT).isSolid())
            return true;
        else
            return false;
    }

    public void reportPos(){
        System.out.println("x: " + anchorX + " x:" + shape.getBounds().x);
    }

    public Shape getShape() {
        return shape;
    }
}
