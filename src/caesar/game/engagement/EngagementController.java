package caesar.game.engagement;

import caesar.game.Game;
import caesar.military.MilitaryUnit;
import caesar.military.gaul.TribeGroup;
import caesar.military.rome.Cohort;
import caesar.military.troop.Troop;
import caesar.ui.Printer;

import java.util.*;
import java.util.concurrent.*;

public class EngagementController {
	
	private Map<MilitaryUnit, MilitaryUnit> engagements;
	
	public EngagementController(List<MilitaryUnit> A, List<MilitaryUnit> B) {
		mapEngagements(A, B);
	}
	
	public EngagementController(MilitaryUnit A, MilitaryUnit B) {
		mapEngagements(
			Collections.singletonList(A),
			Collections.singletonList(B)
		);
	}
	
	private void mapEngagements(List<MilitaryUnit> A, List<MilitaryUnit> B) {
		
		this.engagements = new HashMap<>();
		
		List<MilitaryUnit> biggest;
		List<MilitaryUnit> smallest;
		
		if (A.size() >= B.size()) {
			biggest = A;
			smallest = B;
		} else {
			biggest = B;
			smallest = A;
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
		
		this.engagements.forEach((unitA, unitB) -> engagements
			.add(new Engagement(unitA, unitB, verbose))
		);
		
		try {
			executorService.invokeAll(engagements);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		executorService.shutdown();
	}
	
	public static void main(String ...args) {
		
		Troop A = new Cohort();
		Troop B = new TribeGroup();

		Printer.print(Troop.countSoldiers(A) + " " + Troop.countSoldiers(B));
		A.engage(B, false);
		Printer.print(Troop.countSoldiers(A) + " " + Troop.countSoldiers(B));
	}
}
