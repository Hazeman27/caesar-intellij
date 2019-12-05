package caesar.military.rome;

import caesar.military.MilitaryUnit;
import caesar.military.officer.Officer;
import caesar.military.officer.RomanOfficer;
import caesar.military.officer.RomanRank;
import caesar.military.troop.Troop;

public class RomanArmy extends Troop {
	
	public RomanArmy(int unitCapacity) {
		super(unitCapacity, "[>R<]");
	}
	
	@Override
	protected int getChildUnitCapacity() {
		return Legion.UNIT_CAPACITY;
	}
	
	@Override
	protected Officer getOfficerInstance() {
		return new RomanOfficer(RomanRank.GENERAL, this);
	}
	
	@Override
	protected MilitaryUnit getChildUnitInstance() {
		return new Legion(this);
	}
}
