package caesar.military.rome;

import caesar.military.MilitaryUnit;
import caesar.military.officer.Officer;
import caesar.military.troop.Troop;

import java.util.List;

public class CenturyFirstCohort extends Century {
	
	private static final int unitCapacity = 20;
	
	CenturyFirstCohort(Troop parentUnit) {
		super(parentUnit, unitCapacity, "::");
	}
	
	CenturyFirstCohort(Troop parentUnit, List<MilitaryUnit> units, Officer officer) {
		super(parentUnit, units, officer, unitCapacity, "::");
	}
}