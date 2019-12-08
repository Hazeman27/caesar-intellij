package caesar.military.rome;

import caesar.military.Unit;
import caesar.military.UnitOrigin;
import caesar.military.UnitParent;
import caesar.military.officer.RomanOfficer;
import caesar.military.officer.RomanRank;
import caesar.military.soldier.Soldier;
import caesar.military.troop.Troop;

import java.util.LinkedList;
import java.util.List;

public class Century extends Troop {
	
	static final int UNIT_CAPACITY = 10;
	
	Century(UnitParent parentUnit) {
		super(parentUnit, UNIT_CAPACITY, ":", UnitOrigin.ROME);
	}
	
	Century(UnitParent parentUnit, List<Unit> units, List<Soldier> officers) {
		super(parentUnit, units, officers, UNIT_CAPACITY, ":", UnitOrigin.ROME);
	}
	
	@Override
	protected int getChildCapacity() {
		return Contubernium.UNIT_CAPACITY;
	}
	
	@Override
	protected Soldier getOfficerInstance() {
		return new RomanOfficer(this, RomanRank.CENTURION);
	}
	
	@Override
	protected Unit getChildInstance() {
		return new Contubernium(this);
	}
	
	@Override
	protected Unit getEmptyChildInstance() {
		return new Contubernium(this, new LinkedList<>(), new LinkedList<>());
	}
}