package dan.Tile;

import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Tile {

    public static Tile[] tiles = new Tile[256];
    public static Tile brickTile = new BrickTile(0);
    public static Tile gridTile = new GridTile(1);

    //CLASS
    public static final int TILE_WIDTH = 64, TILE_HEIGHT = 64;

    protected BufferedImage texture;
    protected final int id;

    public Tile(BufferedImage texture, int id){
        this.texture = texture;
        this.id = id;

        tiles[id] = this;
    }

    public boolean isSolid(){
        return false;
    }

    public int getID(){
        return id;
    }

    public void tick(){

    }

    public void render(Graphics g, int x, int y){
        g.drawImage(texture, x, y, TILE_WIDTH, TILE_HEIGHT, null);
    }
}
