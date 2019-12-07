package caesar.game.battle;

import caesar.military.UnitOrigin;
import caesar.military.soldier.Soldier;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class BattleReport {
	
	private int romanVictorsCount = 0;
	private int gallicVictorsCount = 0;
	private final int committedSoldiersCount;
	private final int survivedSoldiersCount;
	
	private final UnitOrigin battleVictor;
	
	BattleReport(
		@NotNull List<Future<Soldier>> battleFutures,
		int committedSoldiersCount
	) {
		
		this.committedSoldiersCount = committedSoldiersCount;
		this.survivedSoldiersCount = battleFutures.size();
		
		battleFutures.forEach(future -> {
			
			try {
				Soldier victor = future.get();
				UnitOrigin origin = victor.getOrigin();
				
				if (origin == UnitOrigin.ROME)
					this.romanVictorsCount++;
				
				else this.gallicVictorsCount++;
				
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		});
		
		if (this.romanVictorsCount > this.gallicVictorsCount)
			this.battleVictor = UnitOrigin.ROME;
		
		else this.battleVictor = UnitOrigin.GAUL;
	}
	
	@Override
	public String toString() {
		
		return "--> Battle report: " +
			"\n--> Enemies defeated: " + romanVictorsCount +
			"\n--> Soldiers lost: " + gallicVictorsCount +
			"\n--> Total battle participants: " + committedSoldiersCount +
			"\n--> Battle survivors: " + survivedSoldiersCount +
			"\n--> Battle victor: " + battleVictor;
	}
}
