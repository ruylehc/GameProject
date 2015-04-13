import java.io.File;
import java.io.IOException;
import java.util.*;
public class testHashMap {
	
	static HashMap<String, String> map = new HashMap<String, String>();
	public static void main(String[] args){
		
		/*
		ArrayList<String> usr = new ArrayList<String>();
		usr.add("123");
		usr.add("456");
		map.put("Tyler", "cat");
		map.put("Holly", "dog");
		
		System.out.println(map.get("Tyler"));
		System.out.println(map.get("Holly"));
		System.out.println(map.keySet());
		*/
		readTextFile();
		
		Set<String> mySet = map.keySet();
		Iterator itr = mySet.iterator();
		while (itr.hasNext()){
			String key = (String)itr.next();
			System.out.println(key + "_" + map.get(key));
		}
		
		
	}
	
	public static void readTextFile(){
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
	}
}
