package util;

public class EditCommand implements Command {
	private String fileName;
	private String doctor;
	private String nurse;
	private String division;
	private String notes;
	private Integer entryNbr;
	
	public EditCommand(String fileName, String doctor, String nurse, String division, String notes, Integer entryNbr) {
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

	public Integer getEntryNbr() {
		return entryNbr;
	}
}
