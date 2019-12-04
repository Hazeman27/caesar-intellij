package caesar.military.soldier;

import caesar.game.Game;
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
	protected Troop troop;
	
	@Contract(pure = true)
	protected Soldier(@NotNull Troop troop) {
		
		this.health = new Status(100, 0);
		this.morale = new Status(100, 0);
		this.satiety = new Status(100, 0);
		this.troop = troop;
	}
	
	@Override
	public void perish() {
		
		this.troop.removeSoldier(this);
		this.troop = null;
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
	
	protected abstract int getDamageBoost();
	protected abstract int block(int damageAmount);
	
	private int attackTarget(@NotNull Soldier target) {
		
		int damage = Game.getRandomInt(this.health.getMaxState());
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