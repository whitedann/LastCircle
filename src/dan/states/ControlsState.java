package dan.states;

import dan.game.Handler;

import java.awt.*;

public class ControlsState extends State {

    public ControlsState(Handler handler) {
        super(handler);
    }

    @Override
    public void tick() {
        if(handler.getKeyManager().confirm)
            StateManager.setState(handler.getGame().menuState);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0,0,handler.getWidth(),handler.getHeight());
        g.setColor(Color.BLACK);
        g.drawString("CONTROL SCREEN!!!", 300, 500);
    }
}
