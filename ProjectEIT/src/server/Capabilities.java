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
			return capability.get(u).get(getRecord(username));
		} else {
			throw new IllegalArgumentException("Invalid user");
		}
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
		throw new IllegalArgumentException("Invalid user");
	}

	public void addRights(User user, Record r, List<String> list) {
		// TODO USE?
	}

	public void addRecord(String username, Record r) {
		// TODO USE?
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
		try {
			while ((line = in.readLine()) != null) {
				JSONObject obj = (JSONObject) parser.parse(line);
				String role = (String) obj.get("role");
				String username = (String) obj.get("username");
				String realName = (String) obj.get("realname");

				HashMap<Record, ArrayList<String>> cap = new HashMap<Record, ArrayList<String>>();
//				JSONArray access = (JSONArray) obj.get("cap");
//				for (int i = 0; i < access.size(); i++) {
//					JSONObject recordAccess = (JSONObject) access.get(i);
//					Record r = getRecord((String) recordAccess.get("username"));
//					String[] rights = ((String) recordAccess.get("rights")).split(",");
//					ArrayList<String> right = new ArrayList<String>();
//					for (int j = 0; j < rights.length; j++) {
//						right.add(rights[i]);
//					}
//					cap.put(r, right);
//				}

				if (role.equals("patient")) {
					Patient p = new Patient(username, realName);
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
	}
}
