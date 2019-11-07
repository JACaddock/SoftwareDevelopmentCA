import java.util.Random; // will use the random class for draws
public class PebbleGame {
	public static void main(String[] args) {
	        UggRock Rock1 = new UggRock(UggRockSize.BIGUN, UggRockType.SPECKLY);
			System.out.println(Rock1);
            }
		
    // UggRockSize inner class as required
    enum UggRockSize{
        BIGUN(5),
        QUITE_BIGUN(3),
        NOT_SO_BIGUN(1);
        int multiplierValue;
        UggRockSize(int multiplierValue) {
            this.multiplierValue = multiplierValue; 
        }
        int getMultiplierValue() {
            return multiplierValue;
        }
    }
    // UggRockType inner class as required
    enum UggRockType{
        SPECKLY(7),
        OUCHY_BLACK(20),
        FLOATY(2),
        HOT_HOT_HOT(15);
        int multiplierValue;
        UggRockType(int multiplierValue) {
            this.multiplierValue = multiplierValue; 
        }
        int getMultiplierValue() {
            return multiplierValue;
        }
    }
	
    // UggRock static nested class class as required
    static class UggRock {
        private UggRockSize rs;
        private UggRockType ut;
        UggRock(UggRockSize rs, UggRockType ut) {
            this.rs = rs;
            this.ut = ut;
        }
        int rockValue() {
            return rs.getMultiplierValue() * ut.getMultiplierValue();    
        }
        
        public UggRockSize returnUggRockSize(){
        	return rs;
        }
        
        public UggRockType returnUggRockType(){
        	return ut;
        }
        
        public String toString() {
            return rs.toString() + " " + ut.toString();
        }
    }
    // class attribute to draw random numbers
    static Random randomNumGen = new Random();
    /* class method to randomly draw rocks in proportions required in the workshop
    * You should consider modifying it to for example use switch statements and/or use int draws..
    */
    static UggRock drawRock() {
        double randSize = randomNumGen.nextDouble();
        double randType = randomNumGen.nextDouble();
        UggRockSize rs;
        UggRockType rt;
        if (randType < 0.25) {
            rt = UggRockType.SPECKLY;
        } else if (randType < 0.5) {
            rt = UggRockType.OUCHY_BLACK;
        } else if (randType < 0.75){
            rt = UggRockType.FLOATY;
        } else {
            rt = UggRockType.HOT_HOT_HOT;
        }
        
        if (randSize < 6.0/9.0) {
            rs = UggRockSize.NOT_SO_BIGUN;
        } else if (randSize < 8.0/9.0) {
            rs = UggRockSize.QUITE_BIGUN;
        } else {
            rs = UggRockSize.BIGUN;
        }
        return new UggRock(rs, rt);
    }
}