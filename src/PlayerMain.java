/**Group Names: Holly Ruyle, Phu Hoang, Michaele House, Tyler Glass        
 * Project Part: The game main constructor - Game app
 * Program Title: Client-Server Communications
 * Course: CSCE 320 - Software Engineering
 * Date: March 9, 2015
 * Language and Compiler: Java written in eclipse and Netbeans
 * Sources: CSCE 320 project - Java Sockets, Java Thread, Design. 
 * 			Question notes from talking with Dr Hauser.
 */

import java.io.IOException;
import java.net.*;

import ClientModel.ClientModel;
import ClientModel.SocketClient;
import Controller.LoginCont;
import Controller.MatchCont;
import Controller.RegisterCont;
import Controller.StartUpCont;
import GUIView.Login;
import GUIView.MatchMaking;
import GUIView.Register;
import GUIView.StartUp;
public class PlayerMain {

	public static void main(String[] args) throws UnknownHostException{
		ClientModel model = new ClientModel();	//constructor for adminModel
		SocketClient sock = null;
		try {
			sock = new SocketClient();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sock.setModel(model);
		sock.createListener();	
		//GUI
		StartUp viewSU = new StartUp();
		Login viewLog = new Login();
		Register viewReg = new Register();
		MatchMaking viewMatch = new MatchMaking();

		//Cont
		StartUpCont contSU = new StartUpCont();
		contSU.setID("StartCtrl");
		LoginCont contLog = new LoginCont();	//constructor for loginCont
		contLog.setID("LoginCtrl");
		RegisterCont contReg = new RegisterCont();
		contReg.setID("RegCtrl");
		MatchCont contMatch = new MatchCont();
		contMatch.setID("MatchCtrl");

		//Glue code
		viewSU.setVisible(true);
		viewSU.setController(contSU);
		contSU.setView(viewSU);
		contSU.setModel(model);

		viewLog.setController(contLog);
		contLog.setModel(model);
		contLog.setView(viewLog);

		viewReg.setController(contReg);
		contReg.setModel(model);
		contReg.setView(viewReg);

		viewMatch.setController(contMatch);
		contMatch.setModel(model);
		contMatch.setView(viewMatch);

		model.addController(contSU);
		model.addController(contLog);
		model.addController(contReg);
		model.addController(contMatch);
		model.setSock(sock);

	}
}
