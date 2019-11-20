package pebblegame;

import java.util.ArrayList;

public class BlackBag extends Bag {

    public BlackBag(String name, ArrayList<Integer> pebbles) {
        super(name, pebbles);
    }

    
    // Method for instantiating a BlackBag
    public static BlackBag makeBlackBag(String name, ArrayList<Integer> pebbles) {
        return new BlackBag(name, pebbles);
    }
}