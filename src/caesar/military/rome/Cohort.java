package caesar.military.rome;

import caesar.military.MilitaryUnit;
import caesar.military.officer.Officer;
import caesar.military.officer.RomanOfficer;
import caesar.military.officer.RomanRank;
import caesar.military.troop.Troop;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class Cohort extends Troop {
	
	static int unitCapacity = 6;
	
	Cohort(Troop parentUnit) {
		super(parentUnit, unitCapacity, "[:]");
		this.officer = new RomanOfficer(RomanRank.LEAD_CENTURION, this);
	}
	
	Cohort(Troop parentUnit, List<MilitaryUnit> units, Officer officer) {
		
		super(parentUnit, unitCapacity, units, "[:]");
		this.officer = officer;
		this.officer.setParentUnit(this);
	}
	
	Cohort(Troop parentUnit, int unitCapacity, String symbol) {
		
		super(parentUnit, unitCapacity, symbol);
		Cohort.unitCapacity = unitCapacity;
		this.officer = new RomanOfficer(RomanRank.LEAD_CENTURION, this);
	}
	
	@Override
	protected int getChildUnitCapacity() {
		return Century.unitCapacity;
	}
	
	@Override
	protected List<MilitaryUnit> initUnits() {
		
		List<MilitaryUnit> units = new LinkedList<>();
		IntStream.range(0, unitCapacity)
		         .forEach(i -> units.add(new Century(this)));
		
		return units;
	}
	
	@Override
	protected Troop getChildUnitInstance(
		List<MilitaryUnit> units,
		Officer officer
	) {
		return new Century(this, units, officer);
	}
}