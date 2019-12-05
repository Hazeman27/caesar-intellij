package caesar.military.rome;

import caesar.military.MilitaryUnit;
import caesar.military.officer.Officer;
import caesar.military.officer.RomanOfficer;
import caesar.military.officer.RomanRank;
import caesar.military.soldier.Roman;
import caesar.military.troop.Troop;

public class Contubernium extends Troop {
	
	static final int UNIT_CAPACITY = 7;
	
	Contubernium(Troop parentUnit) {
		super(parentUnit, UNIT_CAPACITY, ".");
	}
	
	public Contubernium() {
		super(UNIT_CAPACITY, ".");
	}
	
	@Override
	protected int getChildUnitCapacity() {
		return 0;
	}
	
	@Override
	protected Officer getOfficerInstance() {
		return new RomanOfficer(RomanRank.DECANUS, this);
	}
	
	@Override
	protected MilitaryUnit getChildUnitInstance() {
		return new Roman(this);
	}
}