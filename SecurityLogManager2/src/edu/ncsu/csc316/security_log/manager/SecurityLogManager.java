package edu.ncsu.csc316.security_log.manager;

import java.io.IOException;

import edu.ncsu.csc316.security_log.comparator.ARComparator;
import edu.ncsu.csc316.security_log.comparator.UserComparator;
import edu.ncsu.csc316.security_log.comparator.UserOnlyComparator;
import edu.ncsu.csc316.security_log.data.LogEntry;
import edu.ncsu.csc316.security_log.data.TimeStamp;
import edu.ncsu.csc316.security_log.dictionary.HashTable;
import edu.ncsu.csc316.security_log.io.SecurityLogIO;
import edu.ncsu.csc316.security_log.list.ArrayList;

/**
 * Manager class that contains methods for primary operations that program performs.
 * 
 * @author Noah Benveniste
 */
public class SecurityLogManager {
	/** Output for invalid time input */
	private static final String NO_ACTIVITY = "OperationalProfile[\n   No activity was recorded.\n]";
    /** Keeps track of log entries in file */
    private ArrayList<LogEntry> logEntryList;
    /** Used to read in log entries from file */
    private SecurityLogIO io;
    /** The earliest time in the log entry list, used for input verification */
    private long minTimeStamp;
    /** The latest time in the log entry list, used for input verification */
    private long maxTimeStamp;
    /** Whether or not logEntryList has been sorted by name */
    private boolean sorted;
    
