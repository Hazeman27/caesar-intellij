package caesar.military.troop;

import caesar.military.romans.*;

public class TroopFactory {

    public Troop newTroop(String troopType, Troop parentTroop) {

        if (troopType == null)
            return null;

        if (troopType.equalsIgnoreCase("Contubernium"))
            return new Contubernium(parentTroop);

        if (troopType.equalsIgnoreCase("Century"))
            return new Century(parentTroop);

        if (troopType.equalsIgnoreCase("CenturyFirstCohort"))
            return new CenturyFirstCohort(parentTroop);

        if (troopType.equalsIgnoreCase("Cohort"))
            return new Cohort(parentTroop);

        if (troopType.equalsIgnoreCase("CohortFirst"))
            return new CohortFirst(parentTroop);

        if (troopType.equalsIgnoreCase("Legion"))
            return new Legion(parentTroop);
        
        return null;
    }
}