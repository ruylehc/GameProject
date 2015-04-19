package GameServer;
/**Group Names: Tyler Glass, Michael House, Holly Ruyle, Phu Hoang    
 * Project Part: Echo Server 
 * Program Title: Tic-tac-toe Game 
 * Course: CSCE 320 - Software Engineering
 * Date: February 23, 2015
 * Language and Compiler: Java written in eclipse and Netbeans
 * Sources: CSCE 320 references - Trivial Java Example
 */

import java.net.*;
import java.io.*;
import java.util.*;

public class Server implements Runnable{

	// Variables declaration
	private int port;
	private Thread worker;
	private ServerSocket ss;
	private ServerGUI view;
	ArrayList<Connection> list = new ArrayList<Connection>();
	private Authentication model;
        int ptpPort;
	// End of variables declaration

	/**
	 * Default Constructor - Create the new server at assigned port.
	 * @throws IOException
	 */
	public Server(int port) throws IOException{
		this.port = port;
		ss = new ServerSocket(port);
	} //end Server

	/**
	 * Sends message to all Connection objects
	 * @param msg - Message from the user
	 * @throws IOException 
	 */
	public void broadcast(String msg) throws IOException{
		for(Connection c: list)			
			c.sendServerMsg(msg);

	} // end broadcast

	/**
	 * Accepts connections and creates Connection object.
	 */
	@Override
	public void run() {
		view.serverTA.append("Server waiting for Clients on port " + port + ".\n");
		while(true){			
			try{
				Socket sockClient = ss.accept();
				view.serverTA.append("Client at " + sockClient.getInetAddress().getHostAddress() + " on port " + sockClient.getPort() + "\n");
				view.connectTF.setText(1+list.size()+"");
				Connection connect = new Connection(sockClient, this); //Creates a new Thread
				connect.setModel(model);
                                connect.setIP(sockClient.getInetAddress().toString());
                                ptpPort = connect.getPort() + 5; // 5 is to move away from taken port
				list.add(connect);
				connect.start();	
			} catch (IOException e) {
				e.printStackTrace();			
			}
		}
	} // end run

	public void remove(Connection connect){
		//Remove all disconnected clients
		view.serverTA.append("Client at " + connect.getSocket().getInetAddress().getHostAddress() + " on port " + connect.getSocket().getPort() + " disconnected\n");
		list.remove(connect);				
		view.connectTF.setText(1+list.size()+"");
	}

	/**
	 * Set the GUI display.
	 * @param view
	 */
	public void setView(ServerGUI view){
		this.view = view;
	} //end setView

	/**
	 * Calls run() in the new Thread
	 */
	public void listen(){
		worker = new Thread(this);
		worker.start(); 
	} // end listen

	/**
	 * Set the model into the Connection
	 * @param model
	 */
	public void setModel(Authentication model){
		this.model = model;
	}
        
        public HashMap<String,String> getOnlineUser(){ // kevin is worried this might caause a problem
            // will check when we get methods fully connected
            return model.onlineUser;
        }

        
        public void setUserIP(){
            
            
        }
        
        
       boolean checkOnlineUser(String SendTo) {
          //  HashMap<String, String> online = getOnlineUser();
           for(Connection c: list)
               if(c.getUserName().equals(SendTo))
                return online.containsKey(SendTo);
       
    }
       
        
}//end class Server
