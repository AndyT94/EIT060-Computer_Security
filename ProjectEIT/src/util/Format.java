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
					throw new IllegalArgumentException("Missing options");
				}
			}
		}
		return obj.toJSONString();
	}

	public static Command decode(String msg) throws ParseException, IllegalArgumentException {
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(msg);
		String command = (String) obj.get("command");
		
		if (command.equals("read")) {
			String fileName = (String) obj.get("file");
			if(!fileName.isEmpty()) {
				return new ReadCommand(fileName);
			} else {
				throw new IllegalArgumentException("Invalid option");
			}
			
		} else if (command.equals("edit")) {
			String fileName = (String) obj.get("file");
			String doctor = (String) obj.get("doctor");
			String nurse = (String) obj.get("nurse");
			String division = (String) obj.get("division");
			String notes = (String) obj.get("note");
			if(!fileName.isEmpty()) {
				return new EditCommand(fileName, doctor, nurse, division, notes);
			} else {
				throw new IllegalArgumentException("Invalid option");
			}
			
		} else if (command.equals("write")) {
			String fileName = (String) obj.get("file");
			String doctor = (String) obj.get("doctor");
			String nurse = (String) obj.get("nurse");
			String division = (String) obj.get("division");
			String notes = (String) obj.get("note");
			if(!fileName.isEmpty()) {
				return new WriteCommand(fileName, doctor, nurse, division, notes);
			} else {
				throw new IllegalArgumentException("Invalid option");
			}
			
		} else if (command.equals("list")) {
			return new ListCommand();
			
		} else if (command.equals("delete")) {
			String fileName = (String) obj.get("file");
			if(!fileName.isEmpty()) {
				return new DeleteCommand(fileName);
			} else {
				throw new IllegalArgumentException("Invalid option");
			}
			
		} else {
			throw new IllegalArgumentException("Not a valid command");
		}
	}
}
