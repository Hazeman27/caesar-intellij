package caesar.military.rome;

import caesar.military.MilitaryUnit;
import caesar.military.officer.Officer;
import caesar.military.officer.RomanOfficer;
import caesar.military.officer.RomanRank;
import caesar.military.troop.Troop;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Legion extends Troop {
	
	static final int UNIT_CAPACITY = 9;
	
	Legion(Troop parentUnit) {
		super(parentUnit, UNIT_CAPACITY, "[><]");
	}
	
	public Legion() {
		super(UNIT_CAPACITY, "[><]");
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
	protected MilitaryUnit getChildUnitInstance() {
		return new Cohort(this);
	}
	
	@NotNull
	@Override
	protected List<MilitaryUnit> initUnits() {
		
		List<MilitaryUnit> units = super.initUnits();
		units.add(new CohortFirst(this));
		
		return units;
	}
}