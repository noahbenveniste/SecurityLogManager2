package edu.ncsu.csc316.security_log.io;

import java.io.FileReader;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

import edu.ncsu.csc316.security_log.data.LogEntry;
import edu.ncsu.csc316.security_log.list.ArrayList;

/**
 * Used to read a file of log entries and generate a list of LogEntry objects
 * 
 * @author Noah Benveniste
 */
public class SecurityLogIO {
	
	/** */
	public long minTimeStamp;
	/** */
	public long maxTimeStamp;
    
    /**
     * Parses a file of log entries and generates an unsorted list of
     * LogEntry objects
     * 
     * @param fileName the name of the file containing log entries
     * @return a LogEntryList containing LogEntry objects that correspond to each log
     * entry in the file. The list is unsorted i.e. in the same order as the file
     * @throws IOException if a line is not formatted properly
     */
    public ArrayList<LogEntry> readLogEntriesFromFile( String fileName ) throws IOException {
    	minTimeStamp = 0;
    	maxTimeStamp = 0;
    	
    	// TODO: add some way of determining the minimum and maximum timestamps when doing file io
    	
//        BufferedReader reader = new BufferedReader(new FileReader(fileName));
//        ArrayList<LogEntry> list = reader.lines() // add .parallel() to try multi threaded (could be faster)
//        .skip(1) // Skips first line
//        .map(this::readLogEntry)
//        .collect(ArrayList<LogEntry>::new, ArrayList<LogEntry>::add, ArrayList<LogEntry>::addAll);
//        reader.close();
//        return list;
    	Scanner f = new Scanner(new FileReader(fileName));
    	ArrayList<LogEntry> logs = new ArrayList<LogEntry>();
    	
    	// Skip the first line
    	f.nextLine();
    	
    	while (f.hasNextLine()) {
    		try {
    			// Parse the current line to create a log entry
    			LogEntry curr = readLogEntry(f.nextLine());
    			
    			// If the log entry parsed is the first one, initialize the min and max time stamps
    			if (logs.size() == 0) {
    				minTimeStamp = curr.getTimeStamp().getVal();
    				maxTimeStamp = curr.getTimeStamp().getVal();
    			}
    			
    			// Update min and max time stamps
    			if (curr.getTimeStamp().getVal() > maxTimeStamp) {
    				maxTimeStamp = curr.getTimeStamp().getVal();
    			} else if (curr.getTimeStamp().getVal() < minTimeStamp) {
    				minTimeStamp = curr.getTimeStamp().getVal();
    			}
    			
    			// Add the log to the output list
    			logs.add(curr);
    		} catch (IllegalArgumentException e) {
    			f.close();
    			throw new IllegalArgumentException("Error when reading input file");
    		}
    	}
    	f.close();
    	return logs;
    }
    
    /**
     * Parses a single string and generates a LogEntry object
     * 
     * @param line the line to parse
     * @return a LogEntry with the data contained in the line
     */
    private LogEntry readLogEntry( String line ) {
        String user = null;
        String timeStamp = null;
        String action = null;
        String resource = null;
        
        StringTokenizer tokenizer = new StringTokenizer(line, ",");
        
        LogEntry log = null;
        
        try {
            
            user = tokenizer.nextToken();
            timeStamp = tokenizer.nextToken();
            action = tokenizer.nextToken();
            resource = tokenizer.nextToken();

            // timeStamp, action and resource all have an extra leading whitespace. Manually truncate it.
            log = new LogEntry(user, timeStamp.substring(1), action.substring(1), resource.substring(1));
            
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException();
        }
        return log;
    }
    
}
