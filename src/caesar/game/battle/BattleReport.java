package caesar.game.battle;

import caesar.military.UnitOrigin;
import caesar.military.soldier.Soldier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BattleReport {
	
	private int romanVictorsCount = 0;
	private int gallicVictorsCount = 0;
	private final int committedSoldiersCount;
	private final int survivedSoldiersCount;
	
	private final UnitOrigin battleVictor;
	
	BattleReport(
		@NotNull List<Soldier> battleVictors,
		int committedSoldiersCount
	) {
		
		this.committedSoldiersCount = committedSoldiersCount;
		this.survivedSoldiersCount = battleVictors.size();
		
		battleVictors.forEach(victor -> {
			
			UnitOrigin origin = victor.getOrigin();
			
			if (origin == UnitOrigin.ROME)
				this.romanVictorsCount++;
			
			else this.gallicVictorsCount++;
		});
		
		if (this.romanVictorsCount > this.gallicVictorsCount)
			this.battleVictor = UnitOrigin.ROME;
		
		else this.battleVictor = UnitOrigin.GAUL;
	}
	
	@Override
	public String toString() {
		
		return ":::: Battle report: " +
			"\n--> Enemies defeated: " + romanVictorsCount +
			"\n--> Soldiers lost: " + gallicVictorsCount +
			"\n--> Total battle participants: " + committedSoldiersCount +
			"\n--> Battle survivors: " + survivedSoldiersCount +
			"\n--> Battle victor: " + battleVictor;
	}
}
