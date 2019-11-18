package pebblegame;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;


public class PebbleGame {
    // Random number
    static Random r = new Random();

    // Volatile bool for stopping game once winner found
    private volatile static boolean winner = false;

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
    }


    /**
     * The startup of the game, creates players, bags and pebbles
     * @param number of players in the game
     */
    public static void startGame(int number) {
        // Makes the outputs folder
        new File("outputs").mkdirs();

        Object lock = new Object();

        // Instantiates the players
        for (int i = 0; i < number; i++) {
            players.add(new Player(lock));
            threads.add(new Thread(players.get(i), "Thread" + (i + 1)));
        }

        int minPebbles = 11 * players.size();

        // Instantiates the three BlackBags with their pebble weights, adds to ArrayList
        blackbags.add(BlackBag.makeBlackBag("X", minPebbles));
        blackbags.add(BlackBag.makeBlackBag("Y", minPebbles));
        blackbags.add(BlackBag.makeBlackBag("Z", minPebbles));

        // Instantiates the three empty WhiteBags, adds to ArrayList
        whitebags.add(new WhiteBag("A",0));
        whitebags.add(new WhiteBag("B",0));
        whitebags.add(new WhiteBag("C",0));

        for (Thread t : threads) {
            t.start();
        }     
    }


    // Player static nested class
    static class Player implements Runnable {
        static int id = 0;
        private String name;
        private ArrayList<Integer> pebbles;
        private int total;
        private int lastdrawn;
        PrintStream fileOut;
        Object lock;


        Player(Object lock) {
            pebbles = new ArrayList<>();
            id += 1;
            this.name = "Player" + id;
            this.total = 0;
            this.lock = lock;
            try {
                fileOut = new PrintStream("./outputs/" + name + "_Output.txt");
            } catch (FileNotFoundException e) {}
        }


        public void run() {
            synchronized (lock) {
                int rBag = r.nextInt(blackbags.size());
                BlackBag XYZ = blackbags.get(rBag);
                lastdrawn = rBag;
                for (int i = 0; i < 10; i++) {
                    takePebble(XYZ);
                }
            }

            while (!winner) {
                takeTurn();
            }
        }


        /**
         * Method for taking turns
         */
        void takeTurn() {
            synchronized (lock) {
                WhiteBag W = whitebags.get(lastdrawn);
                discardPebble(W);
            
                int rBag = r.nextInt(blackbags.size());
                BlackBag B = blackbags.get(rBag);
                lastdrawn = rBag;

                // Checks if BlackBag is empty
                if (B.getFullness() <= 0) {
                    WhiteBag.emptyWhiteBag(whitebags.get(rBag), B);

                    // If still Empty remove Black and White Bag from ArrayLists
                    if (B.getFullness() <= 0) {
                        blackbags.remove(rBag);
                        whitebags.remove(rBag);
                    }
                }

                takePebble(B);

                if (total == 100) {winner = true;}
            }
        }


        /**
         * Method that randomly takes a pebble from a random black bag 
         * @param B the Chosen BlackBag object
         */
        void takePebble(BlackBag B) {
            int rPeb = r.nextInt(B.getFullness());
            int cPeb = B.getPebbles().remove(rPeb);

            pebbles.add(cPeb);
            B.setFullness(B.getFullness()-1);
            total = 0;

            for (int pebble : pebbles) {
                total += pebble;
            }

            fileOut.println(name + " has drawn a " + cPeb + " from Black Bag " + B.getName());
            fileOut.println(name + " hand is " + pebbles + " = " + total);
        }


        /**
         * Method that discards the biggest Pebble
         * @param W the Chosen WhiteBag object
         */
        void discardPebble(WhiteBag W) {
            int counter = 0;
            int index = 0;
            int lastpeb = 0;
            
            for (int pebble : pebbles) {
                if (pebble > lastpeb && 100 - total < 0 || pebble < (100 - total)) {
                    index = counter;
                    lastpeb = pebble;
                }counter += 1;
            }

            int biggestpeb = pebbles.remove(index); 
            W.getPebbles().add(biggestpeb);
            W.setFullness(W.getFullness()+1);

            fileOut.println(name + " has discarded " + biggestpeb + " to White Bag " + W.getName());
            fileOut.println(name + " hand is " + pebbles + " = " + (total - biggestpeb));
        }
    }
}