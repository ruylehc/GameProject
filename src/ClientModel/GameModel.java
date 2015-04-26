package ClientModel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GameModel implements Runnable{

	public int board[][];
	public int SIZE = 40;
	boolean p1Win = false;
	boolean p2Win = false;
	boolean p1turn;
	boolean p2turn;
	int p1Num = 1;
	int p2Num = 2; 
	int X = 1; // player 1 (host) mark
	int O = -1; // player 2 mark
        private Thread worker;
        private ServerSocket ss;
        private Socket sc; 
	private byte[] bufferIn;
        private final int BYTE_SIZE = 1024;
        private boolean terminate = true;
        private InputStream in;
        private OutputStream out;
        private int port;
	
	// These methods handle connecting to the other class
        // this method needs to be made by a MMcontroller with the
        // gmodel.run() when the use hits the accept button
        
    /**
     * Default Constructor - Create the new server at assigned port.
     *
     * @throws IOException.
     */
    public GameModel(int port) throws IOException {
        bufferIn = new byte[BYTE_SIZE];
        this.port = port;
        ss = new ServerSocket(port);        
    } //end Server.
        
    /**
     * Accepts connections and creates Connection object.
     */
    @Override
    public void run() {
        
        while (true) {
            try {
                Socket sockClient = ss.accept();
                in = sc.getInputStream();
                out = sc.getOutputStream();
                this.readClientMsg();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    } // end run.    
    
    /**
     * Thread One: sends a message to the Client called by the Server.
     *
     * @throws IOException.
     */
    public void sendServerMsg(String msg) {
        //DEBUG
        System.out.println("This is the Connection-ServerModel from server: " + msg);

        byte[] bufferOut = msg.getBytes();
        //Send the message to the Client
        try {
            out.write(bufferOut);
            out.flush();
        } catch (IOException e) {
            System.out.println("Connection is closed!");
            //e.printStackTrace();
            //close();
        }
    } // end sendServerMsg.
    
    /**
     * Thread Two: + Reads a message from the Client + Send server message back.
     * to the Client
     */
    public void readClientMsg() {

        String info = null;

        try {

            bufferIn = new byte[in.available()];
            int len = in.read(bufferIn);
           
            if (len > 0) {
                info = new String(bufferIn, 0, len);
                //insert check move methods to confirm valid move or if it is a win
                
                //Need to validate the info before send the information
                this.sendServerMsg(info);//
            }
         } // Catch the error excepion then close the connection
        catch (IOException e) {
            System.out.println("Connection is closed!");
            e.printStackTrace();
            //close();
        }
    }// End readClientMsg.
    
    /**
     * Calls run() in the new Thread.
     */
    public void listen() {
        worker = new Thread(this);
        worker.start();
    } // end listen.
    
    /*    
    public void run() {
       while(true){
           try {
               Socket sockClient = ss.accept(); // check later   
               in = sc.getInputStream();
               
               listen();
           } catch(IOException e) {
           e.printStackTrace();
           }        
       }
    } // end run
    */
         
    public void readPlayerMsg(){
         bufferIn = new byte[BYTE_SIZE];
         
         
     }
    
    
    public void listen1(){
        while (terminate == true) {
            readPlayerMsg();
        }
            try {
                ss.close();
            } catch (IOException ex) {
                Logger.getLogger(GameModel.class.getName()).log(Level.SEVERE, null, ex);
            }

    }
         public void sendGameSocket(String IP, String Port){
             int intPort =  Integer.parseInt(Port);
             try {
                SocketClient gameSock = new SocketClient(IP, intPort);
            } catch (IOException ex) {
                Logger.getLogger(GameModel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
         }
     
         
         
         
        //this method is our string listen that is listening for a 
         //string to be send to us to inform us what move was made
    
        
        
        
        
        
	
	public void changeSize(int newSize){
		SIZE = newSize;
		
	}
	
	public void fillBoard(){
		for(int i = 0; i<SIZE; i++){
			for(int j = 0; j < SIZE; j++){
				board[i][j] = 0;
			}
		}
		// board filled with 0's
		
		
	} // end fill board method
	
        //
	public void markBoard(int playerNum, int row, int col){
		if(playerNum == 1)
			board[row][col] = X;
		else
			board[row][col] = O;
		
	}// end mark board method
	
	public boolean validMove(int row, int col){
		if(board[row][col]==0)
			return true;
		else
		return false;
	}

    
   
	
}
