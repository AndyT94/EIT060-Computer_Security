package server;

public abstract class User {
	protected String realName;
	
	protected User(String realName) {
		this.realName = realName;
	}
}
