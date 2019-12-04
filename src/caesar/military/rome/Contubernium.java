package caesar.military.rome;

import caesar.military.MilitaryUnit;
import caesar.military.soldier.Officer;
import caesar.military.soldier.Rank;
import caesar.military.soldier.Roman;
import caesar.military.troop.Troop;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Contubernium extends Troop {
	
	Contubernium(Troop parentTroop) {
		super(parentTroop, 7, ".");
		this.officer = new Officer(Rank.DECANUS, this);
	}
	
	protected List<MilitaryUnit> initUnits() {
		
		List<MilitaryUnit> units = new ArrayList<>();
		IntStream.range(0, this.unitsAmount)
		         .forEach(i -> units.add(new Roman(this)));
		
		return units;
	}
}