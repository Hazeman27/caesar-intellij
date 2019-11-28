package caesar.military.rome;

import caesar.military.troop.Troop;
import caesar.military.troop.TroopType;

public class CohortFirst extends Troop {
	
	public CohortFirst(Troop parentTroop) {
		super(TroopType.COHORT_FIRST, parentTroop);
	}
}