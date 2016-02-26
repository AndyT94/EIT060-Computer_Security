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

	public RecordEntry getEntry(int entryNbr) {
		if(entryNbr > recordEntries.size()) {
			return null;
		}
		return recordEntries.get(entryNbr - 1);
	}

	public int getNbrEntries() {
		return recordEntries.size();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Patient: " + patient.getRealName());
		for (int i = 0; i < recordEntries.size(); i++) {
			RecordEntry re = recordEntries.get(i);
			sb.append("\tEntry: " + (i + 1) + "\tDoctor: " + re.getDoctor().getRealName() + "\tNurse: "
					+ re.getNurse().getRealName() + "\tDivision: " + re.getDivision() + "\tNotes: " + re.getNotes());
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((patient == null) ? 0 : patient.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Record other = (Record) obj;
		if (patient == null) {
			if (other.patient != null)
				return false;
		} else if (!patient.equals(other.patient))
			return false;
		return true;
	}

}
