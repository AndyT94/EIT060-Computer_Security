package server;

public abstract class User {
	protected String realName;
	protected String username;
	
	protected User(String username, String realName) {
		this.realName = realName;
		this.username = username;
	}
	
	protected String getUsername() {
		return username;
	}
	
	protected String getRealName() {
		return realName;
	}
	
	protected void setUsername(String username) {
		this.username = username;
	}
	
	protected void setRealName(String realName) {
		this.realName = realName;
	}
}
