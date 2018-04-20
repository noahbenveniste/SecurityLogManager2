package edu.ncsu.csc316.security_log.comparator;

/**
 * 
 * @author Noah Benveniste
 *
 * @param <E>
 */
public interface Comparator<E> {
	
	/**
	 * 
	 * @param e1
	 * @param e2
	 * 
	 * @return
	 */
	public int compareTo(E e1, E e2);
}
