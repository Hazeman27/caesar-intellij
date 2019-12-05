package caesar.military.rome;

import caesar.military.MilitaryUnit;
import caesar.military.officer.RomanOfficer;
import caesar.military.officer.RomanRank;
import caesar.military.troop.Troop;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class RomanArmy extends Troop {
	
	public RomanArmy(int troopsAmount) {
		super(troopsAmount, "[>R<]");
		this.officer = new RomanOfficer(RomanRank.GENERAL, this);
	}
	
	@Override
	protected List<MilitaryUnit> initUnits() {
		
		List<MilitaryUnit> units = new ArrayList<>();
		IntStream.range(0, this.unitCapacity)
		         .forEach(i -> units.add(new Legion(this)));
		
		return units;
	}
}
