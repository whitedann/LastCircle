package dan.game;

import dan.Entities.Creatures.Blob;
import dan.Entities.Creatures.Creature;
import dan.Entities.Creatures.Player;
import dan.Entities.Entity;
import dan.Input.KeyManager;
import dan.Worlds.World;
import dan.Worlds.WorldTracker;
import dan.display.Camera;

import java.util.ArrayList;

public class Handler {

    private Game game;
    private World world;
    private Player player;
    private ArrayList<Creature> creatures;
    private WorldTracker worldTracker;
    private int gameTimer = 0;

    public Handler(Game game){
        this.game = game;
        creatures = new ArrayList<Creature>();
    }

    public int getWidth(){
        return game.getWidth();
    }

    public int getHeight(){
        return game.getHeight();
    }

    public KeyManager getKeyManager(){
        return game.getKeyManager();
    }

    public Camera getCamera(){
        return game.getCamera();
    }

    public Game getGame() {
        return game;
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    public void tickGameTimer(){
        gameTimer++;
    }

    public int getGameTime(){
        return gameTimer;
    }

    public Player getPlayer(){
        return player;
    }

    public void setWorldTracker(WorldTracker e){
        this.worldTracker = e;
    }

    public WorldTracker getWorldTracker() {
        return worldTracker;
    }

    public ArrayList<Creature> getCreatures(){
        return creatures;
    }

    public void addCreature(Creature creature){
        this.creatures.add(creature);
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
