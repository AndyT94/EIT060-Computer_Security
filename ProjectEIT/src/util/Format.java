package util;

public class Format {
 
	public static String encode(String msg) {
		StringBuilder sb = new StringBuilder();
		sb.append("{command:");
		int firstSpace = msg.indexOf(" ");
		if(firstSpace == -1) {
			firstSpace = msg.length();
		}
		sb.append(msg.substring(0, firstSpace));
		if(firstSpace != msg.length()) {
			sb.append(",");
			sb.append(msg.substring(firstSpace, msg.length()));
		}
		sb.append("}");
		return sb.toString();
	}
	
}
