package caesar.game.engagement;

import caesar.military.MilitaryUnit;

import java.util.concurrent.Callable;

public class Engagement implements Callable<MilitaryUnit> {
	
	private MilitaryUnit unitA;
	private MilitaryUnit unitB;
	private boolean verbose;
	
	Engagement(MilitaryUnit unitA, MilitaryUnit unitB, boolean verbose) {
		
		this.unitA = unitA;
		this.unitB = unitB;
		this.verbose = verbose;
	}
	
	@Override
	public MilitaryUnit call() {
		
		if (this.unitA == null || this.unitB == null)
			return null;
		
		return this.unitA.engage(this.unitB, this.verbose);
	}
}
