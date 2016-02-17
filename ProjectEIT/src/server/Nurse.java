package server;

public class Nurse extends User {
	private String division;
	
	public Nurse(String username, String realName) {
		super(username, realName);
		}

	public String getDivision() {
		return division;
	}

	public void setDivision(String newDivision) {
		division = newDivision;
	}
	
}
