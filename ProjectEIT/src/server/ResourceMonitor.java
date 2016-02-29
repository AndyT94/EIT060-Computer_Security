package server;

import java.util.ArrayList;

import util.*;

public class ResourceMonitor {
	private Capabilities cap;

	public ResourceMonitor() {
		cap = new Capabilities("../server/data/datafile");
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
				AuditLog.log(username + " has read record:" + userRecord);
				return cap.getRecord(userRecord).toString();
			} else {
				AuditLog.log("Denied " + username + " from reading record:" + userRecord);
				return "Access Denied!";
			}
			
		} else if (cmd.getClass().equals(AddCommand.class)) {
			AddCommand ac = (AddCommand) cmd;
			String userRecord = ac.getFileName();
			ArrayList<String> rights = cap.getAccessRights(username, userRecord);
			if(rights != null && rights.contains("add") && cap.getUser(username).getClass().equals(Doctor.class)) {
				String note = ac.getNotes();
				String div = ac.getDivision();
				String doctor = ac.getDoctor();
				String nurse = ac.getNurse();
				String result =  cap.addRecord(userRecord, doctor, nurse, div, note);
				AuditLog.log(username + " tried adding record:" + userRecord + ", " + result);
				return result;
			} else {
				AuditLog.log("Denied " + username + " from adding record:" + userRecord);
				return "Access Denied!";
			}
			
		} else if (cmd.getClass().equals(ListCommand.class)) {
			AuditLog.log(username + " listed all readable records");
			return "Files readable: " + cap.getAllReadRecords(username);
			
		} else if (cmd.getClass().equals(DeleteCommand.class)) {
			DeleteCommand dc = (DeleteCommand) cmd;
			String userRecord = dc.getFileName();
			ArrayList<String> rights = cap.getAccessRights(username, userRecord);
			if(rights != null && rights.contains("delete")) {
				cap.deleteRecord(userRecord);
				AuditLog.log(username + " deleted record:" + userRecord);
				return "Deleted record " + userRecord;
			} else {
				AuditLog.log("Denied " + username + " from deleting record:" + userRecord);
				return "Access Denied!";
			}
			
		} else if(cmd.getClass().equals(EditCommand.class)) {
			EditCommand ec = (EditCommand) cmd;
			String userRecord = ec.getFileName();
			ArrayList<String> rights = cap.getAccessRights(username, userRecord);
			if(rights != null && rights.contains("write")) {
				String doctor = ec.getDoctor();
				String nurse = ec.getNurse();
				String div = ec.getDivision();
				String note = ec.getNotes();
				String entryNbr = ec.getEntryNbr();
				String result = cap.editRecord(userRecord, entryNbr, doctor, nurse, div, note);
				AuditLog.log(username + " tried edit record:" + userRecord + ", " + result);
				return result;
			} else {
				AuditLog.log("Denied " + username + " from editing record:" + userRecord);
				return "Access Denied!";
			}
		}
		AuditLog.log(username + " sent invalid command");
		return "Invalid command or option(s)";
	}
}
