package caesar.military.rome;

import caesar.military.MilitaryUnit;
import caesar.military.officer.Officer;
import caesar.military.officer.RomanOfficer;
import caesar.military.officer.RomanRank;
import caesar.military.troop.Troop;

public class Cohort extends Troop {
	
	static int UNIT_CAPACITY = 6;
	
	Cohort(Troop parentUnit) {
		super(parentUnit, UNIT_CAPACITY, "[:]");
	}
	
	Cohort(Troop parentUnit, int unitCapacity, String symbol) {
		super(parentUnit, unitCapacity, symbol);
		Cohort.UNIT_CAPACITY = unitCapacity;
	}
	
	@Override
	protected int getChildUnitCapacity() {
		return Century.UNIT_CAPACITY;
	}
	
	@Override
	protected Officer getOfficerInstance() {
		return new RomanOfficer(RomanRank.LEAD_CENTURION, this);
	}
	
	@Override
	protected MilitaryUnit getChildUnitInstance() {
		return new Century(this);
	}
}