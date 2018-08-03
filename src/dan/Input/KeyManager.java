package dan.Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;

public class KeyManager implements KeyListener {

    private boolean[] keys;
    public boolean up, down, left, right, rotateR, rotateL, fire, confirm, gamePausedPressed, gamePausedReleased;
    public boolean togglePause = false;

    public KeyManager(){
        keys = new boolean[256];
    }

    public void tick(){
        up = keys[KeyEvent.VK_W];
        left = keys[KeyEvent.VK_A];
        right = keys[KeyEvent.VK_D];
        down = keys[KeyEvent.VK_S];
        rotateR = keys[KeyEvent.VK_L];
        rotateL = keys[KeyEvent.VK_J];
        fire = keys[KeyEvent.VK_SPACE];
        confirm = keys[KeyEvent.VK_ENTER];

        if(gamePausedPressed && gamePausedReleased) {
            togglePause = true;
            gamePausedReleased = false;
            gamePausedPressed = false;
        }

    }

    public boolean playerRequestsPause(){
        return this.togglePause;
    }

    public void resetPauseCheck(){
        togglePause = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            gamePausedPressed = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            gamePausedReleased = true;
    }

}
