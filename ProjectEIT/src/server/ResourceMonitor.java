package server;


import java.util.ArrayList;

import util.Command;
import util.ReadCommand;

public class ResourceMonitor {
	private Capabilities cap;
	
	public ResourceMonitor() {
		cap = new Capabilities();
	}

	public synchronized String tryAccess(String username, Command cmd) {
		if(cmd.getClass().equals(ReadCommand.class)) {
			ReadCommand rc = (ReadCommand) cmd;
			String userRecord = rc.getFileName();
			ArrayList<String> rights = cap.getAccessRights(username, userRecord);
			if(rights.contains("read")) {
				return cap.getRecord(userRecord).toString();
			}
		} 
		return null;
	}
}
