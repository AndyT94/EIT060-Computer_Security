package util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Format {

	@SuppressWarnings("unchecked")
	public static String encode(String msg) throws IllegalArgumentException {
		JSONObject obj = new JSONObject();
		int firstSpace = msg.indexOf(" ");
		if (firstSpace == -1) {
			firstSpace = msg.length();
		}
		String cmd = msg.substring(0, firstSpace);
		obj.put("command", cmd);
		if (firstSpace != msg.length()) {
			String[] options = msg.substring(firstSpace).trim().split(" ");
			for (int i = 0; i < options.length; i++) {
				String[] opt = options[i].split(":");
				try {
					obj.put(opt[0], opt[1]);
				} catch (ArrayIndexOutOfBoundsException e) {
					return null;
				}
			}
		}
		return obj.toJSONString();
	}

	public static Command decode(String msg) throws ParseException {
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(msg);
		String command = (String) obj.get("command");
		
		if (command.equals("read")) {
			String fileName = (String) obj.get("file");
			if(fileName != null && !fileName.isEmpty()) {
				return new ReadCommand(fileName);
			} else {
				return new NoCommand();
			}
			
		} else if (command.equals("edit")) {
			String fileName = (String) obj.get("file");
			String doctor = (String) obj.get("doctor");
			String nurse = (String) obj.get("nurse");
			String division = (String) obj.get("division");
			String notes = (String) obj.get("note");
			Integer entryNbr = (Integer) obj.get("entry");
			if(fileName != null && !fileName.isEmpty()) {
				return new EditCommand(fileName, doctor, nurse, division, notes, entryNbr);
			} else {
				return new NoCommand();
			}
			
		} else if (command.equals("add")) {
			String fileName = (String) obj.get("file");
			String doctor = (String) obj.get("doctor");
			String nurse = (String) obj.get("nurse");
			String division = (String) obj.get("division");
			String notes = (String) obj.get("note");
			if(fileName != null && !fileName.isEmpty()) {
				return new AddCommand(fileName, doctor, nurse, division, notes);
			} else {
				return new NoCommand();
			}
			
		} else if (command.equals("list")) {
			return new ListCommand();
			
		} else if (command.equals("delete")) {
			String fileName = (String) obj.get("file");
			if(fileName != null && !fileName.isEmpty()) {
				return new DeleteCommand(fileName);
			} else {
				return new NoCommand();
			}
			
		} else {
			return new NoCommand();
		}
	}
}
