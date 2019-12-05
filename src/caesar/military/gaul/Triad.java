package caesar.military.gaul;

import caesar.military.MilitaryUnit;
import caesar.military.officer.GaulOfficer;
import caesar.military.officer.GaulRank;
import caesar.military.troop.Troop;

public class Triad extends Troop {
	
	static final int unitCapacity = 3;
	
	Triad(Troop parentUnit) {
		super(parentUnit, unitCapacity, "[XX]");
		this.officer = new GaulOfficer(GaulRank.CHIEF_WARLORD, this);
	}
	
	@Override
	protected int getChildUnitCapacity() {
		return TribeGroup.unitCapacity;
	}
	
	@Override
	protected MilitaryUnit getChildUnitInstance() {
		return new TribeGroup(this);
	}
}
