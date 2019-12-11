package caesar.game.battle;

import caesar.military.UnitOrigin;

public class BattleReport {
	
	private final int romanVictorsCount;
	private final int gallicVictorsCount;
	private final int committedSoldiersCount;
	private final int survivedSoldiersCount;
	
	private final UnitOrigin battleVictor;
	
	public BattleReport(
		int romanVictorsCount,
		int gallicVictorsCount,
		int committedSoldiersCount,
		int survivedSoldiersCount
	) {
		
		this.romanVictorsCount = romanVictorsCount;
		this.gallicVictorsCount = gallicVictorsCount;
		this.committedSoldiersCount = committedSoldiersCount;
		this.survivedSoldiersCount = survivedSoldiersCount;
		
		if (romanVictorsCount >= gallicVictorsCount)
			this.battleVictor = UnitOrigin.ROME;
		
		else this.battleVictor = UnitOrigin.GAUL;
	}
	
	@Override
	public String toString() {
		
		return "::: Battle report: " +
			"\n--> Enemies defeated: " + romanVictorsCount +
			"\n--> Soldiers lost: " + gallicVictorsCount +
			"\n--> Total battle participants: " + committedSoldiersCount +
			"\n--> Battle survivors: " + survivedSoldiersCount +
			"\n--> Battle victor: " + battleVictor + "\n";
	}
}
