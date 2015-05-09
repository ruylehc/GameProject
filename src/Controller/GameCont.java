/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import ClientModel.ClientModel;
import java.awt.Graphics;
import ClientModel.GameModel;
import GUIView.GameBoard;
import java.io.IOException;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    
    
    @Override
    public void listen(String value) {
        
        String[] split = info.split("_");
        int row, col;
        
        switch (value) {
            case "quit":
                cmodel.switchController("lobby");
                view.setVisible(false);
                //gmodel.close();
                
                break;
            case "close":
                cmodel.sendUserInfo("close");
                //gmodel.close();
                
                break;
            case "logOut":
                cmodel.switchController("startUp");
                cmodel.sendUserInfo("close");
                //gmodel.sendServerMsg("close");
                view.setVisible(false);
                break;
            case "size":
                gmodel.changeSize(Integer.parseInt(info));
                gmodel.fillBoard();                
                view.reDrawBoard();
                break;
            case "chat":
                gmodel.sendMsg(info);
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
        //DEBUG - do we still need to debug this?
        System.out.println("set view to false is actived");
        view.setVisible(value);
    } //end setVisible.
    
    
    /**
     * Set the controller identity.
     *
     * @param ID the identity of the controller.
     */
    public void setID(String ID) {
        this.ID = ID;
        //DEBUG
        System.out.println("This is the id of the game controller: " + ID);
    } //end setID.
    
    public void setTitle(String user){
        view.setViewTitle(user);
    }
    
    public void draw(Graphics g, int w, int h){
        gmodel.draw(g, w, h);
    }
    
    public void updateBoard(){
        //DEBUG
        System.out.println("board is loading");
        view.reDrawBoard();
    }
    
}
