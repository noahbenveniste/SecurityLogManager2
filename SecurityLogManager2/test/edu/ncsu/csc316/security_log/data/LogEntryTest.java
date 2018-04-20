package edu.ncsu.csc316.security_log.data;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 
 * @author Noah Benveniste
 */
public class LogEntryTest {

	/**
	 * 
	 */
	@Test
	public void testCompareTo() {
		LogEntry dummy = new LogEntry("user", "01/01/2000 12:00:00AM", "action", "resource");
		LogEntry other = dummy;
		assertEquals(0, dummy.compareTo(other));
	}

}
