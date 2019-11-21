package caesar.military.romans;

import caesar.military.troop.Troop;

public class Contubernium extends Troop {

    public Contubernium(Troop parentTroop) {
        
        super("Contubernium", parentTroop);
        initSoldiers(this.soldiers, 7, this);
    }
}