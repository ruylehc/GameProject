package ClientModel;
/**Group Names: Holly Ruyle, Phu Hoang, Tyler Glass, Michael House    
 * Project Part: The Socket - Client.
 * Program Title: Tic-tac-toe Game
 * Course: CSCE 320 - Software Engineering
 * Date: February 23, 2015
 * Language and Compiler: Java written in eclipse and Netbeans
 * Sources: CSCE 320 project - Java Sockets, Java Thread, Design. 
 * 			Question notes from talking with Dr Hauser.
 */

import java.net.*;  
import java.util.Arrays;
import java.io.*;

public class SocketClient implements Runnable{

	// Variables declaration
	//private ClientModel model;
	private Socket s;
	private Thread worker;
	private InputStream in;
	private OutputStream out;
	private final int SIZE_BYTE = 1024;
	public byte[] buffer ;	
	private boolean active = true;;
	private ClientModel model;

	// End of variables declaration

	/**
	 * Default Constructor
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */	
	public SocketClient() throws IOException{
		s = new Socket(Inet4Address.getLocalHost().getHostAddress(), 52546);
		out = s.getOutputStream();
		in = s.getInputStream();
		buffer = new byte[SIZE_BYTE];
	}//end Construct.

	/**
	 * Implement the runnable from the thread
	 * Read and receive the server message. 
	 * Call the handleUserMessage() when a new thread is created
	 */
	@Override
	public void run() {		
		while(active == true)
			try {
				authenticateStatus();
			} catch (IOException e) {
				//DEBUG
				System.out.println("Connection lost");
				e.printStackTrace();
			}
	} //end run

	/**
	 * Create the new thread for the new Client
	 */
	public void createListener(){
		worker = new Thread(this);
		worker.start();
	} //end createListener


	/**
	 * Send the user message to the server.
	 * Getting the message from the user then write it into output stream in byte array
	 * @param input - User input
	 * @throws IOException
	 */
	public void writeUserMessage(String input) throws IOException{
		//Get a message from the user
		//Convert the user text into byte to read	
		byte[] bufferin = input.getBytes();
		//Send the message to the Server
		try {
			System.out.println("This is from socketClient-Write: "+ input);
			out.write(bufferin);	
		} catch (IOException e) {
			e.printStackTrace();
			close();
		}
	}// end writeUserMessage

        
        public void passInvite(String invite) {
            try {
                buffer = new byte[in.available()];
                int len = in.read(buffer);
                
            } catch(IOException e) {
                e.printStackTrace();
                close();
            }
        }
	/**
	 * Read the server message from I/O stream
	 * Update the server massage into the ClientModel
	 * @throws IOException
	 */
	public void authenticateStatus() throws IOException{

		try{
			int len = in.read(buffer) ;
			if (len > 0) { 
				String status = new String(buffer, 0, len);
				model.updateServerMsg(status);	
			}
			else {
				//DEBUG
				System.out.println("Lost server connection");
				active = false;
			}
		}catch (IOException e) {
			e.printStackTrace();	
			close();	//close the socket 
		}
	}// end authenticateStatus()	

	/**
	 * Close the I/O Stream and socket
	 */
	public void close(){
		try {			
			out.close();
			in.close() ;
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}// end close();

	/**
	 * Set the Client model into the socket client.
	 * @param model CLientModel
	 */
	public void setModel(ClientModel model){
		this.model = model;
	}
}

//end SocketClient
