/**Group Names: Tyler Glass, Michael House, Holly Ruyle, Phu Hoang    
 * Project Part: Game Controller
 * Program Title: Tic-tac-toe Game
 * Course: CSCE 320 - Software Engineering
 * Date: Match 16, 2015
 * Language and Compiler: Java written in eclipse and Netbeans
 */

package Controller;

import ClientModel.ClientModel;
import ClientModel.GameModel;
import GUIView.GameBoard;
import java.awt.*;
import java.awt.Graphics;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author PLUCSCE
 */
public class GameCont extends Controller {
    //
    // decalre variables
    private ClientModel cmodel;
    private GameModel gmodel;
    private GameBoard view;
    private String moveInfo = "";
    private String info = "";
    String ID = "undef";

    /**
     * This will set the GUI-View into the controller.
     *
     * @param view - GUI.
     */
    public void setView(GameBoard view) {
        this.view = view;
    } // end setView.
    
    
    /**
     * This will set the Model into the controller.
     *
     * @param model - ClientModel.
     */
    public void setModel(ClientModel clientmodel) {
        this.cmodel = clientmodel;
    } // end setModel.
    
     /**
     * This will set the Model into the controller.
     *
     * @param model - ClientModel.
     */
    public void setGModel(GameModel gamemodel) {
        this.gmodel = gamemodel;
    } // end setModel.
    
    
    /**
     * Listen to the actionPerformed
     * @param value 
     */
    @Override
    public void listen(String value) {
        
        String[] split = info.split("_");
        int row, col;
        
        switch (value) {
            case "quit":
                
                if(!gmodel.aiGame)
                    gmodel.sendMsg("quit");
                
                else{
                    cmodel.switchController("startUp");
                    gmodel.reset();
                }
                view.setVisible(false);
                
                break;
            case "close":
                if(!gmodel.aiGame)
                    cmodel.sendUserInfo("close");
                else
                    JOptionPane.showMessageDialog(null,"Game option is not avaible for offline!");
                break;
            case "logOut":
                if (!gmodel.aiGame) {
                    cmodel.switchController("startUp");
                    cmodel.sendUserInfo("close");
                    view.setVisible(false);
                }
                else
                    JOptionPane.showMessageDialog(null,"Game option is not avaible for offline!");
                break;
            case "size":
                if (!gmodel.aiGame)
                    gmodel.changeSize(Integer.parseInt(info));
                else
                    JOptionPane.showMessageDialog(null,"Game option is not avaible for offline!");
                break;
            case "chat":
                if (!gmodel.aiGame) {
                    gmodel.sendMsg("chat_" + gmodel.getUserID() + ": " + info);
                    view.chatTA.append(gmodel.getUserID() + ": " + info + "\n");
                }
                else
                    JOptionPane.showMessageDialog(null,"Game option is not avaible for offline!");
                break;
            case "singleClick":
                row = Integer.parseInt(split[0]); 
                col = Integer.parseInt(split[1]);       
                gmodel.executeClick(row, col, 1);
                break;
            case "doubleClick":   
                row = Integer.parseInt(split[0]); 
                col = Integer.parseInt(split[1]); 
                gmodel.executeClick(row, col, 2);
                break;
            case "start":
                if(gmodel.aiGame)
                    JOptionPane.showMessageDialog(null,"This will restart the Game!");
                gmodel.setStart(true);
                break;
            case "lobby":
                cmodel.switchController("lobby");
                cmodel.sendUserInfo("endGame_");
                cmodel.sendUserInfo("stats_" + cmodel.userName + "_win");
                view.chatTA.setText("");
                view.setVisible(false);
                break;
                
            case "startUp":
                cmodel.switchController("startUp");
                view.setVisible(false);
                break;
        }
    }

    /**
     *  updates the move info, method will be used while validating move and 
     *  then passing it to send user msg
     * @param info - string passed in to set move information
     */
    public void updateUserInfo(String info) {
        //this.moveInfo = info;
        this.info = info;
    }

    /**
     * Set the Game view visible
     * @param value 
     */
    @Override
    public void switchView(String value) {
        if (value.equals("gameBoard")) {
            view.setVisible(true);
        }
    }
    
     /**
     * Set the view invisible.
     *
     * @param value.
     */
    //@Override
    public void setVisible(boolean value) {
        
        view.setVisible(value);
    } //end setVisible.
    
    
    /**
     * Set the controller identity.
     *
     * @param ID the identity of the controller.
     */
    public void setID(String ID) {
        this.ID = ID;
       
    } //end setID.
    
    /**
     * Set the user name into the game view title
     * @param user 
     */
    public void setTitle(String user){
        view.setViewTitle(user);
    }
    
    /**
     * Display the payer turn
     * @param player 
     */
    public void updatePlayerTurn(String player){
        view.setJLable(player);
    }
    
    /**
     * Draw the game board
     * @param g graphic
     * @param w width   
     * @param h height
     */
    public void draw(Graphics g, int w, int h){
        gmodel.draw(g, w, h);
    }
    
    /**
     * Update the board 
     */
    public void updateBoard(){
        view.reDrawBoard();
    }
    
    /**
     * Update the chat message from all online user.
     * @param msg - chat message.
     */
    public void updateModelMsg(String msg) {
        
        view.chatTA.append(msg+"\n");
    } // end updateModelMsg.
    
}
