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
    
    // Useful bool to check whether game runtime may be long
    private volatile static boolean warning = false;

    // ArrayLists containing the varies objects
    private static final ArrayList<Player> players = new ArrayList<>();
    private static final ArrayList<Thread> threads = new ArrayList<>();
    private static final ArrayList<Bag> whitebags = new ArrayList<>();
    private static final ArrayList<Bag> blackbags = new ArrayList<>();


    public static void main(String[] args) {
        System.out.println("Welcome to the PebbleGame!");
        System.out.println("You must input at least 1 player to play");
        System.out.println("Please make sure you input files that are big enough");
        System.out.println();
        
        int num;
        ArrayList<Integer> x;
        ArrayList<Integer> y;
        ArrayList<Integer> z;
        try (Scanner input = new Scanner(System.in)) {
            do {
                System.out.print("Please Enter the Number of players? : ");
                String playerStr = input.nextLine();
                num = findPlayerNum(playerStr);
            } while (num <= 0);
            
            String textlocation;
            do {
                System.out.print("Please enter the path to the text file for Black Bag X : ");
                textlocation = input.nextLine();
                x = findFileName(num, textlocation);
            } while (x == null);
            
            System.out.print("Would you like to use the same bag true|false (defaults to false) : ");
            boolean decision = Boolean.parseBoolean(input.nextLine().toLowerCase());
            
            do {
                if (!decision) {
                    System.out.print("Please enter the path to the text file for Black Bag Y : ");
                    textlocation = input.nextLine();
                }
                y = findFileName(num, textlocation);
            } while (y == null);
            
            do {
                if (!decision) {
                    System.out.print("Please enter the path to the text file for Black Bag Z : ");
                    textlocation = input.nextLine();
                }
                z = findFileName(num, textlocation);
            } while (z == null);
        }

        new PebbleGame(num, x, y, z);
    }


    public PebbleGame(int number, ArrayList<Integer> pebblesx, ArrayList<Integer> pebblesy, ArrayList<Integer> pebblesz) {
        // Helpful feature to warn about potential long runtime
        if (warning) {
            System.out.println("Warning, One or more BlackBags have pebbles over weight 100");
            System.out.println("This may drastically increase runtime");
        }


        // Instantiates the three BlackBags with their pebble weights, adds to ArrayList
        blackbags.add(new Bag("X", pebblesx));
        blackbags.add(new Bag("Y", pebblesy));
        blackbags.add(new Bag("Z", pebblesz));


        // Instantiates the three empty WhiteBags, adds to ArrayList
        whitebags.add(new Bag("A", new ArrayList<>()));
        whitebags.add(new Bag("B", new ArrayList<>()));
        whitebags.add(new Bag("C", new ArrayList<>()));

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

        // Player Constructor
        Player(Object lock) {
            pebbles = new ArrayList<>();
            id += 1;
            this.name = "Player" + id;
            this.total = 0;
            this.lock = lock;
            try {
                fileOut = new PrintStream("./outputs/" + name + "_Output.txt");
            } catch (FileNotFoundException e) {
                System.out.println("Error, Cannot find Output file");
            }
        }

        public void run() {
            synchronized (lock) {
                int rBag = r.nextInt(blackbags.size());
                Bag B = blackbags.get(rBag);
                lastdrawn = rBag;
                for (int i = 0; i < 10; i++) {
                    takePebble(B);
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
                Bag W = whitebags.get(lastdrawn);

                discardPebble(W);
                int discarded = W.getPebbles().get(W.getPebbles().size() - 1);

                // Logs the players discarded pebble and hand
                fileOut.println(name + " has discarded " + discarded + " to White Bag " + W.getName());
                fileOut.println(name + " hand is " + pebbles + " = " + (total - discarded));


                Bag B;
                int rBag;

                do {
                    rBag = r.nextInt(blackbags.size());
                    B = blackbags.get(rBag);
                    lastdrawn = rBag;   

                    // Checks if BlackBag is empty
                    Bag.emptyBag(whitebags.get(rBag), B);
                } while (B.getPebbles().size() < 1);

                takePebble(B);
                int taken = pebbles.get(pebbles.size() - 1);

                // Logs the players drawn pebble and hand
                fileOut.println(name + " has drawn a " + taken + " from Black Bag " + B.getName());
                fileOut.println(name + " hand is " + pebbles + " = " + total);

                if (total == 100) {
                    winner = true;
                    System.out.println(name + " has won");
                }
            }
        }

        /**
         * Method that randomly takes a pebble from a random black bag
         * @param B the Chosen BlackBag object
         */
        private void takePebble(Bag B) {
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
        private void discardPebble(Bag W) {
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
        
        String getName() {
            return this.name;
        }
        
        ArrayList<Integer> getPebbles() {
            return this.pebbles;
        }
        
        int getTotal() {
            return this.total;
        }
        
        int getLastdrawn() {
            return this.lastdrawn;
        }
    }

    
    private static int findPlayerNum(String playerStr) {
        // The check for the number of players
        int playerNum = 0;
        try {    
            if (playerStr.charAt(0) == 'E') {
                System.exit(0);
            }
            try {
                if (Integer.parseInt(playerStr) > 0) {
                    playerNum = Integer.parseInt(playerStr);
                } else {
                    System.out.println("Please Enter a positive Integer");
                    return 0;}
    
            } catch (NumberFormatException e) {
                System.out.println("Please Enter an Integer");
                return 0;
            }
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Please Enter something");
            return 0;
        }

        return playerNum;
    }


    private static ArrayList<Integer> findFileName(int min, String textlocation) {
        ArrayList<Integer> pebbles = new ArrayList<>();

        try {                
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
                        } 
                        pebbles.add(Integer.parseInt(parts[index]));
                    }
                }
                if (pebbles.size() >= min * 11){
                    int counter = 0;

                    for (int peb : pebbles) {
                        if (peb > 100) {
                            warning = true;
                            counter += 1;
                        } else if (peb >= 10) {
                            counter += 1;
                        }
                    }
                    if (counter > 10) {
                        return pebbles;
                    }

                } else {
                    System.out.println("Error, File too small");
                    pebbles.clear();}       

            } catch (IOException | NumberFormatException e) {
                System.out.println("Error, invalid File");
                pebbles.clear();

            } finally {
                try {
                    br.close();
                } 
                catch (IOException | NullPointerException e) {
                    pebbles.clear();} 
            }
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Error, invalid Input");
            pebbles.clear();}
            
        
        return null;
    }
}