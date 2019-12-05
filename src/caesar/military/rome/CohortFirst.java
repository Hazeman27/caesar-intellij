package caesar.military.rome;

import caesar.military.MilitaryUnit;
import caesar.military.troop.Troop;

public class CohortFirst extends Cohort {
	
	private static final int UNIT_CAPACITY = 5;
	
	CohortFirst(Troop parentUnit) {
		super(parentUnit, UNIT_CAPACITY, "[::]");
	}
	
	@Override
	protected MilitaryUnit getChildUnitInstance() {
		return new CenturyFirstCohort(this);
	}
}