package caesar.military.rome;

import caesar.military.MilitaryUnit;
import caesar.military.soldier.Officer;
import caesar.military.soldier.Rank;
import caesar.military.troop.Troop;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Cohort extends Troop {
	
	public Cohort(Troop parentTroop) {
		super(parentTroop, 6, "[:]");
		this.officer = new Officer(Rank.LEAD_CENTURION, this);
	}
	
	protected List<MilitaryUnit> initUnits() {
		
		List<MilitaryUnit> units = new ArrayList<>();
		IntStream.range(0, this.unitsAmount)
		         .forEach(i -> units.add(new Century(this)));
		
		return units;
	}
}