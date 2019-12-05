package caesar.military.gaul;

import caesar.military.MilitaryUnit;
import caesar.military.officer.GaulOfficer;
import caesar.military.officer.GaulRank;
import caesar.military.officer.Officer;
import caesar.military.troop.Troop;

public class Tribe extends Troop {
	
	static final int UNIT_CAPACITY = 12;
	
	Tribe(Troop parentUnit) {
		super(parentUnit, UNIT_CAPACITY, "[x]");
	}
	
	public Tribe() {
		super(UNIT_CAPACITY, "[x]");
	}
	
	@Override
	protected int getChildUnitCapacity() {
		return Group.UNIT_CAPACITY;
	}
	
	@Override
	protected Officer getOfficerInstance() {
		return new GaulOfficer(GaulRank.WARLORD, this);
	}
	
	@Override
	protected MilitaryUnit getChildUnitInstance() {
		return new Group(this);
	}
}
