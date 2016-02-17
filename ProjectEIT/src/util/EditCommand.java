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
}
