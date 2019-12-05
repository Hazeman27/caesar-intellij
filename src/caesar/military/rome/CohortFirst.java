package caesar.military.rome;

import caesar.military.MilitaryUnit;
import caesar.military.officer.Officer;
import caesar.military.officer.RomanOfficer;
import caesar.military.officer.RomanRank;
import caesar.military.troop.Troop;

public class CohortFirst extends Troop {
	
	private static final int UNIT_CAPACITY = 5;
	
	CohortFirst(Troop parentUnit) {
		super(parentUnit, UNIT_CAPACITY, "[::]");
	}
	
	@Override
	protected int getChildUnitCapacity() {
		return CenturyFirstCohort.UNIT_CAPACITY;
	}
	
	@Override
	protected Officer getOfficerInstance() {
		return new RomanOfficer(RomanRank.LEAD_CENTURION, this);
	}
	
	@Override
	protected MilitaryUnit getChildUnitInstance() {
		return new CenturyFirstCohort(this);
	}
}