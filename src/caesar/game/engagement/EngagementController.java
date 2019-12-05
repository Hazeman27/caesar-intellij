package caesar.game.engagement;

import caesar.game.Game;
import caesar.military.MilitaryUnit;
import caesar.military.gaul.Tribe;
import caesar.military.rome.Century;
import caesar.military.troop.Troop;
import caesar.ui.Printer;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.*;

public class EngagementController {
	
	private Map<MilitaryUnit, MilitaryUnit> engagements;
	
	public EngagementController(List<MilitaryUnit> sideA, List<MilitaryUnit> sideB) {
		mapEngagements(sideA, sideB);
	}
	
	public EngagementController(MilitaryUnit unitA, MilitaryUnit unitB) {
		mapEngagements(
			Collections.singletonList(unitA),
			Collections.singletonList(unitB)
		);
	}
	
	private void mapEngagements(
		@NotNull List<MilitaryUnit> sideA,
		@NotNull List<MilitaryUnit> sideB
	) {
		
		this.engagements = new HashMap<>();
		
		List<MilitaryUnit> biggest;
		List<MilitaryUnit> smallest;
		
		if (sideA.size() >= sideB.size()) {
			biggest = sideA;
			smallest = sideB;
		} else {
			biggest = sideB;
			smallest = sideA;
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
	
	public void start(boolean verbose) {
		
		ExecutorService executorService = Executors.newCachedThreadPool();
		List<Engagement> engagements = new ArrayList<>();
		
		this.engagements.forEach((unitA, unitB) -> {
			engagements.add(new Engagement(unitA, unitB, verbose));
		});
		
		try {
			executorService.invokeAll(engagements);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		executorService.shutdown();
	}
	
	public static void main(String ...args) {
		
		Troop A = new Century();
		Troop B = new Tribe();

		Printer.print(Troop.countSoldiers(A) + " " + Troop.countSoldiers(B));
		A.engage(B, false);
		Printer.print(Troop.countSoldiers(A) + " " + Troop.countSoldiers(B));
		Printer.print(Troop.getFullSummary(A));
	}
}
