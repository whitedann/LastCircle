package dan.display;

import dan.game.Game;

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Dimension;

public class Window {

    private Canvas canvas;
    private JFrame frame;

    public Window(int width, int height, String title, Game game) {
        frame = new JFrame(title);
        frame.setSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setFocusable(false);

        frame.add(canvas);
        frame.pack();
    }

    public Canvas getCanvas(){
        return this.canvas;
    }

    public JFrame getJFrame(){
        return this.frame;
    }
}