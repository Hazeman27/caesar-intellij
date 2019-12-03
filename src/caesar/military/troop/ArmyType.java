package caesar.military.troop;

import caesar.military.soldier.Rank;
import org.jetbrains.annotations.Contract;

public enum ArmyType {
	
	ROMAN(TroopType.LEGION, Rank.GENERAL,"[>R<]"),
	GALLIC(TroopType.TRIAD, Rank.CHIEF_WARLORD, "[xGx]");
	
	protected final String symbol;
	protected final Rank officerRank;
	protected final TroopType troopsType;
	
	@Contract(pure = true)
	ArmyType(TroopType troopsType, Rank officerRank, String symbol) {
		
		this.troopsType = troopsType;
		this.officerRank = officerRank;
		this.symbol = symbol;
	}
}