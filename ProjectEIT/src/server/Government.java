package server;

public class Government extends User {

	public Government(String username, String realName) {
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
		return true;
	}

	@Override
	public boolean hasAddRights() {
		return false;
	}

	@Override
	public String getRole() {
		return "government";
	}
}
