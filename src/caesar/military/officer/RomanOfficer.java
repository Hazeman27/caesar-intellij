package caesar.military.officer;

import caesar.military.UnitOrigin;
import caesar.military.soldier.Name;
import caesar.military.troop.Troop;

public class RomanOfficer extends Officer {
	
	public RomanOfficer(RomanRank rank, Troop parentUnit) {
		
		super(rank, parentUnit, UnitOrigin.ROME);
		this.name = Name.getRandomRoman();
	}
}
