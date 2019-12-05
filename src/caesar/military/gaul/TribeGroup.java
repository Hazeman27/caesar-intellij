package caesar.military.gaul;

import caesar.military.MilitaryUnit;
import caesar.military.officer.GaulOfficer;
import caesar.military.officer.GaulRank;
import caesar.military.troop.Troop;

public class TribeGroup extends Troop {
	
	static final int unitCapacity = 5;
	
	TribeGroup(Troop parentUnit) {
		super(parentUnit, unitCapacity, "[X]");
		this.officer = new GaulOfficer(GaulRank.CHIEF_WARLORD, this);
	}
	
	@Override
	protected int getChildUnitCapacity() {
		return Tribe.unitCapacity;
	}
	
	@Override
	protected MilitaryUnit getChildUnitInstance() {
		return new Tribe(this);
	}
}
