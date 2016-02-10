import java.util.ArrayList;

public class Patient extends Client{
	private String name;
	private boolean read,write,execute;
	private int divison;
	private ArrayList<Record> ownList;

	public Patient(String name, boolean read, boolean write, boolean execute, int divison) {
		super(name, true, false, false, 1);
		ownList = new ArrayList<Record>();
	}

}
