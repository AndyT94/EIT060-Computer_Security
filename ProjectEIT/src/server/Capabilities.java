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
	private Map<User, Map<Record, ArrayList<String>>> capability;

	public Capabilities(String filename) {
		capability = new HashMap<User, Map<Record, ArrayList<String>>>();
		load(filename);
	}

	public ArrayList<String> getAccessRights(String username, String record) {
		User u = getUser(username);
		if (u != null) {
			return capability.get(u).get(getRecord(record));
		}
		return null;
	}

	public Record getRecord(String user) {
		User u = getUser(user);
		if (u != null) {
			for (Record r : capability.get(u).keySet()) {
				if (r.hasPatient(u)) {
					return r;
				}
			}
		}
		return null;
	}

	public void deleteRecord(String record) {
		
	}

	public void addRights(User user, Record r, List<String> list) {
		// TODO USE?
	}

	public void addRecord(String username, Record r) {
		User u = getUser(username);
		
	}

	private User getUser(String user) {
		for (User u : capability.keySet()) {
			if (u.getUsername().equals(user)) {
				return u;
			}
		}
		return null;
	}

	private void load(String filename) {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		String line = null;
		JSONParser parser = new JSONParser();
		HashMap<String, JSONArray> caps = new HashMap<String, JSONArray>();
		HashMap<Patient, JSONArray> records = new HashMap<Patient, JSONArray>();
		try {
			while ((line = in.readLine()) != null) {
				JSONObject obj = (JSONObject) parser.parse(line);
				HashMap<Record, ArrayList<String>> cap = new HashMap<Record, ArrayList<String>>();
				String role = (String) obj.get("role");
				String username = (String) obj.get("username");
				String realName = (String) obj.get("realname");

				caps.put(username, (JSONArray) obj.get("cap"));
				
				if (role.equals("patient")) {
					Patient p = new Patient(username, realName);
					records.put(p, (JSONArray) obj.get("record"));
					capability.put(p, cap);
				} else if (role.equals("nurse")) {
					Nurse n = new Nurse(username, realName);
					capability.put(n, cap);
					n.setDivision((String) obj.get("division"));
				} else if (role.equals("doctor")) {
					Doctor doc = new Doctor(username, realName);
					capability.put(doc, cap);
					doc.setDivision((String) obj.get("division"));
				} else {
					Government gov = new Government(username, realName);
					capability.put(gov, cap);
				}
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		for (Patient p : records.keySet()) {
			Record r = new Record(p);
			JSONArray array = records.get(p);
			for (int i = 0; i < array.size(); i++) {
				JSONObject record = (JSONObject) array.get(i);
				Nurse nurse = (Nurse) getUser((String) record.get("nurse"));
				Doctor doctor = (Doctor) getUser((String) record.get("doctor"));
				String division = (String) record.get("division");
				String note = (String) record.get("note");
				r.addEntry(nurse, doctor, division, note);
			}
			capability.get(p).put(r, new ArrayList<String>());
		}

		for (String s : caps.keySet()) {
			User user = getUser(s);
			JSONArray jsonarray = caps.get(s);
			for (int j = 0; j < jsonarray.size(); j++) {
				JSONObject recordAccess = (JSONObject) jsonarray.get(j);
				Record r = getRecord((String) recordAccess.get("username"));
				String[] rights = ((String) recordAccess.get("rights")).split(",");
				ArrayList<String> rightsList = new ArrayList<String>();
				for (int k = 0; k < rights.length; k++) {
					rightsList.add(rights[k]);
				}
				capability.get(user).put(r, rightsList);
			}
		}
	}

}
