package Controller;

import javax.swing.JButton;

import ClientModel.ClientModel;
import GUIView.StartUp;

/**
 * Group Names: Tyler Glass, Michael House, Holly Ruyle, Phu Hoang. 
 * Project Part: Controller inheritance - Start Up Controller. 
 * Program Title: Tic-tac-toe Game.
 * Course: CSCE 320 - Software Engineering. 
 * Date: Match 16, 2015. 
 * Language and Compiler: Java written in eclipse and Netbeans.
 */
public class StartUpCont extends Controller {

    //Declare variable
    private ClientModel model;
    private StartUp view;
    String info = "";
    //End declare variable

    /**
     * This will set the GUI-View into the controller.
     * @param view - GUI.
     */
    public void setView(StartUp view) {
        this.view = view;
    } // end setView.

    /**
     * This will set the Model into the controller.
     * @param model ClientModel.
     */
    public void setModel(ClientModel model) {
        this.model = model;
    } // end setModel.

    /**
     * Listen to the GUI actionPerformed
     * Pass the string to model to switch from view to another view.
     * @param value - String value from GUI.
     */
    @Override
    public void listen(String value) {
        if (value.equals("newUsr") || value.equals("existUsr") || value.equals("anonymous")) {
            model.runTCP();

            if (value.equals("newUsr")) {
                model.switchController("register");
            }

            if (value.equals("existUsr")) {
                model.switchController("login");
            }

            if (value.equals("anonymous")) {
                model.sendUserInfo("guest_");
                model.switchController("lobby"); // success every time so can switch views here
            }
        }

        if (value.equals("play")) { 
            //System.out.println("this is the start up view displaying difficulty: " + info );
            model.setDifficutly(info);   
            model.setGameMode(true);
            model.switchController("gameBoard");
            
        }
        
        view.setVisible(false);
    } // end listen.

    /**
     * Set the view visible.
     * @param value
     */
    @Override
    public void switchView(String value) {
        if (value.equals("startUp")) {
            view.setVisible(true);
        }
    } // end switchView.

    /**
     * None use
     * @param usrInfo
     */
    @Override
    public void updateUserInfo(String usrInfo) {
        this.info = usrInfo;	
    }

    /**
     * None use
     * @param value
     */
    //@Override
    public void setVisible(boolean value) {
        //N/A
    }

    /**
     * Set the controller identity.
     * @param ID the identity of the controller.
     */
    public void setID(String id) {
        this.ID = id;
    } // end setID.

}
