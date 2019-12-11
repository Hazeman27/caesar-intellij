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

public class Legion extends Troop {
	
	static final int UNIT_CAPACITY = 11;
	
	Legion(UnitParent parent) {
		super(parent, UNIT_CAPACITY, "[><]", UnitOrigin.ROME);
	}
	
	Legion(UnitParent parent, List<Unit> children, List<Soldier> officers) {
		super(parent, children, officers, UNIT_CAPACITY, "[><]", UnitOrigin.ROME);
	}
	
	@Override
	protected Soldier getOfficerInstance() {
		return new RomanOfficer(RomanRank.LEGATE, this);
	}
	
	@Override
	protected Unit getChildInstance() {
		return new Cohort(this);
	}
	
	@Override
	protected int getChildCapacity() {
		return Cohort.UNIT_CAPACITY;
	}
	
	@Override
	protected Rank getOfficerRank() {
		return RomanRank.LEGATE;
	}
	
	@Override
	protected Unit getEmptyChildInstance() {
		return new Cohort(this, new LinkedList<>(), new LinkedList<>());
	}
}
