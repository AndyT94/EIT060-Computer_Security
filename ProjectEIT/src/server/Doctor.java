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

	@Override
	public boolean hasReadRights() {
		return true;
	}

	@Override
	public boolean hasWriteRights() {
		return true;
	}

	@Override
	public boolean hasDeleteRights() {
		return false;
	}

	@Override
	public boolean hasAddRights() {
		return true;
	}
}
