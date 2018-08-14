package dan.states;

import dan.display.Assets;
import dan.game.Handler;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.nio.Buffer;


public class MenuState extends State{

    private float alpha;
    private boolean fadingToGame, doneFading, switchingModes;
    private int menuSelection, currentMode, slideCoordinate;

    public MenuState(Handler handler) {
        super(handler);
        alpha = 0.0f;
        fadingToGame = false;
        doneFading = false;
        slideCoordinate = 0;
        menuSelection = 0;
        currentMode = 1;
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
        g.setColor(Color.WHITE);
        switchModeSelection(g);
        renderMenuNavigation(g);
        renderMenuArrows(g);
        if(fadingToGame)
            fadeScreenToWhite(g);
    }

    public void switchModeSelection(Graphics g){
        if(menuSelection == 0 && handler.getKeyManager().menuRight){
           switchingModes = true;
           currentMode *= -1;
        }
        System.out.println(currentMode);
        switch(currentMode){
            case 1:
                if(switchingModes)
                    animateSlideRight(0,384, 400, g, Assets.controlsButton, Assets.surivalModeButton);
                else
                    g.drawImage(Assets.controlsButton, 384,400, null);
                break;
            case -1:
                if(switchingModes)
                    animateSlideRight(0, 384, 400, g, Assets.surivalModeButton, Assets.controlsButton);
                else
                    g.drawImage(Assets.surivalModeButton, 384, 400, null);
                break;
        }
    }

    public void animateSlideRight(int startX, int endX, int y, Graphics g, BufferedImage incomingImage, BufferedImage outgoingImage){
        //startX is the start x coordinate(pixels) of the slide, endX is the end x coordinate of the slide
        //y is the y coordinate for both start and end of slide.
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform incoming = new AffineTransform();
        AffineTransform outgoing = new AffineTransform();
        if(slideCoordinate < endX && switchingModes){
            incoming.translate(startX + slideCoordinate, y);
            incoming.shear(0.5,0);
            outgoing.translate(endX + slideCoordinate, y);
            outgoing.shear(0.5, 0);
            g2d.drawImage(incomingImage, incoming, null);
            g2d.drawImage(outgoingImage, outgoing, null);
            slideCoordinate += 30;
        }
        else{
            switchingModes = false;
            slideCoordinate = 0;
        }
    }

    public void renderMenuNavigation(Graphics g){
        if(menuSelection == 2)
            g.drawImage(Assets.exitButtonSelected, 384, 502, null);
        else
            g.drawImage(Assets.exitButton, 384, 502, null);
        if(menuSelection == 1)
            g.drawImage(Assets.controlButtonSelected, 384, 451, null);
        else
            g.drawImage(Assets.controlsButton, 384, 451, null);
        if(!switchingModes) {
            if (menuSelection == 0) {
                if (currentMode == -1)
                    g.drawImage(Assets.survivalModeButtonSelected, 384, 400, null);
                else
                    g.drawImage(Assets.controlButtonSelected, 384, 400, null);
            } else {
                if (currentMode == -1)
                    g.drawImage(Assets.surivalModeButton, 384, 400, null);
                else
                    g.drawImage(Assets.controlsButton, 384, 400, null);
            }
        }
    }

    public void renderMenuArrows(Graphics g){
        if(menuSelection == 0) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(Assets.menuArrow, 384 + 200, 410, null);
            AffineTransform at = new AffineTransform();
            //this transform I found via trial and error, there is probably a better way to do this.
            at.translate(432,441);
            at.rotate(Math.PI);
            g2d.drawImage(Assets.menuArrow,at, null);
        }
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
