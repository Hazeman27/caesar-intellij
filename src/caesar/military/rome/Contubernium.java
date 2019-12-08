package caesar.military.rome;

import caesar.military.Unit;
import caesar.military.UnitOrigin;
import caesar.military.officer.RomanOfficer;
import caesar.military.officer.RomanRank;
import caesar.military.soldier.Roman;
import caesar.military.soldier.Soldier;
import caesar.military.troop.Troop;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Contubernium extends Troop {
	
	static final int UNIT_CAPACITY = 7;
	
	Contubernium(Century century) {
		super(century, UNIT_CAPACITY, ".", UnitOrigin.ROME);
	}
	
	Contubernium(Century century, List<Unit> units) {
		super(century, units, UNIT_CAPACITY, ".", UnitOrigin.ROME);
	}
	
	@Override
	protected int getChildCapacity() {
		return 0;
	}
	
	@Override
	protected Soldier getOfficerInstance() {
		return new RomanOfficer(RomanRank.DECANUS, this);
	}
	
	@Override
	protected Soldier getChildInstance() {
		return new Roman(this);
	}
	
	@Override
	protected Soldier getEmptyChildInstance() {
		return new Roman(this);
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
	protected List<Unit> getNotFullUnitsPool(List<Unit> notFullUnits) {
		return null;
	}
	
	@Override
	protected List<Soldier> getNotFullOfficersPool(List<Unit> notFullUnits) {
		return null;
	}
	
	@Override
	protected void assignOfficer(
		@NotNull Troop child,
		@NotNull List<Unit> unitsPool,
		@NotNull List<Soldier> officersPool
	) { }
	
	@Override
	protected void regroupUnits() { }
}