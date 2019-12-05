package caesar.military.rome;

import caesar.military.MilitaryUnit;
import caesar.military.officer.RomanOfficer;
import caesar.military.officer.RomanRank;
import caesar.military.troop.Troop;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Legion extends Troop {
	
	Legion(Troop parentTroop) {
		super(parentTroop, 9, "[><]");
		this.officer = new RomanOfficer(RomanRank.LEGATE, this);
	}
	
	@Override
	protected List<MilitaryUnit> initUnits() {
		
		List<MilitaryUnit> units = new ArrayList<>();
		IntStream.range(0, this.unitCapacity)
		         .forEach(i -> units.add(new Cohort(this)));
		
		units.add(new CohortFirst(this));
		return units;
	}
}