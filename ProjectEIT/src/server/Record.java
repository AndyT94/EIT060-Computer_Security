package server;

import java.util.ArrayList;
import java.util.List;

public class Record {
	private Patient patient;
	private List<RecordEntry> recordEntries;

	public Record(Patient patient) {
		this.patient = patient;
		recordEntries = new ArrayList<RecordEntry>();
	}

	public Patient getPatient() {
		return patient;
	}

	public void addEntry(Nurse n, Doctor d, String div, String notes) {
		recordEntries.add(new RecordEntry(n, d, div, notes));
	}

	public List<RecordEntry> getEntries() {
		return recordEntries;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Patient: " + patient.getRealName() + "\n");
		for (int i = 0; i < recordEntries.size(); i++) {
			RecordEntry re = recordEntries.get(i);
			sb.append("\nEntry: " + (i + 1) + "\nDoctor: " + re.getDoctor().getRealName() + "\nNurse: "
					+ re.getNurse().getRealName() + "\nDivision: " + re.getDivision() + "\nNotes: " + re.getNotes()
					+ "\n");
		}
		return sb.toString();
	}

	public boolean hasDivision(String division) {
		for (RecordEntry e : recordEntries) {
			if (e.getDivision().equals(division)) {
				return true;
			}
		}
		return false;
	}

	public boolean hasDoctor(User u) {
		for (RecordEntry e : recordEntries) {
			if (e.getDoctor().getUsername().equals(u.getUsername())) {
				return true;
			}
		}
		return false;
	}

	public boolean hasNurse(User u) {
		for (RecordEntry e : recordEntries) {
			if (e.getNurse().getUsername().equals(u.getUsername())) {
				return true;
			}
		}
		return false;
	}

	public boolean hasPatient(User u) {
		if (patient.getUsername().equals(u.getUsername())) {
			return true;
		}
		return false;
	}

}
