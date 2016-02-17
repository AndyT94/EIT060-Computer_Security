package server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Capabilities {
	private Map<User, Record> recordList;
	private Map<User, Map<Record, ArrayList<String>>> cap;
	
	public Capabilities() {
		cap = new HashMap<User, Map<Record, ArrayList<String>>>();
		recordList = new HashMap<User, Record>();
	//	load();
	}
	
	public ArrayList<String> getAccessRights(String username, String record) {
		User u = getUser(username);
		if(u != null) {
			return cap.get(u).get(recordList.get(u));
		} else {
			throw new IllegalArgumentException("Invalid user");
		}
	}
	
	public Record getRecord(String userRecord) {
		User u = getUser(userRecord);
		if(u != null) {
			return recordList.get(u);
		} else {
			throw new IllegalArgumentException("Invalid user");
		}
	}

	public void addRights(User user, Record r, List<String> list) {
		recordList.put(user, r);
	}
	
	public void addRecord(String username, Record r) {
	}
	
	private User getUser(String userRecord) {
		for(User u: recordList.keySet()) {
			if(u.getUsername().equals(userRecord)) {
				return u;
			}
		}
		return null;
	}
	
	
	private void load() {
		//TODO FIX THIS LOAD SHIT!!!!
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader("server/data/datafile"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		String line = null;
		JSONParser parser = new JSONParser();
		List<Record> temp = new ArrayList<Record>();
		try {
			while((line = in.readLine()) != null ) {
				JSONObject obj = (JSONObject) parser.parse(line);
				String role = (String) obj.get("role");
				String username = (String) obj.get("username");
				String realName = (String) obj.get("realname");
				
				if(role.equals("patient")) {
					cap.put(new Patient(username, realName), new HashMap<Record, ArrayList<String>>());
					JSONArray array = (JSONArray) obj.get("record");
					for(int i = 0; i < array.size(); i++) {
						JSONObject r = (JSONObject) array.get(i);
						String nurse = (String) r.get("nurse");
						String doc = (String) r.get("doctor");
						String div = (String) r.get("division");
						String note = (String) r.get("notes");
						
					}
				} else if (role.equals("nurse")) {
					Nurse nurse = new Nurse(username, realName);
					cap.put(nurse, new HashMap<Record, ArrayList<String>>());
					nurse.setDivision((String) obj.get("division"));
				} else if (role.equals("doctor")) {
					Doctor doc = new Doctor(username, realName);
					cap.put(doc, new HashMap<Record, ArrayList<String>>());
					doc.setDivision((String) obj.get("division"));
				} else {
					cap.put(new Government(username, realName), new HashMap<Record, ArrayList<String>>());
				}
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		updateAccess(temp);
	}
	
	private void updateAccess(List<Record> temp) {
		
	}
}
