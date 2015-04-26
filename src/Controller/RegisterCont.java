package Controller;

import ClientModel.ClientModel;
import GUIView.Register;

/**
 * Group Names: Tyler Glass, Michael House, Holly Ruyle, Phu Hoang. 
 * Project Part: Controller inheritance - Register Controller. 
 * Program Title: Tic-tac-toe Game.
 * Course: CSCE 320 - Software Engineering. 
 * Date: Match 16, 2015. 
 * Language and Compiler: Java written in eclipse and Netbeans.
 */

public class RegisterCont extends Controller {

    //Declare variable
    private ClientModel model;
    private Register view;
    private String usrInfo = "";
    //End declare variable

    /**
     * This will set the GUI-View into the controller.
     *
     * @param view - GUI.
     */
    public void setView(Register view) {
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
     * from view to another view. Send Register user name and password to model.
     *
     * @param value - String value from GUI.
     */
    @Override
    public void listen(String value) {
        //Listen and switch back to the start up menu.
        if (value.equals("back")) {
            model.sendUserInfo("close");
            model.switchController("startUp");
            view.setVisible(false);
        }

        //Listen to register signal then update the user information to model.
        if (value.equals("register")) {
            model.sendUserInfo(usrInfo);
        }

        //Listen to close signal then update the user information to model.
        if (value.equals("close")) {
            model.sendUserInfo("close");
        }
    } // end listen.

    /**
     * Set the view visible.
     *
     * @param value
     */
    @Override
    public void switchView(String value) {
        if (value.equals("register")) {
            view.setVisible(true);
        }
    } // end switchView.

    /**
     * Update the user information from the GUI text field.
     *
     * @param usr username and password.
     */
    @Override
    public void updateUserInfo(String usrInfo) {
        this.usrInfo = usrInfo;
    } //end updateUserInfo.

    /**
     * Set the view invisible.
     *
     * @param value.
     */
    //@Override
    public void setVisible(boolean value) {
        view.setVisible(value);
    } // end setVisible.

    /**
     * Set the controller identity.
     *
     * @param ID the identity of the controller.
     */
    public void setID(String id) {
        this.ID = id;
    } // end setID.

}
