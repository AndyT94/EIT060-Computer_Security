package server;

import util.Command;

public class ResourceMonitor {
	private Capabilities cap;
	
	public ResourceMonitor() {
		cap = new Capabilities();
	}

	public synchronized void tryAccess(String subject, Command cmd) {
		
	}
}
