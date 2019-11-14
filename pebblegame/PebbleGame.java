package pebblegame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class PebbleGame {
    static Random r = new Random();
    // ArrayLists containing the varies objects
    private static ArrayList<Player> players = new ArrayList<>();
    private static ArrayList<Thread> threads = new ArrayList<>();
    private static ArrayList<WhiteBag> whitebags = new ArrayList<>();
    private static ArrayList<BlackBag> blackbags = new ArrayList<>();

    public static void main(String[] args) {
        // The check for the number of players
        Scanner input = new Scanner(System.in);
        String playerNum = "0";
        do {
            System.out.print("Please Enter the Number of players? : ");
            playerNum = input.next();
            if (playerNum.charAt(0) == 'E') {
                System.exit(0);
            }
            try {
                if (Integer.parseInt(playerNum) > 0) {
                    // Instantiates a lot of the key objects
                    startGame(Integer.parseInt(playerNum));
                }

            } catch (NumberFormatException e) {
                System.out.println("Please Enter a valid Integer");
                playerNum = "0";
            }
        } while (Integer.parseInt(playerNum) <= 0);

        input.close();

        for (Thread thread : threads) {
            thread.start();
        }

        try {
            threads.notifyAll();
        } catch (IllegalMonitorStateException e) {
            System.out.println("Oopsies");
        }
        
    }

    // The startup of the game, creates players, bags and pebbles
    public static void startGame(int number) {
        new File("outputs").mkdirs();

        for (int i = 0; i < number; i++) {
            players.add(makePlayer());
            threads.add(new Thread(players.get(i), "Thread" + (i + 1)));
        }

        for (Thread thread : threads) {
            System.out.println(thread.getName());
        }

        int maxPebbles = 11 * players.size();

        // Instantiates the three BlackBags with their random pebble weights
        blackbags.add(BlackBag.makeBlackBag("X", maxPebbles));
        blackbags.add(BlackBag.makeBlackBag("Y", maxPebbles));
        blackbags.add(BlackBag.makeBlackBag("Z", maxPebbles));

        // Instantiates the three empty WhiteBags
        whitebags.add(new WhiteBag("A", maxPebbles, 0));
        whitebags.add(new WhiteBag("B", maxPebbles, 0));
        whitebags.add(new WhiteBag("C", maxPebbles, 0));
    }

    // Player static nested class
    static class Player implements Runnable {
        static int id = 0;
        private String name;
        private ArrayList<Integer> pebbles;
        private int total;
        private int lastdrawn;
        PrintStream fileOut;
        private volatile boolean winner = false;

        Player() {
            pebbles = new ArrayList<>();
            id += 1;
            this.name = "Player" + id;
            this.total = 0;
            try {
                fileOut = new PrintStream("./outputs/" + name + "_Output.txt");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            System.out.println("Running");
            int rBag = r.nextInt(blackbags.size());
            BlackBag XYZ = blackbags.get(rBag);
            this.lastdrawn = rBag;
            for (int i = 0; i < 10; i++) {
                takePebble(this, XYZ);
            }

            try {
                this.wait();
            } catch (InterruptedException | IllegalMonitorStateException e) {
                System.out.println("Oopsies2");
            }
            while (!winner) {takeTurn(this);};
        }

        
        public void stop() {
            winner = true;
        }

        String getName() {
            return name;
        }

        ArrayList<Integer> getPebbles() {
            return pebbles;
        }

        int getLastDrawn() {
            return lastdrawn;
        }

        int getTotal() {
            return total;
        }
    }

    // Used to create a new Player
    private static Player makePlayer() {
        return new Player();
    }


    // Method for taking turns
    private synchronized static Player takeTurn(Player P) {
        WhiteBag W = whitebags.get(P.getLastDrawn());
        discardPebble(P, W);
      
        int rBag = r.nextInt(blackbags.size());
        BlackBag B = blackbags.get(rBag);
        P.lastdrawn = rBag;

        // Checks if BlackBag is empty
        if (B.getFullness() <= 0) {
            WhiteBag.emptyWhiteBag(whitebags.get(rBag), B);

            // If still Empty remove Black and White Bag from ArrayLists
            if (B.getFullness() <= 0) {
                blackbags.remove(rBag);
                whitebags.remove(rBag);
            }
        }

        takePebble(P, B);

        if (P.getTotal() == 100) {for (Player Pn : players) {Pn.stop();}}

        return P;
    }


    // Method that randomly takes a pebble from a random black bag 
    private static Player takePebble(Player P, BlackBag B) {
        int rPeb = r.nextInt(B.getFullness());
        int cPeb = B.getPebbles().remove(rPeb);

        P.getPebbles().add(cPeb);
        B.setFullness(B.getFullness()-1);
        P.total = 0;

        for (int pebble : P.getPebbles()) {
            P.total += pebble;
        }

        P.fileOut.println(P.getName() + " has drawn a " + cPeb + " from Black Bag " + B.getName());
        P.fileOut.println(P.getName() + " hand is " + P.getPebbles() + " = " + P.getTotal());
        return P;
    }


    // Method that discards the biggest Pebble
    private static Player discardPebble(Player P, WhiteBag W) {
        int counter = 0;
        int index = 0;
        int lastpeb = 0;
        
        for (int pebble : P.getPebbles()) {
            if (pebble > lastpeb && 100 - P.getTotal() < 0 || pebble < (100 - P.getTotal())) {
                index = counter;
                lastpeb = pebble;
            }counter += 1;
        }

        int biggestpeb = P.getPebbles().remove(index); 
        W.getPebbles().add(biggestpeb);
        W.setFullness(W.getFullness()+1);

        P.fileOut.println(P.getName() + " has discarded " + biggestpeb + " to White Bag " + W.getName());
        P.fileOut.println(P.getName() + " hand is " + P.getPebbles() + " = " + (P.getTotal() - biggestpeb));
        return P;
    }
}