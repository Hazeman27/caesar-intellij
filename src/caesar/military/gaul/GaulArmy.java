package caesar.military.gaul;

import caesar.military.MilitaryUnit;
import caesar.military.officer.GaulOfficer;
import caesar.military.officer.GaulRank;
import caesar.military.officer.Officer;
import caesar.military.troop.Troop;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class GaulArmy extends Troop {
	
	private final int unitCapacity;
	
	public GaulArmy(int unitCapacity) {
		
		super(unitCapacity, "[XGX]");
		this.unitCapacity = unitCapacity;
		this.officer = new GaulOfficer(GaulRank.HERO_WARLORD, this);
	}
	
	@Override
	protected int getChildUnitCapacity() {
		return Triad.unitCapacity;
	}
	
	@Override
	protected List<MilitaryUnit> initUnits() {
		
		List<MilitaryUnit> units = new LinkedList<>();
		IntStream.range(0, this.unitCapacity)
		         .forEach(i -> units.add(new Triad(this)));
		
		return units;
	}
	
	@Override
	protected Troop getChildUnitInstance(
		List<MilitaryUnit> units,
		Officer officer
	) {
		return new Triad(this, units, officer);
	}
}
