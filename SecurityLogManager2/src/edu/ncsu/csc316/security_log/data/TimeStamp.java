package edu.ncsu.csc316.security_log.data;

/**
 * A class that represents a time stamp of the form "mm/dd/yyyy hh:mm:ssXX", XX = AM | PM
 * @author Noah Benveniste
 */
public class TimeStamp implements Comparable<TimeStamp> {
	
	/** */
    private String originalString;
    /** */
    private long val;
    
    /**
     * Parses a time stamp string into a single long integer
     * @param s of the form "mm/dd/yyyy hh:mm:ssXX", XX = AM | PM
     */
    public TimeStamp( String s ) {
        this.originalString = s;
        // s is of the form "mm/dd/yyyy hh:mm:ssXX", XX = AM | PM
        // Parse the string into a 12 digit long int for a single comparison
        long init = 100L;
        
        // Need to compare by year first. Multiply init by the year
        init = init * Integer.parseInt(s.substring(6, 10));
        
        // Add the digits for the month, multiply by 100 to get more digits at the end
        init = init + Integer.parseInt(s.substring(0, 2));
        init = init * 100;
        
        // Add days next
        init = init + Integer.parseInt(s.substring(3, 5));
        init = init * 100;
                
        // Get the hour in 24 hour format. If the string ends with AM and is 12:xx, subtract
        // 12 from the hour. If the string ends with PM and is NOT 12:xx, add 12.
        int hr = Integer.parseInt(s.substring(11, 13));
        String amPm = s.substring(19);
        if (amPm.equals("AM") && hr == 12) {
            hr -= 12;
        } else if (amPm.equals("PM") && hr != 12) {
            hr += 12;
        }
        init = init + hr;
        init = init * 100;
        
        // Add minutes next
        init = init + Integer.parseInt(s.substring(14, 16));
        init = init * 100;
        
        // Add seconds last
        init = init + Integer.parseInt(s.substring(17, 19));
        
        // Set the field
        this.val = init;
        
    }
    
    /**
     * Gets the integer value that the time stamp was parsed to
     * @return the integer representing the time stamp
     */
    public long getVal() {
        return this.val;
    }

    /**
     * Gets the original time stamp string
     * @return the original string
     */
    public String getOriginalString() {
        return this.originalString;
    }

    /**
     * Compares two time stamps
     * @param other the time stamp to compare to
     * @return a negative value if this time came before the other time,
     * positive if vice versa, zero if they are the same time
     */
    @Override
    public int compareTo( TimeStamp other ) {
        long result =  this.val - other.getVal();
        if (result < 0) {
            return -1;
        } else if (result > 0) {
            return 1;
        } else {
            return 0;
        }
    }

//    /* (non-Javadoc)
//	 * @see java.lang.Object#hashCode()
//	 */
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + (int) (val ^ (val >>> 32));
//		return result;
//	}
//
//	/* (non-Javadoc)
//	 * @see java.lang.Object#equals(java.lang.Object)
//	 */
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		TimeStamp other = (TimeStamp) obj;
//		if (val != other.val)
//			return false;
//		return true;
//	}

}
