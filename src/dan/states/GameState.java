package dan.states;

import dan.Entities.Creatures.Blob;
import dan.Entities.Creatures.Player;
import dan.Entities.Entity;
import dan.Worlds.World;
import dan.Worlds.WorldTracker;
import dan.game.Handler;
import java.awt.Graphics;
import java.util.Iterator;

public class GameState extends State{

    private Player player;
    private World world;
    private WorldTracker worldTracker;
    private Iterator<Blob> iter;
    private int timer;

    public GameState(Handler handler){
        super(handler);
        this.world = new World(handler,"res/world/world1.txt");
        this.handler.setWorld(world);
        this.player = new Player(handler,500,500,0);
        this.handler.setPlayer(player);
        this.worldTracker = new WorldTracker(handler);
        this.handler.setWorldTracker(worldTracker);
        this.timer = 0;
        player.setSpeed(5.0f);
    }

    @Override
    public void tick() {
        player.tick();
        world.tick();
        worldTracker.tick();
        for (Blob e : this.handler.getBlobs()) {
            e.tick();
        }
        timer += 1;
        checkForGameOver();
        removeDeadEnemies();
        spawnEnemies();
        System.out.println(handler.getBlobs().size());
    }

    @Override
    public void render(Graphics g) {
        world.render(g);
        player.render(g);
        for(Entity e : this.handler.getBlobs())
            if(e.isWithinMaxRenderDistance())
                e.render(g);
    }

    public void checkForGameOver(){
        if(player.finishedDying())
            StateManager.setState(handler.getGame().menuState);
    }

    public void removeDeadEnemies(){
        iter = handler.getBlobs().iterator();
        while(iter.hasNext()){
            Blob blob = iter.next();
            if(blob.hitByPlayer())
                iter.remove();
        }
    }

    public void spawnEnemies() {
        if (timer == 100){
            if(this.handler.getBlobs().size() < 30) {
                this.handler.addEntity(new Blob(handler, 100, 100, 0));
                this.handler.addEntity(new Blob(handler, 600, 100, 0));
                this.handler.addEntity(new Blob(handler, 100, 400, 0));
            }
            timer = 0;
        }
    }
}
