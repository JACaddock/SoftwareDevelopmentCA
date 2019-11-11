package pebblegame;


import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class BlackBag extends Bag {
    private ArrayList<Integer> pebbles;

    public BlackBag(String name, int max, int fullness, boolean isEmpty, ArrayList<Integer> pebbles) {
        super(name, max, fullness, isEmpty);
        this.pebbles = pebbles;
    }

    // Method for instantiating a BlackBag *CURRENTLY NOT WORKING*
    static BlackBag makeBlackBag(String name, int max) {
        Scanner textfile = new Scanner(System.in);
        String textlocation = "0";
        do {
            System.out.print("Please enter the path to the text file for Black Bag " + name + " : ");
            textlocation = textfile.next();
            if (textlocation.charAt(0) == 'E') {
                System.exit(0);
            }
            try (Scanner textInt = new Scanner(new FileReader(textlocation))) {

                String thing = textInt.toString();
                String[] thing2 = thing.split(thing);
                for (String strong : thing2) {
                    System.out.println(strong);
                }
                
            } catch (NumberFormatException | IOException e) {
                System.out.println("Please Enter a valid Path");
                textlocation = "0";} 
        } while (textlocation.length() <= 3);

        textfile.close();



        ArrayList<Integer> pebbles = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            pebbles.add(1);
        }
        BlackBag X = new BlackBag(name, max, pebbles.size(), false, pebbles);

        return X;
    }


    // Getter method for pebbles Array
    public ArrayList<Integer> getPebbles() {
        return this.pebbles;
    }
}