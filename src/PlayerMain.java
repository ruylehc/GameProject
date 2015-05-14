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
import ClientModel.GameModel;
import ClientModel.SocketClient;
import Controller.LoginCont;
import Controller.MatchCont;
import Controller.RegisterCont;
import Controller.StartUpCont;
import Controller.GameCont;
import GUIView.Login;
import GUIView.MatchMaking;
import GUIView.Register;
import GUIView.StartUp;
import GUIView.GameBoard;
public class PlayerMain {

	public static void main(String[] args) throws UnknownHostException{
		ClientModel model = new ClientModel();
                GameModel gmodel = new GameModel(); // this was added since we took away our consturtors that required an ip and port
                //this allows us to create game model and game controller at beginning without needing the port and ip
                //when we move to a game it calls createServer/createSocket 
                    //model.setIp(args[0]);
		//GUI
		StartUp viewSU = new StartUp();
		Login viewLog = new Login();
		Register viewReg = new Register();
		MatchMaking viewMatch = new MatchMaking();
                GameBoard viewGame = new GameBoard();

		//Cont
		StartUpCont contSU = new StartUpCont();
		contSU.setID("StartCtrl");
		LoginCont contLog = new LoginCont();	//constructor for loginCont
		contLog.setID("LoginCtrl");
		RegisterCont contReg = new RegisterCont();
		contReg.setID("RegCtrl");
		MatchCont contMatch = new MatchCont();
		contMatch.setID("MatchCtrl");
                GameCont contGame = new GameCont();
                contGame.setID("GameCtrl");

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
                
                viewGame.setController(contGame);
                contGame.setModel(model);
                contGame.setGModel(gmodel);
                contGame.setView(viewGame);
                
                
                

		model.addController(contSU);
		model.addController(contLog);
		model.addController(contReg);
		model.addController(contMatch);
                model.addController(contGame);
                gmodel.setController(contGame); // uses set rather than add since gmodel only has 1 controller
              
                model.addSubModel(gmodel);
                gmodel.setController(contGame);

		//model.setSock(sock);

	}
}
