package caesar.game.engagement;

import caesar.military.MilitaryUnit;
import caesar.military.troop.Troop;
import caesar.military.troop.TroopType;
import caesar.ui.Printer;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.*;

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
	
	public void start(boolean verbose) throws InterruptedException, ExecutionException {
		
		ExecutorService executorService = Executors.newCachedThreadPool();
		CompletionService<MilitaryUnit> completionService =
			new ExecutorCompletionService<>(executorService);
		
		this.engagements.forEach((unitA, unitB) -> {
			completionService.submit(new Engagement(unitA, unitB, verbose));
		});
		
		int received = 0;
		
		while (received < this.engagements.size()) {
			
			Future<MilitaryUnit> result = completionService.take();
			MilitaryUnit militaryUnit = result.get();
			
			Printer.print(militaryUnit + " has been victorious");
			received++;
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
