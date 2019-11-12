package pebblegame;


// Class exists for White and Black bag to extend
public class Bag {
    // Bool for whether the Bag is Empty or not
    private int fullness;
    private String name;


    public Bag(String name, int fullness) {
        this.name = name;
        this.fullness = fullness;
    }


    // Getter methods
    public String getName(){
        return this.name;
    }


    public int getFullness() {
        return this.fullness;
    }

    
    public void setFullness(int f) {
        fullness = f;
    }
}