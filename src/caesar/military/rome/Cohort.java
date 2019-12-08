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

public class Cohort extends Troop {
	
	static final int UNIT_CAPACITY = 6;
	
	Cohort(UnitParent parent) {
		super(parent, UNIT_CAPACITY, "[:]", UnitOrigin.ROME);
	}
	
	Cohort(UnitParent parent, List<Unit> units) {
		super(parent, units, UNIT_CAPACITY, "[:]", UnitOrigin.ROME);
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
	protected Unit getChildInstance() {
		return new Century(this);
	}
	
	@Override
	protected Unit getEmptyChildInstance() {
		return new Century(this, new LinkedList<>());
	}
	
	@Override
	protected Soldier getNewOfficer(List<Unit> unitsPool) {
		return null;
	}
	
	@Override
	protected List<Unit> getNotFullUnits() {
		return null;
	}
	
	@Override
	protected List<Unit> getNotFullUnitsPool(
		List<Unit> notFullUnits
	) {
		return null;
	}
	
	@Override
	protected List<Soldier> getNotFullOfficersPool(
		List<Unit> notFullUnits
	) {
		return null;
	}
	
	@Override
	protected void assignOfficer(
		Troop child,
		List<Unit> unitsPool,
		List<Soldier> officersPool
	) {
	
	}
	
	@Override
	protected void regroupUnits() {
	
	}
}
