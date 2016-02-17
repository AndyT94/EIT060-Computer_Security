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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	

}
