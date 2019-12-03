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
	Troop troop;
	private String origin;
	
	@Contract(pure = true)
	Soldier(@NotNull Troop troop) {
		
		this.health = new Status(100, 0);
		this.morale = new Status(100, 0);
		this.satiety = new Status(100, 0);
		
		this.origin = troop.getOrigin();
		this.troop = troop;
	}
	
	@Override
	public void perish() {
		
		this.troop.removeSoldier(this);
		this.troop = null;
	}
	
	@Override
	public void flee() {
		this.troop.removeSoldier(this);
	}
	
	@Override
	public MilitaryUnit engage(MilitaryUnit target, boolean verbose) {
		
		if (target == null)
			return this;
		
		Soldier targetSoldier = (Soldier) target;
		int thisDamageDealt;
		int targetDamageDealt;

		while (!this.health.isAtMinimum() && !targetSoldier.health.isAtMinimum()) {
			
			thisDamageDealt = this.attackTarget(targetSoldier);
			targetDamageDealt = targetSoldier.attackTarget(this);
			
			if (verbose) {
				Printer.print(
					this +
					" dealt " +
					thisDamageDealt +
					" damage to " +
					targetSoldier
				);
				
				Printer.print(
					targetSoldier +
					" dealt " +
					targetDamageDealt +
					" damage to " +
					this
				);
			}
		}
		
		if (targetSoldier.health.isAtMinimum())
			return this;
		
		else return target;
	}
	
	public void setTroop(Troop troop) {
		this.troop = troop;
	}
	
	abstract int attackTarget(Soldier target);
	abstract int block(int damageAmount);
	
	int receiveDamage(int damageAmount) {
		
		damageAmount = Math.max(damageAmount - this.block(damageAmount), 0);
		this.health.decrease(damageAmount);
		
		if (this.health.isAtMinimum())
			this.perish();
		
		return damageAmount;
	}
	
	@Override
	public String toString() {
		return "[" + this.origin + "] ";
	}
}