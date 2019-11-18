package pebblegame;

import java.util.ArrayList;

public class BlackBag extends Bag {

    public BlackBag(String name, int fullness, ArrayList<Integer> pebbles) {
        super(name, fullness, pebbles);
    }

    
    // Method for instantiating a BlackBag
    public static BlackBag makeBlackBag(String name, int min, ArrayList<Integer> pebbles) {
        return new BlackBag(name, pebbles.size(), pebbles);
    }
}