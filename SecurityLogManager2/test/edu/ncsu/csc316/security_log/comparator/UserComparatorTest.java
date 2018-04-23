package edu.ncsu.csc316.security_log.comparator;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc316.security_log.data.LogEntry;

/**
 * Tests UserComparator
 * 
 * @author Noah Benveniste
 */
public class UserComparatorTest {

	/**
	 * Tests compareTo()
	 */
	@Test
	public void testCompareTo() {
		LogEntry dummy1 = new LogEntry("user", "01/01/2000 12:00:00AM", "action", "resource");
		LogEntry dummy2 = new LogEntry("user", "01/01/2000 12:00:00AM", "action1", "resource");
		LogEntry dummy3 = new LogEntry("user", "01/01/2000 12:00:00AM", "action", "resource1");
		
		UserComparator c = new UserComparator();
		
		assertTrue(c.compareTo(dummy1, dummy2) < 0);
		assertTrue(c.compareTo(dummy1, dummy3) < 0);
	}

}
