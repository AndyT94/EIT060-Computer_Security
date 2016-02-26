import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Login {
	private String username;
	private String password;
	
	
	public Login(String username, String password){
		this.username = username;
		this.password = password;
		try {
			parse();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String [] args){
		Login lg = new Login("9001010001","password");
	}

	private void parse() throws IOException {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader("server/PasswordFile"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
