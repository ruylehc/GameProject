package GameServer;
/**Group Names: Tyler Glass, Michael House, Holly Ruyle, Phu Hoang    
 * Project Part: Admin Model
 * Program Title: Tic-tac-toe Game 
 * Course: CSCE 320 - Software Engineering
 * Date: February 23, 2015
 * Language and Compiler: Java written in eclipse and Netbeans
 * Sources: CSCE 320 references - Trivial Java Example
*/

import java.util.*;
        
public class ServerModel {
     
    HashMap <String, String> userpass = new HashMap<String, String>();
    

    public String registerUser(String userName, String userPass){
        if (checkUsername(userName) == false)
            return "TakenUserName";
        else if (containSpace(userPass) == true)
            return "spacepassword";
        else 
            addUser(userName, userPass);
            return "success";
    }
    
    public String login(String userN, String userP){
        userpass.put("hello", "12345"); // to check our login method
        String check = "";
        //System.out.println("This is server model " +userN + " " + userP);
        check = userpass.get(userN); 
        
        if (check.equals(userP))
                return "success";
        else return "fail";
    }
    
    public boolean checkUsername(String name){
       if(userpass.containsKey(name))
    	   return false;
       else 
    	   return true;
    }
    
    public void addUser(String name, String pass){
        userpass.put(name, pass);
    }

    private boolean containSpace( String pass){
        int size = pass.length();
        for( int i = 0; i<size; i++){
            char select = pass.charAt(i);
            if(select == ' ')
                return true;         
        }
        return false; 
    }
}
