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
        for (Player P : players) {
            takePebble(P, blackbags);
            System.out.println(P.getName() + " " + P.getScore());
        }
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

        blackbags = BlackBag.makeBlackBags(maxPebbles);
        for (int i = 0; i < blackbags.size(); i++) {
            BlackBag XYZ = blackbags.get(i);
            System.out.println(XYZ.getName());
        }
        
        whitebags = WhiteBag.makeWhiteBags(maxPebbles);
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
    private static Player takePebble(Player P, ArrayList<BlackBag> blackbags) {
        int rBag = r.nextInt(2);
        BlackBag XYZ = blackbags.get(rBag);
        System.out.println(rBag);

        System.out.println(XYZ.getName());
        for (int i = 0; i < 10; i++) {
            int rPeb = r.nextInt(XYZ.getFullness() - i - 1);
            Pebble cPeb = XYZ.getPebbles().remove(rPeb);
            P.score += cPeb.getWeight();
        }
        XYZ.setFullness(XYZ.getFullness()-10);
        return P;
    }


    static Random r = new Random();
}