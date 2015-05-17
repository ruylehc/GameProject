
package ClientModel;

import Controller.GameCont;
import GUIView.GameBoard;
import java.awt.Graphics;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.*;
import javax.swing.JOptionPane;

public class GameModel{ // game model no longer implments runnable since it only has one connections

    //
    //////////////////Board//////////////////////////////
    public int[][] board;
    private int rows;
    private int cols;
    private double cellW;
    private double cellH;
    public int SIZE = 30;   //Default board size might be 30.
    int difficulty;
    
   
    private static final int BUFFER = 4;
    ///////////////////end Board/////////////////////////
    
    
     /////////////////// AI variables ///////////////////
    public AI ai;
    int aiMarker;
    public boolean aiGame = false; 
    
    
    ////////////////////Player///////////////////////////
    boolean p1Win = false;
    boolean p2Win = false;
    boolean p1turn;
    boolean p2turn;
    boolean p1turncounter; //access this boolean to know if p1 moves
    boolean p2turncounter; //access this boolean to know if p2 moves
    int counter = 2;
    int P1 = 1;
    int P2 = 2;
    int PlayerNum = -1; // this holds the value of what player number you are
    int X = 1; // player 1 (host) mark
    int O = 2; // player 2 mark
    ////////////////////end-Player///////////////////////
    
    //////////////////Socket-&-Server////////////////////
    private ServerSocket ss;
    public Socket sock;
    private byte[] buffer;
    private final int BYTE_SIZE = 1024;
    private boolean terminate = false;
    private InputStream in;
    private OutputStream out;
    private int port;
    ///////////////////////////////////////////////////
    
    private ClientModel cmodel;
    private GameCont contGame;
    private String userID = "undef";
    private boolean start = false;
    boolean turn;

