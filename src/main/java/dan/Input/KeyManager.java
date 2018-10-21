package dan.Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {

    private boolean[] keys;
    public boolean up, down, left, right, rotateR, rotateL, fire, confirm, gamePausedPressed, gamePausedReleased,
    menuUP, menuDown, menuRight,menuLeft;
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

        resetKeys();
        processInput();

    }

    public void processInput() {
        if (keyPressedAndReleased(KeyEvent.VK_UP)) {
            menuUP = true;
            resetKeysTyped();
        }
        if (keyPressedAndReleased(KeyEvent.VK_DOWN)) {
            menuDown = true;
            resetKeysTyped();
        }
        if (keyPressedAndReleased(KeyEvent.VK_ENTER)) {
            confirm = true;
            resetKeysTyped();
        }
        if(keyPressedAndReleased(KeyEvent.VK_RIGHT)){
            menuRight = true;
            resetKeysTyped();
        }
        if(keyPressedAndReleased(KeyEvent.VK_LEFT)){
            menuLeft = true;
            resetKeysTyped();
        }
        if(keyPressedAndReleased(KeyEvent.VK_ESCAPE)){
            togglePause = true;
            resetKeysTyped();
        }
    }

    public void resetKeysTyped(){
        keysPressed = new boolean[256];
        keysReleased = new boolean[256];
    }

    public void resetKeys(){
        confirm = false;
        menuUP = false;
        menuDown = false;
        menuLeft = false;
        togglePause = false;
        menuRight = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
        keysPressed[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
        keysReleased[e.getKeyCode()] = true;
    }

    public boolean keyPressedAndReleased(int keyCode){
        if(keysPressed[keyCode] && keysReleased[keyCode])
            return true;
        else
            return false;
    }
}
