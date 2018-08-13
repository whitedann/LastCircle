package dan.states;

import dan.display.Assets;
import dan.game.Handler;

import java.awt.*;


public class MenuState extends State{

    private float alpha;
    private boolean fadingToGame, doneFading;
    private int menuSelection;

    public MenuState(Handler handler) {
        super(handler);
        alpha = 0.0f;
        fadingToGame = false;
        doneFading = false;
        menuSelection = 0;
    }

    @Override
    public void tick(){
        processSelectionMovement();
        if(handler.getKeyManager().confirm) {
            if (menuSelection == 0)
                fadingToGame = true;
            if (menuSelection == 1)
                StateManager.setState(handler.getGame().controlsState);
            if (menuSelection == 2){
                handler.getGame().quit();
            }
        }
        if(doneFading) {
            handler.getGame().resetGame();
            StateManager.setState(handler.getGame().gameState);
            resetMenu();
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.start1, 384, 350, null);
        g.setColor(Color.WHITE);
        if(menuSelection == 2)
            g.drawImage(Assets.exitButtonSelected, 384, 502, null);
        else
            g.drawImage(Assets.exitButton, 384, 502, null);
        if(menuSelection == 1)
            g.drawImage(Assets.controlButtonSelected, 384, 451, null);
        else
            g.drawImage(Assets.controlsButton, 384, 451, null);
        if(menuSelection == 0)
            g.drawImage(Assets.survivalModeButtonSelected, 384, 400, null);
        else
            g.drawImage(Assets.surivalModeButton, 384, 400, null);
        System.out.println(menuSelection);
        if(fadingToGame)
            fadeScreenToWhite(g);
    }

    public void processSelectionMovement(){
        if(handler.getKeyManager().menuUP) {
            if (menuSelection > 0)
                menuSelection--;
        }
        if(handler.getKeyManager().menuDown) {
            if (menuSelection < 2)
                menuSelection++;
        }
    }

    public void resetMenu(){
        menuSelection = 0;
        fadingToGame = false;
        doneFading = false;
        alpha = 0.0f;
        handler.getKeyManager().resetKeys();
    }

    public void fadeScreenToWhite(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.fillRect(0,0, 1024, 720);
        alpha += 0.01f;
        if(alpha >= 1.0f) {
            alpha = 1.0f;
            doneFading = true;
        }
    }
}
