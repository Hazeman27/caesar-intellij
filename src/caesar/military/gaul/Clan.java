package caesar.military.gaul;

import caesar.military.Unit;
import caesar.military.UnitOrigin;
import caesar.military.UnitParent;
import caesar.military.officer.GaulOfficer;
import caesar.military.officer.GaulRank;
import caesar.military.officer.Rank;
import caesar.military.soldier.Gaul;
import caesar.military.soldier.Soldier;
import caesar.military.troop.Troop;

import java.util.List;

public class Clan extends Troop {
	
	static final int UNIT_CAPACITY = 20;
	
	Clan(UnitParent parent) {
		super(parent, UNIT_CAPACITY, "x", UnitOrigin.GAUL);
	}
	
	Clan(UnitParent parent, List<Unit> children, List<Soldier> officers) {
		super(parent, children, officers, UNIT_CAPACITY, "x", UnitOrigin.GAUL);
	}
	
	@Override
	protected Soldier getOfficerInstance() {
		return new GaulOfficer(GaulRank.CHIEF, this);
	}
	
	@Override
	protected Unit getChildInstance() {
		return new Gaul(GaulRank.WARRIOR, this);
	}
	
	@Override
	protected int getChildCapacity() {
		return 0;
	}
	
	@Override
	protected Rank getOfficerRank() {
		return GaulRank.CHIEF;
	}
	
	@Override
	protected Unit getEmptyChildInstance() {
		return new Gaul(GaulRank.WARRIOR, this);
	}
}
