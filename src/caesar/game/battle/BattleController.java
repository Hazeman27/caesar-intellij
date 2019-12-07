package caesar.game.battle;

import caesar.game.Game;
import caesar.military.UnitOrigin;
import caesar.military.gaul.GaulArmy;
import caesar.military.rome.RomanArmy;
import caesar.military.soldier.Soldier;
import caesar.military.troop.Troop;
import caesar.ui.Printer;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class BattleController {
	
	private Map<Soldier, Soldier> engagements;
	private final int committedSoldiersCount;
	
	public BattleController(@NotNull Soldier ...soldiers) {
		
		this.engagements = new HashMap<>();
		this.committedSoldiersCount = soldiers.length;
		
		List<Soldier> romans = Arrays
			.stream(soldiers)
			.filter(UnitOrigin::isRoman)
			.collect(Collectors.toList());
		
		List<Soldier> gauls = Arrays
			.stream(soldiers)
			.filter(UnitOrigin::isGallic)
			.collect(Collectors.toList());
		
		List<Soldier> biggest;
		List<Soldier> smallest;
		
		if (romans.size() >= gauls.size()) {
			biggest = romans;
			smallest = gauls;
		} else {
			biggest = gauls;
			smallest = romans;
		}
		
		int maxSize = biggest.size();
		int minSize = smallest.size();
		int i = 0;
		int j = 0;
		
		while (i < maxSize) {
			
			if (j == minSize) {
				this.engagements.put(
					biggest.get(i++),
					smallest.get(Game.getRandomInt(j))
				);
			} else {
				this.engagements.put(
					biggest.get(i++),
					smallest.get(j++)
				);
			}
		}
	}
	
	public BattleReport start(boolean verbose) {
		
		ExecutorService executorService = Executors.newCachedThreadPool();
		
		List<Battle> battles = new ArrayList<>();
		List<Future<Soldier>> reportFutures = new ArrayList<>();
		
		this.engagements.forEach((soldierA, soldierB) -> battles
			.add(new Battle(soldierA, soldierB, verbose))
		);
		
		try {
			reportFutures = executorService.invokeAll(battles);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		executorService.shutdown();
		
		return new BattleReport(
			reportFutures,
			this.committedSoldiersCount
		);
	}
	
	public static void main(String ...args) {
		
		Troop A = new RomanArmy(10);
		Troop B = new GaulArmy(10);

		Printer.print(Troop.countSoldiers(A) + " " + Troop.countSoldiers(B));
		A.engage(B, false);
		Printer.print(Troop.countSoldiers(A) + " " + Troop.countSoldiers(B));
	}
}
