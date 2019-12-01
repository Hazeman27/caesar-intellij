package caesar.military.troop;

import caesar.military.soldier.Rank;
import org.jetbrains.annotations.Contract;

import java.util.*;

public enum TroopType {

//	Roman army...
	
	CONTUBERNIUM(
		Collections.singletonMap(null, 7),
		Rank.DECANUS,
		TroopOrigin.ROMAN, "."
	),
	
	CENTURY(
		Collections.singletonMap(CONTUBERNIUM, 10),
		Rank.CENTURION,
		TroopOrigin.ROMAN, ":"
	),
	
	CENTURY_FIRST_COHORT(
		Collections.singletonMap(CONTUBERNIUM, 20),
		Rank.CENTURION,
		TroopOrigin.ROMAN, "::"
	),
	
	COHORT(
		Collections.singletonMap(CENTURY, 6),
		Rank.LEAD_CENTURION,
		TroopOrigin.ROMAN, "[:]"
	),
	
	COHORT_FIRST(
		Collections.singletonMap(CENTURY_FIRST_COHORT, 5),
		Rank.LEAD_CENTURION,
		TroopOrigin.ROMAN, "[::]"
	),
	
	LEGION(
		new HashMap<TroopType, Integer>() {
			{ put(COHORT, 9); put(COHORT_FIRST, 1); }
		}, Rank.LEGATE, TroopOrigin.ROMAN, "[><]"
	),
	
//	Gallic tribes...
	
	GAULS_GROUP(
		Collections.singletonMap(null, 20),
		Rank.CHIEF,
		TroopOrigin.GALLIC, "x"
	),
	
	TRIBE(
		Collections.singletonMap(GAULS_GROUP, 25),
		Rank.WARLORD,
		TroopOrigin.GALLIC, "[xx]"
	),
	
	TRIBES_GROUP(
		Collections.singletonMap(TRIBE, 4),
		Rank.WARLORD,
		TroopOrigin.GALLIC, "[X]"
	),
	
	TRIAD(
		Collections.singletonMap(TRIBES_GROUP, 3),
		Rank.WARLORD,
		TroopOrigin.GALLIC, "[XX]"
	);
	
	Map<TroopType, Integer> troops;
	final Rank officerRank;
	final TroopOrigin origin;
	final String symbol;

	@Contract(pure = true)
	TroopType(
		Map<TroopType, Integer> troops,
		Rank officerRank,
		TroopOrigin origin,
		String symbol
	) {
		
		this.troops = troops;
		this.officerRank = officerRank;
		this.origin = origin;
		this.symbol = symbol;
	}
}
