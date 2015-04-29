package GameServer;

/**
 * Group Names: Tyler Glass, Michael House, Holly Ruyle, Phu Hoang. 
 * Project Part: The Socket - Echo Sever - Connection Thread. 
 * Program Title: Tic-tac-toe Game. 
 * Course: CSCE 320 - Software Engineering. 
 * Date: February 23, 2015.
 * Language and Compiler: Java written in eclipse and Netbeans. 
 * Sources: CSCE 320 references - Trivial Java Example.
 */

import java.net.*;
import java.io.*;

public class Connection extends Thread {

    // Variables declaration.
    private Authentication model;
    private Socket sock;
    private InputStream in;
    private OutputStream out;
    private final int BYTE_SIZE = 1024;
    private byte[] bufferIn;
    private Server ss;
    private int port;
    private boolean terminate = true;
    private boolean active = false;
    private String IP = "undef";
    private String userName = "undef";
    boolean inGame = false;
    // End of variables declaration.

    /**
     * Default Constructor.
     *
     * @param s - socket.
     * @param ss - server socket.
     * @throws IOException.
     */
    public Connection(Socket s, Server ss) throws IOException {
        bufferIn = new byte[BYTE_SIZE];
        this.ss = ss;
        this.sock = s;
        this.port = s.getPort();
        in = sock.getInputStream();
        out = sock.getOutputStream();
    } // End Default Connection.

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
            close();
        }
    } // end sendServerMsg.

    /**
     * Comment: this method is duplicate the previous readClient method
     *
     *
     *
     *
     * @param playerInvite / public void sendUserInvite(String playerInvite) {
     * String[] split = playerInvite.split("_");	//Delimiter the string String
     * type = split[0]; String SendTo = split[1]; String ip = "";
     *
     * if (type.equals("invite")) { if (ss.checkOnlineUser(SendTo)) // checks if
     * the player is online { ip = ss.getOnlineUser().get(SendTo); } } for
     * (Connection c : ss.list) // check to ensure it is not giving us a delay
     * (global variable) { if (c.getIP().equals(ip)) {
     * sendServerMsg(playerInvite); } } }
     */
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
                info = new String(bufferIn, 0, len);	//Reading user info to string.
                String[] split = info.split("_");	//String delimiter. 
                String type = split[0];

                String userName = "";
                String passWord = "";
                String rePassword = "";
                String chatMsg = "";

                //Sends server message back to login user. 
                if (type.equals("login")) {
                    if (split.length == 3) {
                        userName = split[1].toLowerCase();
                        passWord = split[2];
                    }

                    //String Validation of the user name and password.
                    String loginStatus = model.login(userName, passWord);
                    //DEBUG
                    System.out.println("This is the connection status from login: " + loginStatus);
                    if (loginStatus.equals("loginSuccess_"+userName)) {	//Set the user name into connection.
                        this.userName = userName;
                        active = true;
                    }
                    //DEBUG
                    System.out.println("login " + userName + ", " + passWord);
                    System.out.println("From connection, server side: " + loginStatus);
                    sendServerMsg(loginStatus);
                } //Close the connection.
                else if (type.equals("close")) {
                    close();
                } //Sends server message back to register user.
                else if (type.equals("register")) {
                    if (split.length == 4) {
                        userName = split[1].toLowerCase();
                        passWord = split[2];
                        rePassword = split[3];
                    }

                    //String Validation of the user name and password.
                    String registerStatus = model.registerUser(userName, passWord, rePassword);
                    if (registerStatus.equals("registerSuccess_"+userName)) {	//Set the user name into connection.
                        this.userName = userName;
                        active = true;
                    }
                    //DEBUG
                    System.out.println("register :" + userName + ", " + passWord);
                    sendServerMsg(registerStatus);

                } //Send server invitation message to one player.
                else if (type.equals("sInvite")) {
                    String userToBeInvited = split[1];
                    String msg = "invite_" + this.userName; //This String contains identity of "invite", and the inviter user name. 
                    if (ss.checkOnlineUser(userToBeInvited) == true)   //Checks if the player is online.
                        ss.sendInvitation(userToBeInvited, msg);
                    
                } //Send server invitation messge to all players.               
                else if(type.equals("mInvite")){
                    String msg = "invite_" + this.userName; //This String contains identity of "invite", and the inviter user name. 
                    ss.sendInvitation("all_"+this.userName, msg);
                    
                } //Send the chat message to all current connected user. 
                else if (type.equals("chat")) {
                    if (split.length == 2) ;
                        chatMsg = split[1];                  
                    String msg = "chat_" + this.getUserName() + ": " + chatMsg;
                    //DEBUG
                    System.out.println("This is the chat msg in run - connection: " + msg);
                    ss.broadcast(msg);
                } 
                else if (type.equals("accept")){
                    //inGame = true;
                    userName = split[2];
                    if (ss.checkOnlineUser(userName) == false){
                        sendServerMsg("lateAccept" + "_" + userName);
                    }
                    else{
                        
                        info += "_" + this.IP;
                        //DEBUG
                        System.out.println(this.IP); 
                        System.out.println("this is the connection info for accept " + info);
                        ss.sendInvitation(userName, info); //we can use sendInvitation for generic messages to specific users
                        sendServerMsg("acceptSuccessful_"); //send a successful accept condition to user to start board
                        inGame = true;
                    }   
                }
                if (active = true) //DEBUG later
                //Display the user online list everytime                
                {
                    ss.broadcast(ss.getOnlineUserList());
                }

            }

            /* } //Try to terminate the socket if the user exit, need to modify at the close signal from gui or user try to log out
             else if (sock.isClosed()) //add on today
             {
             terminate = false;				
             }
             */
        } // Catch the error excepion then close the connection
        catch (IOException e) {
            System.out.println("Connection is closed!");
            e.printStackTrace();
            close();
        }
    }// End readClientMsg.

    /**
     * Close the I/O stream.
     */
    public void close() {
        try {
            out.close();
            in.close();
            sock.close();
            terminate = false;
            active = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }// end close.

    /**
     * Run the Thread Two.
     */
    public void run() {
        while (terminate == true) {
            readClientMsg();
        }
        ss.remove(this);

    }// End run.	

    /**
     * Set the model into the Connection
     *
     * @param model
     */
    public void setModel(Authentication model) {
        this.model = model;
    } //end setModel.

    /**
     * Return the port from the client socket
     *
     * @return the port
     */
    public int getPort() {
        return port;
    } //end getPort.	

    /**
     * Return local variable IP to the string passed into the method.
     *
     * @return the IP address in String format for a specific connection.
     */
    public String getIP() {
        return IP;
    } //end getIP.

    /**
     * @param IP the IP to set.
     */
    public void setIP(String IP) {
        String newIP = IP.substring(1);
        //DEBUG
        System.out.println(newIP);
        this.IP = newIP;
    } //end setIP.

    /**
     * Return the socket object from the client socket.
     *
     * @return the port.
     */
    public Socket getSocket() {
        return sock;
    } //end getSocket.

    /**
     * @return the userName.
     */
    public String getUserName() {
        return userName;
    } // end getUserName.

    /**
     * Return the available of the player.
     *
     * @return returns true is the player is not in game, other wise returns
     * false.
     */
    public boolean isInGame() {
        return inGame;
    } // end isInGame.

}// End Class Connection

