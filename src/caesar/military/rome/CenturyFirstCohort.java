package caesar.military.rome;

import caesar.military.troop.Troop;

public class CenturyFirstCohort extends Century {
	
	private static final int unitCapacity = 20;
	
	CenturyFirstCohort(Troop parentUnit) {
		super(parentUnit, unitCapacity, "::");
	}
}