package server;

public abstract class User {
	protected String realName;
	protected String username;
	
	protected User(String username, String realName) {
		this.realName = realName;
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getRealName() {
		return realName;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	public abstract String getDivision();
	public abstract boolean hasReadRights();
	public abstract boolean hasWriteRights();
	public abstract boolean hasDeleteRights();
	public abstract boolean hasAddRights();
	
}
