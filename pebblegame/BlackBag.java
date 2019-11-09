package pebblegame;


//import java.io.BufferedReader;
//import java.io.FileReader;
import java.util.ArrayList;
//import java.util.Scanner;

public class BlackBag extends Bag {
    private static ArrayList<BlackBag> blackbags = new ArrayList<>();
    private ArrayList<Integer> pebbles;


    public BlackBag(String name, int max, int fullness, boolean isEmpty, ArrayList<Integer> pebbles) {
        super(name, max, fullness, isEmpty);
        this.pebbles = pebbles;
    }

    
    // Method for instantiating the 3 BlackBags
    static ArrayList<BlackBag> makeBlackBag(String name,int max) {
        ArrayList<Integer> pebbles = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            pebbles.add(1);
        }
        BlackBag X = new BlackBag(name, max, pebbles.size(), false, pebbles);
        blackbags.add(X);

        return blackbags;
    }


    public ArrayList<Integer> getPebbles() {
        return this.pebbles;
    }
}