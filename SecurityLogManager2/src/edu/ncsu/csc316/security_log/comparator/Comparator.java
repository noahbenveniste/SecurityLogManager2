package edu.ncsu.csc316.security_log.comparator;

/**
 * A general interface for implementing a comparator class
 * 
 * @author Noah Benveniste
 *
 * @param <E> Indicates that the comparator can be used to compare any object type
 */
public interface Comparator<E> {
	
	/**
	 * Method that compares two objects based on a user-defined natural ordering
	 * 
	 * @param e1 the first object
	 * @param e2 the second object
	 * 
	 * @return negative if e1 comes before e2 in the natural ordering, positive if e1 comes
	 * 		   after e2 in the natural ordering and zero if e1 and e2 are equal across the
	 * 		   fields being compared.
	 */
	public int compareTo(E e1, E e2);
}
