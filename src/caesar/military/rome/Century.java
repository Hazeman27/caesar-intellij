package caesar.military.rome;

import caesar.military.MilitaryUnit;
import caesar.military.officer.RomanOfficer;
import caesar.military.officer.RomanRank;
import caesar.military.troop.Troop;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Century extends Troop {
	
	Century(Troop parentTroop) {
		super(parentTroop, 10, ":");
		this.officer = new RomanOfficer(RomanRank.CENTURION, this);
	}
	
	Century(Troop parentTroop, int troopsAmount, String symbol) {
		super(parentTroop, troopsAmount, symbol);
		this.officer = new RomanOfficer(RomanRank.CENTURION, this);
	}
	
	public Century() {
		super(10, ":");
		this.officer = new RomanOfficer(RomanRank.CENTURION, this);
	}
	
	@Override
	protected List<MilitaryUnit> initUnits() {
		
		List<MilitaryUnit> units = new ArrayList<>();
		IntStream.range(0, this.unitsAmount)
		         .forEach(i -> units.add(new Contubernium(this)));
		
		return units;
	}
}