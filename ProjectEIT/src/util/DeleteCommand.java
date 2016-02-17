package util;

public class DeleteCommand implements Command {
	private String fileName;
	
	public DeleteCommand(String fileName) {
		this.fileName = fileName;
	}
	
}
