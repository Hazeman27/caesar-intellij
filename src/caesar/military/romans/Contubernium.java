package caesar.military.romans;

import caesar.military.soldier.Soldier;
import caesar.military.troop.Troop;

import java.util.List;

public class Contubernium extends Troop {
    
    public Contubernium(List<Soldier> soldiers, Soldier decanus, Troop parentTroop) {

        super("Contubernium", parentTroop, decanus);
        initSoldiers(this.soldiers, soldiers, 7);
    }
    
    public Contubernium(Troop parentTroop) {
        
        super("Contubernium", parentTroop);
        initSoldiers(this.soldiers, 7, this);
    }
}