package pebblegame;


import java.util.ArrayList;

public class WhiteBag extends Bag {
    private ArrayList<Integer> pebbles;
    

    public WhiteBag(String name, int max, int fullness, boolean isEmpty) {
        super(name, max, fullness, isEmpty);
    }


    public WhiteBag(String name, int max, int fullness, boolean isEmpty, ArrayList<Integer> pebbles) {
        super(name, max, fullness, isEmpty);
        this.pebbles = pebbles;
    }


    public ArrayList<Integer> getPebbles() {
        return this.pebbles;
    }
}