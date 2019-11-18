package pebblegame;


import java.util.ArrayList;


public class WhiteBag extends Bag {
    public WhiteBag(String name, int fullness, ArrayList<Integer> pebbles) {
        super(name, fullness, pebbles);
    }


    public static WhiteBag makeWhiteBag(String name) {
        return new WhiteBag(name, 0, new ArrayList<>());
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
}