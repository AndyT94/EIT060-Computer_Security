import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


//public class Login {
//	private String username;
//	private String password;
//	
//	
//	public Login(String username, String password){
//		this.username = username;
//		this.password = password;
//		try {
//			parse(username,password);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	public static void main(String [] args){
//		Login lg = new Login("9001010001","password");
//	}

//	private void parse(String username,String password) throws IOException {
//		BufferedReader in = null;
//		try {
//			in = new BufferedReader(new FileReader("server/PasswordFile"));
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		String line = null;
//		JSONParser parser = new JSONParser();
//		try {
//			while(in.readLine() != null){
//				JSONObject obj = (JSONObject) parser.parse(line);
//				String usr = (String) obj.get("username");
//				String salt = (String) obj.get("salt");
//				String passwd = (String) obj.get("password");
//				
//			} catch (IOException | ParseException e) {
//				e.printStackTrace();
//			}
//			
//		 
//	
//		
	
//}
