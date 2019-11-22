package caesar.military.troop;

import caesar.military.soldier.Rank;

import java.util.*;

public enum TroopType {

//	Roman army...
	
	CONTUBERNIUM(
		Collections.singletonMap(null, 7),
		Rank.DECANUS, "."
	),
	
	CENTURY(
		Collections.singletonMap(CONTUBERNIUM, 10),
		Rank.CENTURION, ":"
	),
	
	CENTURY_FIRST_COHORT(
		Collections.singletonMap(CONTUBERNIUM, 20),
		Rank.CENTURION, "::"
	),
	
	COHORT(
		Collections.singletonMap(CENTURY, 6),
		Rank.LEAD_CENTURION, "[:]"
	),
	
	COHORT_FIRST(
		Collections.singletonMap(CENTURY_FIRST_COHORT, 5),
		Rank.LEAD_CENTURION, "[::]"
	),
	
	LEGION(
		new HashMap<TroopType, Integer>() {
			{ put(COHORT, 9); put(COHORT_FIRST, 1); }
		}, Rank.LEGATE, "[><]"
	),
	
//	Gallic tribes...
	
	GAULS_GROUP(
		Collections.singletonMap(null, 20),
		Rank.CHIEF, "x"
	),
	
	TRIBE(
		Collections.singletonMap(GAULS_GROUP, 25),
		Rank.WARLORD, "X"
	);
	
	protected Map<TroopType, Integer> troops;
	protected final Rank officerRank;
	protected final String symbol;

	TroopType(Map<TroopType, Integer> troops, Rank officerRank, String symbol) {
		
		this.troops = troops;
		this.officerRank = officerRank;
		this.symbol = symbol;
	}
}
