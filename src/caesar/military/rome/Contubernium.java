package caesar.military.rome;

import caesar.military.MilitaryUnit;
import caesar.military.officer.Officer;
import caesar.military.officer.RomanOfficer;
import caesar.military.officer.RomanRank;
import caesar.military.soldier.Roman;
import caesar.military.troop.Troop;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class Contubernium extends Troop {
	
	static final int unitCapacity = 7;
	
	Contubernium(Troop parentUnit) {
		super(parentUnit, unitCapacity, ".");
		this.officer = new RomanOfficer(RomanRank.DECANUS, this);
	}
	
	Contubernium(Troop parentUnit, List<MilitaryUnit> units, Officer officer) {
		
		super(parentUnit, unitCapacity, units, ".");
		this.officer = officer;
		this.officer.setParentUnit(this);
	}
	
	public Contubernium() {
		super(unitCapacity, ".");
		this.officer = new RomanOfficer(RomanRank.DECANUS, this);
	}
	
	@Override
	protected int getChildUnitCapacity() {
		return 0;
	}
	
	@Override
	protected List<MilitaryUnit> initUnits() {
		
		List<MilitaryUnit> units = new LinkedList<>();
		
		IntStream.range(0, unitCapacity)
		         .forEach(i -> units.add(new Roman(this)));
		
		return units;
	}
	
	@Override
	protected Troop getChildUnitInstance(
		List<MilitaryUnit> units,
		Officer officer
	) {
		return null;
	}
}