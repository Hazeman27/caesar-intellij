package caesar.military.rome;

import caesar.military.MilitaryUnit;
import caesar.military.officer.RomanOfficer;
import caesar.military.officer.RomanRank;
import caesar.military.troop.Troop;

public class Century extends Troop {
	
	static int unitCapacity = 10;
	
	Century(Troop parentUnit) {
		super(parentUnit, unitCapacity, ":");
		this.officer = new RomanOfficer(RomanRank.CENTURION, this);
	}
	
	Century(Troop parentUnit, int unitCapacity, String symbol) {
		
		super(parentUnit, unitCapacity, symbol);
		Century.unitCapacity = unitCapacity;
		this.officer = new RomanOfficer(RomanRank.CENTURION, this);
	}
	
	public Century() {
		super(unitCapacity, ":");
		this.officer = new RomanOfficer(RomanRank.CENTURION, this);
	}
	
	@Override
	protected int getChildUnitCapacity() {
		return Contubernium.unitCapacity;
	}
	
	@Override
	protected MilitaryUnit getChildUnitInstance() {
		return new Contubernium(this);
	}
}