package caesar.military.rome;

import caesar.military.MilitaryUnit;
import caesar.military.soldier.Officer;
import caesar.military.soldier.Rank;
import caesar.military.troop.Troop;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class RomanArmy extends Troop {
	
	public RomanArmy(int legionsAmount) {
		super(legionsAmount, "[>R<]");
		this.officer = new Officer(Rank.GENERAL, this);
	}
	
	protected List<MilitaryUnit> initUnits() {
		
		List<MilitaryUnit> units = new ArrayList<>();
		IntStream.range(0, this.unitsAmount)
		         .forEach(i -> units.add(new Legion(this)));
		
		return units;
	}
}
