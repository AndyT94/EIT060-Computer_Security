package tests;

import static org.junit.Assert.*;
import org.junit.Test;

import util.Format;

public class testFormat {

	@Test
	public void testEncodeNoOption() {
		String msg = Format.encode("read");
		assertEquals("{\"command\":\"read\"}", msg);
	}
	
	@Test
	public void testEncodeWithOption() {
		String msg = Format.encode("delete record:ALICE");
		assertEquals("{\"record\":\"ALICE\",\"command\":\"delete\"}", msg);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testEncodeMissingValue() {
		Format.encode("read record:");
	}
}
