package caesar.military.gaul;

import caesar.military.MilitaryUnit;
import caesar.military.officer.*;
import caesar.military.troop.Troop;

public class GaulArmy extends Troop {
	
	public GaulArmy(int unitCapacity) {
		super(unitCapacity, "[XGX]");
	}
	
	@Override
	protected int getChildUnitCapacity() {
		return Triad.UNIT_CAPACITY;
	}
	
	@Override
	protected Officer getOfficerInstance() {
		return new GaulOfficer(GaulRank.HERO_WARLORD, this);
	}
	
	@Override
	protected MilitaryUnit getChildUnitInstance() {
		return new Triad(this);
	}
}
