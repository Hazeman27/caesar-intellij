package caesar.military.rome;

import caesar.military.Unit;
import caesar.military.UnitOrigin;
import caesar.military.UnitParent;
import caesar.military.officer.Rank;
import caesar.military.officer.RomanOfficer;
import caesar.military.officer.RomanRank;
import caesar.military.soldier.Roman;
import caesar.military.soldier.Soldier;
import caesar.military.troop.Troop;

import java.util.List;

public class Contubernium extends Troop {
	
	static final int UNIT_CAPACITY = 7;
	
	Contubernium(UnitParent parent) {
		super(parent, UNIT_CAPACITY, ".", UnitOrigin.ROME);
	}
	
	Contubernium(UnitParent parent, List<Unit> units, List<Soldier> officers) {
		super(parent, units, officers, UNIT_CAPACITY, ".", UnitOrigin.ROME);
	}
	
	@Override
	protected int getChildCapacity() {
		return 0;
	}
	
	@Override
	protected Soldier getOfficerInstance() {
		return new RomanOfficer(this, RomanRank.DECANUS);
	}
	
	@Override
	protected Rank getOfficerRank() {
		return RomanRank.DECANUS;
	}
	
	@Override
	protected Soldier getChildInstance() {
		return new Roman(this, RomanRank.LEGIONARY);
	}
	
	@Override
	protected Soldier getEmptyChildInstance() {
		return new Roman(this, RomanRank.LEGIONARY);
	}
}