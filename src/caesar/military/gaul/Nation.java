package caesar.military.gaul;

import caesar.military.MilitaryUnit;
import caesar.military.officer.GaulOfficer;
import caesar.military.officer.GaulRank;
import caesar.military.officer.Officer;
import caesar.military.troop.Troop;

public class Nation extends Troop {
	
	static final int UNIT_CAPACITY = 10;
	
	Nation(Troop parentUnit) {
		super(parentUnit, UNIT_CAPACITY, "[XX]");
	}
	
	public Nation() {
		super(UNIT_CAPACITY, "[XX]");
	}
	
	@Override
	protected int getChildUnitCapacity() {
		return TribeGroup.UNIT_CAPACITY;
	}
	
	@Override
	protected Officer getOfficerInstance() {
		return new GaulOfficer(GaulRank.CHIEF_WARLORD, this);
	}
	
	@Override
	protected MilitaryUnit getChildUnitInstance() {
		return new TribeGroup(this);
	}
}
