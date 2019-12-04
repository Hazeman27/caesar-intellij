package caesar.military.rome;

import caesar.military.MilitaryUnit;
import caesar.military.officer.RomanOfficer;
import caesar.military.officer.RomanRank;
import caesar.military.soldier.Roman;
import caesar.military.troop.Troop;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Contubernium extends Troop {
	
	Contubernium(Troop parentTroop) {
		super(parentTroop, 7, ".");
		this.officer = new RomanOfficer(RomanRank.DECANUS, this);
	}
	
	public Contubernium() {
		super(7, ".");
		this.officer = new RomanOfficer(RomanRank.DECANUS, this);
	}
	
	@Override
	protected List<MilitaryUnit> initUnits() {
		
		List<MilitaryUnit> units = new ArrayList<>();
		IntStream.range(0, this.unitsAmount)
		         .forEach(i -> units.add(new Roman(this)));
		
		return units;
	}
}