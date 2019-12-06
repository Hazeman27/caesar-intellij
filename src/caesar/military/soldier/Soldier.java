package caesar.military.soldier;

import caesar.game.Game;
import caesar.game.status.Status;
import caesar.game.status.StatusType;
import caesar.military.MilitaryUnit;
import caesar.military.troop.Troop;
import caesar.ui.Printer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public abstract class Soldier implements MilitaryUnit {
	
	protected Map<StatusType, Status> state;
	protected String name;
	protected Troop parentUnit;
	
	@Contract(pure = true)
	protected Soldier(@NotNull Troop parentUnit) {
		
		this.state = new HashMap<>();
		
		this.state.put(
			StatusType.HEALTH,
			new Status(StatusType.HEALTH)
		);
		
		this.state.put(
			StatusType.MORALE,
			new Status(StatusType.MORALE)
		);
		
		this.state.put(
			StatusType.SATIETY,
			new Status(StatusType.SATIETY)
		);
		
		this.parentUnit = parentUnit;
	}
	
	protected abstract int getDamageBoost();
	protected abstract int block(int damage);
	
	protected Status getHealth() {
		return this.state.get(StatusType.HEALTH);
	}
	
	protected Status getMorale() {
		return this.state.get(StatusType.MORALE);
	}
	
	protected Status getSatiety() {
		return this.state.get(StatusType.SATIETY);
	}
	
	public void updateStatusState(StatusType type, int amount) {
		this.state.get(type).updateState(amount);
	}
	
	private boolean isDead() {
		return this.state.get(StatusType.HEALTH).atMinState();
	}
	
	@Override
	public void die() {
		this.parentUnit.removeSoldier(this);
		this.parentUnit = null;
	}
	
	@Override
	public MilitaryUnit engage(MilitaryUnit targetUnit, boolean verbose) {
		
		if (targetUnit == null)
			return this;
		
		Soldier target = (Soldier) targetUnit;
		
		int damageDealt;
		int damageReceived;

		while (!this.isDead() && !target.isDead()) {
			
			damageDealt = this.attack(target);
			damageReceived = target.attack(this);
			
			if (verbose) {
				Printer.print(
					this +
					" dealt " +
					damageDealt +
					" damage to " +
					target
				);
				
				Printer.print(
					target +
					" dealt " +
					damageReceived +
					" damage to " +
					this
				);
			}
		}
		
		if (target.isDead())
			return this;
		
		return targetUnit;
	}
	
	@Override
	public void setParentUnit(MilitaryUnit parentUnit) {
		this.parentUnit = (Troop) parentUnit;
	}
	
	private int attack(@NotNull Soldier target) {
		
		int damage = Game.getRandomInt(
			this.getHealth().getMaxState()
		);
		
		damage += this.getDamageBoost();
		return target.receiveDamage(damage);
	}
	
	private int receiveDamage(int damage) {
		
		damage = Math.max(damage - this.block(damage), 0);
		this.getHealth().updateState(-damage);
		
		if (this.isDead())
			this.die();
		
		return damage;
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
			this.getHealth().getCurrentState() +
			"/" +
			this.getHealth().getMaxState() +
			"]\n-> Morale: [" +
			this.getMorale().getCurrentState() +
			"/" +
			this.getMorale().getMaxState() +
			"]\n-> Satiety: [" +
			this.getSatiety().getCurrentState() +
			"/" +
			this.getSatiety().getMaxState() +
			"]\n";
	}
	
	@Override
	public String toString() {
		
		return "[" +
			this.getClass().getSimpleName() +
			"] " +
			this.name;
	}
}