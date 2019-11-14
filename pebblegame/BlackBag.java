package pebblegame;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class BlackBag extends Bag {
    private ArrayList<Integer> pebbles;

    public BlackBag(String name, int fullness, ArrayList<Integer> pebbles) {
        super(name, fullness);
        this.pebbles = pebbles;
    }

    // Method for instantiating a BlackBag
    public static BlackBag makeBlackBag(String name, int max) {
        ArrayList<Integer> possWeights = new ArrayList<>();
        boolean finished = false;
        Scanner textfile;
        String textlocation;
        do {
            try {
                System.out.print("Please enter the path to the text file for Black Bag " + name + " : ");
                
                textfile = new Scanner(System.in);
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
                            } possWeights.add(Integer.parseInt(parts[index]));
                        }
                    }
                    finished = true;                
                    
                } catch (IOException e) {
                    System.out.println("Error, ind File");
                    possWeights.clear();
                } finally {
                    try {
                        br.close();
                    } catch (IOException | NullPointerException e1) {
                        possWeights.clear();} 
                }
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println("Error, invalid Input");
                possWeights.clear();}
        } while (!finished);


        ArrayList<Integer> pebbles = new ArrayList<>();
        /*for (int count = 0; count < max; count++) {
            // Adds a random number from the possible weights imported from the text file
            pebbles.add(possWeights.get(r.nextInt(possWeights.size())));
        } */

        int index = 0;
        for (int count = 0; count < max; count++) {
            if (index >= possWeights.size()) {
                index = 0;
            }
            pebbles.add(possWeights.get(index));
            index += 1;
        }

        BlackBag X = new BlackBag(name, max, pebbles);

        return X;
    }


    // Getter method for pebbles Array
    public ArrayList<Integer> getPebbles() {
        return this.pebbles;
    }
}