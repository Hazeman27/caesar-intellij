package caesar.military.rome;

import caesar.military.MilitaryUnit;
import caesar.military.officer.RomanOfficer;
import caesar.military.officer.RomanRank;
import caesar.military.troop.Troop;

public class Legion extends Troop {
	
	static final int unitCapacity = 9;
	
	Legion(Troop parentUnit) {
		super(parentUnit, unitCapacity, "[><]");
		this.officer = new RomanOfficer(RomanRank.LEGATE, this);
	}
	
	@Override
	protected int getChildUnitCapacity() {
		return Cohort.unitCapacity;
	}
	
	@Override
	protected MilitaryUnit getChildUnitInstance() {
		return new Cohort(this);
	}
}