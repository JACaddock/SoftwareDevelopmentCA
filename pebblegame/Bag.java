package pebblegame;


public class Bag {
    // Bool for whether the Bag is Empty or not
    private boolean isEmpty;
    private int fullness;
    private int max;
    private String name;


    public Bag(String name, int max, int fullness, boolean isEmpty) {
        this.name = name;
        this.max = max;
        this.fullness = fullness;
        this.isEmpty = isEmpty;
    }


    // Getter methods
    public String getName(){
        return this.name;
    }


    public int getMax() {
        return this.max;
    }


    public boolean getIsEmpty() {
        return this.isEmpty;
    }
    

    public int getFullness() {
        return this.fullness;
    }
}