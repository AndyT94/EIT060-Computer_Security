package server;

import java.util.ArrayList;

import util.Command;
import util.ReadCommand;

public class ResourceMonitor {
	private Capabilities cap;

	public ResourceMonitor() {
		cap = new Capabilities("server/data/datafile");
	}

	public ResourceMonitor(Capabilities cap) {
		this.cap = cap;
	}

	public synchronized String tryAccess(String username, Command cmd) {
		if (cmd.getClass().equals(ReadCommand.class)) {
			ReadCommand rc = (ReadCommand) cmd;
			String userRecord = rc.getFileName();
			ArrayList<String> rights = cap.getAccessRights(username, userRecord);
			if (rights.contains("read")) {
				return cap.getRecord(userRecord).toString();
			} else {
				return "Access Denied!";
			}
		}
		return null;
	}
}
