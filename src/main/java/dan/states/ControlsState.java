package dan.states;

import dan.display.Assets;
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
        g.drawImage(Assets.controlsScreen,0,0,null);
    }
}
