package caesar.military.soldier;

import caesar.game.Game;
import caesar.game.status.Status;
import caesar.military.MilitaryUnit;
import caesar.military.troop.Troop;
import caesar.ui.Printer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public abstract class Soldier implements MilitaryUnit {
	
	private Status health;
	protected Status morale;
	protected Status satiety;
	protected String name;
	protected Troop parentUnit;
	
	@Contract(pure = true)
	protected Soldier(@NotNull Troop parentUnit) {
		
		this.health = new Status();
		this.morale = new Status();
		this.satiety = new Status();
		this.parentUnit = parentUnit;
	}
	
	@Override
	public void perish() {
		this.parentUnit.removeSoldier(this);
		this.parentUnit = null;
	}
	
	@Override
	public MilitaryUnit engage(MilitaryUnit target, boolean verbose) {
		
		if (target == null)
			return this;
		
		Soldier targetSoldier = (Soldier) target;
		int thisDamageDealt;
		int targetDamageDealt;

		while (!this.health.isAtMinimum() &&
			!targetSoldier.health.isAtMinimum()
		) {
			
			thisDamageDealt =
				this.attackTarget(targetSoldier);
			targetDamageDealt =
				targetSoldier.attackTarget(this);
			
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
	
	public void setParentUnit(MilitaryUnit parentUnit) {
		this.parentUnit = (Troop) parentUnit;
	}
	
	protected abstract int getDamageBoost();
	protected abstract int block(int damageAmount);
	
	private int attackTarget(@NotNull Soldier target) {
		
		int damage = Game.getRandomInt(Status.getMaxState());
		damage += this.getDamageBoost();
		
		return target.receiveDamage(damage);
	};
	
	private int receiveDamage(int damageAmount) {
		
		damageAmount = Math.max(damageAmount - this.block(damageAmount), 0);
		this.health.decrease(damageAmount);
		
		if (this.health.isAtMinimum())
			this.perish();
		
		return damageAmount;
	}
	
	@Override
	public String toString() {
		return "[" + this.getClass().getSimpleName() + "] " + this.name;
	}
}