package pebblegame;


import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;


public class PebbleGameTest {
    PebbleGame.Player P;
    ArrayList<Integer> hand;
    Bag B;
    Bag W;
    ArrayList<Integer> empty;
    ArrayList<Integer> full;
    Object lock;
    Method takePebble;
    Method discardPebble;
    
    
    /**
     * Creates the lock Object, Player object, pebbles ArrayList of int
     * and two Bags, one empty and one full - Also gets Methods using
     * reflection.
     * @throws NoSuchMethodException
     * @result Player object, bags and methods all set up for testing
     */
    @Before
    public void setUp() throws NoSuchMethodException {
        lock = new Object();
        P = new PebbleGame.Player(lock);
        hand = P.getHand();
        
        full = new ArrayList<>();
        for (int i = 1; i < 12; i++) {
            full.add(i);
        }
        
        B = new Bag("Btest", full);
        W = new Bag("Wtest", new ArrayList<>());
        
        takePebble = P.getClass().getDeclaredMethod("takePebble", Bag.class);
        discardPebble = P.getClass().getDeclaredMethod("discardPebble", Bag.class);
    }
    
    
    /**
     * Sets all created values to null.
     */
    @After
    public void tearDown() {
        P = null;
        hand = null;
        B = null;
        W = null;
        empty = null;
        full = null;
        lock = null;
        takePebble = null;
        discardPebble = null;
    } 
    
    
    /**
     * Test of takePebble method, of nested class Player.
     * @result Player with pebble and Bag with one less pebble
     */
    @Test
    public void testTakePebble() {
        takePebble.setAccessible(true);
        
        try {
            takePebble.invoke(P, B);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(PebbleGameTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        assertEquals(1, hand.size());
        assertEquals(10, B.getPebbles().size());
        
        System.out.println("The takePebble method is working");
    }
    
    
    /**
     * Test of discardPebble method, of nested class Player.
     * @result Player loses pebble and Bag gains that pebble
     */
    @Test
    public void testDiscardPebble() {
        discardPebble.setAccessible(true);
        P.getHand().add(1);
        
        try {
            discardPebble.invoke(P, W);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(PebbleGameTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        assertEquals(0, hand.size());
        
        // Checks that the pebble in W is of 
        // the same weight as the one discarded
        assertSame(1, W.getPebbles().get(0));
        
        System.out.println("The discardPebble method is working");
    }
}
