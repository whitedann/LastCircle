package dan.Worlds;

import dan.Entities.Creatures.Blob;
import dan.Tile.Tile;
import dan.Utils.Utils;
import dan.game.Handler;

import java.awt.Graphics;

public class World {

    private Handler handler;
    private int width, height, spawnX, spawnY;
    private int[][] tiles;
    private int[][] spawns;
    private int timer = 0;
    private int spawnInterval = 300;
    private int currentWave = 2;

    public World(Handler handler, String path, String spawnPatternFile){
        this.handler = handler;
        loadWorld(path);
        loadSpawnPattern(spawnPatternFile);
    }

    public void tick(){
        timer++;
        spawnEnemies();
    }

    public void render(Graphics g){
        //Only renders tiles within view of the window
        int xStart = (int) Math.max(0, handler.getCamera().getxOffset() / Tile.TILE_WIDTH );
        int xEnd = (int) Math.min(width, (handler.getCamera().getxOffset() + handler.getWidth()) / Tile.TILE_WIDTH +1);
        int yStart = (int) Math.max(0, handler.getCamera().getyOffset() / Tile.TILE_HEIGHT);
        int yEnd = (int) Math.min(height, (handler.getCamera().getyOffset() + handler.getHeight()) / Tile.TILE_HEIGHT + 1);

        for(int y = yStart; y < yEnd; y++){
           for(int x = xStart; x < xEnd; x++) {
               getTile(x, y).render(g, (int)(x * Tile.TILE_WIDTH - handler.getCamera().getxOffset()),
                       (int)(y * Tile.TILE_HEIGHT - handler.getCamera().getyOffset()));
           }
       }
    }

    public void spawnEnemies(){
        if(timer == spawnInterval) {
            if(currentWave > 4)
                currentWave = 2;
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (spawns[x][y] == currentWave) {
                        if(!handler.getWorldTracker().thisCellContainsEntiies(x,y))
                            handler.addCreature(new Blob(handler,getTileCenterXFromIndex(x),getTileCenterYFromIndex(y), 0));
                    }
                }
            }
            currentWave++;
            timer = 0;
        }
    }

    public Tile getTile(int x, int y){
        if(x < 0 || y < 0 || x >= width || y >= height)
            return Tile.gridTile;
        Tile t = Tile.tiles[tiles[x][y]];
        if(t == null)
            //return this tile as default
            return Tile.gridTile;
        return t;
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }

    public static int getTileCenterXFromIndex(int i){
        int tileCenterX;
        tileCenterX = i * Tile.TILE_WIDTH + Tile.TILE_WIDTH / 2;
        return tileCenterX;
    }

    public static int getTileCenterYFromIndex(int j){
        int tileCenterY;
        tileCenterY = j * Tile.TILE_HEIGHT + Tile.TILE_HEIGHT / 2;
        return tileCenterY;
    }

    public static int getTileCenterX(int x){
        int tileCenterX;
        //this math looks weird, but the first division truncates the index (because type is int), then multiply
        //again to find edge of indexed tile, finally adding width/2 to find center coordinate of the tile.
        tileCenterX = (x / Tile.TILE_WIDTH) * Tile.TILE_WIDTH  + (Tile.TILE_WIDTH / 2);
        return tileCenterX;
    }

    public static int getTileCenterY(int y){
        int tileCenterY;
        tileCenterY = (y / Tile.TILE_HEIGHT) * Tile.TILE_HEIGHT + (Tile.TILE_HEIGHT / 2);
        return tileCenterY;
    }

    private void loadWorld(String path) {
        String file = Utils.loadFileAsString(path);
        String[] tokens = file.split("\\s+");
        width = Utils.parseInt(tokens[0]);
        height = Utils.parseInt(tokens[1]);
        spawnX = Utils.parseInt(tokens[2]);
        spawnY = Utils.parseInt(tokens[3]);

        tiles = new int[width][height];
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                //tokens is 1D array, so the elements are located in tiles[] at x + y*width, with the first four
                //being the width, height, and spawnX spawnY variables, so we also add 4.
                tiles[x][y] = Utils.parseInt(tokens[(x + y * width + 4)]);
            }
        }
    }

    public void incrementDifficulty(){
        spawnInterval -= 10;
    }

    private void loadSpawnPattern(String path){
        String file = Utils.loadFileAsString(path);
        String[] tokens = file.split("\\s+");

        spawns = new int[width][height];
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++)
                spawns[x][y] = Utils.parseInt(tokens[(x + y * width)]);
        }

    }
}
