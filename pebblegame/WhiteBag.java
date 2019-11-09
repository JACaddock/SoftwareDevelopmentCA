package pebblegame;


import java.util.ArrayList;

public class WhiteBag extends Bag {
    private static ArrayList<WhiteBag> whitebags = new ArrayList<>();
    private ArrayList<Integer> pebbles;
    

    public WhiteBag(String name, int max, int fullness, boolean isEmpty) {
        super(name, max, fullness, isEmpty);
    }


    public WhiteBag(String name, int max, int fullness, boolean isEmpty, ArrayList<Integer> pebbles) {
        super(name, max, fullness, isEmpty);
        this.pebbles = pebbles;
    }


    // Method for instantiating the 3 WhiteBags
    static ArrayList<WhiteBag> makeWhiteBag(String name, int max) {
        WhiteBag A = new WhiteBag(name,max,0,true);
        whitebags.add(A);

        return whitebags;
    }


    public ArrayList<Integer> getPebbles() {
        return this.pebbles;
    }
}