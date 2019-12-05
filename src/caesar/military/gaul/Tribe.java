package caesar.military.gaul;

import caesar.military.MilitaryUnit;
import caesar.military.officer.GaulOfficer;
import caesar.military.officer.GaulRank;
import caesar.military.officer.Officer;
import caesar.military.troop.Troop;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class Tribe extends Troop {
	
	static final int unitCapacity = 12;
	
	Tribe(Troop parentUnit) {
		super(parentUnit, unitCapacity, "[x]");
		this.officer = new GaulOfficer(GaulRank.WARLORD, this);
	}
	
	Tribe(Troop parentUnit, List<MilitaryUnit> units, Officer officer) {
		
		super(parentUnit, unitCapacity, units, "[x]");
		this.officer = officer;
		this.officer.setParentUnit(this);
	}
	
	public Tribe() {
		super(unitCapacity, "[x]");
		this.officer = new GaulOfficer(GaulRank.WARLORD, this);
	}
	
	@Override
	protected int getChildUnitCapacity() {
		return Group.unitCapacity;
	}
	
	@Override
	protected List<MilitaryUnit> initUnits() {
		
		List<MilitaryUnit> units = new LinkedList<>();
		IntStream.range(0, unitCapacity)
		         .forEach(i -> units.add(new Group(this)));
		
		return units;
	}
	
	@Override
	protected Troop getChildUnitInstance(
		List<MilitaryUnit> units,
		Officer officer
	) {
		return new Group(this, units, officer);
	}
}
