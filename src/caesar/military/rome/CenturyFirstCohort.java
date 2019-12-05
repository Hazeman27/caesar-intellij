package caesar.military.rome;

import caesar.military.MilitaryUnit;
import caesar.military.officer.Officer;
import caesar.military.officer.RomanOfficer;
import caesar.military.officer.RomanRank;
import caesar.military.troop.Troop;

public class CenturyFirstCohort extends Troop {
	
	static final int UNIT_CAPACITY = 20;
	
	CenturyFirstCohort(Troop parentUnit) {
		super(parentUnit, UNIT_CAPACITY, "::");
	}
	
	public CenturyFirstCohort() {
		super(UNIT_CAPACITY, "::");
	}
	
	@Override
	protected int getChildUnitCapacity() {
		return Contubernium.UNIT_CAPACITY;
	}
	
	@Override
	protected Officer getOfficerInstance() {
		return new RomanOfficer(RomanRank.CENTURION, this);
	}
	
	@Override
	protected MilitaryUnit getChildUnitInstance() {
		return new Contubernium(this);
	}
}