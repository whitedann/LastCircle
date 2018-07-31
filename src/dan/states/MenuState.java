package dan.states;

import dan.display.Assets;
import dan.game.Game;
import dan.game.Handler;

import java.awt.*;


public class MenuState extends State{

    private float alpha;
    private boolean fadingToGame, doneFading;

    public MenuState(Handler handler) {
        super(handler);
        alpha = 0.0f;
        fadingToGame = false;
        doneFading = false;
    }

    @Override
    public void tick(){
        if(handler.getKeyManager().confirm) {
            fadingToGame = true;
        }
        if(doneFading)
            handler.getGame().switchToGameState();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.start1, 384, 350, null);
        g.fillRect(256,200,512,100);
        if(fadingToGame)
            fadeSceenToWhite(g);
    }

    public void fadeSceenToWhite(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.WHITE);
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
