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
    public static BlackBag makeBlackBag(String name, int min) {
        ArrayList<Integer> pebbles = new ArrayList<>();
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
                    } catch (IOException | NullPointerException e1) {
                        pebbles.clear();} 
                }
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println("Error, invalid Input");
                pebbles.clear();}
        } while (!finished);

        BlackBag X = new BlackBag(name, pebbles.size(), pebbles);

        return X;
    }


    // Getter method for pebbles Array
    public ArrayList<Integer> getPebbles() {
        return this.pebbles;
    }
}