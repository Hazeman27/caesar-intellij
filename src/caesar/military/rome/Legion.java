package caesar.military.rome;

import caesar.military.troop.Troop;
import caesar.military.troop.TroopType;
import caesar.ui.Printer;

public class Legion extends Troop {
	
	public Legion() {
		super(TroopType.LEGION);
	}
	
	public Legion(Troop parentTroop) {
		super(TroopType.LEGION, parentTroop);
	}
}