package server;

public class RecordEntry {
	private Nurse nurse;
	private Doctor doctor;
	private String division;
	private String notes;
	
	public RecordEntry(Nurse nurse, Doctor doctor, String division, String notes) {
		this.nurse = nurse;
		this.doctor = doctor;
		this.division = division;
		this.notes = notes;
	}
	
	public void setNurse(Nurse newNurse){
		nurse = newNurse;
	}
	
	public void setDoctor(Doctor newDoctor) {
		doctor = newDoctor;
	}
	
	public void setDivision(String newDivision) {
		division = newDivision;
	}
	
	public void setNotes(String newNotes) {
		notes = newNotes;
	}
}
