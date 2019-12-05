package caesar.military.gaul;

import caesar.military.MilitaryUnit;
import caesar.military.officer.GaulOfficer;
import caesar.military.officer.GaulRank;
import caesar.military.officer.Officer;
import caesar.military.troop.Troop;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class Triad extends Troop {
	
	static final int unitCapacity = 3;
	
	Triad(Troop parentUnit) {
		super(parentUnit, unitCapacity, "[XX]");
		this.officer = new GaulOfficer(GaulRank.CHIEF_WARLORD, this);
	}
	
	Triad(Troop parentUnit, List<MilitaryUnit> units, Officer officer) {
		
		super(parentUnit, unitCapacity, units, "[XX]");
		this.officer = officer;
		this.officer.setParentUnit(this);
	}
	
	@Override
	protected int getChildUnitCapacity() {
		return TribeGroup.unitCapacity;
	}
	
	@Override
	protected List<MilitaryUnit> initUnits() {
		
		List<MilitaryUnit> units = new LinkedList<>();
		IntStream.range(0, unitCapacity)
		         .forEach(i -> units.add(new TribeGroup(this)));
		
		return units;
	}
	
	@Override
	protected Troop getChildUnitInstance(
		List<MilitaryUnit> units,
		Officer officer
	) {
		return new TribeGroup(this, units, officer);
	}
}