    /**
     * Constructs the SecurityLogManager
     * 
     * @param fileName the name of the file to read
     * 
     * @throws IOException if the file is not formatted properly
     */
    public SecurityLogManager(String fileName) {
        io = new SecurityLogIO();
        try {
            logEntryList = io.readLogEntriesFromFile(fileName);
            minTimeStamp = io.minTimeStamp;
            maxTimeStamp = io.maxTimeStamp;
            sorted = false;
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
    
    /**
     * Generates the operational profile given a start time and end time
     * 
     * @param startTime the start time
     * @param endTime the end time
     * 
     * @return the operational profile as a single string
     */
    public String generateOperationalProfile( String startTime, String endTime ) {
        // 1. Validate the startTime and endTime by comparing them to
    	//    the min and max time stored when the input file was read in
    	TimeStamp start = new TimeStamp(startTime);
    	TimeStamp end = new TimeStamp(endTime);
    	
    	// 1a. startTime must be less than endTime
    	if (start.getVal() >= end.getVal()) {
    		return NO_ACTIVITY;
    	}
    	
    	// 1b. startTime must be less than the max time stamp read in
    	if (start.getVal() >= this.maxTimeStamp) {
    		return NO_ACTIVITY;
    	}
    	
    	// 1c. endTime must be greater than the min time stamp read in
    	if (end.getVal() <= this.minTimeStamp) {
    		return NO_ACTIVITY;
    	}
    	
    	// 2. Create an empty hash table and array list
    	HashTable<LogEntry> ht = new HashTable<LogEntry>();
    	ArrayList<LogEntry> out = new ArrayList<LogEntry>();
    	
    	// Reset static counter for total log entries within a time interval
    	LogEntry.total = 0;
    	
    	// 3. Loop through the unsorted list of log entries
    	for (int i = 0; i < logEntryList.size(); i++) {
    		// Get the current log entry
    		LogEntry curr = logEntryList.get(i);
    		//Check if its in the input time interval
    		if (curr.getTimeStamp().getVal() >= start.getVal() && curr.getTimeStamp().getVal() <= end.getVal()) {
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
    	}
    	
    	// 4. sort the arraylist using a comparator based on frequency, then action and resource
    	out.sort(new ARComparator());
    	
    	// 5. Generate output string
    	String startStr = "OperationalProfile[\n";
        String endStr = "]";
        StringBuilder sb = new StringBuilder(startStr);
    	
    	// 5a. For each entry in out, get the frequency, calculate percentage for the current action/resource
        for (int i = 0; i < out.size(); i++) {
        	// 5c. Append to output string
        	LogEntry curr = out.get(i);
        	double percentage = (((double) curr.getFrequency()) / LogEntry.total) * 100;
        	sb.append("   ")
        	  .append(curr.getAction())
        	  .append(" ")
        	  .append(curr.getResource())
        	  .append(": frequency: ")
        	  .append(curr.getFrequency())
        	  .append(", percentage: ");
        	
        	sb.append(String.format("%.1f%%\n", percentage));
        }
    	
    	// Return output string
        sb.append(endStr);
    	return sb.toString();
    }
    
    /**
     * Generates the user report
     * 
     * @param userID the user name of the user to generate the report for
     * 
     * @return the user report as a single string
     */
    public String getUserReport( String userID ) {
    	// First, sort the logEntryList (if it hasn't been sorted already by user)
    	if (!sorted) {
    		logEntryList.sort(new UserComparator());
    		sorted = true;
    	}
    	
    	/* Build output string */
    	
    	// Search for the user; first create a dummy entry with the given user
    	LogEntry dummy = new LogEntry(userID, "01/01/2000 12:00:00AM", "action", "resource");
    	int idx = logEntryList.contains(dummy, new UserOnlyComparator());
    	
    	String startStr = new StringBuilder("Activity Report for ")
    			.append(userID)
    			.append("[\n")
    			.toString();
        String endStr = "]";
        
        StringBuilder sb = new StringBuilder();
        
        // TODO: optimize this
        
        // If the user was not found by the binary search
        if (idx == -1) {
        	return sb.append(startStr)
        			 .append("   No activity was recorded.\n")
        			 .append(endStr)
        			 .toString();
        } else {
        	// If the user was found, start by looping to the right of the index until
        	// the indexed log entry has a different user. Append each of these to the
        	// right of the central string
//        	StringBuilder sbCurr = null;
//        	StringBuilder sbPrev = new StringBuilder();
        	
        	sb.append(startStr);
        	
        	for (int i = 0; i < logEntryList.size(); i++) {
        		LogEntry current = logEntryList.get(i);
        		if (current.getUser().equals(userID)) {
        			sb.append("   ")
				      .append(current.getTimeStamp().getOriginalString())
				      .append(" - ")
				      .append(current.getAction())
				      .append(" ")
				      .append(current.getResource())
				      .append("\n");
        		}
        	}
//        	int i = idx;
//        	LogEntry current = logEntryList.get(i);								
//        	while (current.getUser().equals(userID)) {
//        	     sbPrev.append("   ")
//				  	   .append(current.getTimeStamp().getOriginalString())
//				  	   .append(" - ")
//				  	   .append(current.getAction())
//				  	   .append(" ")
//				  	   .append(current.getResource())
//				  	   .append("\n");
//        		i++;
//        		// Break for out of bounds index
//        		if (i == logEntryList.size()) {
//        			break;
//        		}
//        		current = logEntryList.get(i);
//        	}
//        	
//        	// Next, loop to the left until a different user is found, appending to
//        	// the left of the above string
//        	i = idx - 1;
//        	current = logEntryList.get(i);
//        	while (current.getUser().equals(userID)) {
//        		sbCurr = new StringBuilder().append("   ")
// 				  	   .append(current.getTimeStamp().getOriginalString())
// 				  	   .append(" - ")
// 				  	   .append(current.getAction())
// 				  	   .append(" ")
// 				  	   .append(current.getResource())
// 				  	   .append("\n");
//        		sbCurr.append(sbPrev.toString());
//        		sbPrev = sbCurr;
//        		i--;
//        		// Break for out of bounds index
//        		if (i == -1) {
//        			break;
//        		}
//        		current = logEntryList.get(i);
//        	}
        	// Add the framing around the output
//            sb.append(startStr).append(sbPrev.toString()).append(endStr);
        	// Return the output
        	sb.append(endStr);
            return sb.toString();
        }
        
    }
    
}
