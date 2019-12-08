package caesar.military.rome;

import caesar.military.Unit;
import caesar.military.UnitOrigin;
import caesar.military.UnitParent;
import caesar.military.officer.Rank;
import caesar.military.officer.RomanOfficer;
import caesar.military.officer.RomanRank;
import caesar.military.soldier.Soldier;
import caesar.military.troop.Troop;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class Century extends Troop {
	
	static final int UNIT_CAPACITY = 10;
	
	Century(UnitParent parentUnit) {
		super(parentUnit, UNIT_CAPACITY, ":", UnitOrigin.ROME);
	}
	
	Century(UnitParent parentUnit, List<Unit> children, List<Soldier> officers) {
		super(parentUnit, children, officers, UNIT_CAPACITY, ":", UnitOrigin.ROME);
	}
	
	@Override
	protected int getChildCapacity() {
		return Contubernium.UNIT_CAPACITY;
	}
	
	@NotNull
	@Override
	protected Soldier getOfficerInstance() {
		return new RomanOfficer(RomanRank.CENTURION, this);
	}
	
	@NotNull
	@Override
	protected Rank getOfficerRank() {
		return RomanRank.CENTURION;
	}
	
	@NotNull
	@Override
	protected Unit getChildInstance() {
		return new Contubernium(this);
	}
	
	@NotNull
	@Override
	protected Unit getEmptyChildInstance() {
		return new Contubernium(this, new LinkedList<>(), new LinkedList<>());
	}
}