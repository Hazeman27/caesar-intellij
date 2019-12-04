package caesar.military.gaul;

import caesar.military.MilitaryUnit;
import caesar.military.officer.GaulOfficer;
import caesar.military.officer.GaulRank;
import caesar.military.troop.Troop;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Tribe extends Troop {
	
	Tribe(Troop parentTroop) {
		super(parentTroop, 25, ".");
		this.officer = new GaulOfficer(GaulRank.WARLORD, this);
	}
	
	public Tribe() {
		super(25, ".");
		this.officer = new GaulOfficer(GaulRank.WARLORD, this);
	}
	
	protected List<MilitaryUnit> initUnits() {
		
		List<MilitaryUnit> units = new ArrayList<>();
		IntStream.range(0, this.unitsAmount)
		         .forEach(i -> units.add(new Group(this)));
		
		return units;
	}
}
