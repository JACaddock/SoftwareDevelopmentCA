package pebblegame;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class PebbleGame {
    //Arrays containing the varies objects
    private static ArrayList<Player> players = new ArrayList<>();
    private static ArrayList<WhiteBag> whitebags = new ArrayList<>();
    private static ArrayList<BlackBag> blackbags = new ArrayList<>();

    public static void main(String[] args) {
        // main - For testing atm
        try {
            Scanner playerNum = new Scanner(System.in);
            System.out.print("Please Enter the Number of players? : ");
            int number = playerNum.nextInt();
            playerNum.close();
            System.out.println(number + " players");

            // Instantiates a lot of the key objects
            startGame(number);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Please Enter a valid Integer");
        }  

        // Currently players take turns to takePebbles but 
        // We need them to happen at the same time w/threading
        for (Player P : players) {
            int rBag = r.nextInt(3);
            BlackBag XYZ = blackbags.get(rBag);
            for (int i = 0; i < 10; i++) {
                takePebble(P, XYZ);
            }
            System.out.println(P.getName() + " " + P.getScore());
        }

        // Prints the name and fullness of every black bag
        System.out.println(blackbags.get(0).getName() + blackbags.get(0).getFullness());
        System.out.println(blackbags.get(1).getName() + blackbags.get(1).getFullness());
        System.out.println(blackbags.get(2).getName() + blackbags.get(2).getFullness());
    }

    // The startup of the game, creates players, bags and pebbles
    public static void startGame(int number) {
        for (int i = 0; i < number; i++) {
            players.add(makePlayer());
        }

        int maxPebbles = 11*players.size();

        blackbags.add(BlackBag.makeBlackBag("X", maxPebbles));
        blackbags.add(BlackBag.makeBlackBag("Y", maxPebbles));
        blackbags.add(BlackBag.makeBlackBag("Z", maxPebbles));
        for (int i = 0; i < blackbags.size(); i++) {
            BlackBag XYZ = blackbags.get(i);
            System.out.println(XYZ.getName());
        }
        
        whitebags.add(new WhiteBag("A", maxPebbles, 0, true));
        whitebags.add(new WhiteBag("B", maxPebbles, 0, true));
        whitebags.add(new WhiteBag("C", maxPebbles, 0, true));
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


    // Method that randomly takes a pebble from a random black bag 
    private static Player takePebble(Player P, BlackBag XYZ) {
        System.out.println(XYZ.getName());
        int rPeb = r.nextInt(XYZ.getFullness() - 1);
        int cPeb = XYZ.getPebbles().remove(rPeb);
        P.score += cPeb;
        XYZ.setFullness(XYZ.getFullness()-1);
        return P;
    }


    static Random r = new Random();
}