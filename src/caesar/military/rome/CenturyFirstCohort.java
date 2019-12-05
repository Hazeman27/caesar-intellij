package caesar.military.rome;

import caesar.military.troop.Troop;

public class CenturyFirstCohort extends Century {
	
	private static final int UNIT_CAPACITY = 20;
	
	CenturyFirstCohort(Troop parentUnit) {
		super(parentUnit, UNIT_CAPACITY, "::");
	}
}