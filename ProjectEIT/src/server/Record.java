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

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Patient: " + patient.getRealName() + "\n");
		for (int i = 0; i < recordEntries.size(); i++) {
			RecordEntry re = recordEntries.get(i);
			sb.append("\nRecord: " + (i+1) + "\nDoctor: " + re.getDoctor().getRealName() + "\nNurse: "
					+ re.getNurse().getRealName() + "\nDivision: " + re.getDivision() + "\nNotes: " + re.getNotes()
					+ "\n");
		}
		return sb.toString();
	}

}
