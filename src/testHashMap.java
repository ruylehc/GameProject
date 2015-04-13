import java.util.*;
public class testHashMap {
	public static void main(String[] args){
		HashMap<String, String> map = new HashMap<String, String>();
		ArrayList<String> usr = new ArrayList<String>();
		usr.add("123");
		usr.add("456");
		map.put("Tyler", "cat");
		map.put("Holly", "dog");
		
		System.out.println(map.get("Tyler"));
		System.out.println(map.get("Holly"));
		System.out.println(map.keySet());
	}
}
