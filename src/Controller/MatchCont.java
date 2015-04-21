package Controller;

import ClientModel.ClientModel;
import GUIView.MatchMaking;

/**
 * Group Names: Tyler Glass, Michael House, Holly Ruyle, Phu Hoang. 
 * Project Part: Controller inheritance - Matchmaking Controller. 
 * Program Title: Tic-tac-toe.
 * Game Course: CSCE 320 - Software Engineering. 
 * Date: Match 16, 2015.
 * Language and Compiler: Java written in eclipse and Netbeans.
 */
public class MatchCont extends Controller {

    //Declare variable
    private ClientModel model;
    private MatchMaking view;
    private String usrInfo = "";
	//End declare variable

    /**
     * This will set the GUI-View into the controller.
     * @param view - GUI.
     */
    public void setView(MatchMaking view) {
        this.view = view;
    } // end setView.

    /**
     * This will set the Model into the controller.
     * @param model - ClientModel.
     */
    public void setModel(ClientModel model) {
        this.model = model;
    } // end setModel.

    /**
     * Listen to the GUI actionPerformed.
     * Pass the string to model to switch from view to another view.
     * Implements Chat function.
     * Implements send invitation to another player.
     * @param value - String value from GUI.
     */
    @Override
    public void listen(String value) {
    	//Listen and switch back to the start up menu.
        if (value.equals("logOut")) {
            model.switchController("startUp");
            model.sendUserInfo("close");
            view.setVisible(false);
        }

        //Listen the chat information then update the chat text into model.
        if (value.equals("chat")) {
            model.sendUserInfo(usrInfo);
        }
        
        // added by Tyler to handle user invites
        //Listen and invite one player then sent the invitation of host to the model.
        if (value.equals("sInvite")) {
            model.sendUserInfo(usrInfo);
        }
        
        //Listen and invite all available player then sent the invitation of host to the model.
        if (value.equals("mInvite")) {
            model.sendUserInfo(usrInfo);
        }
        
      //Listen to close signal then update the user information to model.
        if (value.equals("close")) {
            model.sendUserInfo("close");
        }
    } //end listen.

    /**
     * Update the chat message from all online user.
     * @param msg - chat message.
     */
    public void updateModelMsg(String msg) {
        //DEBUG
        System.out.println("This is the display on view: " + msg);
        view.chatBoxTA.append(msg);
    } // end updateModelMsg.

    /**
     * Set the view visible.
     * @param value.
     */
    @Override
    public void switchView(String value) {
        if (value.equals("lobby")) 
            view.setVisible(true);
    } // end switchView.

    /**
     * Update the user information from the GUI text field.
     * @param usr text field.
     */
    @Override
    public void updateUserInfo(String usrInfo) {
        this.usrInfo = usrInfo;
    } // end updateUserInfo.

    /**
     * This method takes the status string of the available players and deletes
     * the identifier then passes it to matchmaking view to enter the user names
     * on the list
     *
     * @param users the status string of format "List_user1_user2".
     */
    public void setAvailableList(String users) {
        String justUsers = users.substring(5); // this is to eliminate "List_"
        view.setList(justUsers); // passes it to view since its fields are private

    } //end setAvailableList.

    /**
     * Set the view invisible.
     * @param value.
     */
    //@Override
    public void setVisible(boolean value) {
        view.setVisible(value);
    } // end setVisible.

    /**
     * Set the controller identity.
     * @param ID the identity of the controller.
     */
    public void setID(String id) {
        this.ID = id;
    } //end setID.
}
