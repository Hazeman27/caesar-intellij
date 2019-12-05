package caesar.military.rome;

import caesar.military.MilitaryUnit;
import caesar.military.officer.Officer;
import caesar.military.officer.RomanOfficer;
import caesar.military.officer.RomanRank;
import caesar.military.troop.Troop;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class Century extends Troop {
	
	static int unitCapacity = 10;
	
	Century(Troop parentUnit) {
		super(parentUnit, unitCapacity, ":");
		this.officer = new RomanOfficer(RomanRank.CENTURION, this);
	}
	
	Century(Troop parentUnit, List<MilitaryUnit> units, Officer officer) {
		
		super(parentUnit, unitCapacity, units, ":");
		this.officer = officer;
		this.officer.setParentUnit(this);
	}
	
	Century(Troop parentUnit, int unitCapacity, String symbol) {
		
		super(parentUnit, unitCapacity, symbol);
		Century.unitCapacity = unitCapacity;
		this.officer = new RomanOfficer(RomanRank.CENTURION, this);
	}
	
	Century(
		Troop parentUnit,
		List<MilitaryUnit> units,
		Officer officer,
		int unitCapacity,
		String symbol
	) {
		
		super(parentUnit, unitCapacity, units, symbol);
		Century.unitCapacity = unitCapacity;
		
		this.officer = officer;
		this.officer.setParentUnit(this);
	}
	
	public Century() {
		super(unitCapacity, ":");
		this.officer = new RomanOfficer(RomanRank.CENTURION, this);
	}
	
	@Override
	protected int getChildUnitCapacity() {
		return Contubernium.unitCapacity;
	}
	
	@Override
	protected List<MilitaryUnit> initUnits() {
		
		List<MilitaryUnit> units = new LinkedList<>();
		IntStream.range(0, unitCapacity)
		         .forEach(i -> units.add(new Contubernium(this)));
		
		return units;
	}
	
	@Override
	protected Troop getChildUnitInstance(
		List<MilitaryUnit> units,
		Officer officer
	) {
		return new Contubernium(this, units, officer);
	}
}