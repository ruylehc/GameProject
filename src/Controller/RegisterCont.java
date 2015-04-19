package Controller;

import ClientModel.ClientModel;
import GUIView.Register;

/**Group Names: Tyler Glass, Michael House, Holly Ruyle, Phu Hoang    
 * Project Part: Controller inheritance - Register Controller
 * Program Title: Tic-tac-toe Game
 * Course: CSCE 320 - Software Engineering
 * Date: Match 16, 2015
 * Language and Compiler: Java written in eclipse and Netbeans
 */
public class RegisterCont extends Controller{
    
        //Declare variable
	private ClientModel model;
	private Register view;	
	private String usrInfo = "";
        //End declare variable
        
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

        /**
         * Listen to the GUI actionPerformed
         * @param value 
         */
	@Override
	public void listen(String value) {		
		if(value.equals("back")){
			model.switchController("startUp");
			view.setVisible(false);
		}
                
		if(value.equals("register"))
			model.sendUserInfo(usrInfo);		
	} // end listen	

        /**
         * Set the view visible
         * @param value 
         */
	@Override
	public void switchView(String value) {
		if(value.equals("register"))
                    view.setVisible(true);
	} // end switchView

        /**
         * Update the user information from the GUI text field
         * @param usr username and password
         */
	@Override
	public void updateUserInfo(String usrInfo) {
		this.usrInfo = usrInfo;
	}

         /**
         * Set the view invisible
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
        } // end setID





}
