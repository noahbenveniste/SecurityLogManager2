package edu.ncsu.csc316.security_log.dictionary;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import edu.ncsu.csc316.security_log.comparator.ARComparator;
import edu.ncsu.csc316.security_log.data.LogEntry;
import edu.ncsu.csc316.security_log.io.SecurityLogIO;
import edu.ncsu.csc316.security_log.list.ArrayList;

/**
 * Unit tests for the HashTable class. Tests all major functionality
 * including insert and lookUp as well as auxiliary functionality
 * such as compressHash and rehash.
 * 
 * @author Noah Benveniste
 */
public class HashTableTest {

	/**
	 * Tests insert functionality
	 */
	@Test
	public void testInsert() {
		// Create a new hash table
		HashTable<Integer> ht = new HashTable<Integer>();
		
		// Insert some values into the table
		ht.insert(1);
		ht.insert(2);
		ht.insert(3);
		
		// Check that the size of the table increased
		assertEquals(3, ht.size());
	}
	
	/**
	 * Tests lookUp functionality
	 */
	@Test
	public void testLookUp() {
		// Test by building the log dictionary
		SecurityLogIO io = new SecurityLogIO();
		ArrayList<LogEntry> logs = null;
		
		try {
			logs = io.readLogEntriesFromFile("input/activityLog_small.txt");
		} catch (IOException e) {
			fail(e.getMessage());
		}
		
		String expected1 = "fzalcala, 04/26/2017 12:33:15PM, sort, ICD-9 Code 196\n" + 
				"fzalcala, 07/03/2017 12:36:05AM, sort, ICD-9 Code 196\n" + 
				"quhundley, 08/04/2017 11:01:45AM, sort, ICD-9 Code 196\n" + 
				"fzalcala, 08/10/2017 05:10:54AM, sort, ICD-9 Code 196\n" + 
				"fzalcala, 08/26/2017 08:15:06AM, sort, ICD-9 Code 196\n" + 
				"quhundley, 09/21/2017 08:50:13AM, import, office visit OV04312\n" + 
				"fzalcala, 10/24/2017 11:38:02AM, sort, ICD-9 Code 196\n" + 
				"fzalcala, 11/20/2017 11:38:22AM, sort, ICD-9 Code 196\n" + 
				"fzalcala, 05/04/2015 02:09:40PM, sort, ICD-9 Code 196\n" + 
				"fzalcala, 06/09/2015 06:26:04AM, sort, ICD-9 Code 196\n" + 
				"quhundley, 07/18/2015 07:57:42PM, sort, ICD-9 Code 196\n" + 
				"fzalcala, 10/04/2015 12:17:49PM, sort, ICD-9 Code 196\n" + 
				"quhundley, 02/04/2016 08:49:22AM, sort, ICD-9 Code 196\n" + 
				"fzalcala, 08/04/2016 06:57:34AM, resolve, message M2964\n" + 
				"fzalcala, 10/07/2016 07:08:47AM, sort, ICD-9 Code 196\n" + 
				"quhundley, 11/20/2016 02:07:54PM, sort, ICD-9 Code 196\n";
		
		assertEquals(16, logs.size());
		assertEquals("20150504140940", io.minTimeStamp + "");
		assertEquals("20171120113822", io.maxTimeStamp + "");
		
		String actual1 = "";
		for (int i = 0; i < logs.size(); i++) {
			actual1 = actual1 + logs.get(i) + "\n";
		}
		
		assertEquals(expected1, actual1);
		
		HashTable<LogEntry> ht = new HashTable<LogEntry>();
    	ArrayList<LogEntry> out = new ArrayList<LogEntry>();
		
    	// Reset static counter for total log entries within a time interval
    	LogEntry.total = 0;
    	
    	assertTrue(0 == LogEntry.total);
    	
    	// 3. Loop through the unsorted list of log entries
    	for (int i = 0; i < logs.size(); i++) {
    		// Get the current log entry
    		LogEntry curr = logs.get(i);
			// Increment counter that keeps track of total number of entries over the interval
			LogEntry.total++;
			// 3a. Look up the current entry
    		LogEntry lookedUp = ht.lookUp(curr);
    		if (lookedUp == null) {
    			// 3b. If lookUp returns null, insert the element into the table and the arraylist
    			ht.insert(curr);
    			out.add(curr); 
    		} else {
        		// 3c. If the element is already in the table, increment the frequency counter for that entry in the table
    			lookedUp.incrementFrequency();
    		}
    	}
    	
    	assertTrue(16 == LogEntry.total);
    	
    	out.sort(new ARComparator());
    	assertEquals(3, out.size());
    	
    	assertEquals("sort ICD-9 Code 196", out.get(0).getAction() + " " + out.get(0).getResource());
    	assertEquals(14, out.get(0).getFrequency());
    	assertEquals("import office visit OV04312", out.get(1).getAction() + " " + out.get(1).getResource());
    	assertEquals(1, out.get(1).getFrequency());
    	assertEquals("resolve message M2964", out.get(2).getAction() + " " + out.get(2).getResource());
    	assertEquals(1, out.get(2).getFrequency());
    	
	}
	
	/**
	 * Tests the rehash() method
	 */
	@Test
	public void testRehash() {
		HashTable<Integer> ht = new HashTable<Integer>();
		int oldLength = ht.getHashTableLength();
		ht.insert(1);
		ht.rehash();
		assertEquals(oldLength * 2, ht.getHashTableLength());
	}

}
