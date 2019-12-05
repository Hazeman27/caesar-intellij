package caesar.military.gaul;

import caesar.military.MilitaryUnit;
import caesar.military.officer.GaulOfficer;
import caesar.military.officer.GaulRank;
import caesar.military.troop.Troop;

public class GaulArmy extends Troop {
	
	public GaulArmy(int unitCapacity) {
		super(unitCapacity, "[XGX]");
		this.officer = new GaulOfficer(GaulRank.HERO_WARLORD, this);
	}
	
	@Override
	protected int getChildUnitCapacity() {
		return Triad.unitCapacity;
	}
	
	@Override
	protected MilitaryUnit getChildUnitInstance() {
		return new Triad(this);
	}
}
