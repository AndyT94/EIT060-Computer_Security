package server;

import java.util.ArrayList;

import util.*;

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
			if (rights != null && rights.contains("read")) {
				return cap.getRecord(userRecord).toString();
			} else {
				return "Access Denied!";
			}
			
		} else if (cmd.getClass().equals(WriteCommand.class)) {
			
		} else if (cmd.getClass().equals(ListCommand.class)) {
			return cap.getAllReadRecords(username);
			
		} else if (cmd.getClass().equals(DeleteCommand.class)) {
			DeleteCommand dc = (DeleteCommand) cmd;
			String userRecord = dc.getFileName();
			ArrayList<String> rights = cap.getAccessRights(username, userRecord);
			if(rights.contains("delete")) {
				cap.deleteRecord(userRecord);
				return "Deleted record " + userRecord;
			} else {
				return "Access Denied!";
			}
			
		} else if(cmd.getClass().equals(EditCommand.class)) {
			EditCommand ec = (EditCommand) cmd;
			String userRecord = ec.getFileName();
			
			

		}
		return "Invalid command or option(s)";
	}
}
