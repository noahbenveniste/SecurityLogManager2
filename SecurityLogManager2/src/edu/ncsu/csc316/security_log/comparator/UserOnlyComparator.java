package edu.ncsu.csc316.security_log.comparator;

import edu.ncsu.csc316.security_log.data.LogEntry;

/**
 * Comparator used for binary searching a list of log entries solely by user
 * 
 * @author Noah Benveniste
 */
public class UserOnlyComparator implements Comparator<LogEntry> {

	/**
	 * Sorts alphabetically by user
	 * 
	 * @param e1 the first entry
	 * @param e2 the second entry
	 * 
	 * @return negative if e1 < e2, positive if e1 > e2, zero if e1 == e2
	 */
	@Override
	public int compareTo(LogEntry e1, LogEntry e2) {
		
		return e1.getUser().compareTo(e2.getUser());
		
	}

}
