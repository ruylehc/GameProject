/**Group Names: Tyler Glass, Michael House, Holly Ruyle, Phu Hoang    
 * Project Part: GUI Diplay - JPanel to draw the board 
 * Program Title: Tic-tac-toe Game 
 * Course: CSCE 320 - Software Engineering
 * Date: February 23, 2015
 * Language and Compiler: Java written in eclipse and Netbeans
 * Sources: CSCE 320 references - Trivial Java Example
*/

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
        //DEBUG
        //System.out.println("Board JPanel is actived");
        
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();
        g.setColor(Color.white);
        //controller = null;
        
    }

    /**
     * Draws the content for this BoardView.
     */
    public void paintComponent(Graphics g) {
        //DEBUG
        //System.out.println("Board JPanel - paint is actived");
        super.paintComponent(g);

        g.drawImage(img, 0, 0, this);
    }

    /**
     * Initiates a redraw of the board. If the Board is not set
     */
    public void reDrawBoard() {
        //DEBUG
        //System.out.println("Board JPanel - reDrawboard is actived");
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
        //DEBUG
        //System.out.println("Board JPanel - set Controller is actived");
        this.controller = controller;
    }
}
