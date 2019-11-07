package pebblegame;

import java.util.Random; // used for random selection of weight 

public class Pebble {
    private int weight;
    // the amount of players

    public Pebble() {
        this.weight = randomNumGen.nextInt(100);
    }

    public int getWeight() {
        return this.weight;
    } 

    static Random randomNumGen = new Random();
}