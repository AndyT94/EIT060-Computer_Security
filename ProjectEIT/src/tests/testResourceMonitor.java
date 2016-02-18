package tests;

import org.junit.Before;
import org.junit.Test;

import server.Capabilities;
import server.ResourceMonitor;

public class testResourceMonitor {
	private ResourceMonitor rm;
	private Capabilities cap;
	
	@Before
	public void setUp() {
		rm = new ResourceMonitor();
	}
}
