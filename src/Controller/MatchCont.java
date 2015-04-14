package Controller;
import ClientModel.ClientModel;
import GUIView.MatchMaking;

/**Group Names: Tyler Glass, Michael House, Holly Ruyle, Phu Hoang    
 * Project Part: Controller Interface - Matchmaking Controller
 * Program Title: Tic-tac-toe Game
 * Course: CSCE 320 - Software Engineering
 * Date: Match 16, 2015
 * Language and Compiler: Java written in eclipse and Netbeans
*/
public class MatchCont extends Controller{
	private ClientModel model;
	private MatchMaking view;
	private String usrInfo = "";
	
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

	@Override
	public void listen(String value) {	
		
		if(value.equals("logOut")){
			model.switchController("startUp");
			view.setVisible(false);
		}
		
		else if(value.equals("chat")){
			model.authentication(usrInfo);
		}		
	}

	@Override
	public void switchView(String value) {
		if(value.equals("lobby"))
			view.setVisible(true);
	}

	@Override
	public void updateUserInfo(String usrInfo) {
		this.usrInfo = usrInfo;
	}
	
	
}
