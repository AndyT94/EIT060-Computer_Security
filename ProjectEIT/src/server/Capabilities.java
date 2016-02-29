package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Capabilities {
	private Map<User, Map<Record, ArrayList<String>>> capability;
	private String filename;

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

	public void deleteRecord(String patient) {
		for (User u : capability.keySet()) {
			for (Record r : capability.get(u).keySet()) {
				if (r.getPatient().getUsername().equals(patient)) {
					capability.get(u).remove(r);
					break;
				}
			}
		}
		save(filename);
	}

	public String addRecord(String username, String doctor, String nurse, String div, String note) {
		if (doctor == null || nurse == null || div == null || note == null) {
			return "FAIL! Missing mandatory options! (file:VALUE doctor:VALUE nurse:VALUE division:VALUE note:VALUE)";
		}
		if (getUser(username) == null) {
			return "FAIL! Invalid patient!";
		}
		Record r = getRecord(username);
		if (r == null) {
			r = new Record((Patient) getUser(username));
		}
		Nurse n = (Nurse) getUser(nurse);
		Doctor d = (Doctor) getUser(doctor);
		if (n == null || d == null) {
			return "FAIL! Invalid doctor or nurse!";
		}
		r.addEntry(n, d, div, note);

		for (User u : capability.keySet()) {
			Map<Record, ArrayList<String>> rights = capability.get(u);
			ArrayList<String> listOfRights = rights.get(r);
			if (listOfRights == null) {
				listOfRights = new ArrayList<String>();
				rights.put(r, listOfRights);
			}
			listOfRights.clear();
			if (u.getRole().equals("government")) {
				listOfRights.add("delete");
				listOfRights.add("read");
			}

			if (r.hasDoctor(getUser(u.getUsername())) || r.hasNurse(getUser(u.getUsername()))) {
				listOfRights.add("read");
				listOfRights.add("write");
			}
			if (!listOfRights.contains("read")
					&& (r.hasPatient(getUser(u.getUsername())) || r.hasDivision(u.getDivision()))) {
				listOfRights.add("read");
			}
			if (u.getRole().equals("doctor")) {
				listOfRights.add("add");
			}
		}
		save(filename);
		return "SUCCESS! Record entry added to " + username;
	}

	public User getUser(String user) {
		for (User u : capability.keySet()) {
			if (u.getUsername().equals(user)) {
				return u;
			}
		}
		return null;
	}

	public String getAllReadRecords(String username) {
		User u = getUser(username);
		if (u != null) {
			StringBuilder sb = new StringBuilder();
			Map<Record, ArrayList<String>> records = capability.get(u);
			for (Record r : records.keySet()) {
				if (records.get(r).contains("read")) {
					sb.append(r.getPatient().getUsername() + " ");
				}
			}
			return sb.toString();
		}
		return " ";
	}

	// TODO REFACTOR!
	public String editRecord(String record, String entryNbr, String doctor, String nurse, String div, String note) {

		for (User u : capability.keySet()) {
			Map<Record, ArrayList<String>> rights = capability.get(u);
			for (Record r : rights.keySet()) {
				if (r.getPatient().getUsername().equals(record)) {
					if (entryNbr == null) {
						return "FAIL! Missing entry number!";
					}
					RecordEntry re = r.getEntry(Integer.parseInt(entryNbr));
					if (re != null) {
						if (doctor != null) {
							if (getUser(doctor) != null) {
								re.setDoctor((Doctor) getUser(doctor));
							} else {
								return "FAIL! Invalid doctor!";
							}
						}
						if (nurse != null) {
							if (getUser(nurse) != null) {
								re.setNurse((Nurse) getUser(nurse));
							} else {
								return "FAIL! Invalid nurse!";
							}

						}
						if (div != null) {
							re.setDivision(div);
						}
						if (note != null) {
							re.setNotes(note);
						}
					}
					ArrayList<String> listOfRights = rights.get(r);
					if (listOfRights == null) {
						listOfRights = new ArrayList<String>();
					}
					listOfRights.clear();
					if (r.hasPatient(getUser(u.getUsername())) || r.hasDivision(u.getDivision())) {
						listOfRights.add("read");
					}
					if (r.hasDoctor(getUser(u.getUsername())) || r.hasNurse(getUser(u.getUsername()))) {
						listOfRights.add("read");
						listOfRights.add("write");
					}
					if (!listOfRights.isEmpty()) {
						rights.put(r, listOfRights);
					}
					break;
				}
			}
		}
		save(filename);
		return "SUCCESS! Record updated!";
	}

	@SuppressWarnings("unchecked")
	private void save(String filename) {
		PrintWriter out = null;
		try {
			out = new PrintWriter(new File(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		for (User u : capability.keySet()) {
			JSONObject json = new JSONObject();
			json.put("username", u.getUsername());
			json.put("realname", u.getRealName());
			if (u.getRole().equals("patient")) {
				JSONArray record = new JSONArray();
				Record r = getRecord(u.getUsername());
				if (r != null) {
					for (int i = 0; i < r.getNbrEntries(); i++) {
						RecordEntry re = r.getEntry(i + 1);
						JSONObject recordEntry = new JSONObject();
						recordEntry.put("division", re.getDivision());
						recordEntry.put("note", re.getNotes());
						recordEntry.put("doctor", re.getDoctor().getUsername());
						recordEntry.put("nurse", re.getNurse().getUsername());
						record.add(recordEntry);
					}
				}
				json.put("record", record);
			}
			json.put("role", u.getRole());
			if (!u.getDivision().isEmpty()) {
				json.put("division", u.getDivision());
			}
			JSONArray record = new JSONArray();
			for (Record r : capability.get(u).keySet()) {
				ArrayList<String> list = capability.get(u).get(r);
				if (list != null && !list.isEmpty()) {
					JSONObject rights = new JSONObject();
					rights.put("username", r.getPatient().getUsername());
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < list.size(); i++) {
						sb.append(list.get(i) + ",");
					}
					sb.delete(sb.length() - 1, sb.length());
					rights.put("rights", sb.toString());
					record.add(rights);
				}
			}
			json.put("cap", record);
			out.println(json.toJSONString());
		}
		out.flush();
		out.close();
	}

	private void load(String filename) {
		this.filename = filename;
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
