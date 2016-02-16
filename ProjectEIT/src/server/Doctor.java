package server;

public class Doctor extends User {
	private String username;
	private String realName;
	private String division;
	
	public Doctor(String username, String realName, String division) {
		this.username = username;
		this.realName = realName;
		this.division = division;
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
