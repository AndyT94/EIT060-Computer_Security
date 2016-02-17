package server;

public abstract class User {
	protected String realName;
	protected String username;
	
	protected User(String username, String realName) {
		this.realName = realName;
		this.username = username;
	}
}
