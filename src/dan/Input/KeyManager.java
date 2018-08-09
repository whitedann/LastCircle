package dan.Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;

public class KeyManager implements KeyListener {

    private boolean[] keys;
    public boolean up, down, left, right, rotateR, rotateL, fire, confirm, gamePausedPressed, gamePausedReleased;
    public boolean togglePause = false;

    private boolean[] keysPressed, keysReleased;

    public KeyManager(){
        keysPressed = new boolean[256];
        keysReleased = new boolean[256];
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

        if(keysPressed[KeyEvent.VK_ENTER] && keysReleased[KeyEvent.VK_ENTER]){
            confirm = true;
            keysReleased = new boolean[256];
            keysPressed = new boolean[256];
        }


        if(gamePausedPressed && gamePausedReleased) {
            togglePause = true;
            gamePausedReleased = false;
            gamePausedPressed = false;
        }

    }

    public void resetKeys(){
        confirm = false;
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
        keysPressed[e.getKeyCode()] = true;
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            gamePausedPressed = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
        keysReleased[e.getKeyCode()] = true;
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            gamePausedReleased = true;
    }

    public void keyPressedAndReleased(KeyEvent e){

    }

}
