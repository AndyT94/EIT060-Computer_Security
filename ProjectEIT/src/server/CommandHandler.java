package server;

public class CommandHandler {
	private ResourceMonitor rm;
	private String user;
	private String name;

	public CommandHandler(String subject) {
		rm = new ResourceMonitor();
		String[] usertype = subject.split("=");
		user = usertype[1].substring(1, usertype[1].length());
		name = usertype[2].split("\"")[0];
	}

	public void processCommand(String clientMsg) {
		//process Command send to resorcemonitor
		
	}
}
