package pebblegame;


import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class PebbleGame {
    //ArrayLists containing the varies objects 
    private static ArrayList<Player> players = new ArrayList<>();
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


        /* Currently players take turns to takePebbles but 
           We need them to happen at the same time w/threading */
        for (Player P : players) {
            int rBag = r.nextInt(3);
            BlackBag XYZ = blackbags.get(rBag);
            for (int i = 0; i < 10; i++) {
                takePebble(P, XYZ);
            }
            System.out.println(P.getName() + " " + P.getPebbles() + " " + P.getTotal());
        }

        // Prints the name, fullness and contents of every black bag
        System.out.println(blackbags.get(0).getName() + blackbags.get(0).getFullness() + " " + blackbags.get(0).getPebbles());
        System.out.println(blackbags.get(1).getName() + blackbags.get(1).getFullness() + " " + blackbags.get(1).getPebbles());
        System.out.println(blackbags.get(2).getName() + blackbags.get(2).getFullness() + " " + blackbags.get(2).getPebbles());
    }

    // The startup of the game, creates players, bags and pebbles
    public static void startGame(int number) {
        for (int i = 0; i < number; i++) {
            players.add(makePlayer());
        }

        int maxPebbles = 11*players.size();

        // Instantiates the three BlackBags with their random pebble weights
        blackbags.add(BlackBag.makeBlackBag("X", maxPebbles));
        blackbags.add(BlackBag.makeBlackBag("Y", maxPebbles));
        blackbags.add(BlackBag.makeBlackBag("Z", maxPebbles));
        
        // Instantiates the three empty WhiteBags
        whitebags.add(new WhiteBag("A", maxPebbles, 0, true));
        whitebags.add(new WhiteBag("B", maxPebbles, 0, true));
        whitebags.add(new WhiteBag("C", maxPebbles, 0, true));
    }

    // Player static nested class
    static class Player {
        static int id = 0;
        private String name;
        private ArrayList<Integer> pebbles;
        private int total;

        Player() {
            pebbles = new ArrayList<>();
            id += 1;
            this.name = "Player" + id;
            this.total = 0;
        }

        String getName() {
            return name;
        }

        ArrayList<Integer> getPebbles() {
            return pebbles;
        }

        int getTotal() {
            return total;
        }
    }


    // Used to create a new Player
    private static Player makePlayer() {
        return new Player();
    }


    // Method that randomly takes a pebble from a random black bag 
    private static Player takePebble(Player P, BlackBag XYZ) {
        int rPeb = r.nextInt(XYZ.getFullness() - 1);
        int cPeb = XYZ.getPebbles().remove(rPeb);
        P.getPebbles().add(cPeb);
        XYZ.setFullness(XYZ.getFullness()-1);
        for (int pebble : P.getPebbles()) {
            P.total += pebble;
        }

        return P;
    }


    static Random r = new Random();
}