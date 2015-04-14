package ClientModel;
/**Group Names: Tyler Glass, Michael House, Holly Ruyle, Phu Hoang    
 * Project Part: Admin Model
 * Program Title: Tic-tac-toe Game 
 * Course: CSCE 320 - Software Engineering
 * Date: February 23, 2015
 * Language and Compiler: Java written in eclipse and Netbeans
 * Sources: CSCE 320 references - Trivial Java Example
 */

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import Controller.*;

public class ClientModel {
	private ArrayList<Controller> list = new ArrayList<Controller>();
	private String signal = "";
	private String usrInfo = "";
	private SocketClient sock;
	private String msg = "";

	boolean isLogValid = false;
	LoginCont contLog;

	/**
	 * Add the new controller to the ArrayList
	 * @param newController
	 */
	public void addController(Controller newController){
		list.add(newController);
	}

	/**
	 * Read the controller signal to switch to another controller and view
	 * @param signal
	 */
	public void switchController(String signal){
		this.signal = signal;
		System.out.println(this.signal);
		for(Controller c: list)
			if(c.ID.equals("LoginCntrl")){
				//contLog = (LoginCont) c;
				contLog.setVisible(false);
			} else
				c.switchView(signal);
	}

	/**
	 * Write the user information into the I/O stream to the server
	 * @param usrInfo: user information from the GUI textfield.
	 */
	public void authentication(String usrInfo){

		try {
			sock.writeUserMessage(usrInfo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	public void authenticateStatus() throws IOException{
		String rY = "rY"; //register success
		String rUN = "rUN"; //register username problem

		String LY = "lY"; //lY = login success
		String LUN = "1UN"; //lUN = login username problem
		String LUP = "1UP"; //1UP = login password incorrect
		String status; // authentication status
		//updateController = null;
		boolean conn = true;
		//while(conn) {
			//int len = in.read(buffer) ;
			//if (len >0) { //Display the server message if the user actually typing.
				//status = new String(buffer, 0, len) ;
				//updateController = status;

				if (status.equals("success")){
					//display register success
		//			updateController = "loginSuccess";
					//remove login view bring in lobby view
				}
				else if()
			//		updateController = "loginFalse";

				else if (status.equals(rUN)){
					//display register username fail
					//clear username field
				}
				else if (status.equals(LY)){
					//StartUpCont.listen(updateController);
					//remove login view bring in lobby view
				}
				else if (status.equals(LUN)){
					//display login username fail
					//clear username
				}
				else if (status.equals(LUP)){
					//display login password problem
					//clear password field
				}

			//}
		//	else {
				//view.msgTF.append("Lost Server Connection") ;
			//	conn = false ;
			//}
		//} 
		//return updateController;
		//close the I/O Stream and the socket.
		//out.close();
		//in.close() ;
		//s.close();
	}	

	 */

	
	/**
	 * 
	 * @param msg
	 */
	public void updateServerMsg(String msg) {
		this.msg = msg;		
		if(msg.equals("success"))
			isLogValid = true;
		if(isLogValid)
			this.switchController("lobby");

	}	

	/**
	 * 
	 * @param sock
	 */
	public void setSock(SocketClient sock){
		this.sock = sock;
	}



}
