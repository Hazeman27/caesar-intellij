package caesar.military.gaul;

import caesar.military.Unit;
import caesar.military.UnitOrigin;
import caesar.military.UnitParent;
import caesar.military.officer.GaulOfficer;
import caesar.military.officer.GaulRank;
import caesar.military.officer.Rank;
import caesar.military.soldier.Soldier;
import caesar.military.troop.Troop;

import java.util.LinkedList;
import java.util.List;

public class Chiefdom extends Troop {
	
	static final int UNIT_CAPACITY = 5;
	
	Chiefdom(UnitParent parent) {
		super(parent, UNIT_CAPACITY, "[X]", UnitOrigin.GAUL);
	}
	
	Chiefdom(UnitParent parent, List<Unit> children, List<Soldier> officers) {
		super(parent, children, officers, UNIT_CAPACITY, "[X]", UnitOrigin.GAUL);
	}
	
	@Override
	protected Soldier getOfficerInstance() {
		return new GaulOfficer(GaulRank.CHIEF_WARLORD, this);
	}
	
	@Override
	protected Unit getChildInstance() {
		return new Tribe(this);
	}
	
	@Override
	protected int getChildCapacity() {
		return Tribe.UNIT_CAPACITY;
	}
	
	@Override
	protected Rank getOfficerRank() {
		return GaulRank.CHIEF_WARLORD;
	}
	
	@Override
	protected Unit getEmptyChildInstance() {
		return new Tribe(this, new LinkedList<>(), new LinkedList<>());
	}
}
