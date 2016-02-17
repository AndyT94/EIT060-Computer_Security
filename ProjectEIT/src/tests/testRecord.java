package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import server.Doctor;
import server.Nurse;
import server.Patient;
import server.Record;

public class testRecord {
	private Record r;

	@Before
	public void setUp() {
		r = new Record(new Patient("9090909090", "Alice"));
	}

	@Test
	public void testgetPatient() {
		assertEquals("Alice", r.getPatient().getRealName());
	}

	@Test
	public void testPrintOneEntry() {
		r.addEntry(new Nurse("1", "Q"), new Doctor("2", "W"), "A", "feber");
		assertEquals("Patient: Alice\n\nEntry: 1\nDoctor: W\nNurse: Q\nDivision: A\nNotes: feber\n", r.toString());
	}

	@Test
	public void testPrintMultipleEntries() {
		r.addEntry(new Nurse("1", "Q"), new Doctor("2", "W"), "A", "feber");
		r.addEntry(new Nurse("4", "E"), new Doctor("5", "R"), "B", "förkylning");
		assertEquals(
				"Patient: Alice\n\nEntry: 1\nDoctor: W\nNurse: Q\nDivision: A\nNotes: feber\n\nEntry: 2\nDoctor: R\nNurse: E\nDivision: B\nNotes: förkylning\n",
				r.toString());
	}
}
