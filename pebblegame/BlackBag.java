package pebblegame;


import java.util.ArrayList;

public class BlackBag extends Bag {
    private static ArrayList<BlackBag> blackbags = new ArrayList<>();
    private ArrayList<Pebble> pebbles;


    public BlackBag(String name, int max, int fullness, boolean isEmpty, ArrayList<Pebble> pebbles) {
        super(name, max, fullness, isEmpty);
        this.pebbles = pebbles;
    }

    
    // Method for instantiating the 3 BlackBags
    static ArrayList<BlackBag> makeBlackBags(int max) {
        ArrayList<Pebble> pebblesx = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            pebblesx.add(spawnPebble());
        }
        BlackBag X = new BlackBag("X",max,max,false,pebblesx);
        blackbags.add(X);

        ArrayList<Pebble> pebblesy = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            pebblesy.add(spawnPebble());
        }
        BlackBag Y = new BlackBag("Y",max,max,false,pebblesy);
        blackbags.add(Y);

        ArrayList<Pebble> pebblesz = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            pebblesz.add(spawnPebble());
        }
        BlackBag Z = new BlackBag("Z",max,max,false,pebblesz);
        blackbags.add(Z);

        return blackbags;
    }


    public ArrayList<Pebble> getPebbles() {
        return this.pebbles;
    }

    private static Pebble spawnPebble() {
        return new Pebble();
    }
}