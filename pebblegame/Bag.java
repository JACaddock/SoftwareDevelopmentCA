package pebblegame;


public class Bag {
    private boolean isFull;
    private boolean isEmpty;
    private int max = PebbleGame.pebbleMax;
    private int fullness;


    public int getMax() {
        return this.max;
    }


    public boolean getIsEmpty() {
        return this.isEmpty;
    }


    public boolean getIsFull() {
        return this.isFull;
    }


    public int getFullness() {
        return this.fullness;
    }
    
}