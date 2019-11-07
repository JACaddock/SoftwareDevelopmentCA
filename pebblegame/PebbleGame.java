package pebblegame;


public class PebbleGame {
	public static void main(String[] args) {
    // main - USing for testing atm
        Player Player1 = makePlayer();
        Player Player2 = makePlayer();
        Pebble Pebble1 = new Pebble(23, Player.id);
        System.out.println(Pebble1.getNumber());
        System.out.println(Player1.getName());
        System.out.println(Player2.getName());
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