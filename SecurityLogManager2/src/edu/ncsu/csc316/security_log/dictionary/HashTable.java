package edu.ncsu.csc316.security_log.dictionary;

import edu.ncsu.csc316.security_log.list.LinkedAbstractList;

/**
 * An array-based hash table implementation utilizing separate chaining
 * and golden ratio hashing. Can achieve O(1) lookups by sacrificing on
 * space efficiency.
 * 
 * @author Noah Benveniste
 *
 * @param <E> Indicates that the table can work with any object type
 */
public class HashTable<E> {
	
	/** The inverse of the number phi, used by compression function */
	private static final double PHI_INVERSE = ((1 + Math.sqrt(5)) / 2) - 1;
	/** Initialize the table to 2^12; makes golden ratio hashing faster */
	private static final int INIT_SIZE = 2 ^ 14;
	/** Load factor threshold at which the table needs to be rehashed */
	private static final double MAX_LOAD_FACTOR = 0.75;
	
	/** The underlying array for the hash table */
	private LinkedAbstractList<E>[] table;
	/** Number of elements currently in the hash table */
	private int n;
	/** Hash table capacity */
	private int m;
	/** Used to determine when the rehash() function needs to be called; defined as n/m */
	private double loadFactor;

	/**
	 * Constructs a new generic HashTable with some initial default capacity
	 */
	@SuppressWarnings("unchecked")
	public HashTable() {
		table = new LinkedAbstractList[INIT_SIZE];
		this.n = 0;
		this.m = table.length;
	}
	
	/**
	 * Inserts the generic value E into the hash table
	 *
	 * @param value - the value to insert into the hash table
	 */
	public void insert(E value) {
		// Recalculate the load factor based on current n and m to see if the table needs to be rehashed
		refreshLoadFactor();
		
		// Check if the table needs to be rehashed
		if (loadFactor >= MAX_LOAD_FACTOR) {
			rehash();
		}
		
		// Hash value, apply compression function
		int hash = value.hashCode();
		int idx = compressHash(hash);
		
		// Check the index corresponding to the compressed hash
		if (table[idx] == null) {
			// If the index contains null, create a new LinkedList, insert it into the index
			table[idx] = new LinkedAbstractList<E>();	
		}
		
		// Grab the linked list from the index, insert the element to the front
		table[idx].add(0, value);
		
		// Increment the size counter
		n++;
	}
	
	/**
	 * Compresses the hash code for some arbitrary key down to a value in the
	 * 0 to m-1, where m is the current size of the hash table.
	 * 
	 * @param f hash code corresponding to some arbitrary key
	 * 
	 * @return an integer in the range 0 ... m-1
	 */
	public int compressHash(int f) {
		return (int) Math.floor(m * (f * PHI_INVERSE - Math.floor(f * PHI_INVERSE)));
	}
	
	/**
	 * Recalculates the load factor of the table before an element is added
	 * to determine if the table needs to be rehashed.
	 */
	private void refreshLoadFactor() {
		this.loadFactor = ((double) size()) / getHashTableLength();
	}
	
	/**
	 * Used to create a new hash table with a larger capacity once the load
	 * factor reaches a set threshold.
	 */
	@SuppressWarnings("unchecked")
	public void rehash() {
		// Store the old table to copy over
		LinkedAbstractList<E>[] old = table;
		
		// Create a new generic array of double the current capacity
		m *= 2;
		n = 0;
		table = new LinkedAbstractList[m];
		
		// Loop through the old hash table grabbing each bucket.
		// For each bucket, loop through its contents. Grab each element
		// and insert it into the new hash table
		for (int i = 0; i < old.length; i++) {
			// Check that the bucket had an actual list in it to avoid NPE
			if (old[i] != null) {
				LinkedAbstractList<E>.ListNode bucket = old[i].front;
				while (bucket != null) {
					E curr = bucket.data;
					this.insert(curr);
					bucket = bucket.next;
				}
			}
		}
	}
	
	/**
	 * Finds the value E in the hash table. Returns the value E
	 * if the value was found in the hash table. If the value is
	 * not in the hash table, return null.
	 *
	 * @param value - the value to search for in the hash table
	 * @return the reference to the value in the hash table, or null if the value 
	 *              is not in the hash table
	 */
	public E lookUp(E value) {
	    int idx = compressHash(value.hashCode());
	    if (table[idx] == null) {
	    	return null;
	    } else {
	    	// Iterate through the bucket return a reference to the object matching value
	    	LinkedAbstractList<E>.ListNode bucket = table[idx].front;
	    	while (bucket != null) {
				E curr = bucket.data;
				if (curr.equals(value)) {
					return curr;
				} else {
					bucket = bucket.next;
				}
			}
	    	// input hashed to the same hash as the bucket, but was not in the bucket
	    	return null;
	    }
	}
	
	/**
	 * Returns the number of values in the hash table
	 * 
	 * @return the number of values in the hash table
	 */
	public int size() {
		return n;
	}
	
	/**
	 * Returns the length/capacity of the hash table
	 * 
	 * @return the length/capacity of the hash table
	 */
	public int getHashTableLength() {
	    return m;
	}

}
