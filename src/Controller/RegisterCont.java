package Controller;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import ClientModel.ClientModel;
import GUIView.Register;

/**Group Names: Tyler Glass, Michael House, Holly Ruyle, Phu Hoang    
 * Project Part: Controller Interface - Register Controller
 * Program Title: Tic-tac-toe Game
 * Course: CSCE 320 - Software Engineering
 * Date: Match 16, 2015
 * Language and Compiler: Java written in eclipse and Netbeans
 */
public class RegisterCont extends Controller{
	private ClientModel model;
	private Register view;	
	private String usrInfo = "";
	/**
	 * This will set the GUI-View into the controller
	 * @param view - GUI
	 */
	public void setView(Register view){		
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
			view.setVisible(false);
		}

		if(value.equals("register")){
			System.out.println("This is info from reg cont: " + usrInfo);
			model.authentication(usrInfo);
			//if(model.isRegValid()){
			//	model.switchController("login");
			//	view.setVisible(false);
			//}
			//else
				JOptionPane.showMessageDialog(null, "Failed register");
		}
	}	

	@Override
	public void switchView(String value) {
		if(value.equals("register"))
			view.setVisible(true);		
	}

	@Override
	public void updateUserInfo(String usrInfo) {
		this.usrInfo = usrInfo;
	}





}
