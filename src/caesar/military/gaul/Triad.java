package caesar.military.gaul;

import caesar.military.MilitaryUnit;
import caesar.military.officer.GaulOfficer;
import caesar.military.officer.GaulRank;
import caesar.military.troop.Troop;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Triad extends Troop {
	
	Triad(Troop parentTroop) {
		super(parentTroop, 3, ".");
		this.officer = new GaulOfficer(GaulRank.CHIEF_WARLORD, this);
	}
	
	@Override
	protected List<MilitaryUnit> initUnits() {
		
		List<MilitaryUnit> units = new ArrayList<>();
		IntStream.range(0, this.unitsAmount)
		         .forEach(i -> units.add(new TribeGroup(this)));
		
		return units;
	}
}
