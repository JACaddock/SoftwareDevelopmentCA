package pebblegame;

//import java.util.ArrayList;
import java.util.Scanner;

public class PebbleGame {
    static int pebbleMax = Player.id * 11;

    //Arrays containing the varies objects
    //private ArrayList<Pebble> pebbles = new ArrayList<>();
    //private ArrayList<Player> players = new ArrayList<>();
    //private ArrayList<BlackBag> blackbags = new ArrayList<>();
    //private ArrayList<WhiteBag> whitebags = new ArrayList<>();

	public static void main(String[] args) {
    // main - USing for testing atm
        Scanner input = new Scanner(System.in);
        System.out.println("How Many players?");
        int number = input.nextInt();
        System.out.println(number + " players");
        input.close();
        for (int n = 0; n < number; n++) {
            Player Player = makePlayer();
            
            System.out.println(Player.getName());

        }
        Pebble Pebble1 = spawnPebble();
        System.out.println(Pebble1.getWeight());
    }


    private static Pebble spawnPebble() {
        return new Pebble();
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
    static Player makePlayer() {
        return new Player();
    }
}