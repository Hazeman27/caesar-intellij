package caesar.military.officer;

import caesar.military.UnitOrigin;
import caesar.military.soldier.Name;
import caesar.military.troop.Troop;
import org.jetbrains.annotations.NotNull;

public class RomanOfficer extends Officer {
	
	public RomanOfficer(@NotNull RomanRank rank, Troop parentUnit) {
		
		super(rank, parentUnit, UnitOrigin.ROME);
		this.name = Name.getRandomRoman();
	}
}
