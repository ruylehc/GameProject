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

    private boolean isValid = false;
    private boolean chat = false;
    private boolean userList = false;
    private LoginCont contLog;
    private RegisterCont contReg;
    private MatchCont contMatch;
    private String userName = "";
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
            if (c.ID.equals("LoginCtrl") && isValid == true) {
                contLog = (LoginCont) c;
                contLog.setVisible(false);
            } else if (c.ID.equals("RegCtrl") && isValid == true) {
                contReg = (RegisterCont) c;
                contReg.setVisible(false);
            }
            c.switchView(signal);
        }
    } // end switchController

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

    /*
     public void authenticateStatus() throws IOException{
     String rY = "rY"; //register success
     String rUN = "rUN"; //register username problem

     String LY = "lY"; //lY = login success
     String LUN = "1UN"; //lUN = login username problem
     String LUP = "1UP"; //1UP = login password incorrect
     String status; // sendUserInfo status
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
     * Update the server message
     *
     * @param msg - Server message from I/O stream
     */
    public void updateServerMsg(String msg) {

        String[] split = msg.split("_");
        //DEBUG
        System.out.println(msg);

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
                break;
            case "registerSuccess":
                temp = true;
                isValid = temp;
                this.switchController("lobby");
                break;
            case "invite":
                handleInvite(msg);
                break;
            case "accept":
                handleAccept(msg);
                break;
            case "list":
                //DEBUG
                System.out.println("From the list of server: " + msg);
                userList = true;
                this.updateOnlineList(msg);

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

    public void handleInvite(String inviteMsg) { // handle received invite from server
        contMatch.handleInviteView(inviteMsg);	 //pass to controller to handle view
    }

    /**
     * Connect the socket client into the model.
     *
     * @param sock Socket
     */
    public void handleAccept(String acceptMsg) { // handles an accept msg from the server (after we've sent an invite)
        String[] split = acceptMsg.split("_");
        startGame(split[3], split[4]);

    }

    public void weAccept(String acceptedStatus) { //sending up an accept msg from our view to the server
        String[] split = msg.split("_");
        String aggregate = new String(split[0] + "_" + userName + "_" + split[1]);
        sendUserInfo(aggregate);
    }

    public void setSock(SocketClient sock) {
        this.sock = sock;
    } // end setSock

    public void startGame(String internetAddress, String portNumber) { // startGame after we handleAccept above
        // write startGame method with invitee's network information
    }
	//Comment:
    // I dont think that this one is working for now because we already sent the information 
    // from the client through the sendUserMsg() , which means there is no need for this method
	/*
     public void sendSingleInvite(String info) {

     try {
     sock.writeUserMessage(info);
     } catch (IOException e) {
     // TODO Auto-generated catch block
     e.printStackTrace();
     }
     }

     public void sendMultpleInvite(String info) {

     }
     */
}
