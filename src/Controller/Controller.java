package Controller;

/**Group Names: Tyler Glass, Michael House, Holly Ruyle, Phu Hoang    
 * Project Part: Controller abstract - Start Up Controller
 * Program Title: Tic-tac-toe Game
 * Course: CSCE 320 - Software Engineering
 * Date: Match 16, 2015
 * Language and Compiler: Java written in eclipse and Netbeans
 */

public abstract class Controller {
	public abstract void listen(String value);
	public abstract void updateUserInfo(String usrInfo);
	public abstract void switchView(String value);
	public String ID = "undef";
}
