package GameServer;
/**Group Names: Tyler Glass, Michael House, Holly Ruyle, Phu Hoang    
 * Project Part: Authentication Model - Server Side
 * Program Title: Tic-tac-toe Game 
 * Course: CSCE 320 - Software Engineering
 * Date: February 23, 2015
 * Language and Compiler: Java written in eclipse and Netbeans
 * Sources: CSCE 320 references - Trivial Java Example
 */

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Authentication {

	//Key is the username, value is the password (need to implement hashing + salt)
	HashMap <String, String> map = new HashMap<String, String>(); 

	//Key is the user name, value is the IP address in string format
	/*
	 * Comment: This one is might be not use able because of connection could 
	 * provide ip, username 
	 */
	HashMap <String,String> onlineUser = new HashMap<String,String>(); 

	/**
	 * Loading the user information from the text file when server started.
	 */
	public void loadData(){            
		//DEBUG
		System.out.println("loaded user info");
		//Load the user information
		readTextFile();		
	}// end loadData.

	/**
	 * Verify the new user name and password.
	 * Returns the String massage if the user information is correct or any error.
	 * @param userName.
	 * @param userPass.
	 * @return returns "takenUserName" if the new user information is already in the file.
	 * 		   returns "spacePassword" if the new user information contains any white spaces. 
	 * 		   otherwise, returns "success" and put the new user information into file.
	 */
	public String registerUser(String userName, String userPass, String rePass){

		//DEBUG
		System.out.println("reg from model of sever: "+userName + ", " + userPass);

		//Valid the user name and return the error messages.
		if(userName.equals("") || userPass.equals("") || rePass.equals(""))
			return "Please enter valid user name and password!";

		else if (map.containsKey(userName))
			return "User name is taken!\nPlease enter another one";

		else if (userName.contains(" "))
			return "User name contains space!\nPlease enter valid user name.";

		else if(!userPass.equals(rePass))
			return "Password is not matched!";

		//Record the valid username and password
		else{ 
			try {
				writeTextFile(userName, userPass);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			map.put(userName, userPass);	//Put new valid user into map.
			
			/*
			 * Comment: this is might not user anymore while connection class do the thing.
			String ipaddr = "";
			onlineUser.put(userName,"");
			*/
			return "registerSuccess";
		}
	}// end registerUser.

	/**
	 * Verify the login user information.
	 * Returns the String massage if the user information is correct or any error.
	 * @param userN - user name.
	 * @param userP - user password.
	 * @return returns "incorectPassword" if the new user information is not match in the file.
	 * 		   returns "success" if the new user information valid. 
	 * 		   otherwise, returns "failed".
	 */
	public String login(String userN, String userP){
		String status = "";

		//Valid the user name and return the error messages.
		if(userN.equals("") || userP.equals(""))
			return "Please enter valid user name and password!";
		
		if(map.containsKey(userN) && userP.equals(map.get(userN))){
			System.out.println("log from model of sever: "+ userN + ", " + userP);
			status = "loginSuccess";
			
			/*
			 * Comment: this is might not user anymore while connection class do the thing.
			String ipaddr = "";
			onlineUser.put(userN,ipaddr);
			*/
			
		} else if(map.containsKey(userN) && !userP.equals(map.get(userN)))
			status = "Incorect UserName or Password!";
		
		else
			status = "Account does not exist!";    

		return status;
	}// end login	

	/**
	 * This method writes the new user info to a Text file to store the user info.
	 * Uses bufferWriter and file writer to accomplish this.
	 * This method is called after the register user info has been validated and writes
	 * before storing it in the hashmap
	 */
	public void writeTextFile (String userTextfile, String passwordTextfile) throws FileNotFoundException{
		//public void writeTextFile (String userTextfile, ArrayList<String> info) throws FileNotFoundException{  // for updated hashmap
		PrintWriter writer = null;
		String userpass = new String(userTextfile + "_" + passwordTextfile);
		/*String pass = info.get(0);
      String wins = "info.get(1);
      String lose = "info.get(2);
      String draw = "info.get(3);
      String ratio = "info.get(4);
      String userpass = new String(userTextfile + "_" + pass + "_" + wins + "_" + lose + "_" + ratio);

    this code will be implemented when we convert our hashmap to a hashmap<String, Arraylist <String> >
		 */
		try {

			writer = new PrintWriter( new BufferedWriter(new FileWriter("SuperSecretLoginInfo.txt", true)));
			System.out.println("From write file: " + userpass);
			writer.println(userpass);
			writer.flush();


		} catch (IOException ex) {
			//Logger.getLogger(TextFileWriter.class.getName()).log(Level.SEVERE, null, ex);
			ex.printStackTrace();
		}
		finally{
			if(writer != null)
				writer.close();
		}
	}// end writeTextFile.

	/**
	 * Reads from a text file that holds the user info when the server is shut down.
	 * It reads the file line by line with each line being a diferent user info.
	 * it then stores it in a hashmap of existing users.
	 */	
	public void readTextFile(){
		//BufferedReader reader = new BufferedReader("SuperSecretLoginInfo.txt");
		// Create the scanner (open the file for reading)
		Scanner scan = null;
		Scanner lineScan = null;  // used to parse one line

		try {
			scan = new Scanner( new File ("SuperSecretLoginInfo.txt") );
		} catch (IOException e) {
			System.err.println("Error reading from: SuperSecretLoginInfo.txt");
			System.exit(1);
		}
		String line;
		String user;
		String pass;
		/*
    String wins = ""; later implementation strings for scores in the text file
    String loss = "";
    String draw = "";
    String ratio = "";
		 */
		while (scan.hasNext()){
			line = scan.nextLine();
			//String[] data = finder.split("_");
			lineScan = new Scanner(line);
			lineScan.useDelimiter("\\s*\\_\\s*");  // delimiters are "_"
			user = lineScan.next();
			pass = lineScan.next();
			/*
        wins = data[2];
        loss = data[3];
        draw = data[4];
        ratio = data[5];
			 */
			map.put(user, pass);
		}
	}//end readTextFile.
	
}// end Authentication.
