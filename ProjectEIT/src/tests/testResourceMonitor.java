package tests;

import org.junit.Before;
import org.junit.Test;

import server.ResourceMonitor;

public class testResourceMonitor {
	private ResourceMonitor rm;
	
	@Before
	public void setUp() {
		rm = new ResourceMonitor();
	}
}
