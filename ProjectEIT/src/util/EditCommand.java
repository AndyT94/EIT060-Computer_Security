package util;

public class EditCommand implements Command {
	private String fileName;
	private String doctor;
	private String nurse;
	private String division;
	private String notes;
	private String entryNbr;
	
	public EditCommand(String fileName, String doctor, String nurse, String division, String notes, String entryNbr) {
		this.fileName = fileName;
		this.nurse = nurse;
		this.doctor = doctor;
		this.division = division;
		this.notes = notes;
		this.entryNbr = entryNbr;
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

	public String getEntryNbr() {
		return entryNbr;
	}
}
