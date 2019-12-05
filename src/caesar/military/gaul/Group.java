package caesar.military.gaul;

import caesar.military.MilitaryUnit;
import caesar.military.officer.GaulOfficer;
import caesar.military.officer.GaulRank;
import caesar.military.soldier.Gaul;
import caesar.military.troop.Troop;

public class Group extends Troop {
	
	static final int unitCapacity = 9;
	
	Group(Troop parentUnit) {
		super(parentUnit, unitCapacity, "x");
		this.officer = new GaulOfficer(GaulRank.CHIEF, this);
	}
	
	@Override
	protected int getChildUnitCapacity() {
		return 0;
	}
	
	@Override
	protected MilitaryUnit getChildUnitInstance() {
		return new Gaul(this);
	}
}
