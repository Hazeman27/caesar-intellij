package caesar.military.rome;

import caesar.military.troop.Troop;
import caesar.military.troop.TroopType;

public class Century extends Troop {
	
	public Century(Troop parentTroop) {
		super(TroopType.CENTURY, parentTroop);
	}
}