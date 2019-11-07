package pebblegame;

import java.util.Random; // used for random selection of weight 

public class Pebble {
    private int weight;
    // the amount of players
    private int number;

    public Pebble(int weight, int playerNum) {
        this.weight = weight;
        this.number = 11 * playerNum;
    }

    public int getWeight() {
        return this.weight;
    } 

    public int getNumber() {
        return this.number;
    }


    static Random randomNumGen = new Random();
}