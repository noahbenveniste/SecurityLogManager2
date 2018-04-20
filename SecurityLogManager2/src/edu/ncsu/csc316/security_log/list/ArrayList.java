package edu.ncsu.csc316.security_log.list;

import java.util.Random;

import edu.ncsu.csc316.security_log.comparator.Comparator;

/**
 * A custom implemented array list class that is able to adjust size
 * automatically, elements can be added to the front, end or middle. Duplicate
 * elements in the array are automatically rejected form the ArrayList.
 * 
 * @param <E> Specifies that the ArrayList can contain any object type
 *            
 * @author Noah Benveniste
 * @author Kevin Hildner
 * 
 */
public class ArrayList<E extends Comparable<? super E>> {
    /**
     * The array's current size, based on the number of non-null elements present
     */
    private static final int INIT_SIZE = 1000;
    /** The underlying array for the ArrayList */
    private E[] list;
    /** The number of elements that has been placed in the array */
    private int size;
    /** The total capacity of the underlying array */
    private int capacity;

    /**
     * Constructs an empty ArrayList with a desired initial max capacity.
     * Useful if the client knows ahead of time how much space needs to
     * be allocated to the array. Can save on runtime by not needing to
     * generate a new array and copy the contents of the old array over
     * when capacity is reached.
     * @param capacity the maximum initial capacity of the array list
     */
    @SuppressWarnings("unchecked")
    public ArrayList(int capacity) {
        this.size = 0;
        this.list = (E[]) new Comparable[capacity];
        this.capacity = list.length;
    }

    /**
     * Constructor that initializes array capacity to a default size.
     */
    public ArrayList() {
        this(INIT_SIZE);
    }
    /**
     * Adds a desired element to the ArrayList at a specified index, right-shifting
     * necessary values
     * 
     * @param idx
     *            the zero-based index to add the new element at
     * @param element
     *            the element to add to the ArrayList
     * @throws NullPointerException
     *             if the added element is null
     * @throws IndexOutOfBoundsException
     *             if the index is less than zero or greater than the ArrayList's
     *             size
     * @throws IllegalArgumentException
     *             if the added element is a duplicate of an element already in the
     *             list
     */
    public void add(int idx, E element) {
        if (element == null) {
            throw new NullPointerException("Cannot add null elements");
        }
        if (idx < 0 || idx > this.size()) {
            throw new IndexOutOfBoundsException("Index is outside the accepatble range");
        }
        if (this.size() == this.capacity) { // Grow the array if list is full
            this.growArray();
        }
        if (idx == this.size()) { // Adding an element to the end of the list
            list[idx] = element;
        } else { // Add an element to the front or middle of the list
            // Right shift all values before the index to insert the new element
            for (int i = this.size; i > idx; i--) {
                list[i] = list[i - 1];
            }
            // Add the element to the desired index
            list[idx] = element;
        }
        // Increment the size of the ArrayList
        this.size++;
    }
    
    /**
     * Appends an element to the end of the list
     * @param e the element to add
     */
    public void add(E e) {
        this.add(this.size, e);
    }
    
    /**
     * Adds an element to the list in sorted order
     * @param e the element to add
     * @return true if it was added successfully, false otherwise
     */
    public boolean addSorted(E e) {
        // Special case of adding to an empty list
        if (this.size == 0) {
            list[0] = e;
            this.size++;
            return true;
        // Special case of inserting at the end of the list
        } else if (list[this.size - 1].compareTo(e) < 0) {
            list[size] = e;
            this.size++;
            return true;
        // Check that the array hasn't reached capacity
        } else if (this.size == this.capacity) {
            this.growArray();
        }
        for (int i = 0; i < this.size; i++) {
            if (list[i].compareTo(e) > 0) {
                // Right shift
                for (int j = this.size; j > i; j--) {
                    list[j] = list[j - 1];
                }
                list[i] = e;
                this.size++;
                return true;
            }
        }
        return false;
    }

    /**
     * Used to grow the array if size == capacity; Doubles the capacity by default
     */
    @SuppressWarnings("unchecked")
    private void growArray() {
        // Update capacity
        this.capacity *= 2;
        // Create a new object array of double the capacity of the current array
        E[] temp = (E[]) new Comparable[capacity];
        // Assign the elements from the old array to the same index in the new array
        for (int i = 0; i < this.size(); i++) {
            temp[i] = this.list[i];
        }
        // Assign the new array to the list field
        this.list = temp;
    }

    /**
     * Removes an element from the ArrayList at a specified index, left-shifting all
     * remaining elements and returning the removed element
     * 
     * @param idx
     *            indicates the index of the element to remove from the ArrayList
     * @return the removed element
     * @throws IndexOutOfBoundsException
     *             if the specified index is out of bounds
     */
    public E remove(int idx) {
        if (idx < 0 || idx >= this.size()) {
            throw new IndexOutOfBoundsException("Index is outside the accepatble range");
        }
        // Get the element at the specified index
        E temp = list[idx];
        for (int i = idx; i < this.size() - 1; i++) {
            list[i] = list[i + 1];
        }
        // Set the repeated element at the end of the list to null
        list[this.size() - 1] = null;
        // Decrement the size
        this.size--;
        // Return the removed element
        return temp;
    }

