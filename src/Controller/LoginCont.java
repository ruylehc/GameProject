package Controller;
import ClientModel.ClientModel;
import GUIView.Login;

import javax.swing.JOptionPane;



/**Group Names: Tyler Glass, Michael House, Holly Ruyle, Phu Hoang    
 * Project Part: Controller Interface - Login Controller
 * Program Title: Tic-tac-toe Game
 * Course: CSCE 320 - Software Engineering
 * Date: Match 16, 2015
 * Language and Compiler: Java written in eclipse and Netbeans
 */
public class LoginCont extends Controller{
	private ClientModel model;
	private Login view;
	private String usrInfo = "";
	private String ID;



	/**
	 * This will set the GUI-View into the controller
	 * @param view - GUI
	 */
	public void setView(Login view){		
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
	public void listen(String value) {
		if(value.equals("back")){
			model.switchController("startUp");
			//model.closeTCP();
			view.setVisible(false);
		}

		else if(value.equals("login")){
			model.authentication(usrInfo);
                        if(model.isValid() == true)
                            view.setVisible(false);
                        else
                            JOptionPane.showMessageDialog(null, model.updateModelMsg(ID) );
		}

	}
        
        /**
         * Update the user information from the GUI text field
         * @param usr username and password
         */
	public void updateUserInfo(String usr) {
            this.usrInfo = usr;		
	}

	/**
         * Set the view visible
         * @param value 
         */
	public void switchView(String value) {
            if(value.equals("login")){
		view.setVisible(true);
		}
	}	

	public void setVisible(boolean value){
            //DEBUG
            System.out.println("set view to false is actived");
            view.setVisible(value);
	}

	public void setID(String ID){
            this.ID = ID;
            //DEBUG
            System.out.println("This is the id of the login controller: " + ID);
	}
}
