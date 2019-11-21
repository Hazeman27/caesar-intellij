package caesar.military.romans;

import caesar.military.soldier.Officer;
import caesar.military.troop.Troop;

import java.util.List;

public class CenturyFirstCohort extends Troop {
    
    public CenturyFirstCohort(List<Troop> contuberniums, Troop parentTroop, Officer officer) {
        super("CenturyFirstCohort", parentTroop, contuberniums, 20, officer);
    }

    public CenturyFirstCohort(Troop parentTroop) {
        super("CenturyFirstCohort", parentTroop, 20);
    }
}