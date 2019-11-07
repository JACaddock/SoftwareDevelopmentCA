package pebblegame;


import java.util.ArrayList;

public class WhiteBag extends Bag {
    private static ArrayList<WhiteBag> whitebags = new ArrayList<>();
    private ArrayList<Pebble> pebbles;
    

    public WhiteBag(String name, int max, int fullness, boolean isEmpty) {
        super(name, max, fullness, isEmpty);
    }


    public WhiteBag(String name, int max, int fullness, boolean isEmpty, ArrayList<Pebble> pebbles) {
        super(name, max, fullness, isEmpty);
        this.pebbles = pebbles;
    }


    // Method for instantiating the 3 WhiteBags
    static ArrayList<WhiteBag> makeWhiteBags(int max) {
        WhiteBag A = new WhiteBag("A",max,0,true);
        whitebags.add(A);
        WhiteBag B = new WhiteBag("B",max,0,true);
        whitebags.add(B);
        WhiteBag C = new WhiteBag("C",max,0,true);
        whitebags.add(C);

        return whitebags;
    }


    public ArrayList<Pebble> getPebbles() {
        return this.pebbles;
    }
}