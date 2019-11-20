package pebblegame;

import java.util.ArrayList;

// Class exists for White and Black bag to extend
public class Bag {
    private ArrayList<Integer> pebbles;
    private String name;


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
}