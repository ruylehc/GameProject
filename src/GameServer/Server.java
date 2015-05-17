package GameServer;

/**
 * Group Names: Tyler Glass, Michael House, Holly Ruyle, Phu Hoang. 
 * Project Part: Echo Server. Program Title: Tic-tac-toe Game. 
 * Course: CSCE 320 - Software Engineering. 
 * Date: February 23, 2015. 
 * Language and Compiler: Java written in eclipse and Netbeans. 
 * Sources: CSCE 320 references - Trivial Java Example.
 */

import java.net.*;
import java.io.*;
import java.util.*;

public class Server implements Runnable {

    // Variables declaration.
    private int port;
    private Thread worker;
    private ServerSocket ss;
    private ServerGUI view;
    ArrayList<Connection> list = new ArrayList<Connection>();
    private Authentication model;
    int ptpPort;
    // End of variables declaration.

    /**
     * Default Constructor - Create the new server at assigned port.
     *
     * @throws IOException.
     */
    public Server(int port) throws IOException {
        this.port = port;
        ss = new ServerSocket(port);
    } //end Server.

    /**
     * Sends message to all Connection objects.
     *
     * @param msg - Message from the user.
     * @throws IOException.
     */
    public void broadcast(String msg){
        for (Connection c : list) {
            c.sendServerMsg(msg);
        }

    } // end broadcast.

    /**
     * Accepts connections and creates Connection object.
     */
    @Override
    public void run() {
        view.serverTA.append("Server waiting for Clients on port " + port + ".\n");
        while (true) {
            try {
                Socket sockClient = ss.accept();
                view.serverTA.append("Client at " + sockClient.getInetAddress().getHostAddress() + " on port " + sockClient.getPort() + "\n");
                view.connectTF.setText(1 + list.size() + "");
                Connection connect = new Connection(sockClient, this); //Creates a new Thread
                connect.setModel(model);
                connect.setIP(sockClient.getInetAddress().toString());
                ptpPort = connect.getPort() + 5; // 5 is to move away from taken port
                list.add(connect);
                connect.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    } // end run.

    /**
     * Remove the connection from the list if disconnected.
     *
     * @param connect
     */
    public void remove(Connection connect) {
        //Remove all disconnected clients
        view.serverTA.append("Client at " + connect.getSocket().getInetAddress().getHostAddress() + " on port " + connect.getSocket().getPort() + " disconnected\n");
        list.remove(connect);
        view.connectTF.setText(list.size() + "");
    }// end remove.

    /**
     * Set the GUI display.
     *
     * @param view
     */
    public void setView(ServerGUI view) {
        this.view = view;
    } //end setView.

    /**
     * Calls run() in the new Thread.
     */
    public void listen() {
        worker = new Thread(this);
        worker.start();
    } // end listen.

    /**
     * Set the model into the Connection.
     *
     * @param model Authentication.
     */
    public void setModel(Authentication model) {
        this.model = model;
    }// end setModel.

    /**
     * Checks the exist connection - online user inside the ArrayList of
     * Connection. Return IP address of the online user who connected to the
     * server.
     *
     * @param user - userName.
     * @return returns String - IP of the userName.
     */
    public String getOnlineUserIp(String user) {
        //return model.onlineUser.get(user);
        String ip = "";
        for (Connection c : list) {
            if (c.getUserName().equals(user)) {
                ip = c.getIP();
                break;
            }
        }
        return ip;
    }// end getOnlineUserIp.

    /**
     * Sending the invitation from host to another player.
     *
     * @param userName - Player to be invited.
     * @param invitation - Invitation.
     */
    public void sendInvitation(String userName, String invitation) {
         String[] split1 = invitation.split("_");
        for (Connection c : list) {
            if (c.getUserName().equals(userName)) {
                c.sendServerMsg(invitation);
                if(split1[0].equals("accept"))
                    c.inGame = true;
                //DEBUG
                //System.out.println("This is the server in sendIvitation: "+ c.inGame);
            }
            String[] split = userName.split("_");
            if (split[0].equals("all")) {
                if (!c.getUserName().equals(split[1]) && c.isInGame() == false) {
                    c.sendServerMsg(invitation);
                }
            }
        }
    }// end sendInviation.

    /**
     * Return the list of connected online user.
     *
     * @return returns String - list of online user.
     */
    public String getOnlineUserList() {
        String userList = "list_";
        for (Connection c : list) {
            if (c.isInGame() == false) {
                userList += c.getUserName() + "_";
            }
        }
        return userList;
    }// end getOnlineUserList.

    /**
     * Checks the user if online and connected to the server. Go through the
     * ArrayList of Connection and check the user name.
     *
     * @param userName - player user name.
     * @return returns true if the user is online, otherwise returns false.
     */
    boolean checkOnlineUser(String userName) {
        boolean on = false;
        for (Connection c : list) {
            if (c.getUserName().equals(userName) && c.inGame == false) {
                on = true;
                break;
            }
        }
        return on;
    } // end checkOnlineUser.

}//end class Server
