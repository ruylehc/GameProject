package Controller;
import ClientModel.ClientModel;
import GUIView.MatchMaking;

/**Group Names: Tyler Glass, Michael House, Holly Ruyle, Phu Hoang    
 * Project Part: Controller inheritance - Matchmaking Controller
 * Program Title: Tic-tac-toe Game
 * Course: CSCE 320 - Software Engineering
 * Date: Match 16, 2015
 * Language and Compiler: Java written in eclipse and Netbeans
*/
public class MatchCont extends Controller{
    
        //Declare variable
	private ClientModel model;
	private MatchMaking view;
	private String usrInfo = "";
	//End declare variable
        
	/**
	 * This will set the GUI-View into the controller
	 * @param view - GUI
	 */
	public void setView(MatchMaking view){		
		this.view = view;
	} // end setView

	/**
	 * This will set the Model into the controller
	 * @param model
	 */
	public void setModel(ClientModel model){
		this.model = model;
	} // end setModel

        /**
         * Listen to the GUI actionPerformed
         * @param value 
         */
	@Override
	public void listen(String value) {	
		
		if(value.equals("logOut")){
			model.switchController("startUp");
			view.setVisible(false);
		}
		
		else if(value.equals("chat")){
			model.sendUserInfo(usrInfo);
		}
                // added by tyler to handle user invites
                else if(value.equals("SInvite"))
                        model.sendUserInfo(usrInfo);
                else if(value.equals("MInvite"))
                        model.sendUserInfo(usrInfo);
	} //end listen
        
        public void updateModelMsg(String msg){
            view.chatBoxTA.append(msg);
        }
        /**
         * Set the view visible
         * @param value 
         */
	@Override
	public void switchView(String value) {
            if(value.equals("lobby"))
                view.setVisible(true);
	} // end switchView

        /**
         * Update the user information from the GUI text field
         * @param usr text filed
         */
	@Override
	public void updateUserInfo(String usrInfo) {
		this.usrInfo = usrInfo;
	} // end updateUserInfo

        /**
         * Set the view visible
         * @param value 
         */
        //@Override
        public void setVisible(boolean value) {
            view.setVisible(value);
        } // end setVisible

        /**
         * Set the controller identity.
         * @param ID the identity of the controller.
         */
        public void setID(String id) {
            this.ID = id;
        } //end setID
}
