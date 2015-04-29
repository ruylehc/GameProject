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
    public String userName = "";
    public int port = -1; 
    //End declare variable

    /**
     * Add the new controller to the ArrayList
     *
     * @param newController
     */
    public void addController(Controller newController) {
        list.add(newController);
    }
    
    public void runTCP(){
        try {
            sock = new SocketClient();
            sock.setModel(this);
            sock.createListener();
        } catch (IOException ex) {
            Logger.getLogger(ClientModel.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public void runGameServer(){
        try {
            
            port = sock.s.getPort() + 5;
            //gmodel = new GameModel(port);
            gmodel = new GameModel(port); // kevins implement of using server sock port
            gmodel.setController(contGame);
            gmodel.listen();
        } catch (IOException ex) {
            Logger.getLogger(ClientModel.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            } else if (c.ID.equals("GameCtrl")) {
                contGame = (GameCont) c;
                contGame.setTitle(userName);
            }
            else if (c.ID.equals("MatchCtrl") && gameMode == true) {
                contMatch = (MatchCont) c;
                contMatch.setVisible(false);
            }
            c.switchView(signal);
        }
    } // end switchController
    
    public void setTitle(String usr){
        for (Controller c : list) {
            if (c.ID.equals("MatchCtrl") && isValid == true) {
                contMatch = (MatchCont) c;
                contMatch.setTitle(usr);
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

    public int findOpenPort() {
        for (int i = 1; i < 65000; i++) {
            try {
                ServerSocket test = new ServerSocket(i);
                test.close();
                port = i;
                break;
            } catch (IOException e) {
                System.out.println("Could not listen on port: " + i);
                e.printStackTrace();
    // ...
            }
        }
        return port; 
    }
    
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
                break;
                
            default:
                JOptionPane.showMessageDialog(null, msg);   //Display server messages if failed login or register
                break;

        }

        /*
         if (split[0].equals("chat")) {
         chat = true;
         String chatMsg = split[1];
         this.update(chatMsg + "\n");
         } 
         //Switch to the lobby if login or register successful.
         else if (msg.equals("loginSuccess") || msg.equals("registerSuccess")) {
         temp = true;
         isValid = temp;
         this.switchController("lobby");

         } 
         //Display the server error message.
         else if (temp == false)
         JOptionPane.showMessageDialog(null, msg);   //Display server messages if failed login or register
         */
    } // end updateServerMsg            
    
    
    public void lateAccept(){
        contMatch.lateAcceptDisplay();
    }
    
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
        gmodel = new GameModel(split[4], split[3]);
        gmodel.setController(contGame);
        //gmodel.startGame(split[4], split[3]);

    }
   
    public void weAccept(String acceptedStatus) { //sending up an accept msg from our view to the server
        String[] split = msg.split("_");
        String aggregate = new String(split[0] + "_" + userName + "_" + split[1]);
        sendUserInfo(aggregate);
    }

    public void setSock(SocketClient sock) {
        this.sock = sock;
    } // end setSock

}
