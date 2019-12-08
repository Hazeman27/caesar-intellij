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

public class Nation extends Troop {
	
	static final int UNIT_CAPACITY = 12;
	
	Nation(UnitParent parent) {
		super(parent, UNIT_CAPACITY, "[XX]", UnitOrigin.GAUL);
	}
	
	Nation(UnitParent parent, List<Unit> children, List<Soldier> officers) {
		super(parent, children, officers, UNIT_CAPACITY, "[XX]", UnitOrigin.GAUL);
	}
	
	@Override
	protected int getChildCapacity() {
		return Chiefdom.UNIT_CAPACITY;
	}
	
	@Override
	protected Soldier getOfficerInstance() {
		return new GaulOfficer(GaulRank.CHIEF_WARLORD, this);
	}
	
	@Override
	protected Rank getOfficerRank() {
		return GaulRank.CHIEF_WARLORD;
	}
	
	@Override
	protected Unit getChildInstance() {
		return new Chiefdom(this);
	}
	
	@Override
	protected Unit getEmptyChildInstance() {
		return new Chiefdom(this, new LinkedList<>(), new LinkedList<>());
	}
}
