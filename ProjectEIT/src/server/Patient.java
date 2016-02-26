package server;

public class Patient extends User {
	
	public Patient(String username, String realName) {
		super(username, realName);
	}

	@Override
	public String getDivision() {
		return "";
	}

	@Override
	public boolean hasReadRights() {
		return true;
	}

	@Override
	public boolean hasWriteRights() {
		return false;
	}

	@Override
	public boolean hasDeleteRights() {
		return false;
	}

	@Override
	public boolean hasAddRights() {
		return false;
	}

	@Override
	public String getRole() {
		return "patient";
	}
}