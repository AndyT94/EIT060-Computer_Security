package util;

public class ReadCommand implements Command {
	private String fileName;
	
	public ReadCommand(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}
}
