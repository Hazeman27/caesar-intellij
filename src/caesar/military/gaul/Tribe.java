package caesar.military.gaul;

import caesar.military.MilitaryUnit;
import caesar.military.officer.GaulOfficer;
import caesar.military.officer.GaulRank;
import caesar.military.troop.Troop;

public class Tribe extends Troop {
	
	static final int unitCapacity = 12;
	
	Tribe(Troop parentUnit) {
		super(parentUnit, unitCapacity, "[x]");
		this.officer = new GaulOfficer(GaulRank.WARLORD, this);
	}
	
	public Tribe() {
		super(unitCapacity, "[x]");
		this.officer = new GaulOfficer(GaulRank.WARLORD, this);
	}
	
	@Override
	protected int getChildUnitCapacity() {
		return Group.unitCapacity;
	}
	
	@Override
	protected MilitaryUnit getChildUnitInstance() {
		return new Group(this);
	}
}
