package pebblegame;


import java.util.ArrayList;


public class WhiteBag extends Bag {
    private ArrayList<Integer> pebbles;


    public WhiteBag(String name, int max, int fullness) {
        super(name, fullness);
        this.pebbles = new ArrayList<>();
    }


    public static BlackBag emptyWhiteBag(WhiteBag W, BlackBag B) {
        for (int pebble : W.getPebbles()) {
            B.getPebbles().add(pebble);
        }
        W.getPebbles().clear();
        B.setFullness(B.getPebbles().size());
        return B;
    }


    public ArrayList<Integer> getPebbles() {
        return this.pebbles;
    }
}