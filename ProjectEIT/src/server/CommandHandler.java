package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class CommandHandler {
	private ResourceMonitor rm;
	private String user;
	private String name;
	private PrintWriter out;
	private BufferedReader in;

	public CommandHandler(String subject, PrintWriter out, BufferedReader in) {
		rm = new ResourceMonitor();
		this.out = out;
		this.in = in;
		String[] usertype = subject.split("=");
		user = usertype[1].substring(1, usertype[1].length());
		name = usertype[2].split("\"")[0];
	}

	public void processCommand(String clientMsg) throws IOException {
		if (clientMsg.toLowerCase().equals("read")) {
			out.println("Who or what division do you want to read?");
			String target = in.readLine();
			System.out.println(target);
			//CALL TO RM
		} else if (clientMsg.toLowerCase().equals("write")){
			
		}
	}
}
