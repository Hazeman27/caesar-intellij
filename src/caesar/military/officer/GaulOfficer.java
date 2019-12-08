package caesar.military.officer;

import caesar.military.UnitOrigin;
import caesar.military.soldier.Name;
import caesar.military.troop.Troop;

public class GaulOfficer extends Officer {
	
	public GaulOfficer(GaulRank rank, Troop parentUnit) {
		
		super(rank, parentUnit, UnitOrigin.GAUL);
		this.name = Name.getRandomGallic();
	}
}
