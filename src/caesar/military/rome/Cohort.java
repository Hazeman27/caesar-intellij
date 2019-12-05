package caesar.military.rome;

import caesar.military.MilitaryUnit;
import caesar.military.officer.Officer;
import caesar.military.officer.RomanOfficer;
import caesar.military.officer.RomanRank;
import caesar.military.troop.Troop;

public class Cohort extends Troop {
	
	static final int UNIT_CAPACITY = 6;
	
	Cohort(Troop parentUnit) {
		super(parentUnit, UNIT_CAPACITY, "[:]");
	}
	
	public Cohort() {
		super(UNIT_CAPACITY, "[:]");
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