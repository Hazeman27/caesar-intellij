package caesar.game.engagement;

import caesar.military.MilitaryUnit;
import caesar.military.troop.Troop;
import caesar.military.troop.TroopType;
import caesar.ui.Printer;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class EngagementController {
	
	private Map<MilitaryUnit, MilitaryUnit> engagements;
	
	public EngagementController(List<MilitaryUnit> sideA, List<MilitaryUnit> sideB) {
		mapEngagements(sideA, sideB);
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
			
			if (j == minSize)
				this.engagements.put(biggest.get(i++), smallest.get(j - 1));
			
			else this.engagements.put(biggest.get(i++), smallest.get(j++));
		}
	}
	
	public void start(boolean verbose) {
		
		ExecutorService executorService = Executors.newCachedThreadPool();
		
		List<Future<MilitaryUnit>> engagementFutures = new ArrayList<>();
		List<Engagement> engagementTasks = new ArrayList<>();
		
		this.engagements.forEach((unitA, unitB) -> {
			engagementTasks.add(new Engagement(unitA, unitB, verbose));
		});
		
		try {
			engagementFutures = executorService.invokeAll(engagementTasks);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		Troop A = new Troop(TroopType.CENTURY);
		Troop B = new Troop(TroopType.TRIBE);
		
		Printer.print(Troop.countSoldiers(A) + " " + Troop.countSoldiers(B));
		A.engage(B, false);
		Printer.print(Troop.countSoldiers(A) + " " + Troop.countSoldiers(B));
	}
}
