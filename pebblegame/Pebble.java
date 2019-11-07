package pebblegame;

import java.util.Random; // used for random selection of weight 

public class Pebble {
    private int weight;
    // the amount of players

    // Pebble constructor
    public Pebble() {
        this.weight = randomNumGen.nextInt(100);
    }

    // Getter method for Weight
    public int getWeight() {
        return this.weight;
    } 

    static Random randomNumGen = new Random();
}