package ClientModel;

import Controller.GameCont;
import GUIView.GameBoard;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.*;
import javax.swing.JOptionPane;

public class GameModel implements Runnable {

    //
    //////////////////Board///////////////////
    public int[][] board;
    private int rows;
    private int cols;
    public int SIZE = 30;   //Default board size might be 30.
    
    private static final int BUFFER = 4;
    /////////////////////////////////////////
    boolean p1Win = false;
    boolean p2Win = false;
    boolean p1turn;
    boolean p2turn;
    boolean p1turncounter; //access this boolean to know if p1 moves
    boolean p2turncounter; //access this boolean to know if p2 moves
    int counter = 2;
    int p1Num = 1;
    int p2Num = 2;
    int X = 1; // player 1 (host) mark
    int O = -1; // player 2 mark
    private Thread worker;
    private ServerSocket ss;
    public Socket sc;
    private byte[] bufferIn;
    private final int BYTE_SIZE = 1024;
    private boolean terminate = true;
    private InputStream in;
    private OutputStream out;
    private int port;
    private ClientModel cmodel;
    private GameCont contGame;
   // private GameBoard gameB = new GameBoard();

    // These methods handle connecting to the other class
    // This method needs to be made by a MMcontroller with the
    // gmodel.run() when the use hits the accept button
    /**
     * Default Constructor1 - Create the new server at assigned port.
     *
     * @throws IOException.
     */
    public GameModel(String IP, String Port) {
        int intPort = Integer.parseInt(Port);
        try {
            SocketClient gameSock = new SocketClient(IP, intPort);
        } catch (IOException ex) {
            Logger.getLogger(GameModel.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        
        fillBoard();
        
    }//end Socket Client.

    /**
     * method sets the controller for game model
     *
     * @param gamecontroller
     */
    public void setController(GameCont gamecontroller) {
        this.contGame = gamecontroller;
    }

    /**
     * Default Constructor2 - Create the new server at assigned port.
     *
     * @throws IOException.
     */
    public GameModel(int port) {
        bufferIn = new byte[BYTE_SIZE];
        this.port = port;
        try {
            ss = new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(GameModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        fillBoard();
    }
    /**
     * Close the I/O stream.
     */
    public void close() {
        try {
            out.close();
            in.close();
            sc.close();
            terminate = false;
            //active = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }// end close.
    /**
     * Accepts connections and creates Connection object.
     */
    @Override
    public void run() {

        while (terminate == true) {
            try {
                Socket sockClient = ss.accept();
                in = sockClient.getInputStream();
                out = sockClient.getOutputStream();
                this.readClientMsg();
            } catch (IOException e) {
                e.printStackTrace();
                close();
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
            handleQuit();
            System.out.println("Connection is closed!");
            //e.printStackTrace();
            //close();
        }
    } // end sendServerMsg.

    /**
     * this method is our string listen that is listening for a 
     * string to be send to us to inform us what move was made
     * Thread Two: + Reads a message from the Client 
     *             + Send server message back to the Client
     */
    public void readClientMsg() {

        String info = null;

        try {

            bufferIn = new byte[in.available()];
            int len = in.read(bufferIn);

            if (len > 0) {
                info = new String(bufferIn, 0, len);

                String[] split = info.split("_");	//String delimiter. 
                String type = split[0];

                switch (type) {  //these switch statements need to be implented for game play
                    case "move":
                        int row =  Integer.parseInt(split[2]);
                        int col = Integer.parseInt(split[3]);
                        
                        break;
                        
                    case "win":

                        break;

                    case "tie":

                        break;
                        
                    case "quit":
                        handleQuit();
                        break;
                    
                    case "close":
                        close();
                        break;
                        
                    default:

                        break;
                }

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

    /**
     * this method starts the game by sending a socket to the client server and
     * then switches views to the game board
     *
     * @param ip - IP of the server socket that was created
     * @param port -- available port passed by server socket
     */
    public void startGame(String ip, String port) {
        sendGameSocket(ip, port);
        cmodel.switchController("gameBoard");

    }

    /**
     * method creates a socket to the opened Server socket created by the other
     * players GameModel
     *
     * @param IP
     * @param Port
     */
    public void sendGameSocket(String IP, String Port) {
        int intPort = Integer.parseInt(Port);
        try {
            SocketClient gameSock = new SocketClient(IP, intPort);
        } catch (IOException ex) {
            Logger.getLogger(GameModel.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

    }

    /**
     * method changes the size of the game board if they players so desire
     *
     * @param newSize new size to change the board to
     */
    public void changeSize(int newSize) {
        SIZE = newSize;

    }

    /**
     * fills the board with 0's which stand for an empty square, initializes the
     * board for a new game
     */
    public void fillBoard() {
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
            return false;
        }
    }
    
    /**
     * Draws this Board using the given Graphics object.
     *
     * @param g a Graphics object
     * @param w the width of the canvas in pixels.
     * @param h the height of the canvas in pixels.
     */
    public void draw(Graphics g, int w, int h) {
        double cellW = (double) w / cols;
        double cellH = (double) h / rows;
        int cellWi = (int) Math.round(cellW);
        int cellHi = (int) Math.round(cellH);

        int x, y;
        g.setColor(Color.white);
        g.fillRect(0, 0, w, h);
        g.setColor(Color.black);
        //g.drawLine(w, 0, w, h);
        //g.drawLine(0, h, w, h);
        for (int r = 0; r <= rows; r++) {
            y = (int) (r * cellH);
            g.drawLine(0, y, w, y);

            for (int c = 0; c <= cols; c++) {
                x = (int) (c * cellW);
                g.drawLine(x, 0, x, h);
                int cell = board[r][c];
                if (cell == 1) {    //Preresent for play are 1
                    g.setColor(Color.blue);
                    g.fillOval(x + BUFFER, y + BUFFER, cellWi - 2 * BUFFER, cellHi - 2 * BUFFER);
                }
                if (cell == -1) {   //Preresent for play are 2
                    g.setColor(Color.red);
                    g.fillOval(x + BUFFER, y + BUFFER, cellWi - 2 * BUFFER, cellHi - 2 * BUFFER);
                }
                g.setColor(Color.black);
            }
        }
    }

    /**
     * this method updates the moveCounter every time a move is made
     * 
     */
    public void updateMoveCounter() {
        if (p1turn = true) {
            if (counter % 2 == 0) {
                p1turncounter = true;
                p2turncounter = false;
                counter++;
            } else {
                p1turncounter = false;
                p2turncounter = true;
                counter++;
            }
        } else if (p2turn = true) {

            if (counter % 2 == 0) {
                p2turncounter = true;
                p1turncounter = false;
                counter++;
            } else {
                p2turncounter = false;
                p1turncounter = true;
                counter++;
            }

        }
    }

    //Decide which player gets to go first
    //Not sure if we need the parameters of string user1 and user2, 
    //p1turn and p2turn are global variables so I was thinking of just updating those 
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
                p2turn = true;
            } else {
                p1turn = true;
            }
        } //while

    }
/**
 * this method validates and draws the move; used when receiving a new move
 * @param row the row the move was made in
 * @param col the col the move was made in
 * @param playerToken the player token value either 1 or 2
 *  player token - 1 is for "home player" or you, 2 is for opponent 
 */
    public boolean validateOppMove(int row, int col, int playerToken){
      if(row<SIZE && col <SIZE && row >-1 && col > -1){  // checks the boundarys of the board
        if(validMove(row, col)){ // checks if the move location has already been taken
            if(p2turncounter == true){
                markBoard(playerToken, row, col);
                drawBoard(playerToken, row, col);
                return true; 
            }
            checkTie();
        }
      }
      return false;
    } // end validateMove
    
    
    /**
 * this method validates and draws the move; used to validate our move
 * @param row the row the move was made in
 * @param col the col the move was made in
 * @param playerToken the player token value either 1 or 2
 *  player token - 1 is for "home player" or you, 2 is for opponent 
 */
    public boolean validateOurMove(int row, int col, int playerToken){
      if(row<SIZE && col <SIZE && row >-1 && col > -1){  // checks the boundarys of the board
        if(validMove(row, col)){ // checks if the move location has already been taken
            if(p1turncounter == true){
                markBoard(playerToken, row, col);
                drawBoard(playerToken, row, col);
                return true; 
            }
            checkTie();
        }
      }
      return false;
    } // end validateMove
    
    
    
    /**
     * errors are because rows and columns are not yet declared in the game
     * board class, want to wait see what you guys wanted to do
     */
    public boolean checkTie() {
        if (counter == ((SIZE) * (SIZE) +2)) {
            handleTie();
            return true;
        } else {
            return false;
        }
    }
    
    /*
     public void drawMove(Gameboard, int xCord, int yCord) {
     int jHeight = Jpanel.getHeight();
     int jWidth = Jpanel.getWidth();
     int gameSize = Gameboard.boardSize;

     yrectInc = (jHeight / gameSize);
     xrectInc = (jWidth / gameSize);

     topRect = (rectInc) * xCord;
     leftRect = (xrectInc) * yCord;

     //bottomRect = (topRect) - yrectInc;
     //rightRect = (leftRect) + xrectInc;
     GameBoard.gui.fillRect(leftRect + 2, xrectInc - 2, topRect + 2, yrectInc + 2);
     }
*/
    
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

    public void pause() {

    }

    public void gameStartClock() {

    }

    public void handleWin() {
        cmodel.sendUserInfo("stats_" + cmodel.userName + "_win");
    }

    public void handleQuit() {
        JOptionPane.showMessageDialog(null, "They quit!");
        handleWin();
        //cmodel.switchController("lobby");
        close();
        /*
        try {
            sc.close();
        } catch (IOException ex) {
            Logger.getLogger(GameModel.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        */
    }

    public void handleTie() {
        JOptionPane.showMessageDialog(null, "Tie!");
        cmodel.switchController("lobby");
        
        try {
            sc.close();
        } catch (IOException ex) {
            Logger.getLogger(GameModel.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    private void drawBoard(int playerToken, int row, int col) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
