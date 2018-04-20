package edu.ncsu.csc316.security_log.data;

/**
 * A class that represents a log entry. Stores the user, time stamp, action
 * and resource associated with the log entry
 * 
 * @author Noah Benveniste
 */
public class LogEntry implements Comparable<LogEntry> {

	/** */
    private String user;
    /** */
    private String action;
    /** */
    private String resource;
    /** */
    private TimeStamp timeStamp;
    /** */
    private int hashCode;
    /** */
    private int freq;
    /** Keeps track of the total number of entries over a time interval */
    public static int total = 0;
    
    /**
     * Constructs a LogEntry
     * 
     * @param user the user associated with the log entry
     * @param timeStamp the time at which the log entry was made
     * @param action the action the user took
     * @param resource the resource that was affected by the action
     */
    public LogEntry( String user, String timeStamp, String action, String resource ) {
        this.user = user;
        this.timeStamp = new TimeStamp(timeStamp);
        this.action = action;
        this.resource = resource;
        this.hashCode = 0;
        this.freq = 1;
    }
    
    /**
     * Gets the user
     * 
     * @return the user
     */
    public String getUser() {
        return this.user;
    }
    
    /**
     * Gets the action
     * 
     * @return the action
     */
    public String getAction() {
        return this.action;
    }
    
    /**
     * Gets the resource
     * 
     * @return the resource
     */
    public String getResource() {
        return this.resource;
    }
    /**
     * Gets the time stamp
     * 
     * @return the time stamp
     */
    public TimeStamp getTimeStamp() {
        return this.timeStamp;
    }
    
    /**
     * 
     * @return
     */
    public int getFrequency() {
    	return freq;
    }
    
    /**
     * Increases the frequency counter by 1
     */
    public void incrementFrequency() {
    	freq++;
    }
    
    /**
     * Unused method; comparisons done via comparators instead
     * 
     * @param other the other log entry to compare
     * 
     * @return 0
     */
    @Override
    public int compareTo( LogEntry other ) {
        return 0;
    }
    
    /**
     * Used for testing, returns the log entry as it would appear in the input file
     * 
     * @return a string representation of the log entry
     */
    public String toString() {
    	return new StringBuilder().append(user)
    							  .append(", ")
    							  .append(timeStamp.getOriginalString())
    							  .append(", ")
    							  .append(action)
    							  .append(", ")
    							  .append(resource).toString();
    }

	/**
	 * Generates a hash code for this log entry based on action and resource.
	 * If the log entry is being hashed for the first time, save the hash
	 * code to save on subsequent compression operations during rehashing.
	 * 
	 * @return the hash code for this log entry
	 */
	@Override
	public int hashCode() {
		if (this.hashCode == 0) {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((action == null) ? 0 : action.hashCode());
			result = prime * result + ((resource == null) ? 0 : resource.hashCode());
			this.hashCode = result;
			return result;
		} else {
			return this.hashCode;
		}
	}

	/**
	 * Equals method that compares only action and resource
	 * 
	 * @param obj
	 * 
	 * @return
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LogEntry other = (LogEntry) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (resource == null) {
			if (other.resource != null)
				return false;
		} else if (!resource.equals(other.resource))
			return false;
		return true;
	}

}
