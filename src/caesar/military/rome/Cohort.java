package caesar.military.rome;

import caesar.military.MilitaryUnit;
import caesar.military.officer.RomanOfficer;
import caesar.military.officer.RomanRank;
import caesar.military.troop.Troop;

public class Cohort extends Troop {
	
	static int unitCapacity = 6;
	
	Cohort(Troop parentUnit) {
		super(parentUnit, unitCapacity, "[:]");
		this.officer = new RomanOfficer(RomanRank.LEAD_CENTURION, this);
	}
	
	Cohort(Troop parentUnit, int unitCapacity, String symbol) {
		
		super(parentUnit, unitCapacity, symbol);
		Cohort.unitCapacity = unitCapacity;
		this.officer = new RomanOfficer(RomanRank.LEAD_CENTURION, this);
	}
	
	@Override
	protected int getChildUnitCapacity() {
		return Century.unitCapacity;
	}
	
	@Override
	protected MilitaryUnit getChildUnitInstance() {
		return new Century(this);
	}
}