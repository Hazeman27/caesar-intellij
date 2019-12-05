package caesar.military.gaul;

import caesar.military.MilitaryUnit;
import caesar.military.officer.GaulOfficer;
import caesar.military.officer.GaulRank;
import caesar.military.officer.Officer;
import caesar.military.troop.Troop;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class TribeGroup extends Troop {
	
	static final int unitCapacity = 5;
	
	TribeGroup(Troop parentUnit) {
		super(parentUnit, unitCapacity, "[X]");
		this.officer = new GaulOfficer(GaulRank.CHIEF_WARLORD, this);
	}
	
	TribeGroup(Troop parentUnit, List<MilitaryUnit> units, Officer officer) {
		
		super(parentUnit, unitCapacity, units, "[X]");
		this.officer = officer;
		this.officer.setParentUnit(this);
	}
	
	@Override
	protected int getChildUnitCapacity() {
		return Tribe.unitCapacity;
	}
	
	@Override
	protected List<MilitaryUnit> initUnits() {
		
		List<MilitaryUnit> units = new LinkedList<>();
		IntStream.range(0, unitCapacity)
		         .forEach(i -> units.add(new Tribe(this)));
		
		return units;
	}
	
	@Override
	protected Troop getChildUnitInstance(
		List<MilitaryUnit> units,
		Officer officer
	) {
		return new Tribe(this, units, officer);
	}
}
