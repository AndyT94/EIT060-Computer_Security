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
	
	public Nurse getNurse() {
		return nurse;
	}
	
	public void setDoctor(Doctor newDoctor) {
		doctor = newDoctor;
	}
	
	public Doctor getDoctor() {
		return doctor;
	}
	
	public void setDivision(String newDivision) {
		division = newDivision;
	}
	
	public String getDivision() {
		return division;
	}
	
	public void setNotes(String newNotes) {
		notes = newNotes;
	}
	
	public String getNotes() {
		return notes;
	}
}
