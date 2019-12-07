package caesar.game.battle;

import caesar.military.soldier.Soldier;

import java.util.concurrent.Callable;

public class Battle implements Callable<Soldier> {
	
	private final Soldier A;
	private final Soldier B;
	private final boolean verbose;
	
	Battle(Soldier A, Soldier B, boolean verbose) {
		
		this.A = A;
		this.B = B;
		this.verbose = verbose;
	}
	
	@Override
	public Soldier call() {
		
		if (this.A == null || this.B == null)
			return null;
		
		return A.engage(B, this.verbose);
	}
}
