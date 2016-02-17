package util;

public class EditCommand implements Command {
	private String fileName;
	private String doctor;
	private String nurse;
	private String division;
	private String notes;
	
	public EditCommand(String fileName, String doctor, String nurse, String division, String notes) {
		this.fileName = fileName;
		this.nurse = nurse;
		this.doctor = doctor;
		this.division = division;
		this.notes = notes;
	}

	public String getFileName() {
		return fileName;
	}

	public String getDoctor() {
		return doctor;
	}

	public String getNurse() {
		return nurse;
	}

	public String getDivision() {
		return division;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
}
