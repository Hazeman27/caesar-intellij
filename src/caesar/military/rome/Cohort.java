package caesar.military.rome;

import caesar.military.Unit;
import caesar.military.UnitOrigin;
import caesar.military.UnitParent;
import caesar.military.officer.Rank;
import caesar.military.officer.RomanOfficer;
import caesar.military.officer.RomanRank;
import caesar.military.soldier.Soldier;
import caesar.military.troop.Troop;

import java.util.LinkedList;
import java.util.List;

public class Cohort extends Troop {
	
	static final int UNIT_CAPACITY = 6;
	
	Cohort(UnitParent parent) {
		super(parent, UNIT_CAPACITY, "[::]", UnitOrigin.ROME);
	}
	
	Cohort(UnitParent parent, List<Unit> children, List<Soldier> officers) {
		super(parent, children, officers, UNIT_CAPACITY, "[::]", UnitOrigin.ROME);
	}
	
	@Override
	protected int getChildCapacity() {
		return Century.UNIT_CAPACITY;
	}
	
	
	@Override
	protected Soldier getOfficerInstance() {
		return new RomanOfficer(RomanRank.LEAD_CENTURION, this);
	}
	
	
	@Override
	protected Rank getOfficerRank() {
		return RomanRank.LEAD_CENTURION;
	}
	
	
	@Override
	protected Unit getChildInstance() {
		return new Century(this);
	}
	
	
	@Override
	protected Unit getEmptyChildInstance() {
		return new Century(this, new LinkedList<>(), new LinkedList<>());
	}
}
