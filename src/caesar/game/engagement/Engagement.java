package caesar.game.engagement;

import caesar.military.MilitaryUnit;
import caesar.ui.Printer;
import org.jetbrains.annotations.Contract;

public class Engagement implements Runnable {
	
	static int engagementsCount = 0;
	private MilitaryUnit unitA;
	private MilitaryUnit unitB;
	private boolean verbose;
	
	@Contract(pure = true)
	Engagement(MilitaryUnit unitA, MilitaryUnit unitB, boolean verbose) {
		
		this.unitA = unitA;
		this.unitB = unitB;
		this.verbose = verbose;
	}
	
	@Override
	public void run() {
		
		if (this.verbose) {
			
			Printer.print(this.unitA + " is engaging with " + this.unitB);
			this.unitA.engage(this.unitB, true);
			
			if (!this.unitB.isAlive())
				Printer.print(this.unitA + " has eliminated " + this.unitB);
			else if (!this.unitA.isAlive())
				Printer.print(this.unitB + " has eliminated " + this.unitA);
		} else {
			this.unitA.engage(this.unitB, false);
		}
		
		engagementsCount++;
		
		if (engagementsCount == EngagementController.engagementsCount)
			Printer.print();
	}
}
