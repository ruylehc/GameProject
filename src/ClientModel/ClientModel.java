package ClientModel;

/**Group Names: Tyler Glass, Michael House, Holly Ruyle, Phu Hoang    
 * Project Part: Admin Model
 * Program Title: Tic-tac-toe Game 
 * Course: CSCE 320 - Software Engineering
 * Date: February 23, 2015
 * Language and Compiler: Java written in eclipse and Netbeans
 * Sources: CSCE 320 references - Trivial Java Example
 */

import Controller.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ClientModel {

    //Declare variable 
    private ArrayList<Controller> list = new ArrayList<Controller>();
    private String signal = "";
    private String usrInfo = "";
    private String msg = "";
    private SocketClient sock;
    private GameModel gmodel;

    private boolean isValid = false;
    private boolean chat = false;
    private boolean userList = false;
    private boolean invited = false;
    private boolean gameMode = false;
    
    private LoginCont contLog;
    private RegisterCont contReg;
    private MatchCont contMatch;
    private GameCont contGame;
    public String userName = "undef";
    public int port = -1; 
    public String IP = "";
    //End declare variable

    /**
     * Add the new controller to the ArrayList
     *
     * @param newController
     */
    public void addController(Controller newController) {
        list.add(newController);
    }// end addController.
    
    /**
     * Start the new TCP socket to connect to the client server socket
     */
    public void runTCP(){
        try {

            //sock = new SocketClient(IP,52546); //this line is used for starting from cmd line
            sock = new SocketClient(); // this line is used for non cmd line starting

            sock.setModel(this);
            sock.createListener();
        } catch (IOException ex) {
            Logger.getLogger(ClientModel.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    /**
     * Start the Game Server to listen for a socket from the 
     * client who invited you to a game
     */
    public void runGameServer() {
        //DEBUG
        System.out.println("Game server is created");
        port = sock.s.getPort() + 5;
        gmodel.createServer(port);     
        
    }// end 
    
    /**
     * this method sets the global IP for socket creation/ running from cmd line
     * @param IP string of the IP - taken from cmd line
     */
    public void setIp(String IP){
        this.IP = IP;
    }

    /**
     * Read the controller signal to switch to another controller and view
     *
     * @param signal - String signal
     */
    public void switchController(String signal) {
        this.signal = signal;
        //DEBUG
        System.out.println("This is the signal from the switch view: " + this.signal);

        for (Controller c : list) {
            if (c.ID.equals("LoginCtrl" ) && isValid == true) {
                contLog = (LoginCont) c;
                contLog.setVisible(false);
            } else if (c.ID.equals("RegCtrl") && isValid == true) {
                contReg = (RegisterCont) c;
                contReg.setVisible(false);
            } else if (c.ID.equals("GameCtrl") && gameMode == true) {
                contGame = (GameCont) c;
                contGame.setVisible(false);
            } else if (c.ID.equals("MatchCtrl") && gameMode == true) {
                contMatch = (MatchCont) c;
                contMatch.setVisible(false);
            } c.switchView(signal);
        }
    } // end switchController
    
    /**
     * sets the title of the lobby so we know the userName of the user
     * @param usr - the user name passed to it to append it to the title
     */
    public void setTitle(String usr){
        for (Controller c : list) {
            if (c.ID.equals("MatchCtrl") && isValid == true) {
                contMatch = (MatchCont) c;
                contMatch.setTitle(usr);
            }
            if (c.ID.equals("GameCtrl") && gameMode == true) {
                contGame = (GameCont) c;
                contGame.setTitle(usr);
            }
        }
    }
    /**
     * Update the chat server message into the MatchMaking controller.
     *
     * @param update - chat message from the server.
     */
    public void updateChat(String update) {
        //DEBUG
        System.out.println("update is active");

        for (Controller c : list) {
            if (c.ID.equals("MatchCtrl") && chat == true) {
                contMatch = (MatchCont) c;
                contMatch.updateModelMsg(update);
            }
        }
    } //end update
    
    /**
     * Update the online user list - server message into the MatchMaking controller.
     *
     * @param update - online user list from the server.
     */
    public void updateOnlineList(String update) {
        //DEBUG
        System.out.println("update is active");

        for (Controller c : list) {
            if (c.ID.equals("MatchCtrl") && userList == true) {
                contMatch = (MatchCont) c;
                contMatch.setAvailableList(update);
            }
        }
    } //end updateOnlineList. 

    /**
     * Write the user information into the I/O stream to the server
     *
     * @param usrInfo: user information from the GUI textfield.
     */
    public void sendUserInfo(String usrInfo) {

        try {
            
            sock.writeUserMessage(usrInfo);
            if (usrInfo.equals("close") || usrInfo.equals("back")) {	//Close the socket if user logout or disconnected 
                sock.close();
                sock = null;
                //System.exit(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }// end sendUserInfo

    
    
    /**
     * Update the server message
     *
     * @param msg - Server message from I/O stream
     */
    public void updateServerMsg(String msg) {

        String[] split = msg.split("_");
        //DEBUG
        System.out.println("This is client model recieving msg from server " + msg);

        boolean temp = false;

        //Display the chat message from multiple online user into the chat Text Area. 
        switch (split[0]) {
            case "chat":
                chat = true;
                String chatMsg = split[1];
                this.updateChat(chatMsg + "\n");
                break;
            case "loginSuccess":
                temp = true;
                isValid = temp;
                this.switchController("lobby");
                userName = split[1];
                this.setTitle(userName);
                break;
            case "registerSuccess":
                temp = true;
                isValid = temp;
                this.switchController("lobby");
                userName = split[1];
                this.setTitle(userName);
                break;
            case "guest":
                isValid = true;
                this.userName = split[1];
                this.setTitle(userName);
                break;
            case "invite":
                invited = true;
                String host = split[1];
                this.handleInvite(host);
                break;
            case "accept":
                System.out.println("this is the CModel on the recieving end " + msg);
                gameMode = true;
                handleAccept(msg);
                this.switchController("gameBoard");
                this.setTitle(userName);
                break;
            case "list":
                //DEBUG
                System.out.println("From the list of server: " + msg);
                userList = true;
                this.updateOnlineList(msg);
                break;
            case "lateAccept":
                lateAccept();
                // close socket here
                break;
            case "acceptSuccessful":
                gameMode = true;
                this.switchController("gameBoard");
                this.setTitle(userName);
                break;
            default:
                JOptionPane.showMessageDialog(null, msg);   //Display server messages if failed login or register
                break;

        }

    } // end updateServerMsg            

    /**
     * this method handles late expect by calling the match controller to
     * display a error message
     */
    public void lateAccept() {
        contMatch.lateAcceptDisplay();
    }

    /**
     * this method handles invite by passing it to the view
     *
     * @param inviteMsg the status string that is passed from the other client
     */
    public void handleInvite(String inviteMsg) { // handle received invite from server
        //contMatch.handleInviteView(inviteMsg);	 //pass to controller to handle view
        for (Controller c : list) {
            if (c.ID.equals("MatchCtrl") && invited == true) {
                contMatch = (MatchCont) c;
                contMatch.handleInviteView(inviteMsg);
            }
        }
    }

    /**
     * Connect the socket client into the model.
     *
     * @param sock Socket
     */
    public void handleAccept(String acceptMsg) { // handles an accept msg from the server (after we've sent an invite)
        String[] split = acceptMsg.split("_");
       // String invitedUser = split[0] ;
       // if(invitedUser)
        //runGameServer();
        System.out.println("this is the CModel on the recieving end " + acceptMsg);
        gmodel.createSocket(split[4], split[3]);
        gmodel.drawBoard();
        
        

    }

    /**
     * this method is a setter method for the socket client 
     * @param sock 
     */
    public void setSock(SocketClient sock) {
        this.sock = sock;
    } // end setSock
    
    public void addSubModel(GameModel model){
        this.gmodel = model;
    }

}
