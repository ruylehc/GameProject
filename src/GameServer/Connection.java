package GameServer;
/**Group Names: Tyler Glass, Michael House, Holly Ruyle, Phu Hoang    
 * Project Part: The Socket - Echo Sever - Connection Thread
 * Program Title: Tic-tac-toe Game 
 * Course: CSCE 320 - Software Engineering
 * Date: February 23, 2015
 * Language and Compiler: Java written in eclipse and Netbeans
 * Sources: CSCE 320 references - Trivial Java Example
 */

import java.net.*;
import java.io.*;

public class Connection extends Thread {

	// Variables declaration
	private Authentication model;
	private Socket sock;
	private InputStream in;
	private OutputStream out;
	private final int BYTE_SIZE = 1024;
	private byte [] bufferIn;
	private Server ss;
	private int port;
	private boolean terminate = true;
        private String IP = "";
        
	// End of variables declaration

	/**
	 * Default Constructor 
	 * @param s
	 * @param ss
	 * @throws IOException
	 */
	public Connection(Socket s, Server ss) throws IOException{
		bufferIn = new byte[BYTE_SIZE];
		this.ss = ss;
		this.sock = s;
		this.port = s.getPort();
		in = sock.getInputStream();
		out = sock.getOutputStream();


	} // End Default Connection

	/**
	 *  Thread One: sends a message to the Client 
	 *  called by the Server
	 * @throws IOException
	 */
	public void sendServerMsg(String msg){	
		System.out.println("This is the Connection-ServerModel from server: " + msg);
		byte[] bufferOut = msg.getBytes();
		//Send the message to the Client
		try {					
			out.write(bufferOut);	
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();			
			//ss.remove(this);
		}
	}
        
        public void sendUserInvite(String playerInvite){
            String[] split = playerInvite.split("_");	//Delimiter the string 
            String type = split[0];
            String SendTo = split[1];
            String ip = "";
            
            
            if(type.equals("invite"))
                if(ss.checkOnlineUser(SendTo)) // checks if the player is online
                    ip = ss.getOnlineUser().get(SendTo);
                    ss.sentUserInvite(playerInvite);  // sent the invite to the invited player's connection
            
        }
        
	/**
	 * Thread Two: 
	 * 	+ Reads a message from the Client
	 *	+ Send server message back to the Client 
	 */
	public void readClientMsg() {
		
		String info = null;
		
		try {
			
				bufferIn = new byte[in.available()];
				int len = in.read(bufferIn);
				
				if(len > 0){
					info = new String(bufferIn, 0, len);	//Reading user info to string
					String[] split = info.split("_");	//Delimiter the string 
					String type = split[0];

					//Sends server message back to login user 
					if(type.equals("login")){
						String userName = split[1].toLowerCase();
						String passWord = split[2] ;
						String loginStatus = model.login(userName, passWord);
						System.out.println("login "+userName + ", " + passWord);
						System.out.println("From connection, server side; " + loginStatus);
						sendServerMsg(loginStatus);
					}
					else if(type.equals("close"))
						close();

					//Sends server message back to register user
					else if(type.equals("register")){
						String userName = split[1].toLowerCase();
						String passWord = split[2];
						String rePassword = split[3];
						String registerStatus = model.registerUser(userName, passWord, rePassword);
						System.out.println("register :" +userName + ", " + passWord);
						sendServerMsg(registerStatus);  
					}

					//Sends the chat message to all connection
					else{				
						String chatMsg = split[1];
						ss.broadcast(chatMsg);
					}
				}
				else if(sock == null) //add on today
					terminate = false;				
			
		}catch (IOException e) {
			e.printStackTrace();			
			close();
		}		
	}// End readClientMsg

	public void close(){
		try{			
			out.close();
			in.close();
			sock.close();
			terminate = false;
		}catch(IOException e){
			e.printStackTrace();
		}
		
			
	}
	/**
	 * Run the Thread Two
	 */
	public void run(){
		while(terminate == true){
			readClientMsg();
			if(!terminate)
				ss.remove(this);
		}
		ss.remove(this);
		//ss.remove(this);
	}// End run	

	/**
	 * Set the model into the Connection
	 * @param model
	 */
	public void setModel(Authentication model){
		this.model = model;
	}

	/**
	 * Return the port from the client socket
	 * @return the port
	 */
	public int getPort() {
		return port;
	}	
        
        public void setPort(){
            
        }
        
        /**
         * 
         * @return the IP address in String format for a specific connection
         */
        public String getIP(){
            return IP;
        }
        
        /**
         * sets local variable IP to the string passed into the method
         * @param IP passes a string representation of the IP address of a new connection
         */
        public void setIP(String IP){
            
            this.IP = IP;
        }
        
	/**
	 * Return the socket object from the client socket
	 * @return the port
	 */
	public Socket getSocket() {
		return sock;
	}	
}// End Class Connection

