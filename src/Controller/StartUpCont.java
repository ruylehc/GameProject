package Controller;
import javax.swing.JButton;

import ClientModel.ClientModel;
import GUIView.StartUp;

/**Group Names: Tyler Glass, Michael House, Holly Ruyle, Phu Hoang    
 * Project Part: Controller Interface - Start Up Controller
 * Program Title: Tic-tac-toe Game
 * Course: CSCE 320 - Software Engineering
 * Date: Match 16, 2015
 * Language and Compiler: Java written in eclipse and Netbeans
 */
public class StartUpCont extends Controller{
	private ClientModel model;
	private StartUp view;

	/**
	 * This will set the GUI-View into the controller
	 * @param view - GUI
	 */
	public void setView(StartUp view){		
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
	public void listen(String value){
		if(value.equals("newUsr") || value.equals("existUsr") || value.equals("anonymous")){
			//model.runTCP();
			
			if(value.equals("newUsr"))
				model.switchController("register");

			if(value.equals("existUsr"))
				model.switchController("login");

			if(value.equals("anonymous"))
				model.switchController("lobby");
		}

		if(value.equals("play"))
			model.switchController("game");

		view.setVisible(false);
	}



	@Override
	public void switchView(String value) {
		if(value.equals("startUp"))
			view.setVisible(true);
	}

	@Override
	public void updateUserInfo(String usrInfo) {
		// TODO Auto-generated method stub		
	}


}
