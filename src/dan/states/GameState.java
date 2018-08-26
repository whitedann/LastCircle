package dan.states;

import dan.Entities.Bullet;
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
    private int score, time;

    public GameState(Handler handler, int gameMode){
        super(handler);
        this.world = new World(handler,"res/world/world1.txt", "res/world/NoSpawns.txt");
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
        if(time % 1200 == 0)
            world.decreaseSpawnInterval();
        world.tick();
        removeDeadEnemies();
        removeBullets();
        checkForEndGame();
        player.levelUp(score);
        if(!player.playerKilled)
            time++;
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
        else
            renderUI(g);
    }

    public void renderUI(Graphics g){
       g.setColor(Color.ORANGE);
       g.setFont(new Font("Calibri", Font.BOLD,30));
       g.drawString(String.format("Score: %d ", score), 10,30);
       g.drawString(String.format("Time: %d ", time/60), 850, 30);
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
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        g2d.setFont(new Font("Calibri", Font.BOLD, 80));
        g2d.setColor(Color.RED);
        g2d.drawString("GAME OVER", 275, 400);
        g2d.setFont(new Font("Calibri", Font.PLAIN, 40));
        g2d.drawString(String.format("Time: %d", time/60), 320,450);
        g2d.drawString(String.format("Score: %d", score), 570, 450);
    }

    public void removeDeadEnemies(){
        iter = handler.getCreatures().iterator();
        while(iter.hasNext()){
            Creature creature = iter.next();
            if(creature.finishedDying()) {
                iter.remove();
                score += 5;
            }
        }
    }

    public void removeBullets(){
        Iterator<Bullet> iter = handler.getPlayer().getBullets().iterator();
        while(iter.hasNext()){
            Bullet bullet = iter.next();
            if(bullet.insideSolidTile())
                iter.remove();
        }
    }
}
