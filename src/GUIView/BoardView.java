package GUIView;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import Controller.GameCont;

/**
 * The panel that manages the graphical representation of the board.
 */
public class BoardView extends JPanel {

    private BufferedImage img;
    private int WIDTH = 720;
    private int HEIGHT = 720;
    private GameCont controller;

    /**
     * Creates a "blank" BoardView. The view is initially
     */
    public BoardView() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        //controller = null;
        reDrawBoard();
    }

    /**
     * Draws the content for this BoardView.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(img, 0, 0, this);
    }

    /**
     * Initiates a redraw of the board. If the Board is not set
     */
    public void reDrawBoard() {
        if (controller != null) {
            Graphics g = img.getGraphics();
            controller.draw(g, WIDTH, HEIGHT);
            this.repaint();
        }
    }

    /**
     * Sets the board to be used by this BoardView.
     *
     * @param b a Board.
     */
    public void setController(GameCont controller) {
        this.controller = controller;
    }

    public void setGrid(int w, int h) {
        this.WIDTH = w;
        this.HEIGHT = h;
    }
}
