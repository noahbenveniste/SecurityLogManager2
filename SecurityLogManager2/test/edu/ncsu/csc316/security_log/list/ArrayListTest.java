package edu.ncsu.csc316.security_log.list;


import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

import edu.ncsu.csc316.security_log.comparator.Comparator;

/**
 * Unit tests for ArrayList
 * 
 * @author Noah Benveniste
 * @author Kevin Hildner
 */
public class ArrayListTest {

    /**
     * Test method for the ArrayList constructor.
     */
    @Test
    public void testArrayList() {
        // Test creating a valid ArrayList
        ArrayList<String> list = new ArrayList<String>();
        assertEquals(0, list.size());

        // Test that the list grows by adding at least 11 elements
        // Remember the list's initial capacity is 10
        list.add(0, "a");
        list.add(1, "b");
        list.add(2, "c");
        list.add(3, "d");
        list.add(4, "e");
        list.add(5, "f");
        list.add(6, "g");
        list.add(7, "h");
        list.add(8, "i");
        list.add(9, "j");
        list.add(10, "k");
        assertEquals(11, list.size());
    }

    /**
     * Test for the size() method
     */
    @Test
    public void testSize() {
        // Create the initial array
        ArrayList<String> list = new ArrayList<String>();
        // Check that initial size is zero
        assertEquals(0, list.size());
        // Add 3 elements
        list.add(0, "a");
        list.add(1, "b");
        list.add(2, "c");
        // Check that the size updated
        assertEquals(3, list.size());
        // Add more than the initial capacity and check that the size adjusts
        list.add(3, "d");
        list.add(4, "e");
        list.add(5, "f");
        list.add(6, "g");
        list.add(7, "h");
        list.add(8, "i");
        list.add(9, "j");
        list.add(10, "k");
        assertEquals(11, list.size());
        // Remove an element and check that the size adjusts
        list.remove(5);
        assertEquals(10, list.size());
    }

    /**
     * Test method for add(int, E)
     */
    @Test
    public void testAddIntE() {
        // Create an ArrayList
        ArrayList<String> list = new ArrayList<String>();
        // Check that initial size is zero
        assertEquals(0, list.size());
        // Test adding to an empty array list
        list.add(0, "a");
        list.add(1, "b");
        list.add(2, "c");
        assertEquals(3, list.size());
        // Test adding to the front of the list
        list.add(0, "d");
        assertEquals(4, list.size());
        assertEquals("d", list.get(0));
        assertEquals("a", list.get(1));
        assertEquals("b", list.get(2));
        assertEquals("c", list.get(3));
        // Test adding to the middle
        list.add(2, "e");
        assertEquals(5, list.size());
        assertEquals("d", list.get(0));
        assertEquals("a", list.get(1));
        assertEquals("e", list.get(2));
        assertEquals("b", list.get(3));
        assertEquals("c", list.get(4));
        // Test adding to the end
        list.add(5, "f");
        assertEquals(6, list.size());
        assertEquals("d", list.get(0));
        assertEquals("a", list.get(1));
        assertEquals("e", list.get(2));
        assertEquals("b", list.get(3));
        assertEquals("c", list.get(4));
        assertEquals("f", list.get(5));
        // Test adding a null element
        try {
            list.add(3, null);
            fail();
        } catch(NullPointerException e) {
            assertEquals("Cannot add null elements", e.getMessage());
        }
        // Test adding to an index below the boundary
        try {
            list.add(-1, "z");
            fail();
        } catch(IndexOutOfBoundsException e) {
            assertEquals("Index is outside the accepatble range", e.getMessage());
        }
        // Test adding to an index above the boundary
    }

