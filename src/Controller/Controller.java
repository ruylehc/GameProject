package Controller;

/**Group Names: Tyler Glass, Michael House, Holly Ruyle, Phu Hoang    
 * Project Part: Controller abstract - Start Up Controller
 * Program Title: Tic-tac-toe Game
 * Course: CSCE 320 - Software Engineering
 * Date: Match 16, 2015
 * Language and Compiler: Java written in eclipse and Netbeans
 */

public abstract class Controller {
	
	/**
	 * Listen the string value from the GUIView
	 * @param value - String signal
	 */
	public abstract void listen(String value);
	
	/**
	 * Update the user information from the JTextFiled or GUIView components into controller
	 * @param usrInfo - String user information
	 */
	public abstract void updateUserInfo(String usrInfo);
	
	/**
	 * Switch between from view to view
	 * @param value - name of the GUIView
	 */
	public abstract void switchView(String value);
	
	/**
	 * Controller identity 
	 */
	public String ID = "undef";
	
} //end Controller
