package caesar.military.gaul;

import caesar.military.Unit;
import caesar.military.UnitOrigin;
import caesar.military.officer.*;
import caesar.military.troop.Troop;

public class GaulArmy extends Troop {
	
	public GaulArmy(int unitCapacity) {
		super(unitCapacity, "[XGX]", UnitOrigin.GAUL);
	}
	
	@Override
	protected int getChildUnitCapacity() {
		return Nation.UNIT_CAPACITY;
	}
	
	@Override
	protected Officer getOfficerInstance() {
		return new GaulOfficer(GaulRank.HERO_WARLORD, this);
	}
	
	@Override
	protected Unit getChildUnitInstance() {
		return new Nation(this);
	}
}
