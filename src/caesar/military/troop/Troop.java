package caesar.military.troop;

import caesar.game.Game;
import caesar.game.battle.Battle;
import caesar.game.battle.BattleReport;
import caesar.game.status.StatusType;
import caesar.military.Unit;
import caesar.military.UnitParent;
import caesar.military.UnitOrigin;
import caesar.military.officer.Officer;
import caesar.military.soldier.Soldier;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public abstract class Troop implements Unit, UnitParent {
	
	private final int capacity;
	private final String symbol;
	private final UnitOrigin origin;
	
	protected List<Unit> children;
	protected UnitParent parent;
	protected Soldier officer;
	
	protected Troop(
		UnitParent parent,
		List<Unit> children,
		int capacity,
		String symbol,
		UnitOrigin origin
	) {
		
		this.parent = parent;
		this.capacity = capacity;
		this.symbol = symbol;
		this.origin = origin;
		this.children = children;
	}
	
	protected Troop(
		UnitParent parent,
		int capacity,
		String symbol,
		UnitOrigin origin
	) {
		
		this.parent = parent;
		this.capacity = capacity;
		this.symbol = symbol;
		this.origin = origin;
		this.children = this.initChildren();
		this.officer = this.initOfficer();
	}
	
	protected Troop(int capacity, String symbol, UnitOrigin origin) {
		
		this.capacity = capacity;
		this.symbol = symbol;
		this.origin = origin;
		this.children = this.initChildren();
		this.officer = this.initOfficer();
	}

	@NotNull
	private List<Unit> initChildren() {
		
		List<Unit> units = new LinkedList<>();
		
		IntStream.range(0, this.capacity)
		         .forEach(i -> units.add(this.getChildInstance()));
		
		return units;
	}
	
	private Soldier initOfficer() {
		return this.getOfficerInstance();
	}
	
	protected abstract int getChildCapacity();
	protected abstract Soldier getOfficerInstance();
	protected abstract Unit getChildInstance();
	protected abstract Unit getEmptyChildInstance();
	protected abstract Soldier getNewOfficer(List<Unit> unitsPool);
	protected abstract void regroupUnits();
	
	@Override
	public UnitParent getParent() {
		return parent;
	}
	
	@Override
	public void setParent(UnitParent parent) {
		this.parent = parent;
	}
	
	@Override
	public void perish() {
		
		if (this.parent == null)
			return;
		
		this.parent.removeChild(this);
	}
	
	@Override
	public void removeChild(Unit child) {
		
		this.children.remove(child);
		
		if (this.children.isEmpty())
			this.perish();
	}
	
	@Override
	public void removeAll(List<Unit> children) {
		
		this.children.removeAll(children);
		
		if (this.children.isEmpty())
			this.perish();
	}
	
	@Override
	public void addChild(Unit child) {
		this.children.add(child);
	}
	
	@Override
	public List<Unit> getChildren() {
		return this.children;
	}
	
	@Override
	public UnitOrigin getOrigin() {
		return this.origin;
	}
	
	public Soldier getOfficer() {
		return this.officer;
	}
	
	public void setOfficer(Soldier officer) {
		this.officer = officer;
	}
	
	public void removeOfficer() {
		this.officer = null;
	}
	
	public boolean isFull() {
		return this.children.size() == this.getChildCapacity();
	}
	
	public int getAverageMoraleState() {
		return getUnitTotalMoraleStateSum(this) / getSoldiersCount(this);
	}
	
	public BattleReport engage(Unit target, boolean verbose) {
		
		if (target == null)
			return null;
	
		List<Soldier> soldiers = getSoldiers(this);
		soldiers.addAll(getSoldiers(target));
		
		BattleReport report = new Battle(
			soldiers.toArray(new Soldier[0])
		).start(verbose);
		
		return report;
	}
	
	protected static void transferUnit(
		@NotNull Unit unit,
		@NotNull UnitParent destination
	) {
		destination.addChild(unit);
		unit.setParent(destination);
	}
	
	protected static void transferUnitsRange(
		@NotNull List<Unit> units,
		@NotNull UnitParent destination,
		int limit
	) {
		
		for (int i = 0; i < limit && !units.isEmpty(); i++) {
			
			Unit unit = units.get(0);
			transferUnit(unit, destination);
			units.remove(0);
		}
	}
	
	public static void updateUnitStatus(
		Unit unit,
		StatusType statusType,
		int amount
	) {
		
		if (unit == null)
			return;
		
		if (unit instanceof Soldier) {
			((Soldier) unit).updateStatus(statusType, amount);
			return;
		}
		
		((Troop) unit).children.forEach(
			u -> updateUnitStatus(u, statusType, amount)
		);
	}
	
	public static List<Soldier> getSoldiers(Unit unit) {
		
		if (unit == null)
			return null;
		
		if (unit instanceof Soldier)
			return Collections.singletonList((Soldier) unit);
		
		List<Soldier> units = new LinkedList<>();
		Troop troop = (Troop) unit;
		
		troop.children.forEach(u -> units.addAll(getSoldiers(u)));
		units.add(troop.officer);
		
		return units;
	}
	
	public static int getUnitTotalMoraleStateSum(Unit unit) {
		
		if (unit == null)
			return 0;
		
		if (unit instanceof Soldier)
			return ((Soldier) unit)
				.getMorale()
				.getCurrentState();
		
		Troop troop = (Troop) unit;
		
		int moraleState = troop.children
			.stream()
			.mapToInt(Troop::getUnitTotalMoraleStateSum)
			.sum();
		
		moraleState += troop.officer == null ? 0 :
			troop.officer.getMorale()
			             .getCurrentState();
		
		return moraleState;
	}
	
	public static int getSoldiersCount(Unit unit) {
		
		if (unit == null) return 0;
		if (unit instanceof Soldier) return 1;
		
		Troop troop = (Troop) unit;
		int total = troop.children
			.stream()
			.mapToInt(Troop::getSoldiersCount)
			.sum();
		
		return total + (troop.officer == null ? 0 : 1);
	}
	
	public static String getFullSummary(Unit unit) {
		
		if (unit == null)
			return null;
		
		if (unit instanceof Soldier)
			return unit.getFullSummary();
		
		Troop troop = (Troop) unit;
		
		return troop.children.stream()
		                     .map(Troop::getFullSummary)
		                     .collect(Collectors.joining());
	}
	
	@Override
	public String getSummary() {
		
		return this.getClass().getSimpleName() +
			":\n" +
			"-> Officer: " +
			this.officer +
			"\n" +
			"-> Units count: " +
			this.children.size() +
			"\n";
	}
	
	@Override
	public String getFullSummary() {
		
		String summary = this.getSummary();
		summary += "-> Soldiers count: " + getSoldiersCount(this) + "\n";
		
		summary += this.children
			.stream()
			.map(Unit::getSummary)
			.collect(Collectors.joining());
		
		return summary;
	}
	
	@Override
	public String toString() {
		
		return this.getClass().getSimpleName() +
			"[" +
			this.symbol +
			"] (" +
			this.children.size() +
			")";
	}
	
	protected static void removeRandomChild(Troop troop) {
		
		if (troop == null || troop.children.isEmpty())
			return;
		
		troop.removeChild(troop.children.get(
			Game.getRandomInt(troop.children.size())
		));
	}
}