package Controller;

import ClientModel.ClientModel;
import GUIView.Login;

/**
 * Group Names: Tyler Glass, Michael House, Holly Ruyle, Phu Hoang. 
 * Project Part: Controller inheritance - Login Controller. 
 * Program Title: Tic-tac-toe Game.
 * Course: CSCE 320 - Software Engineering.
 * Date: Match 16, 2015.
 * Language and Compiler: Java written in eclipse and Netbeans.
 */

public class LoginCont extends Controller {

    //Declare variable.
    private ClientModel model;
    private Login view;
    private String usrInfo = "";
    //End declare variable.

    /**
     * This will set the GUI-View into the controller.
     *
     * @param view - GUI.
     */
    public void setView(Login view) {
        this.view = view;
    } // end setView.

    /**
     * This will set the Model into the controller.
     *
     * @param model - ClientModel.
     */
    public void setModel(ClientModel model) {
        this.model = model;
    } // end setModel.

    /**
     * Listen to the GUI actionPerformed. Pass the string to model to switch
     * from view to another view. Send Login user name and password to model.
     *
     * @param value - String value from GUI.
     */
    @Override
    public void listen(String value) {
        //Listen and switch back to the start up menu. 
        if (value.equals("back")) {
            model.switchController("startUp");
            //model.sendUserInfo("close");
            view.setVisible(false);
        }
        //Listen to login signal then update the user information to model.
        if (value.equals("login")) {
            model.sendUserInfo(usrInfo);
        }

        //Listen to close signal then update the user information to model.
        if (value.equals("close")) {
            model.sendUserInfo("close");
        }

    } // end listen.

    /**
     * Update the user information from the GUI text field.
     *
     * @param usr user name and password.
     */
    @Override
    public void updateUserInfo(String usr) {
        this.usrInfo = usr;
    } //end updateUserInfo.

    /**
     * Set the view visible.
     *
     * @param value.
     */
    @Override
    public void switchView(String value) {
        if (value.equals("login")) {
            view.setVisible(true);
        }
    } //end switchView

    /**
     * Set the view invisible.
     *
     * @param value.
     */
    //@Override
    public void setVisible(boolean value) {
        //DEBUG
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
        System.out.println("This is the id of the login controller: " + ID);
    } //end setID.
}
