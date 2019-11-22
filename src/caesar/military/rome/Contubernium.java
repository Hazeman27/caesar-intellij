package caesar.military.rome;

import caesar.military.troop.Troop;
import caesar.military.troop.TroopType;

public class Contubernium extends Troop {
    
    public Contubernium(Troop parentTroop) {
        super(TroopType.CONTUBERNIUM, parentTroop);
    }
}