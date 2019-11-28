package caesar.game.engagement;

import caesar.military.MilitaryUnit;
import caesar.military.troop.Troop;
import caesar.military.troop.TroopType;
import caesar.ui.Printer;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EngagementController {
	
	static int engagementsCount = 0;
	private Map<MilitaryUnit, MilitaryUnit> engagements;
	
	public EngagementController(List<MilitaryUnit> sideA, List<MilitaryUnit> sideB) {
		initPartakers(sideA, sideB);
	}
	
	private void initPartakers(
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
		engagementsCount = maxSize;
		
		int i = 0;
		int j = 0;
		
		while (i < maxSize) {
			
			if (j == minSize)
				this.engagements.put(biggest.get(i++), smallest.get(j - 1));
			
			else this.engagements.put(biggest.get(i++), smallest.get(j++));
		}
	}
	
	public void start(boolean verbose) {

		for (Map.Entry<MilitaryUnit, MilitaryUnit> entry: this.engagements.entrySet()) {
			
			Printer.print(entry.getKey() + " vs " + entry.getValue());
			
			new Thread(new Engagement(
				entry.getKey(),
				entry.getValue(),
				verbose
			)).start();
		}
	}
	
	public static void main(String[] args) {
		
		Troop A = new Troop(TroopType.CONTUBERNIUM);
		Troop B = new Troop(TroopType.GAULS_GROUP);
		
		Printer.print(A + " " + B);
		
		A.engage(B, true);
		Printer.print(A + " " + B);
	}
}
