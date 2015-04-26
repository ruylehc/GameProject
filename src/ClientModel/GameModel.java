package ClientModel;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


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
        private  ServerSocket ss;
        private SocketClient sc; 
	
	
	// These methods handle connecting to the other class
        // this method needs to be made by a MMcontroller with the
        // gmodel.run() when the use hits the accept button
         public void run() {
       while(true){
           try {
               Socket sockClient = ss.accept(); // check later   
              listen();
           } catch(IOException e) {
           e.printStackTrace();
       }     
           
       }
    }
     
         
         
         
        //this method is our string listen that is listening for a 
         //string to be send to us to inform us what move was made
      public void listen(){
          
      }  
        
        
        
        
        
	
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
