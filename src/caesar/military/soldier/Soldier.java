package caesar.military.soldier;

import caesar.game.Game;
import caesar.game.status.State;
import caesar.game.status.StateType;
import caesar.military.MilitaryUnit;
import caesar.military.troop.Troop;
import caesar.ui.Printer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public abstract class Soldier implements MilitaryUnit {
	
	protected Map<StateType, State> state;
	protected String name;
	protected Troop parentUnit;
	
	@Contract(pure = true)
	protected Soldier(@NotNull Troop parentUnit) {
		
		this.state = new HashMap<>();
		
		this.state.put(StateType.HEALTH, new State());
		this.state.put(StateType.MORALE, new State());
		this.state.put(StateType.SATIETY, new State());
		
		this.parentUnit = parentUnit;
	}
	
	protected abstract int getDamageBoost();
	protected abstract int block(int damageAmount);
	
	public void modifyState(StateType type, int amount) {
		this.state.get(type).modify(amount);
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

		while (!this.state.get(StateType.HEALTH).isAtMinimum() &&
			!targetSoldier.state.get(StateType.HEALTH).isAtMinimum()
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
		
		if (targetSoldier.state.get(StateType.HEALTH).isAtMinimum())
			return this;
		
		else return target;
	}
	
	@Override
	public void setParentUnit(MilitaryUnit parentUnit) {
		this.parentUnit = (Troop) parentUnit;
	}
	
	private int attackTarget(@NotNull Soldier target) {
		
		int damage = Game.getRandomInt(
			this.state.get(StateType.HEALTH).getMax()
		);
		
		damage += this.getDamageBoost();
		
		return target.receiveDamage(damage);
	};
	
	private int receiveDamage(int damageAmount) {
		
		damageAmount = Math.max(damageAmount - this.block(damageAmount), 0);
		this.state.get(StateType.HEALTH).modify(-damageAmount);
		
		if (this.state.get(StateType.HEALTH).isAtMinimum())
			this.perish();
		
		return damageAmount;
	}
	
	@Override
	public String getSummary() {
		
		return "[" +
			this.getClass().getSimpleName() +
			"] " +
			this.name +
			"\n-> Unit: " +
			this.parentUnit +
			"\n";
	}
	
	@Override
	public String getFullSummary() {
		
		return "[" +
			this.getClass().getSimpleName() +
			"] " +
			this.name +
			"\n-> Unit: " +
			this.parentUnit +
			"\n-> Health: [" +
			this.state.get(StateType.HEALTH).getCurrent() +
			"/" +
			this.state.get(StateType.HEALTH).getMax() +
			"]\n-> Morale: [" +
			this.state.get(StateType.MORALE).getCurrent() +
			"/" +
			this.state.get(StateType.MORALE).getMax() +
			"]\n-> Satiety: [" +
			this.state.get(StateType.SATIETY).getCurrent() +
			"/" +
			this.state.get(StateType.SATIETY).getMax() +
			"]\n";
	}
	
	@Override
	public String toString() {
		return "[" + this.getClass().getSimpleName() + "] " + this.name;
	}
}