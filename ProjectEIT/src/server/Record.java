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
}
