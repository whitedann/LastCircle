package dan.display;

import dan.Entities.Entity;
import dan.game.Game;

public class Camera {

    private Game game;
    private float xOffset, yOffset;

    public Camera(Game game, float xOffest, float yOffset){
        this.xOffset = xOffest;
        this.yOffset = yOffset;
        this.game = game;
    }

    public void move(float xAmt, float yAmt){
        xOffset += xAmt;
        yOffset += yAmt;
    }

    public void centerOnEntity(Entity e){
        xOffset = e.getX() - game.getWidth() / 2;
        yOffset = e.getY() - game.getHeight() /2;
    }
    public float getxOffset() {
        return xOffset;
    }

    public void setxOffset(float xOffset) {
        this.xOffset = xOffset;
    }

    public float getyOffset() {
        return yOffset;
    }

    public void setyOffset(float yOffset) {
        this.yOffset = yOffset;
    }
}