    /**
     * Test method for remove()
     */
    @Test
    public void testRemoveInt() {
        // Create the initial array
        ArrayList<String> list = new ArrayList<String>();
        // Check that initial size is zero
        assertEquals(0, list.size());
        // Add 7 elements
        list.add(0, "a");
        list.add(1, "b");
        list.add(2, "c");
        list.add(3, "d");
        list.add(4, "e");
        list.add(5, "f");
        list.add(6, "g");
        //Try removing an element from an index less than 0
        try {
            list.remove(-1);
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals("Index is outside the accepatble range", e.getMessage());
        }
        //Try removing an element from an index greater than or equal to the ArrayList's size
        try {
            list.remove(7);
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals("Index is outside the accepatble range", e.getMessage());
        }
        //Remove an element at the front of the list
        list.remove(0);
        assertEquals(6, list.size());
        assertEquals("b", list.get(0));
        assertEquals("c", list.get(1));
        assertEquals("d", list.get(2));
        assertEquals("e", list.get(3));
        assertEquals("f", list.get(4));
        assertEquals("g", list.get(5));
        //Remove an element at the end of the list
        list.remove(5);
        assertEquals(5, list.size());
        assertEquals("b", list.get(0));
        assertEquals("c", list.get(1));
        assertEquals("d", list.get(2));
        assertEquals("e", list.get(3));
        assertEquals("f", list.get(4));
        //Remove an element from the middle of the list
        list.remove(2);
        assertEquals(4, list.size());
        assertEquals("b", list.get(0));
        assertEquals("c", list.get(1));
        assertEquals("e", list.get(2));
        assertEquals("f", list.get(3));
    }

    /**
     * Test method for get()
     */
    @Test
    public void testGetInt() {
        // Create the initial array
        ArrayList<String> list = new ArrayList<String>();
        // Check that initial size is zero
        assertEquals(0, list.size());
        // Add 5 elements
        list.add(0, "a");
        list.add(1, "b");
        list.add(2, "c");
        list.add(3, "d");
        list.add(4, "e");
        //Try getting a value at an index less than zero
        String s = null;
        try {
            s = list.get(-1);
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals("Index is outside the acceptable range", e.getMessage());
            assertNull(s);
        }
        //Try getting a value at an index outside the size of the array
        try {
            s = list.get(5);
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals("Index is outside the acceptable range", e.getMessage());
            assertNull(s);
        }
        //Get a value from the front of the array
        s = list.get(0);
        assertEquals(s, "a");
        //Get a value from the back of the array
        s = list.get(4);
        assertEquals(s, "e");
        //Get a value from the middle of the array
        s = list.get(2);
        assertEquals(s, "c");
    }

    /**
     * Test method for set()
     */
    @Test
    public void testSetIntE() {
        // Create the initial array
        ArrayList<String> list = new ArrayList<String>();
        // Check that initial size is zero
        assertEquals(0, list.size());
        // Add 5 elements
        list.add(0, "a");
        list.add(1, "b");
        list.add(2, "c");
        list.add(3, "d");
        list.add(4, "e");
        //Try getting a value at an index less than zero
        String s = null;
        try {
            s = list.set(-1, "z");
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals("Index is outside the acceptable range", e.getMessage());
            assertNull(s);
        }
        //Try getting a value at an index outside the size of the array
        try {
            s = list.set(5, "z");
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals("Index is outside the acceptable range", e.getMessage());
            assertNull(s);
        }
        //Try setting a null element
        try {
            list.set(0, null);
        } catch (NullPointerException e) {
            assertEquals("Cannot set null elements", e.getMessage());
            assertNull(s);
        }
        //Set a value at the front of the array
        s = list.set(0, "z");
        assertEquals(s, "a");
        assertEquals("z", list.get(0));
        //Set a value at the end of the array
        s = list.set(4, "q");
        assertEquals(s, "e");
        assertEquals("q", list.get(4));
        //Set a value in the middle of the array
        s = list.set(2, "x");
        assertEquals(s, "c");
        assertEquals("x", list.get(2));
    }

    /**
     * Tests addSorted()
     */
    @Test
    public void testAddSorted() {
        ArrayList<Integer> ints = new ArrayList<Integer>();
        assertEquals(0, ints.size());
        assertTrue(ints.addSorted(1));
        assertTrue(ints.addSorted(5));
        assertTrue(ints.addSorted(-1));
        assertTrue(ints.addSorted(0));
        assertTrue(ints.addSorted(1012));
        assertTrue(ints.addSorted(500));
        assertEquals(6, ints.size());
        int[] expected = {-1, 0, 1, 5, 500, 1012};
        for (int i = 0; i < ints.size(); i++) {
            assertTrue(expected[i] == ints.get(i));
        }
    }
    
