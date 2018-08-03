package dan.game;

import dan.Input.KeyManager;
import dan.display.Assets;
import dan.display.Camera;
import dan.display.Window;
import dan.states.GameState;
import dan.states.MenuState;
import dan.states.State;
import dan.states.StateManager;

import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;


public class Game implements Runnable{
    //Resolution: 1024x720
    private static final int WIDTH = 1024, HEIGHT = WIDTH / 12 * 8;
    private Thread thread;
    private boolean running = false;
    public boolean gamePaused = false;
    public int gamePausedint = 0;

    //Window
    private Window MainWindow;

    //States
    public State gameState;
    public State menuState;

    //Graphics
    private BufferStrategy bs;
    private Graphics2D g;

    //Input
    private KeyManager keyManager;

    //Camera
    private Camera camera;

    //Handler
    private Handler handler;

    public Game(){
    }

    private void init(){
        this.keyManager = new KeyManager();
        this.MainWindow = new Window(WIDTH, HEIGHT, "Game", this);
        this.MainWindow.getJFrame().addKeyListener(this.keyManager);
        Assets.init();
        this.camera = new Camera(this,0,0);
        this.handler = new Handler(this);
        this.menuState = new MenuState(handler);
        StateManager.setState(this.menuState);
    }

    public synchronized void start(){
        if(running)
            return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public KeyManager getKeyManager() {
        return this.keyManager;
    }

    public int getWidth(){
        return this.WIDTH;
    }

    public int getHeight(){
        return this.HEIGHT;
    }

    public Camera getCamera()
    {
        return this.camera;
    }

    public void resetGame(){
        this.handler = new Handler(this);
        this.gameState = new GameState(handler);
    }

    public synchronized void stop(){
        if(!running)
            return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void render() {
        this.bs = this.MainWindow.getCanvas().getBufferStrategy();
        if (this.bs == null) {
            this.MainWindow.getCanvas().createBufferStrategy(3);
            return;
        }
        this.g = (Graphics2D) this.bs.getDrawGraphics();
        //  Clears the Screen
        g.clearRect(0, 0, WIDTH, HEIGHT);

        //  Render background for all states
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 12; j++) {
                if (i == 0 || j == 0 || i == 7 || j == 7) {
                    g.drawImage(Assets.border, i * 135, j * 90, null);
                }
            }
        }

        //  Render the current state
        if(StateManager.getState() != null){
            StateManager.getState().render(g);
        }

        bs.show();
        g.dispose();
    }

    private void tick(){
        if(StateManager.getState() != null){
            StateManager.getState().tick();
        }
    }

    public void run(){
        init();

        int fps = 60;
        double timePerTick = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;

        while(running){
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;


            if(delta >= 1) {

                //  The keyManager must tick outside main tick method so we can listen for
                //  pause requests even when the game is paused/not ticking.
                this.keyManager.tick();

                //  Checks for player pressing pause
                if(keyManager.playerRequestsPause()) {
                    togglePause();
                    this.keyManager.resetPauseCheck();
                }

                if(!gamePaused) {
                    tick();
                    render();
                }
                ticks++;
                delta--;
            }
            if(timer >= 1000000000){
                System.out.println("TPS: " + ticks);
                ticks = 0;
                timer = 0;
            }
        }
        stop();
    }

    public void togglePause() {
        switch(gamePausedint){
            case 1: gamePaused = false;
                    gamePausedint = 0;
                    break;
            case 0: gamePaused = true;
                    gamePausedint = 1;
                    break;
        }
    }

}
