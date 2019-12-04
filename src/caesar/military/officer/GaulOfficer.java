package caesar.military.officer;

import caesar.military.soldier.Name;
import caesar.military.troop.Troop;

public class GaulOfficer extends Officer {
	
	public GaulOfficer(GaulRank rank, Troop troop) {
		super(rank, troop);
		this.name = Name.getRandomGallic();
	}
}
