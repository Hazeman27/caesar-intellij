package caesar.military.rome;

import caesar.military.MilitaryUnit;
import caesar.military.officer.RomanOfficer;
import caesar.military.officer.RomanRank;
import caesar.military.soldier.Roman;
import caesar.military.troop.Troop;

public class Contubernium extends Troop {
	
	static final int unitCapacity = 7;
	
	Contubernium(Troop parentUnit) {
		super(parentUnit, unitCapacity, ".");
		this.officer = new RomanOfficer(RomanRank.DECANUS, this);
	}
	
	public Contubernium() {
		super(unitCapacity, ".");
		this.officer = new RomanOfficer(RomanRank.DECANUS, this);
	}
	
	@Override
	protected int getChildUnitCapacity() {
		return 0;
	}
	
	@Override
	protected MilitaryUnit getChildUnitInstance() {
		return new Roman(this);
	}
}