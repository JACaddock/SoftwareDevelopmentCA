package pebblegame;

import java.util.ArrayList;

// Class exists for White and Black bag to extend
public class Bag {
    private final ArrayList<Integer> pebbles;
    private final String name;


    public Bag(String name, ArrayList<Integer> pebbles) {
        this.name = name;
        this.pebbles = pebbles;
    }


    // Getter methods
    public String getName(){
        return this.name;
    }
    

    public ArrayList<Integer> getPebbles() {
        return this.pebbles;
    }
        
    
    public static void emptyBag(Bag W, Bag B) {
        if (B.getPebbles().size() < 1) {
            if (W.getPebbles().size() > 0) {
                for (int pebble : W.getPebbles()) {
                    B.getPebbles().add(pebble);
                } W.getPebbles().clear();
            }
        }
    }
}