    /**
     * Overwrites the element at a specified index with a new element
     * 
     * @param idx
     *            the index in the array that is being over written
     * @param element
     *            the new value/object for that position in the array
     * @return the old element at the specified index
     * @throws NullPointerException
     *             if the added element is null
     * @throws IndexOutOfBoundsException
     *             if the index is less than zero or greater than the ArrayList's
     *             size
     * @throws IllegalArgumentException
     *             if the added element is a duplicate of an element already in the
     *             list
     */
    public E set(int idx, E element) {
        if (element == null) {
            throw new NullPointerException("Cannot set null elements");
        }
        if (idx < 0 || idx >= this.size()) {
            throw new IndexOutOfBoundsException("Index is outside the acceptable range");
        }
        E temp = list[idx];
        list[idx] = element;
        return temp;
    }

    /**
     * Gets an element at a specified index
     * 
     * @param idx the index to get the element at
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException
     *             if the specified index is out of bounds
     */
    public E get(int idx) {
        if (idx < 0 || idx >= this.size()) {
            throw new IndexOutOfBoundsException("Index is outside the acceptable range");
        }
        return this.list[idx];
    }

    /**
     * Getter for the size field
     * 
     * @return the size of the ArrayList i.e. how many elements it contains
     */
    public int size() {
        return this.size;
    }
    
    /**
     * Searches for an element in the list
     * Precondition: Only functions properly if the list is maintained in sorted order
     * @param e the element to search for
     * @return the index of the element, or -1 if not found
     */
    public int contains(E e, Comparator<E> c) {
        if (size == 0) {
            return -1;
        } else {
            return binarySearch(e, 0, size - 1, c);
        }
    }
    
    // TODO: Update to use comparators and find the first instance of e in the list, update tests
    
    /**
     * Recursive binary search utilized by contains()
     * @param e the element to search for
     * @return the index of the specified element if it is in the list, -1 if it isn't found.
     */
    private int binarySearch(E e, int low, int high, Comparator<E> c) {
        int pivot = (high + low) / 2;
        // Base case 1: size is 0, element was not found
        if (low > high) {
            return -1;
        // Base case 2: found the element at the pivot index
        } else if (c.compareTo(list[pivot], e) == 0) {
            return pivot;
        // Recursive case 1: e < list[pivot]: search sublist left of the pivot
        } else if (c.compareTo(e, list[pivot]) < 0) {
            return binarySearch(e, low, pivot - 1, c);
        // Recursive case 2: e > list[pivot]: search sublist right of the pivot
        } else if (c.compareTo(e, list[pivot]) > 0) {
            return binarySearch(e, pivot + 1, high, c);
        // Catch-all for unexpected failure/bugs.
        } else {
            throw new IllegalArgumentException("Binary search failed unexpectedly.");
        }
    }
    
    // TODO: update to use comparator, update tests
    
    /**
     * Sorts the list in ascending order
     * 
     * @param c
     */
    public void sort(Comparator<E> c)  {
        quickSort(c, 0, this.size - 1);
    }
    
    /**
     * A recursive quick sort algorithm.
     * Source of algorithm explanation: https://www.cp.eng.chula.ac.th/~vishnu/datastructure/QuickSort.pdf
     * @param low the lowest index of the subarray
     * @param high the highest index of the subarray
     */
    private void quickSort(Comparator<E> c, int low, int high) {
        // Base case 1: sub array with fewer than two elements
        if (high <= low) {
            return;
        // Base case 2: sub array with 2 elements
        } else if (high - (low + 1) == 0) {
            if (c.compareTo(list[low], list[high]) > 0) {
                E temp = list[low];
                list[low] = list[high];
                list[high] = temp;
            }
            return;
        }
        
        // Bounds for generating random pivot index
        int min = low + 1;
        int max = high;
        
        // Randomly generate a pivot index
        Random rand = new Random();
        int pivotIdx = rand.nextInt(max - min) + min;
        
        // Get the pivot value
        E pivot = list[pivotIdx];
        
        // Swap the pivot value with the first element in the array
        list[pivotIdx] = list[low];
        list[low] = pivot;
        
        // first points to the first value that is greater than the pivot. After all comparisons are done,
        // the value before first will correspond to a value less than the pivot
        int first = low + 1;
        for (int i = low + 1; i <= high; i++) {
            if (c.compareTo(list[i], pivot) < 0) {
                // Swap list[i] with list[first]
                E temp = list[i];
                list[i] = list[first];
                list[first] = temp;
                // Increment first
                first++;
            }
        }
        
        // Swap pivot with value before first
        list[low] = list[first - 1];
        list[first - 1] = pivot;
        
        // Recursive calls
        // pivot is located at index first - 1
        quickSort(c, low, first - 1); // subarray left of and including the pivot
        quickSort(c, first, high); // subarray right of the pivot
    }
    
    /**
     * Combines a passed list with this list
     * @param in the list to combine this list with
     */
    public void addAll(ArrayList<E> in) {
        for (int i = 0; i < in.size(); i++) {
            this.add(in.get(i));
        }
    }
    
}