package caesar.military.rome;

import caesar.military.MilitaryUnit;
import caesar.military.officer.Officer;
import caesar.military.officer.RomanOfficer;
import caesar.military.officer.RomanRank;
import caesar.military.troop.Troop;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class RomanArmy extends Troop {
	
	private final int unitCapacity;
	
	public RomanArmy(int unitCapacity) {
		
		super(unitCapacity, "[>R<]");
		this.unitCapacity = unitCapacity;
		this.officer = new RomanOfficer(RomanRank.GENERAL, this);
	}
	
	@Override
	protected int getChildUnitCapacity() {
		return Legion.unitCapacity;
	}
	
	@Override
	protected List<MilitaryUnit> initUnits() {
		
		List<MilitaryUnit> units = new LinkedList<>();
		IntStream.range(0, this.unitCapacity)
		         .forEach(i -> units.add(new Legion(this)));
		
		return units;
	}
	
	@Override
	protected Troop getChildUnitInstance(
		List<MilitaryUnit> units,
		Officer officer
	) {
		return new Legion(this, units, officer);
	}
}
