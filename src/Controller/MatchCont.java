package Controller;

import ClientModel.ClientModel;
import GUIView.MatchMaking;
import javax.swing.JOptionPane;

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
            model.sendUserInfo("close");
            model.switchController("startUp"); 
            view.chatBoxTA.setText("");
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
        
        //Listen to the accept button
        if(value.equals("accept")) {
            model.runGameServer();
            String username = model.userName;
            String[] split = usrInfo.split("_");
            String newInfo = split[0] + "_" + username + "_" + split[1] + "_" + model.port;
            System.out.println("This is the accept send in Match cont" + newInfo);
            model.sendUserInfo(newInfo);
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
       // System.out.println("This is the display on view: " + msg);
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
     * method displays a pop-up pane that the user invite they accepted
     * was from a suer that is no longer available (either in game or logged out)
     */
    public void lateAcceptDisplay(){
        JOptionPane.showMessageDialog(null, "Invited user is no longer available for a game.");
    }
    
    /**
     * This method takes the status string of the available players and deletes
     * the identifier then passes it to matchmaking view to enter the user names
     * on the list
     *
     * @param users the status string of format "List_user1_user2".
     */
    public void setAvailableList(String users) {
                
        String justUsers = users.substring(5); // this is to eliminate "List_"
        //DEBUG
        //System.out.println("This is from the mat controller: "+justUsers);
        
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
     * sets the title for the lobby view so we have the user name in title bar
     * @param user - the user name that should be appended to the title bar
     */
    public void setTitle(String user){
        view.setViewTitle(user);
    }
    
    /**
     * this method sets the list of available players online
     * @param inviteMsg the status string of just user names that is then passed
     * to matchView
     */
    public void handleInviteView(String inviteMsg){
        //implement joption pane for inviteMsg received from server =>>>> display on the invites Jlist
        view.setInviterList(inviteMsg);
        
    }
    /**
     * Set the controller identity.
     * @param ID the identity of the controller.
     */
    public void setID(String id) {
        this.ID = id;
    } //end setID.


}
