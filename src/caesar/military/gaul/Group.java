package caesar.military.gaul;

import caesar.military.MilitaryUnit;
import caesar.military.officer.GaulOfficer;
import caesar.military.officer.GaulRank;
import caesar.military.soldier.Gaul;
import caesar.military.troop.Troop;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Group extends Troop {
	
	Group(Troop parentTroop) {
		super(parentTroop, 19, ".");
		this.officer = new GaulOfficer(GaulRank.CHIEF, this);
	}
	
	public Group() {
		super(19, ".");
		this.officer = new GaulOfficer(GaulRank.CHIEF, this);
	}
	
	protected List<MilitaryUnit> initUnits() {
		
		List<MilitaryUnit> units = new ArrayList<>();
		IntStream.range(0, this.unitsAmount)
		         .forEach(i -> units.add(new Gaul(this)));
		
		return units;
	}
}
