package caesar.military.rome;

import caesar.military.MilitaryUnit;
import caesar.military.officer.Officer;
import caesar.military.officer.RomanOfficer;
import caesar.military.officer.RomanRank;
import caesar.military.troop.Troop;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class Legion extends Troop {
	
	static final int unitCapacity = 9;
	
	Legion(Troop parentUnit) {
		super(parentUnit, unitCapacity, "[><]");
		this.officer = new RomanOfficer(RomanRank.LEGATE, this);
	}
	
	Legion(Troop parentUnit, List<MilitaryUnit> units, Officer officer) {
		
		super(parentUnit, unitCapacity, units, "[><]");
		this.officer = officer;
		this.officer.setParentUnit(this);
	}
	
	@Override
	protected int getChildUnitCapacity() {
		return Cohort.unitCapacity;
	}
	
	@Override
	protected List<MilitaryUnit> initUnits() {
		
		List<MilitaryUnit> units = new LinkedList<>();
		IntStream.range(0, unitCapacity)
		         .forEach(i -> units.add(new Cohort(this)));
		
		units.add(new CohortFirst(this));
		return units;
	}
	
	@Override
	protected Troop getChildUnitInstance(
		List<MilitaryUnit> units,
		Officer officer
	) {
		return new Cohort(this, units, officer);
	}
}