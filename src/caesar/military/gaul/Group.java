package caesar.military.gaul;

import caesar.military.MilitaryUnit;
import caesar.military.officer.GaulOfficer;
import caesar.military.officer.GaulRank;
import caesar.military.officer.Officer;
import caesar.military.soldier.Gaul;
import caesar.military.troop.Troop;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class Group extends Troop {
	
	static final int unitCapacity = 9;
	
	Group(Troop parentUnit) {
		super(parentUnit, unitCapacity, "x");
		this.officer = new GaulOfficer(GaulRank.CHIEF, this);
	}
	
	Group(Troop parentUnit, List<MilitaryUnit> units, Officer officer) {
		
		super(parentUnit, unitCapacity, units, "x");
		this.officer = officer;
		this.officer.setParentUnit(this);
	}
	
	public Group() {
		super(19, ".");
		this.officer = new GaulOfficer(GaulRank.CHIEF, this);
	}
	
	@Override
	protected int getChildUnitCapacity() {
		return 0;
	}
	
	@Override
	protected List<MilitaryUnit> initUnits() {
		
		List<MilitaryUnit> units = new LinkedList<>();
		IntStream.range(0, unitCapacity)
		         .forEach(i -> units.add(new Gaul(this)));
		
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
