package caesar.military.rome;

import caesar.military.troop.Troop;
import caesar.military.troop.TroopType;

public class Cohort extends Troop {
	
	public Cohort(Troop parentTroop) {
		super(TroopType.COHORT, parentTroop);
	}
}