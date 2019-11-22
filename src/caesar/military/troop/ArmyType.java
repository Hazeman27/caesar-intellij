package caesar.military.troop;

import caesar.military.soldier.Rank;

public enum ArmyType {
	
	ROMAN(TroopType.LEGION, Rank.GENERAL,"[>R<]"),
	GALLIC(TroopType.TRIBE, Rank.CHIEF_WARLORD, "[xGx]");
	
	protected final String symbol;
	protected final Rank officerRank;
	protected final TroopType troopsType;
	
	ArmyType(TroopType troopsType, Rank officerRank, String symbol) {
		
		this.troopsType = troopsType;
		this.officerRank = officerRank;
		this.symbol = symbol;
	}
}
