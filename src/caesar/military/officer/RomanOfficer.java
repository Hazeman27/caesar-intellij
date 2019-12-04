package caesar.military.officer;

import caesar.military.soldier.Name;
import caesar.military.troop.Troop;

public class RomanOfficer extends Officer {
	
	public RomanOfficer(RomanRank rank, Troop troop) {
		super(rank, troop);
		this.name = Name.getRandomRoman();
	}
}
