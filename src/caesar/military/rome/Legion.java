package caesar.military.rome;

import caesar.military.Unit;
import caesar.military.UnitOrigin;
import caesar.military.officer.Officer;
import caesar.military.officer.RomanOfficer;
import caesar.military.officer.RomanRank;
import caesar.military.troop.Troop;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Legion extends Troop {
	
	static final int UNIT_CAPACITY = 9;
	
	Legion(Troop parentUnit) {
		super(parentUnit, UNIT_CAPACITY, "[><]", UnitOrigin.ROME);
	}
	
	@Override
	protected int getChildUnitCapacity() {
		return Cohort.UNIT_CAPACITY;
	}
	
	@Override
	protected Officer getOfficerInstance() {
		return new RomanOfficer(RomanRank.LEGATE, this);
	}
	
	@Override
	protected Unit getChildUnitInstance() {
		return new Cohort(this);
	}
	
	@NotNull
	@Override
	protected List<Unit> initUnits() {
		
		List<Unit> units = super.initUnits();
		units.add(new CohortFirst(this));
		
		return units;
	}
}