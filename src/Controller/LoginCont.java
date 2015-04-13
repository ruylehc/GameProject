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
public class LoginCont implements Controller{
	private ClientModel model;
	private Login view;
	public String usrInfo = "";
	

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

	@Override
	public void listen(String value) {
		if(value.equals("back")){
			model.switchController("startUp");
			//model.closeTCP();
			view.setVisible(false);
		}

		else if(value.equals("login")){
			model.authentication(usrInfo);
			if(model.isLoginValid()){
				model.switchController("lobby");
				view.setVisible(false);
			}
			else{
				JOptionPane.showMessageDialog(null, "Username does not exist; please re-enter");
				
				//if(control.matchingPW(user, pass)== false)
				//	JOptionPane.showMessageDialog(null, "Password does not match; Re-enter \n Remeber: Password is case sensitive");
				}
			}
	}
	
	@Override
	public void updateUserInfo(String usr) {
		this.usrInfo = usr;		
	}

	@Override
	public void switchView(String value) {
		if(value.equals("login")){
			view.setVisible(true);
		}
	}	
}
