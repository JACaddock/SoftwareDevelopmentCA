package pebblegame;

import java.util.ArrayList;

// Class exists for White and Black bag to extend
public class Bag {
    private ArrayList<Integer> pebbles;
    private int fullness;
    private String name;


    public Bag(String name, int fullness, ArrayList<Integer> pebbles) {
        this.name = name;
        this.fullness = fullness;
        this.pebbles = pebbles;
    }


    // Getter methods
    public String getName(){
        return this.name;
    }


    public int getFullness() {
        return this.fullness;
    }

    public ArrayList<Integer> getPebbles() {
        return this.pebbles;
    }

    
    public void setFullness(int f) {
        fullness = f;
    }
}