package caesar.military.officer;

import caesar.military.UnitOrigin;
import caesar.military.soldier.Name;
import caesar.military.troop.Troop;

public class RomanOfficer extends Officer {
	
	public RomanOfficer(Troop parentUnit, RomanRank rank) {
		
		super(parentUnit, rank, UnitOrigin.ROME);
		this.name = Name.getRandomRoman();
	}
}
