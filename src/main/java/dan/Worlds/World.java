package dan.Worlds;

import java.awt.Graphics;

import dan.Entities.Creatures.Arrow;
import dan.Entities.Creatures.Blob;
import dan.Entities.Creatures.BlobType;
import dan.Tile.Tile;
import dan.Utils.Utils;
import dan.game.Handler;

public class World {

    private Handler handler;
    private int width, height, spawnX, spawnY;
    private int[][] tiles;
    private int[][] spawns;
    private int timer = 0;
    private int spawnInterval = 100;
    private int currentWave = 2;

    public World(Handler handler, String path, String spawnPatternFile){
        this.handler = handler;
        loadWorld(path);
        loadSpawnPattern(spawnPatternFile);
    }

    public void tick(){
        timer++;
        spawnBlobEnemies();
        if((timer % 1000) == 300) {
            spawnArrowEnemiesTopLeft();
        }
        else if((timer / 2000) == 100){
            spawnArrowEnemiesBottomLeft();
        }
        else if((timer % 2000) == 200){
            spawnArrowEnemiesDown();
        }
        else if((timer % 2000 == 300)){
            spawnArrowEnemiesTopRight();
        }
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

    public void spawnBlobEnemies(){
        if(timer % spawnInterval == 0) {
            BlobType currentType;
            if(currentWave > 9)
                currentWave = 2;
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if(currentWave == 5)
                        currentType = BlobType.BIG;
                    else if (currentWave % 2 == 0)
                        currentType = BlobType.GREEN;
                    else
                        currentType = BlobType.PINWHEEL;
                    if (spawns[x][y] == currentWave) {
                        if(!handler.getWorldTracker().thisCellContainsEntiies(x,y))
                            handler.addCreature(new Blob(handler,getTileCenterXFromIndex(x),getTileCenterYFromIndex(y), 0, currentType));
                    }
                }
            }
            currentWave++;
        }
    }

    public void spawnArrowEnemiesTopLeft(){
        for(int y = 1; y < 10; y++) {
            if (!handler.getWorldTracker().thisCellContainsEntiies(2, y)) {
                handler.addCreature(new Arrow(handler, getTileCenterXFromIndex(2), getTileCenterYFromIndex(y), 1));
                handler.addCreature(new Arrow(handler, getTileCenterXFromIndex(2), getTileCenterYFromIndex(y) + 32, 1));
            }
        }
    }

    public void spawnArrowEnemiesTopRight(){
        for(int y = 1; y < 10; y++) {
            if (!handler.getWorldTracker().thisCellContainsEntiies(width - 2, y)) {
                handler.addCreature(new Arrow(handler, getTileCenterXFromIndex(width - 3), getTileCenterYFromIndex(y), 2));
                handler.addCreature(new Arrow(handler, getTileCenterXFromIndex(width - 3), getTileCenterYFromIndex(y) + 32, 2));
            }
        }
    }

    public void spawnArrowEnemiesBottomLeft(){
        for(int y = 11; y < height - 2; y++){
                handler.addCreature(new Arrow(handler, getTileCenterXFromIndex(2), getTileCenterYFromIndex(y), 1));
                handler.addCreature(new Arrow(handler, getTileCenterXFromIndex(2), getTileCenterYFromIndex(y) + 32, 1));
        }
    }

    public void spawnArrowEnemiesDown(){
        for(int x = 1; x < width - 2; x ++){
            if(!handler.getWorldTracker().thisCellContainsEntiies(x, 2)){
                handler.addCreature(new Arrow(handler, getTileCenterXFromIndex(x), getTileCenterYFromIndex(2), 3));
                handler.addCreature(new Arrow(handler, getTileCenterXFromIndex(x) + 32, getTileCenterYFromIndex(2), 3));
            }
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

    public void decreaseSpawnInterval(){
        spawnInterval -= 15;
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
