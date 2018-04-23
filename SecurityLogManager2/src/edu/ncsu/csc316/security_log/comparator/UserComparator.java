package edu.ncsu.csc316.security_log.comparator;

import edu.ncsu.csc316.security_log.data.LogEntry;

/**
 * Compares log entry objects by user first to sort log entries
 * into groupings corresponding to the same user.
 * 
 * @author Noah Benveniste
 */
public class UserComparator implements Comparator<LogEntry> {

	/**
	 * Sorts by user, timestamp, action, then resource
	 * 
	 * @param e1 the first entry
	 * @param e2 the second entry
	 * 
	 * @return negative if e1 < e2, positive if e1 > e2, zero if e1 == e2
	 */
	@Override
	public int compareTo(LogEntry e1, LogEntry e2) {
		
		if (!e1.getUser().equals(e2.getUser())) {
			return e1.getUser().compareTo(e2.getUser());
		} else if (e1.getTimeStamp().compareTo(e2.getTimeStamp()) != 0) {
            return e1.getTimeStamp().compareTo(e2.getTimeStamp());
        } else if (!e1.getAction().equals(e2.getAction())) {
            return e1.getAction().compareTo(e2.getAction());
        } else {
            return e1.getResource().compareTo(e2.getResource());
        } 
		
	}

}
