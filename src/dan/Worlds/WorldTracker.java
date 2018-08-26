package dan.Worlds;

import dan.Entities.Entity;
import dan.Tile.Tile;
import dan.game.Handler;

import java.util.ArrayList;

public class WorldTracker {

    private ArrayList[][] cellGrid;
    private Handler handler;

    public WorldTracker(Handler handler){
        this.handler = handler;
        reinitializeCellGrid();
    }

    public void tick(){
        for (int i = 0; i < cellGrid.length; i++){
            for (int j = 0; j < cellGrid[i].length; j++)
                sortEntitiesIntoCells(i, j);
        }
    }

    //The cell at index (j, k) is populated with the entities that overlap it
    public void sortEntitiesIntoCells(int i, int j){
        for (Entity e : handler.getCreatures()) {
            if (entityIntersectsCell(i, j, e) && e != handler.getPlayer())
                cellGrid[i][j].add(e);
        }
    }

    public void reinitializeCellGrid(){
         cellGrid = new ArrayList[handler.getWorld().getWidth()][handler.getWorld().getHeight()];
         for(int i = 0; i < cellGrid.length; i++){
             for(int j = 0; j < cellGrid[i].length; j++)
                 cellGrid[i][j] = new ArrayList<Entity>();
         }
    }

    public ArrayList<Entity> getEntitiesInThisCell(int i, int j) {
        return cellGrid[i][j];
    }

    public boolean thisCellContainsEntiies(int i, int j){
        if (cellGrid[i][j].isEmpty())
            return false;
        else
            return true;
    }
    public boolean entityIntersectsCell(int i, int j, Entity e){
        //takes coordinates (in pixels) and returns true if bounds of circular entity
        //overlaps the tile containing given coordinates.
        double circleDistanceX = Math.abs(e.getBounds().getCenterX() - handler.getWorld().getTileCenterXFromIndex(i));
        double circleDistanceY = Math.abs(e.getBounds().getCenterY() - handler.getWorld().getTileCenterYFromIndex(j));
        if (circleDistanceX > e.getBounds().getRadius() + Tile.TILE_WIDTH / 2)
            return false;
        if (circleDistanceY > e.getBounds().getRadius() + Tile.TILE_HEIGHT / 2)
            return false;
        if (circleDistanceX <= Tile.TILE_WIDTH / 2)
            return true;
        if (circleDistanceY <= Tile.TILE_HEIGHT / 2)
            return true;
        double sqrdCornerDistance = (Math.pow(circleDistanceX - Tile.TILE_WIDTH / 2, 2) + Math.pow(circleDistanceY - Tile.TILE_HEIGHT / 2, 2));
        return (sqrdCornerDistance <= Math.pow(e.getBounds().getRadius(), 2));
    }
}
