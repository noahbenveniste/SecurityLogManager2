package edu.ncsu.csc316.security_log.comparator;

import edu.ncsu.csc316.security_log.data.LogEntry;

/**
 * 
 * @author Noah Benveniste
 */
public class ARComparator implements Comparator<LogEntry> {

	/**
	 * Sorts by frequency, action, then resource
	 * 
	 * @param e1
	 * @param e2
	 * 
	 * @return
	 */
	@Override
	public int compareTo(LogEntry e1, LogEntry e2) {
		if (e1.getFrequency() != e2.getFrequency()) {
			return e2.getFrequency() - e1.getFrequency();
		} else if (!e1.getAction().equals(e2.getAction())) {
			return e1.getAction().compareTo(e2.getAction());
		} else {
			return e1.getResource().compareTo(e2.getResource());
		}
	}
	
}
