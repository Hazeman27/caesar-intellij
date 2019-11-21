package caesar.military.romans;

import caesar.military.soldier.Officer;
import caesar.military.troop.Troop;

import java.util.List;

public class CohortFirst extends Troop {
    
    public CohortFirst(List<Troop> centuries, Troop parentTroop, Officer officer) {
        super("CohortFirst", parentTroop, centuries, 5, officer);
    }

    public CohortFirst(Troop parentTroop) {
        super("CohortFirst", parentTroop, 5);
    }
}