    // These methods handle connecting to the other class
    // This method needs to be made by a MMcontroller with the
    // gmodel.run() when the use hits the accept button
    /**
     * Default Constructor1 - Create the new socket to connect to serverSocket
     * on other side.
     *
     * @throws IOException.
     */
    public void createSocket(String IP, String Port) {
        terminate = false;
        int intPort = Integer.parseInt(Port);
        buffer = new byte[BYTE_SIZE];
        (new Thread() {
            @Override
            public void run() {
                try {
                    //SocketClient gameSock = new SocketClient(IP, intPort); changing from a socketclient to a normal Socket
                    sock = new Socket(IP, intPort);
                    in = sock.getInputStream();
                    out = sock.getOutputStream();

                    //Waiting and reading the meassage from the server side
                    while (terminate == false) {
                        readClientMsg();
                    }
                    //close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        fillBoard();    //Call the default contructor for board game - 2D array variable
    }//end createSocket

    /**
     * Default Constructor2 - Create the new server at assigned port.
     *
     * @throws IOException.
     */
    public void createServer(int port) {
        terminate = false;
        buffer = new byte[BYTE_SIZE];
        this.port = port;
        (new Thread() {
            @Override
            public void run() {
                try {
                    ss = new ServerSocket(port);
                    sock = ss.accept();
                    in = sock.getInputStream();
                    out = sock.getOutputStream();
                    
                    //Waiting and reading the message from the socket side
                    while (terminate == false) {
                        readClientMsg();
                    }
                    
                } catch (IOException e) {
                    e.printStackTrace();
                    
                }
            }
        }).start();
        fillBoard();    //Call the default contructor for board game - 2D array variable
    }// end createServer.    
    
    /**
     * method sets the controller for game model
     *
     * @param gamecontroller
     */
    public void setController(GameCont gamecontroller) {
        this.contGame = gamecontroller;
    }//end setController.

    /**
     * Set the specify user name to this model.
     * @param userID 
     */
    public void setUserID(String userID, int playerNum){
        this.PlayerNum = playerNum;
        this.userID = userID;
        this.contGame.setTitle(userID);
    }// end setUserID
    
    /**
     * Set the specify user name to this model.
     * @param userID 
     */
    public String getUserID(){
        return userID;
    }// end setUserID
    
    /**
     * Close the I/O stream.
     */
    public void close() {
        try {
            this.SIZE = 30;
            out.close();
            in.close();
            terminate = true;
            start = false;
            turn = false;
            if(PlayerNum==1)
                ss.close();
            else
                sock.close();
            this.setTurn();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }// end closeServer.
    
    /**
     *sends a message to the other player (client) 
     *
     * @throws IOException.
     */
    public synchronized void sendMsg(String msg) {
        //DEBUG
        System.out.println("This is the GameModel send - write to Output Stream: "+ this.getUserID()+"-" + msg);

        byte[] bufferOut = msg.getBytes();
        //Send the message to the Client
        try {
            out.write(bufferOut);
            out.flush();
            if(msg.equals("quit")){
                contGame.listen("lobby");
                terminate = true;
                close();
            }
        } catch (IOException e) {
            System.out.println("Connection is closed! Cannot execute send message!");
            e.printStackTrace();
        }
    } // end sendServerMsg.

    /**
     * This method will read from the TCP waiting for a msg from the other player
     */
    public synchronized void readClientMsg() {

        String info = null;

        try {
            
            
            buffer = new byte[in.available()];
            
            int len = in.read(buffer);
            int playerNum, row, col;
            if (len > 0) {
                info = new String(buffer, 0, len);
                System.out.println("Received: "+ info);
                String[] split = info.split("_");	//String delimiter. 
                String type = split[0];

                switch (type) {  //these switch statements need to be implented for game play
                    case "move":
                        playerNum = Integer.parseInt(split[1]);
                        row =  Integer.parseInt(split[2]);
                        col = Integer.parseInt(split[3]);
                        turn = Boolean.parseBoolean(split[4]);
                        this.markBoard(playerNum, row, col);
                        this.drawBoard();
                        this.setTurn();
                        break;
                        
                    case "win":
                        playerNum = Integer.parseInt(split[1]);
                        row =  Integer.parseInt(split[2]);
                        col = Integer.parseInt(split[3]);
                        turn = Boolean.parseBoolean(split[4]);
                        this.markBoard(playerNum, row, col);
                        this.drawBoard();
                        JOptionPane.showMessageDialog(null, "You have lost!");
                        contGame.listen("lobby");
                        close();
                        break;

                    case "tie":

                        break;
                        
                    case "quit":
                        handleQuit();
                        break;
                    
                    case "close":
                        close();
                        break;
                        
                    case "chat":
                        contGame.updateModelMsg(split[1]);
                        break;
                        
                    case "size":                        
                        this.SIZE = Integer.parseInt(split[1]);
                        this.fillBoard();
                        this.drawBoard();
                        break;
                        
                    case "start":
                        start = true;
                        turn = Boolean.parseBoolean(split[1]);
                        this.setTurn();
                        break;
                        
                    case "turn":
                        JOptionPane.showMessageDialog(null,split[1]);
                        break;
                    default:

                        break;
                }

                //Need to validate the info before send the information
                // this.sendMsg(info);// we don't need to send information back immediately but rather wait till the player makes their move
            }
        } // Catch the error excepion then close the connection
        catch (IOException e) {
            System.out.println("Connection is closed! Cannot execute read client message");
            e.printStackTrace();
        }
    }// End readClientMsg.    

    /**
     * method changes the size of the game board if they players so desire
     *
     * @param newSize new size to change the board to
     */
    public void changeSize(int newSize) {
        if(PlayerNum == 1 && !start){
            SIZE = newSize;
            fillBoard();
            drawBoard();
            this.sendMsg("size_"+ newSize);
            System.out.println("size_"+ newSize);
        }else if(PlayerNum == 1 && start)
            JOptionPane.showMessageDialog(null,"Game is running!");
        else if(this.PlayerNum == 2)
            JOptionPane.showMessageDialog(null,"You do not have the game option");
    }

    /**
     * fills the board with 0's which stand for an empty square, initializes the
     * board for a new game
     */
    public void fillBoard() {
        //DEBUG
        System.out.println("Board JPanel is actived");
        rows = cols = SIZE;
        board = new int[rows][cols];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = 0;
            }
        }
        // board filled with 0's

    } // end fill board method

    /**
     * method marks the board for a given player move
     *
     * @param playerNum - whether the player is first or second player
     * @param row - the row they clicked
     * @param col the col they clicked
     */
    public void markBoard(int playerNum, int row, int col) {
        if (playerNum == 1) {
            board[row][col] = X;
        } else {
            board[row][col] = O;
        }
    }// end mark board method
    
    /**
     * method marks the board for a given player move
     *
     * @param playerNum - whether the player is first or second player
     * @param row - the row they clicked
     * @param col the col they clicked
     */
    public void eraseBoard(int row, int col) {
       board[row][col] = 0;
    }// end mark board method

    /**
     * method validates the move by checking if the square clicked is empty
     *
     * @param row - row clicked
     * @param col - col clicked
     * @return
     */
    public boolean validMove(int row, int col) {
        if (board[row][col] == 0) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null,"Please choose a valid move!");
            return false;
        }
    }
    
    /**
     * Update the turn label to the view
     * Display opponent turn - offender
     * Display defender turn
     */
    public void setTurn() {
        if(terminate == true)
            contGame.updatePlayerTurn("--waiting--");
        else if (turn == true)            
            contGame.updatePlayerTurn("Your Turn!");
        else
            contGame.updatePlayerTurn("Opponent Turn!");
    }
    /**
     * Draws this Board using the given Graphics object.
     *
     * @param g a Graphics object
     * @param w the width of the canvas in pixels.
     * @param h the height of the canvas in pixels.
     */
    public void draw(Graphics g, int w, int h) {
        //DEBUG
        System.out.println("model - raw - activie");
        cellW = (double) w / cols;
        cellH = (double) h / rows;
        int cellWi = (int) Math.round(cellW);
        int cellHi = (int) Math.round(cellH);

        int x, y;

        g.fillRect(0, 0, w, h);
        g.setColor(Color.black);
        for (int r = 0; r < rows; r++) {
            x = (int) (r * cellH);
            g.drawLine(0, x, w, x);

            for (int c = 0; c < cols; c++) {
                y = (int) (c * cellW);
                g.drawLine(y, 0, y, h);
                int cell = board[r][c];
                if (cell == 1) {    //Preresent for play are 1
                    g.setColor(Color.blue);
                    g.fillOval(x + BUFFER, y + BUFFER, cellWi - 2 * BUFFER, cellHi - 2 * BUFFER);
                }
                if (cell == 2) {   //Preresent for play are 2
                    g.setColor(Color.red);
                    g.fillOval(x + BUFFER, y + BUFFER, cellWi - 2 * BUFFER, cellHi - 2 * BUFFER);
                }
                g.setColor(Color.black);
            }
        }
    }

    /**
     * Update the game board 
     */
    public void drawBoard(){
        //DEBUG
        System.out.println("draw board is actived");
        contGame.updateBoard();
    }
    
    /**
     * this method updates the moveCounter every time a move is made
     * 
     */
    public void updateMoveCounter() {

            if (counter % 2 == 0) {
                p1turncounter = true;
                p2turncounter = false;
                counter++;
            } else {
                p1turncounter = false;
                p2turncounter = true;
                counter++;
            }
    }

     
    
    /**
     * Decides which player gets to go first
     * 
     */
    public void flipCoin() {
        int max = 10;
        int min = 1;
        int result;
        result = 0;

        while (result == 0) {
            Integer p1Flip = (int) (min + Math.random() * (max - min) + 1);
            Integer p2Flip = (int) (min + Math.random() * (max - min) + 1);

            result = p1Flip.compareTo(p2Flip);

            if (result == -1) {
                turn = true;
            } else {
                turn = false;
            }
        } //while
        System.out.println(turn);
    }
    
    /**
     * Handle player move and click.
     * Modifies and displays the win and lose  
     * @param row
     * @param col
     * @param count Mouse Click counter
     */
    public void executeClick(int row, int col, int count) {
        //Taking the 2D array coordinate from the JPanel pixels. 
        int[][] temp = board;
        row = (int) (row / this.cellH);
        col = (int) (col / this.cellW);
        if (start == true) {
            if (turn == true) {
                if (validMove(row, col) == true) {                   
                        this.markBoard(PlayerNum, row, col);
                        
                    this.drawBoard();             
                    
                    if (count == 1) {
                        this.eraseBoard(row, col);
                        board = temp;
                    } else {
                        if (this.checkWin(board, PlayerNum) == true) {
                            if (!aiGame) {
                                this.sendMsg("win_" + this.PlayerNum + "_" + row + "_" + col + "_" + turn);
                            } // skipps this step if you are playing an AI
                            this.handleWin();
                        } else { //not win case
                            if (!aiGame) {
                                this.sendMsg("move_" + this.PlayerNum + "_" + row + "_" + col + "_" + turn);
                            } else {
                                turn = false;
                                this.setTurn();
                                ai.aiMove(board);
                                this.drawBoard(); // this method calls all functions needed for AI to make a move
                                
                                if(checkWin(board, P2)){ // this AI is always player 2
                                    handleAIWin();
                                }
                                
                                turn = true;
                                this.setTurn();
                            }
                            //Execute this if not AI Game Mode.
                            if (!aiGame) {                                
                                turn = false;
                                this.setTurn();
                            }
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "It is not your turn!\nPlease wait!");
            }
        } else {
            if (this.PlayerNum == 2) {
                JOptionPane.showMessageDialog(null, "Game was not start!\nPlease wait!");
            } else {
                JOptionPane.showMessageDialog(null, "Game was not start!\nPlease start the game!");
            }
        }
    }

    
    
    /**
     * based on the size of the board it calculated how many turns it would take to fill the board
     * it waits till the last move to check since a player could win in the last move
     */
    public boolean checkTie() {
        if (counter >= ((SIZE) * (SIZE) +2)) {
            handleTie();
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * method takes in a move and checks if the move resulted in a win
     * @param theboard - the 2d array board passed to be checked
     * @param playerToken - 1 or 2 to identify the player
     * @return 
     */
    public boolean checkWin(int[][] theboard, int playerToken) {
        if (checkRow(theboard, playerToken) || checkCol(theboard, playerToken) || checkDiag(theboard, playerToken)) {
            return true;
        }
        return false;
    }

    /**
     * checks the Rows of the board to see if there is 5 in a row
     *
     * @param theboard the 2d array to be scanned
     * @return true if the board contains 5 in a row
     */
    public boolean checkRow(int[][] theboard, int playerToken) {

        int size = theboard.length;

        for (int r = 0; r < size; r++) { // moves through the rows once col -4 have been checked
            for (int c = 0; c < size - 4; c++) {
                if (theboard[r][c] == playerToken && theboard[r][c + 1] == playerToken && theboard[r][c + 2] == playerToken
                        && theboard[r][c + 3] == playerToken && theboard[r][c + 4] == playerToken) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * checks the Cols of the board to see if there is 5 in a row
     * @param theboard the 2d array to be scanned
     * @return true if the board contains 5 in a row
     */   
    public boolean checkCol(int[][] theboard, int playerToken) {

        int size = theboard.length;

        for (int c = 0; c < size; c++) { // moves through the rows once col -4 have been checked
            for (int r = 0; r < size - r; r++) {
                if (theboard[r][c] == playerToken && theboard[r + 1][c] == playerToken && theboard[r + 2][c] == playerToken
                        && theboard[r + 3][c] == playerToken && theboard[r + 4][c] == playerToken) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * checks the diagonals of the board to see if there is 5 in a row
     *
     * @param theboard the 2d array to be scanned
     * @return true if the board contains 5 in a row
     */
    public boolean checkDiag(int[][] theboard, int playerToken) {

        int size = theboard.length;

        for (int r = 0; r < size - 4; r++) { // moves through the rows once col -4 have been checked
            for (int c = 0; c < size - 4; c++) {
                if (theboard[r][c] == playerToken && theboard[r + 1][c + 1] == playerToken && theboard[r + 2][c + 2] == playerToken
                        && theboard[r + 3][c + 3] == playerToken && theboard[r + 4][c + 4] == playerToken) {
                    return true;
                    // this checks the left and down diagnoal only when we are on the 4th col or further
                } else if (c > 3 && theboard[r][c] == playerToken && theboard[r + 1][c - 1] == playerToken
                        && theboard[r + 2][c - 2] == playerToken && theboard[r + 3][c - 3] == playerToken
                        && theboard[r + 4][c - 4] == playerToken) {
                    return true;
                }
            }
        }
        return false;
    }

    
     /**
     * This method handles the case when the AI wins, Since we have no offense we assume it will be rare
     * but just in case it is here
     */
    public void handleAIWin() {
        //cmodel.sendUserInfo("stats_" + cmodel.userName + "_win");
        // need code to exit us from the game and display the lobby view/ is below code enough?
        JOptionPane.showMessageDialog(null,"You have lost to the AI; continue practicing! You need it");
        
            contGame.listen("startUp");
            reset();
        
    }
    /**
     * handles when the player whens against an AI or another player
     */
    public void handleWin() {
        //cmodel.sendUserInfo("stats_" + cmodel.userName + "_win");
        // need code to exit us from the game and display the lobby view/ is below code enough?
        JOptionPane.showMessageDialog(null,"Congrats! You are victorious!");
        if(aiGame == true){
            contGame.listen("startUp");
            reset();
        }
        else{
        contGame.listen("lobby");
        turn = false;
        close();
        } // end else
    }

    /**
     * if the other player quit it pops up a message and kicks you back to the lobby
     */
    public void handleQuit() {
        JOptionPane.showMessageDialog(null, "They quit!");
        contGame.listen("lobby");
        close();        
    }

    /**
     * if in the rare case there is a Tie is displays Tie and returns both users to the lobby
     */
    public void handleTie() {
        JOptionPane.showMessageDialog(null, "Tie!");
        contGame.listen("lobby");
        
        try {
            sock.close();
        } catch (IOException ex) {
            Logger.getLogger(GameModel.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    /**
     * Start the game
     * @param start the start to set
     */
    public void setStart(boolean start) {
        if(sock == null){
           startAIGame();          
        }
        else if(this.PlayerNum == 1 && !this.start && sock != null){
            this.start = start;
            this.flipCoin();
            this.sendMsg("start_" + !turn);
            if(turn == true){
                this.setTurn();
                JOptionPane.showMessageDialog(null,"It is your turn.");
            }
            else
                sendMsg("turn_It is your turn");
            this.updateMoveCounter();            
        }        
        else
            JOptionPane.showMessageDialog(null,"You do not have the game option");
    }
    
    /**
     * sets the client model to game model so that can communicate
     * @param cmodel 
     */
    public void setSubModel(ClientModel cmodel){
        this.cmodel = cmodel;
    }

    /**
     * method passes the difficulty to the AI 
     * @param info 
     */
    public void setDifficulty(String info) {
        System.out.println("this is the game model displaying difficulty: " + info );
        ai.setDifficulty(info);
    }
    
    /**
     * this is a special case start game so it doesn't do socket communication
     */
    public void startAIGame(){
        aiMarker = P2;
        turn = true;  // sets the first turn to the human
        aiGame = true;  // tells the gamemodel it is a AI game so it doesnt use socket
        start = true;
        PlayerNum = P1;
        fillBoard();
        drawBoard();
           
   
    }   

    /**
     * Connects the AI to the model
     * @param ai 
     */
    public void setAI(AI ai) {
       this.ai = ai;
    }

    // resets all variables that are changed when user is in a game back to non game status
    public void reset() {
       turn = false;
       aiGame = false; 
       PlayerNum = -1; 
       start = false; 
       fillBoard();
       drawBoard();       
    }
}