    /**
     * Tests contains()
     */
    @Test
    public void testContains() {
        ArrayList<Integer> ints = new ArrayList<Integer>();
        assertEquals(0, ints.size());
        assertTrue(ints.addSorted(1));
        assertEquals(-1, ints.contains(-5, new IntComparator()));
        assertEquals(-1, ints.contains(10, new IntComparator()));
        assertTrue(ints.addSorted(5));
        assertTrue(ints.addSorted(-1));
        assertTrue(ints.addSorted(0));
        assertTrue(ints.addSorted(1012));
        assertTrue(ints.addSorted(500));
    }
    
    /**
     * Tests the sort() method
     */
    @Test
    public void testSort() {
        // Test sorting arrays with random ordering or things
        ArrayList<Integer> ints = new ArrayList<Integer>();
        ints.add(9);
        ints.add(1);
        ints.add(5);
        ints.add(7);
        ints.add(10);
        ints.add(2);
        ints.add(4);
        ints.add(6);
        ints.add(3);
        ints.add(8);
        
        ints.sort(new IntComparator());
        
        int[] expected1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        for (int i = 0; i < expected1.length; i++) {
            assertTrue(expected1[i] == ints.get(i));
        }
        
        ArrayList<String> list = new ArrayList<String>();
        list.add("q");
        list.add("g");
        list.add("t");
        list.add("c");
        list.add("u");
        list.add("v");
        list.add("f");
        list.add("w");
        list.add("a");
        list.add("o");
        list.add("i");
        list.add("j");
        list.add("z");
        list.add("n");
        list.add("p");
        list.add("h");
        list.add("l");
        list.add("y");
        list.add("d");
        list.add("r");
        list.add("m");
        list.add("e");
        list.add("k");
        list.add("s");
        list.add("x");
        list.add("b");
        
        list.sort(new StringComparator());
        
        String[] expected2 = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        for (int i = 0; i < expected2.length; i++) {
            assertEquals(expected2[i], list.get(i));
        }
        
        //Test sorting array with duplicates
        list = new ArrayList<String>();
        
    }
    
    /**
     * Tests growArray()
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
    public void testGrowArray() {
        ArrayList<Integer> a = new ArrayList<Integer>();
        Class c = a.getClass();
        Method method = null;
        try {
            method = c.getDeclaredMethod("growArray", null);
        } catch (NoSuchMethodException | SecurityException e) {
            fail(e.getMessage());
        }
        assertNotNull(method);
        method.setAccessible(true);
        try {
            method.invoke(a, null);
        } catch (IllegalAccessException e) {
            fail(e.getMessage());
        } catch (IllegalArgumentException e) {
            fail(e.getMessage());
        } catch (InvocationTargetException e) {
            fail(e.getMessage());
        }
        assertTrue(a.size() == 0);
    }
    
    /**
     * Tests addAll()
     */
    @Test
    public void testAddAll() {
        ArrayList<Integer> a = new ArrayList<Integer>();
        ArrayList<Integer> b = new ArrayList<Integer>();
        a.add(1);
        b.add(2);
        a.addAll(b);
        assertEquals(2, a.size());
        assertTrue(1 == a.get(0));
        assertTrue(2 == a.get(1));
    }
    
    /**
     * Comparator class for ints for testing sort() functionality
     * 
     * @author Noah Benveniste
     */
    private class IntComparator implements Comparator<Integer> {
    	
    	/**
    	 * Compares two integers
    	 * 
    	 * @param a the first integer
    	 * @param b the second integer
    	 * 
    	 * @return negative if a < b, positive if a > b, zero if a == b
    	 */
    	@Override
    	public int compareTo(Integer a, Integer b) {
    		return a - b;
    	}

    }
    
    /**
     * Comparator class for strings for testing sort() functionality
     * 
     * @author Noah Benveniste
     */
    private class StringComparator implements Comparator<String> {
    	
    	/**
    	 * Compares two strings alphabetically
    	 * 
    	 * @param a the first string
    	 * @param b the second string
    	 * 
		 * @return negative if a < b, positive if a > b, zero if a == b
    	 */
    	public int compareTo(String a, String b) {
    		return a.compareTo(b);
    	}
    }
    
}