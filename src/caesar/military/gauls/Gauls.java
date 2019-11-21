package caesar.military.gauls;

import caesar.military.troop.Troop;

public class Gauls extends Troop {

    public Gauls(int soldiersAmount) {
        
        super("Gauls", null);
        initSoldiers(this.soldiers, soldiersAmount, this);
    }

    public static void main(String[] args) {

        Gauls gauls = new Gauls(1000);
        Troop.printSummary(gauls);
    }
}