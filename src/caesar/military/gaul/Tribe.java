package caesar.military.gaul;

import caesar.military.Unit;
import caesar.military.UnitOrigin;
import caesar.military.officer.GaulOfficer;
import caesar.military.officer.GaulRank;
import caesar.military.officer.Officer;
import caesar.military.troop.Troop;

public class Tribe extends Troop {
	
	static final int UNIT_CAPACITY = 12;
	
	Tribe(Troop parentUnit) {
		super(parentUnit, UNIT_CAPACITY, "[x]", UnitOrigin.GAUL);
	}
	
	public Tribe() {
		super(UNIT_CAPACITY, "[x]", UnitOrigin.GAUL);
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
	protected Unit getChildUnitInstance() {
		return new Group(this);
	}
}
