package caesar.military.rome;

import caesar.military.MilitaryUnit;
import caesar.military.officer.RomanOfficer;
import caesar.military.officer.RomanRank;
import caesar.military.troop.Troop;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Cohort extends Troop {
	
	Cohort(Troop parentTroop) {
		super(parentTroop, 6, "[:]");
		this.officer = new RomanOfficer(RomanRank.LEAD_CENTURION, this);
	}
	
	Cohort(Troop parentTroop, int troopsAmount, String symbol) {
		super(parentTroop, troopsAmount, symbol);
		this.officer = new RomanOfficer(RomanRank.LEAD_CENTURION, this);
	}
	
	@Override
	protected List<MilitaryUnit> initUnits() {
		
		List<MilitaryUnit> units = new ArrayList<>();
		IntStream.range(0, this.unitsAmount)
		         .forEach(i -> units.add(new Century(this)));
		
		return units;
	}
}