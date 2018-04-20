package edu.ncsu.csc316.security_log.comparator;

import edu.ncsu.csc316.security_log.data.LogEntry;

/**
 * Comparator used for binary searching a list of log entries solely by user
 * 
 * @author Noah Benveniste
 */
public class UserOnlyComparator implements Comparator<LogEntry> {

	/**
	 * 
	 * @param e1
	 * @param e2
	 */
	@Override
	public int compareTo(LogEntry e1, LogEntry e2) {
		return e1.getUser().compareTo(e2.getUser());
	}

}
