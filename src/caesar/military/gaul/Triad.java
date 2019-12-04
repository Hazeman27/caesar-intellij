package caesar.military.gaul;

import caesar.military.MilitaryUnit;
import caesar.military.officer.Officer;
import caesar.military.officer.Rank;
import caesar.military.troop.Troop;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Triad extends Troop {
	
	Triad(Troop parentTroop) {
		super(parentTroop, 3, ".");
		this.officer = new Officer(Rank.CHIEF_WARLORD, this);
	}
	
	protected List<MilitaryUnit> initUnits() {
		
		List<MilitaryUnit> units = new ArrayList<>();
		IntStream.range(0, this.unitsAmount)
		         .forEach(i -> units.add(new TribeGroup(this)));
		
		return units;
	}
}
