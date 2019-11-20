package pebblegame;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
    private static final ArrayList<Player> players = new ArrayList<>();
    private static final ArrayList<Thread> threads = new ArrayList<>();
    private static final ArrayList<WhiteBag> whitebags = new ArrayList<>();
    private static final ArrayList<BlackBag> blackbags = new ArrayList<>();


    public static void main(String[] args) {
        int num;
        ArrayList<Integer> x;
        ArrayList<Integer> y;
        ArrayList<Integer> z;
        try (Scanner input = new Scanner(System.in)) {
            num = findPlayerNum(input);
            x = findFileName("X", num, input);
            y = findFileName("Y", num, input);
            z = findFileName("Z", num, input);
        }

        new PebbleGame(num, x, y, z);
    }


    public PebbleGame(int number, ArrayList<Integer> pebblesx, ArrayList<Integer> pebblesy, ArrayList<Integer> pebblesz) {
        // Instantiates the three BlackBags with their pebble weights, adds to ArrayList
        blackbags.add(BlackBag.makeBlackBag("X", pebblesx));
        blackbags.add(BlackBag.makeBlackBag("Y", pebblesy));
        blackbags.add(BlackBag.makeBlackBag("Z", pebblesz));


        // Instantiates the three empty WhiteBags, adds to ArrayList
        whitebags.add(WhiteBag.makeWhiteBag("A"));
        whitebags.add(WhiteBag.makeWhiteBag("B"));
        whitebags.add(WhiteBag.makeWhiteBag("C"));

        // Makes the outputs folder
        new File("outputs").mkdirs();

        Object lock = new Object();

        // Instantiates the players
        for (int i = 0; i < number; i++) {
            players.add(new Player(lock));
            threads.add(new Thread(players.get(i), "Thread" + (i + 1)));
        }

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
        final Object lock;

        Player(Object lock) {
            pebbles = new ArrayList<>();
            id += 1;
            this.name = "Player" + id;
            this.total = 0;
            this.lock = lock;
            try {
                fileOut = new PrintStream("./outputs/" + name + "_Output.txt");
            } catch (FileNotFoundException e) {
            }
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
         * Method for taking turns and logging to an Output file
         */
        private void takeTurn() {
            synchronized (lock) {
                WhiteBag W = whitebags.get(lastdrawn);
                
                discardPebble(W);
                int discarded = W.getPebbles().get(W.getPebbles().size() - 1);
                
                // Logs the players discarded pebble and hand
                fileOut.println(name + " has discarded " + discarded + " to White Bag " + W.getName());
                fileOut.println(name + " hand is " + pebbles + " = " + (total - discarded));
                
                
                BlackBag B;
                int rBag;
                
                do {
                    rBag = r.nextInt(blackbags.size());
                    B = blackbags.get(rBag);
                    lastdrawn = rBag;   

                    // Checks if BlackBag is empty
                    WhiteBag.emptyWhiteBag(whitebags.get(rBag), B);
                } while (B.getPebbles().size() < 1);

                takePebble(B);
                int taken = pebbles.get(pebbles.size() - 1);
            
                // Logs the players drawn pebble and hand
                fileOut.println(name + " has drawn a " + taken + " from Black Bag " + B.getName());
                fileOut.println(name + " hand is " + pebbles + " = " + total);

                if (total == 100) {
                    winner = true;
                }
            }
        }

        /**
         * Method that randomly takes a pebble from a random black bag
         * @param B the Chosen BlackBag object
         */
        private void takePebble(BlackBag B) {
            int rPeb = r.nextInt(B.getPebbles().size());
            int cPeb = B.getPebbles().remove(rPeb);
            pebbles.add(cPeb);
            
            total = 0;
            for (int pebble : pebbles) {
                total += pebble;
            }
        }

        /**
         * Method that discards the biggest Pebble
         * @param W the Chosen WhiteBag object
         */
        private void discardPebble(WhiteBag W) {
            int lastpeb = 0;
            int counter = 0;
            int index = 0;

            for (int pebble : pebbles) {
                if (pebble > lastpeb && 100 - total < 0 || pebble < (100 - total)) {
                    lastpeb = pebble;
                    index = counter;
                } counter += 1;
            }

            int biggestpeb = pebbles.remove(index);
            W.getPebbles().add(biggestpeb);
        }
    }

    private static int findPlayerNum(Scanner input) {
        // The check for the number of players
        int playerNum = 1;
        String playerStr;
        do {
            try {
                System.out.print("Please Enter the Number of players? : ");
                playerStr = input.nextLine();
    
                if (playerStr.charAt(0) == 'E') {
                    System.exit(0);
                }
                try {
                    if (Integer.parseInt(playerStr) > 0) {
                        // Instantiates a lot of the key objects
                        playerNum = Integer.parseInt(playerStr);
                    }
    
                } catch (NumberFormatException e) {
                    System.out.println("Please Enter a valid Integer");
                    playerStr = "0";
                }
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println("Please Enter a valid Integer");
                playerStr = "0";
            }
        } while (Integer.parseInt(playerStr) <= 0);

        return playerNum;
    }


    private static ArrayList<Integer> findFileName(String name, int min, Scanner textfile) {
        ArrayList<Integer> pebbles = new ArrayList<>();
        boolean finished = false;
        String textlocation;

        do {
            try {
                System.out.print("Please enter the path to the text file for Black Bag " + name + " : ");
                
                textlocation = textfile.nextLine();
                
                if (textlocation.charAt(0) == 'E') {
                    System.exit(0);}

                BufferedReader br = null;
                try{ 
                    br = new BufferedReader(new FileReader(textlocation));
                    String in;

                    while((in = br.readLine()) != null) {
                        String[] parts = in.split(",");
                        for (int index = 0; index < parts.length; index++) {
                            if (Integer.parseInt(parts[index]) <= 0) {
                                br.close();
                            } pebbles.add(Integer.parseInt(parts[index]));
                        }
                    }
                    if (pebbles.size() >= min){
                        finished = true;
                    } else {
                        System.out.println("Error, File too small");
                        pebbles.clear();}       
                    
                } catch (IOException e) {
                    System.out.println("Error, invalid File");
                    pebbles.clear();
                } finally {
                    try {
                        br.close();
                    } catch (IOException | NullPointerException e) {
                        pebbles.clear();} 
                }
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println("Error, invalid Input");
                pebbles.clear();}
        } while (!finished);

        return pebbles;
    }
}