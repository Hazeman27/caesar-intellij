package caesar.military.soldier;

import caesar.military.MilitaryUnit;
import caesar.military.troop.Troop;
import caesar.ui.Printer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public abstract class Soldier implements MilitaryUnit {
	
	Status health;
	Status morale;
	Status satiety;
	String origin;
	Troop troop;
	boolean alive;
	
	@Contract(pure = true)
	Soldier(@NotNull Troop troop) {
		
		this.health = new Status(100, 0);
		this.morale = new Status(100, 0);
		this.satiety = new Status(100, 0);
		this.origin = troop.getOrigin();
		this.troop = troop;
		this.alive = true;
	}
	
	@Override
	public boolean isAlive() {
		return this.alive;
	}
	
	@Override
	public void perish() {
		this.troop.removeSoldier(this);
		this.alive = false;
	}
	
	@Override
	public void flee() {
		this.troop.removeSoldier(this);
	}
	
	@Override
	public void engage(MilitaryUnit target, boolean verbose) {
		
		if (target == null)
			return;
		
		Soldier targetSoldier = (Soldier) target;
		int thisDamageDealt;
		int targetDamageDealt;

		while (this.isAlive() && target.isAlive()) {
			
			thisDamageDealt = this.attackTarget(targetSoldier);
			targetDamageDealt = targetSoldier.attackTarget(this);
			
			if (verbose) {
				Printer.print(this + " dealt " + thisDamageDealt + " damage to " + target);
				Printer.print(targetSoldier + " dealt " + targetDamageDealt + " damage to " + this);
			}
		}
	}
	
	abstract int attackTarget(Soldier target);
	abstract int block(int damageAmount);
	
	int receiveDamage(int damageAmount) {
		
		damageAmount = Math.max(damageAmount - this.block(damageAmount), 0);
		this.health.decrease(damageAmount);
		
		if (this.health.getState() == this.health.getMinState())
			this.perish();
		
		return damageAmount;
	}
	
	@Override
	public String toString() {
		return "[" + this.origin + "] ";
	}
}
