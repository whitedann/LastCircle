package dan.states;

import dan.Entities.Creatures.Creature;
import dan.Entities.Creatures.Player;
import dan.Entities.Entity;
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
    private int gameTime, score;

    public GameState(Handler handler){
        super(handler);
        this.world = new World(handler,"res/world/world1.txt", "res/world/SpawnPattern1.txt");
        this.handler.setWorld(world);
        this.player = new Player(handler,500,500,0);
        this.handler.setPlayer(player);
        this.worldTracker = new WorldTracker(handler);
        this.handler.setWorldTracker(worldTracker);
        this.score = 0;
        player.setSpeed(5.0f);
    }

    @Override
    public void tick() {
        worldTracker.reinitializeCellGrid();
        worldTracker.tick();
        player.tick();
        for(Creature e : this.handler.getCreatures())
            e.tick();
        handler.tickGameTimer();
        world.tick();
        removeDeadEnemies();
        checkForEndGame();
        player.levelUp(score);
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
        g.setColor(Color.ORANGE);
        g.setFont(new Font("Calibri", Font.BOLD,45));
        g.drawString(String.format("%d ", score), 100,50);
    }


    public void checkForEndGame(){
        if(player.playerKilled){
            if(handler.getKeyManager().confirm) {
                handler.getKeyManager().resetKeys();
                StateManager.setState(handler.getGame().menuState);
            }
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
                score += 10;
            }
        }
    }
}
