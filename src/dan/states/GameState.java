package dan.states;

import dan.Entities.Creatures.Blob;
import dan.Entities.Creatures.Creature;
import dan.Entities.Creatures.Player;
import dan.Entities.Entity;
import dan.Worlds.World;
import dan.Worlds.WorldTracker;
import dan.game.Handler;

import java.awt.Graphics;

public class GameState extends State{

    private Player player;
    private World world;
    private WorldTracker worldTracker;

    public GameState(Handler handler){
        super(handler);
        this.world = new World(handler,"res/world/world1.txt");
        this.handler.setWorld(world);
        this.player = new Player(handler,500,500,0);
        this.handler.setPlayer(player);
        this.worldTracker = new WorldTracker(handler);
        this.handler.setWorldTracker(worldTracker);
        this.handler.addEntity(new Blob(handler,120,300,0));
        this.handler.addEntity(new Blob(handler, 200, 300, 0));
        this.handler.addEntity(new Blob(handler,500,100,0));
        this.handler.addEntity(new Blob(handler, 1000,1000,0));
    }

    public Player getPlayer(){
        return this.player;
    }

    @Override
    public void tick() {
        player.tick();
        world.tick();
        worldTracker.tick();
        for(Entity e : this.handler.getEntities()) {
            e.tick();
        }
    }

    @Override
    public void render(Graphics g) {
        world.render(g);
        player.render(g);
        for(Entity e : this.handler.getEntities())
            e.render(g);
    }
}
