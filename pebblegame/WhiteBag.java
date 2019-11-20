package pebblegame;


import java.util.ArrayList;


public class WhiteBag extends Bag {
    public WhiteBag(String name, ArrayList<Integer> pebbles) {
        super(name, pebbles);
    }


    public static WhiteBag makeWhiteBag(String name) {
        return new WhiteBag(name, new ArrayList<>());
    }


    public static void emptyWhiteBag(WhiteBag W, BlackBag B) {
        if (B.getPebbles().size() < 1) {
            if (W.getPebbles().size() > 0) {
                for (int pebble : W.getPebbles()) {
                    B.getPebbles().add(pebble);
                } W.getPebbles().clear();
            }
        }
    }
}