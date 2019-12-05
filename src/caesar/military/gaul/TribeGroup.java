package caesar.military.gaul;

import caesar.military.MilitaryUnit;
import caesar.military.officer.GaulOfficer;
import caesar.military.officer.GaulRank;
import caesar.military.officer.Officer;
import caesar.military.troop.Troop;

public class TribeGroup extends Troop {
	
	static final int UNIT_CAPACITY = 5;
	
	TribeGroup(Troop parentUnit) {
		super(parentUnit, UNIT_CAPACITY, "[X]");
	}
	
	public TribeGroup() {
		super(UNIT_CAPACITY, "[X]");
	}
	
	@Override
	protected int getChildUnitCapacity() {
		return Tribe.UNIT_CAPACITY;
	}
	
	
	@Override
	protected Officer getOfficerInstance() {
		return new GaulOfficer(GaulRank.CHIEF_WARLORD, this);
	}
	
	@Override
	protected MilitaryUnit getChildUnitInstance() {
		return new Tribe(this);
	}
}
