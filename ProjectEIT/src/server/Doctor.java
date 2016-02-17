package server;

public class Doctor extends User {
	private String division;
	
	public Doctor(String username, String realName) {
		super(username, realName);
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String newDivision) {
		division = newDivision;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String newRealName) {
		realName = newRealName;
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(String newUsername) {
		username = newUsername;
	}
}
