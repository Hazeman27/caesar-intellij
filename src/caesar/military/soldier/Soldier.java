package caesar.military.soldier;

import caesar.game.Game;
import caesar.game.status.Status;
import caesar.game.status.StatusType;
import caesar.military.Unit;
import caesar.military.UnitOrigin;
import caesar.military.troop.Troop;
import caesar.ui.Printer;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public abstract class Soldier implements Unit {
	
	protected final Map<StatusType, Status> state;
	protected final UnitOrigin origin;
	protected String name;
	protected Troop parentUnit;
	
	protected Soldier(@NotNull Troop parentUnit, UnitOrigin origin) {
		
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
		this.origin = origin;
	}
	
	protected abstract int getDamageBoost();
	protected abstract int block(int damage);
	
	protected Status getHealth() {
		return this.state.get(StatusType.HEALTH);
	}
	
	public Status getMorale() {
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
	public UnitOrigin getOrigin() {
		return this.origin;
	}
	
	@Override
	public void die() {
		this.parentUnit.removeSoldier(this);
		this.parentUnit = null;
	}
	
	public Soldier engage(Soldier target, boolean verbose) {
		
		if (target == null)
			return this;
		
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
		
		return target;
	}
	
	@Override
	public void setParentUnit(Unit parentUnit) {
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