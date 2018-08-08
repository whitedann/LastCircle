package dan.states;

import dan.Entities.Creatures.Blob;
import dan.Entities.Creatures.Contagion;
import dan.Entities.Creatures.Creature;
import dan.Entities.Creatures.Player;
import dan.Entities.Entity;
import dan.Tile.Tile;
import dan.Worlds.World;
import dan.Worlds.WorldTracker;
import dan.game.Handler;

import java.awt.*;
import java.util.Iterator;

public class GameState extends State{

    private Player player;
    private World world;
    private WorldTracker worldTracker;
    private Iterator<Creature> iter;
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
        Contagion cont1 = new Contagion(handler, 0, 0,  (float) (Math.PI));
        Contagion cont2 = new Contagion(handler, 0, 0, 0);
        Contagion cont3 = new Contagion(handler, 0, 0, (float) (Math.PI/2));
        Contagion cont4 = new Contagion(handler, 0, 0, (float) Math.PI/3);
        handler.addCreature(cont4);
        handler.addCreature(cont2);
        handler.addCreature(cont3);
        handler.addCreature(cont1);
        player.setSpeed(5.0f);
    }

    @Override
    public void tick() {
        worldTracker.reinitializeCellGrid();
        worldTracker.tick();
        player.tick();
        for(Creature e : this.handler.getCreatures())
            e.tick();
        world.tick();
        timer += 1;
        removeDeadEnemies();
        spawnEnemies();
        checkForEndGame();
    }

    @Override
    public void render(Graphics g) {
        world.render(g);
        player.render(g);
        for(Entity e : this.handler.getCreatures())
            if(e.isWithinMaxRenderDistance())
                e.render(g);
        if(player.finishedDying())
            showDeathScreen(g);
    }

    public void checkForEndGame(){
        if(player.playerKilled){
            if(handler.getKeyManager().confirm)
               StateManager.setState(handler.getGame().menuState);
        }
    }

    public void showDeathScreen(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.fillRect(0,0, 1024, 720);
    }

    public void removeDeadEnemies(){
        iter = handler.getCreatures().iterator();
        while(iter.hasNext()){
            Creature creature = iter.next();
            if(creature.finishedDying()) {
                iter.remove();
            }
        }
    }

    public void spawnEnemies() {
        if (timer == 100){
            int x, y;
            if(this.handler.getCreatures().size() < 1000) {
                for(int i = 1; i < 20; i++) {
                    x = i*64;
                    y = 100;
                    if(!handler.getWorldTracker().thisCellContainsEntiies(x / Tile.TILE_WIDTH + 1, y / Tile.TILE_HEIGHT + 1) &&
                            !handler.getWorldTracker().thisCellContainsEntiies( x / Tile.TILE_WIDTH, y / Tile.TILE_HEIGHT + 1))
                        handler.addCreature(new Blob(handler, handler.getWorld().getTileCenterX(x) , handler.getWorld().getTileCenterY(y) , 0));
                }
            }
            timer = 0;
        }
    }
}
