package caesar.military.gaul;

import caesar.military.Unit;
import caesar.military.UnitOrigin;
import caesar.military.officer.GaulOfficer;
import caesar.military.officer.GaulRank;
import caesar.military.officer.Officer;
import caesar.military.troop.Troop;

public class Nation extends Troop {
	
	static final int UNIT_CAPACITY = 10;
	
	Nation(Troop parentUnit) {
		super(parentUnit, UNIT_CAPACITY, "[XX]", UnitOrigin.GAUL);
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
	protected Unit getChildUnitInstance() {
		return new TribeGroup(this);
	}
}
