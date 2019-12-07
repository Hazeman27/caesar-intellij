package caesar.military.gaul;

import caesar.military.Unit;
import caesar.military.UnitOrigin;
import caesar.military.officer.GaulOfficer;
import caesar.military.officer.GaulRank;
import caesar.military.officer.Officer;
import caesar.military.soldier.Gaul;
import caesar.military.troop.Troop;

public class Group extends Troop {
	
	static final int UNIT_CAPACITY = 9;
	
	Group(Troop parentUnit) {
		super(parentUnit, UNIT_CAPACITY, "x", UnitOrigin.GAUL);
	}
	
	@Override
	protected int getChildUnitCapacity() {
		return 0;
	}
	
	@Override
	protected Officer getOfficerInstance() {
		return new GaulOfficer(GaulRank.CHIEF, this);
	}
	
	@Override
	protected Unit getChildUnitInstance() {
		return new Gaul(this);
	}
}
