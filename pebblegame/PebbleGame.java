package pebblegame;

import java.util.ArrayList;
import java.util.Scanner;

public class PebbleGame {
    //Arrays containing the varies objects
    private static ArrayList<Player> players = new ArrayList<>();

    public static void main(String[] args) {
        // main - For testing atm
        try {
            Scanner input = new Scanner(System.in);
            System.out.print("Please Enter the Number of players? : ");
            int number = input.nextInt();
            input.close();
            System.out.println(number + " players");
            startGame(number);
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Please Enter a valid Integer");
        }    
    }

    // The startup of the game, creates players, bags and pebbles
    private static void startGame(int number) {
        for (int i = 0; i < number; i++) {
            players.add(makePlayer());
        }

        int maxPebbles = 11*players.size();

        ArrayList<BlackBag> blackbags = BlackBag.makeBlackBags(maxPebbles);
        for (int i = 0; i < blackbags.size(); i++) {
            BlackBag XYZ = blackbags.get(i);
            System.out.println(XYZ.getName());
        }
        
        ArrayList<WhiteBag> whitebags = WhiteBag.makeWhiteBags(maxPebbles);
        for (int i = 0; i < whitebags.size(); i++) {
            WhiteBag ABC = whitebags.get(i);
            System.out.println(ABC.getName());      
        }
    }

    // Player static nested class
    static class Player {
        static int id = 0;
        private String name;
        private int score;

        Player() {
            score = 0;
            id += 1;
            this.name = "Player" + id;
        }

        String getName() {
            return name;
        }

        int getScore() {
            return score;
        }
    }


    // Used to create a new Player
    private static Player makePlayer() {
        return new Player();
    }


    // Method that takes a pebble randomly from a black bag 
    /* private static Player takePebble() {
        return null;
    } */
}