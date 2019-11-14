package pebblegame;


import java.util.ArrayList;


public class WhiteBag extends Bag {
    private ArrayList<Integer> pebbles;


    public WhiteBag(String name, int max, int fullness) {
        super(name, fullness);
        this.pebbles = new ArrayList<>();
    }


    public static void emptyWhiteBag(WhiteBag W, BlackBag B) {
        if (W.getPebbles().size() > 0) {
            for (int pebble : W.getPebbles()) {
                B.getPebbles().add(pebble);
            }
            W.getPebbles().clear();
            B.setFullness(B.getPebbles().size());
        }
    }


    public ArrayList<Integer> getPebbles() {
        return this.pebbles;
    }
}