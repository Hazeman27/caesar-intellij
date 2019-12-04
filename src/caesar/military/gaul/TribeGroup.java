package caesar.military.gaul;

import caesar.military.MilitaryUnit;
import caesar.military.soldier.Officer;
import caesar.military.soldier.Rank;
import caesar.military.troop.Troop;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class TribeGroup extends Troop {
	
	public TribeGroup(Troop parentTroop) {
		super(parentTroop, 19, ".");
		this.officer = new Officer(Rank.CHIEF, this);
	}
	
	protected List<MilitaryUnit> initUnits() {
		
		List<MilitaryUnit> units = new ArrayList<>();
		IntStream.range(0, this.unitsAmount)
		         .forEach(i -> units.add(new Tribe(this)));
		
		return units;
	}
}
