package pebblegame;

import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;


public class BagTest {
    Bag full;
    Bag result;
    Bag empty;
    ArrayList<Integer> before;
    ArrayList<Integer> after;

    
    /**
     * Creates an empty Bag, two ArrayLists of Integers with the same
     * contents, a Bag with an ArrayList attached.
     * @result an empty Bag and a Bag full with Integers 1 - 33.
     */
    @Before
    public void setUp() {
        empty = new Bag("empty", new ArrayList<>());
        
        
        before = new ArrayList<>();
        after = new ArrayList<>();
        
        for (int i = 1; i < 34; i++) {
            before.add(i);
            after.add(i);
        }
        
        full = new Bag("full", after);
    }
    
    
    /**
     * Sets all created values to null.
     */
    @After
    public void tearDown() {
        full = null;
        empty = null;
        result = null;
        before = null;
        after = null;
    }
    
    
    /**
     * Test of getName method, of class Bag.
     * @result the correct name of the Bag object
     */
    @Test
    public void testGetName() {
        assertEquals("empty", empty.getName());
        assertEquals("full", full.getName());
        
        System.out.println("The getName method is working");
    }

    
    /**
     * Test of getPebbles method, of class Bag.
     * @result the correct ArrayList of Integers from 
     * the Bag object
     */
    @Test
    public void testGetPebbles() {
        assertEquals(new ArrayList<>(), empty.getPebbles());
        assertEquals(before, full.getPebbles());
        
        System.out.println("The getPebbles method is working");
    }
    
    
    /**
     * Test of makeBag method, of class Bag.
     * @result the expected Name and empty ArrayList
     */
    @Test
    public void testMakeBag() {        
        result = new Bag("empty", new ArrayList<>());
        
        assertEquals(empty.getName(), result.getName());
        assertEquals(empty.getPebbles(), result.getPebbles());
        
        System.out.println("The makeBag method is working");
    }


    /**
     * Test of emptyBag method, of class Bag.
     * @result the full Bag is empty and the Bag is full
     */
    @Test
    public void testEmptyBag() {
        Bag.emptyBag(full, empty);
        
        // Checks that the WhiteBag
        assertEquals(new ArrayList<>(), full.getPebbles());
        assertEquals(before, empty.getPebbles());
        
        System.out.println("The emptyBag method is working");
    }
}
