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
		return false;
	}

	@Override
	public String getRole() {
		return "nurse";
	}
	
}
