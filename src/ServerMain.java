/**Group Names: Holly Ruyle, Phu Hoang, Michaele House, Tyler Glass        
 * Project Part: The Socket - Ser Main Constructor
 * Program Title: Client-Server Communications
 * Course: CSCE 320 - Software Engineering
 * Date: March 9, 2015
 * Language and Compiler: Java written in eclipse and Netbeans
 * Sources: CSCE 320 project - Java Sockets, Java Thread, Design. 
 * 			Question notes from talking with Dr Hauser.
 */

import java.io.IOException;
import GameServer.*;
public class ServerMain {

	/**
	 * Implement the connection between Echo server and the Server GUI
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		//Create the new server, Model, and GUI
		ServerGUI serView = new ServerGUI();
		Server ss = new Server(52546);
		Authentication model = new Authentication();
		//Connect Echo Sever, Model and the GUI
		serView.setServer(ss);
		ss.setView(serView);
		ss.setModel(model);
		serView.setVisible(true);
	}//end main
	
}//end ServerMain