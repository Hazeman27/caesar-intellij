package caesar.military.soldier;

import caesar.game.Game;
import caesar.game.status.Status;
import caesar.game.status.StatusType;
import caesar.military.Unit;
import caesar.military.UnitOrigin;
import caesar.military.UnitParent;
import caesar.military.officer.Rank;
import caesar.ui.Printer;

import java.util.HashMap;
import java.util.Map;

public abstract class Soldier implements Unit {
	
	protected final Map<StatusType, Status> state;
	protected final UnitOrigin origin;
	protected String name;
	protected UnitParent parent;
	protected Rank rank;
	
	protected Soldier(Rank rank, UnitParent parent, UnitOrigin origin) {
		
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
		
		this.parent = parent;
		this.rank = rank;
		this.origin = origin;
	}
	
	@Override
	public void setParent(UnitParent parent) {
		this.parent = parent;
	}
	
	@Override
	public void perish() {
		this.parent.removeChild(this);
	}
	
	@Override
	public UnitOrigin getOrigin() {
		return this.origin;
	}
	
	@Override
	public String getSummary() {
		return "[" + this.rank + "] " + this.name +
			"\n-> Unit: " + this.parent + "\n";
	}
	
	@Override
	public String getFullSummary() {
		
		return this.getSummary() + "-> Health: [" +
			this.getHealth().getCurrentState() +
			"/" +
			StatusType.HEALTH.getMaxState() +
			"]\n-> Morale: [" +
			this.getMorale().getCurrentState() +
			"/" +
			StatusType.MORALE.getMaxState() +
			"]\n-> Satiety: [" +
			this.getSatiety().getCurrentState() +
			"/" +
			StatusType.SATIETY.getMaxState() +
			"]\n";
	}
	
	protected Status getHealth() {
		return this.state.get(StatusType.HEALTH);
	}
	
	public Status getMorale() {
		return this.state.get(StatusType.MORALE);
	}
	
	protected Status getSatiety() {
		return this.state.get(StatusType.SATIETY);
	}
	
	public void setRank(Rank rank) {
		this.rank = rank;
	}
	
	public void updateStatus(StatusType type, int amount) {
		this.state.get(type).updateState(amount);
	}
	
	public Soldier engage(Soldier target, boolean verbose, boolean fullVerbose) {
		
		if (target == null)
			return this;
		
		int damageDealt;
		int damageReceived;
		
		while (!this.isDead() && !target.isDead()) {
			
			damageDealt = this.attack(target);
			damageReceived = target.attack(this);
			
			if (fullVerbose) {
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
		
		if (target.isDead()) {
			if (verbose) System.out.println(this + " has eliminated" + target);
			return this;
		}
		
		if (verbose) System.out.println(target + " has eliminated" + this);
		return target;
	}
	
	private boolean isDead() {
		return this.state.get(StatusType.HEALTH).atMinState();
	}
	
	private int attack(Soldier target) {
		
		if (this.isDead())
			return 0;
		
		int damage = Game.getRandomInt(
			StatusType.HEALTH.getMaxState()
		);
		
		damage += this.getDamageBoost();
		return target.receiveDamage(damage);
	}
	
	protected abstract int getDamageBoost();
	
	private int receiveDamage(int damage) {
		
		damage = Math.max(damage - this.block(damage), 0);
		this.getHealth().updateState(-damage);
		
		if (this.isDead())
			this.perish();
		
		return damage;
	}
	
	protected abstract int block(int damage);
	
	public Soldier engage(Soldier target, boolean verbose) {
		
		if (target == null)
			return this;
		
		while (!this.isDead() && !target.isDead()) {
			this.attack(target);
			target.attack(this);
		}
		
		if (target.isDead()) {
			
			if (verbose)
				Printer.print(this + " has eliminated " + target);
			
			return this;
		}
		
		if (verbose)
			Printer.print(target + " has eliminated " + this);
		
		return target;
	}
	
	@Override
	public String toString() {
		return "[" + this.rank + "] " + this.name;
	}
}