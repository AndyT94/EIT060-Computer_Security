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
			
		} else if (cmd.getClass().equals(AddCommand.class)) {
			AddCommand ac = (AddCommand) cmd;
			String userRecord = ac.getFileName();
			ArrayList<String> rights = cap.getAccessRights(username, userRecord);
			if(rights.contains("write") && cap.getUser(username).getClass().equals(Doctor.class)) {
				String note = ac.getNotes();
				String div = ac.getDivision();
				String doctor = ac.getDoctor();
				String nurse = ac.getNurse();
				return cap.addRecord(userRecord, doctor, nurse, div, note);
			}
			
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
			ArrayList<String> rights = cap.getAccessRights(username, userRecord);
			if(rights.contains("write")) {
				String doctor = ec.getDoctor();
				String nurse = ec.getNurse();
				String div = ec.getDivision();
				String note = ec.getNotes();
				int entryNbr = ec.getEntryNbr();
				cap.editRecord(userRecord, entryNbr, doctor, nurse, div, note);
			} else {
				return "Access Denied!";
			}
		}
		return "Invalid command or option(s)";
	}
}
