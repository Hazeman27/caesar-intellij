package caesar.military.gaul;

import caesar.military.Unit;
import caesar.military.UnitOrigin;
import caesar.military.UnitParent;
import caesar.military.officer.GaulOfficer;
import caesar.military.officer.GaulRank;
import caesar.military.officer.Rank;
import caesar.military.soldier.Soldier;
import caesar.military.troop.Troop;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class Tribe extends Troop {
	
	static final int UNIT_CAPACITY = 6;
	
	Tribe(UnitParent parent) {
		super(parent, UNIT_CAPACITY, "X", UnitOrigin.GAUL);
	}
	
	Tribe(UnitParent parent, List<Unit> children, List<Soldier> officers) {
		super(parent, children, officers, UNIT_CAPACITY, "X", UnitOrigin.GAUL);
	}
	
	@Override
	protected int getChildCapacity() {
		return Clan.UNIT_CAPACITY;
	}
	
	@NotNull
	@Override
	protected Soldier getOfficerInstance() {
		return new GaulOfficer(GaulRank.WARLORD, this);
	}
	
	@NotNull
	@Override
	protected Rank getOfficerRank() {
		return GaulRank.WARLORD;
	}
	
	@NotNull
	@Override
	protected Unit getChildInstance() {
		return new Clan(this);
	}
	
	@NotNull
	@Override
	protected Unit getEmptyChildInstance() {
		return new Clan(this, new LinkedList<>(), new LinkedList<>());
	}
}
