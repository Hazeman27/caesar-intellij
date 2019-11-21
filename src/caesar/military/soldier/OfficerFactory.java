package caesar.military.soldier;

import caesar.military.troop.Troop;

public class OfficerFactory {

    public Officer newOfficer(String troopType, Troop troop) {

        if (troopType == null)
            return null;

        if (troopType.equalsIgnoreCase("Contubernium"))
            return new Officer(troop, Rank.DECANUS);

        if (troopType.equalsIgnoreCase("Century") ||
            troopType.equalsIgnoreCase("CenturyFirstCohort"))
            
            return new Officer(troop, Rank.CENTURION);

        if (troopType.equalsIgnoreCase("Cohort") ||
            troopType.equalsIgnoreCase("CohortFirst"))
            
            return new Officer(troop, Rank.LEAD_CENTURION);

        if (troopType.equalsIgnoreCase("Legion"))
            return new Officer(troop, Rank.LEGATE);

        if (troopType.equalsIgnoreCase("RomanArmy"))
            return new Officer(troop, Rank.GENERAL);

        if (troopType.equalsIgnoreCase("Gauls"))
            return new Officer(troop, Rank.GENERAL);
        
        return null;
    }
}