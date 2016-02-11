package server;

public class Record {
	private Patient patient;
	private String date;
	private Nurse nurse;
	private Doctor doctor;
	private String notes;

	public Record(Patient patient, String date, Nurse nurse, Doctor doctor, String notes) {
		this.patient = patient;
		this.date = date;
		this.nurse = nurse;
		this.doctor = doctor;
		this.notes = notes;
	}

	public void setNurse(Nurse newNurse) {
		nurse = newNurse;
	}

	public void setDoctor(Doctor newDoctor) {
		doctor = newDoctor;
	}

	public void setNotes(String newNotes) {
		notes = newNotes;
	}

	public void setDate(String newDate) {
		date = newDate;
	}

	public String toString() {
		return "Patient: " + patient.getName() + "\nDivision: " + doctor.getDivision() + "\nDate:" + date + "\nNurse: "
				+ nurse.getName() + "\nDoctor: " + doctor.getName() + "\nNotes: " + notes;
	}
}
