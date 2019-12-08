package caesar.military.troop;

import caesar.game.Game;
import caesar.game.battle.Battle;
import caesar.game.battle.BattleReport;
import caesar.game.status.StatusType;
import caesar.military.Unit;
import caesar.military.UnitParent;
import caesar.military.UnitOrigin;
import caesar.military.officer.Rank;
import caesar.military.rome.Cohort;
import caesar.military.soldier.Soldier;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class Troop implements Unit, UnitParent {
	
	private final int capacity;
	private final String symbol;
	private final UnitOrigin origin;
	
	protected List<Unit> children;
	protected List<Soldier> officers;
	protected UnitParent parent;
	
	protected Troop(
		UnitParent parent,
		List<Unit> children,
		List<Soldier> officers,
		int capacity,
		String symbol,
		UnitOrigin origin
	) {
		
		this.parent = parent;
		this.capacity = capacity;
		this.symbol = symbol;
		this.origin = origin;
		this.officers = officers;
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
		this.officers = this.initOfficer();
		this.children = this.initChildren();
	}
	
	protected Troop(int capacity, String symbol, UnitOrigin origin) {
		
		this.capacity = capacity;
		this.symbol = symbol;
		this.origin = origin;
		this.officers = this.initOfficer();
		this.children = this.initChildren();
	}
	
	@NotNull
	private List<Soldier> initOfficer() {
		
		List<Soldier> officers = new LinkedList<>();
		officers.add(this.getOfficerInstance());
		
		return officers;
	}


	@NotNull
	private List<Unit> initChildren() {
		
		List<Unit> units = new LinkedList<>();
		IntStream.range(0, this.capacity)
		         .forEach(i -> units.add(this.getChildInstance()));
		
		return units;
	}
	
	private void regroupUnits() {
		Grouper.regroup(this);
	}
	
	protected abstract int getChildCapacity();
	
	protected abstract Soldier getOfficerInstance();
	
	protected abstract Rank getOfficerRank();
	
	protected abstract Unit getChildInstance();
	
	protected abstract Unit getEmptyChildInstance();
	
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
	public void addChild(Unit child) {
		this.children.add(child);
	}
	
	@Override
	public void removeChild(Unit child) {
		
		this.children.remove(child);
		
		if (this.isEmpty()) this.perish();
	}
	
	@Override
	public void removeAllChildren(List<Unit> children) {
		
		this.children.removeAll(children);
		
		if (this.isEmpty()) this.perish();
	}
	
	@Override
	public void addOfficer(Soldier officer) {
		this.officers.add(officer);
	}
	
	@Override
	public void removeOfficer(Soldier officer) {
		
		this.officers.remove(officer);
		
		if (this.isEmpty()) this.perish();
	}
	
	@Override
	public boolean isEmpty() {
		return this.officers.isEmpty() && this.children.isEmpty();
	}
	
	@Override
	public boolean isFull() {
		return this.children.size() == this.capacity
			&& !this.officers.isEmpty();
	}
	
	@Override
	public List<Unit> getChildren() {
		return this.children;
	}
	
	@Override
	public UnitOrigin getOrigin() {
		return this.origin;
	}
	
	List<Soldier> getOfficers() {
		return this.officers;
	}
	
	static void transferOfficer(
		@NotNull Soldier officer,
		@NotNull UnitParent destination
	) {
		destination.addOfficer(officer);
		officer.setParent(destination);
	}
	
	static void transferAllOfficers(
		@NotNull List<Soldier> officers,
		@NotNull UnitParent destination
	) {
		officers.forEach(officer ->
			transferOfficer(officer, destination)
		);
	}
	
	static void transferUnit(
		@NotNull Unit unit,
		@NotNull UnitParent destination
	) {
		destination.addChild(unit);
		unit.setParent(destination);
	}
	
	static void transferUnitsRange(
		@NotNull List<Unit> units,
		@NotNull UnitParent destination,
		final int limit
	) {
		
		for (int i = 0; i < limit && !units.isEmpty(); i++) {
			
			Unit unit = units.get(0);
			transferUnit(unit, destination);
			units.remove(0);
		}
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
		units.addAll(troop.officers);
		
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
		
		moraleState += troop.officers
			.stream()
			.mapToInt(Troop::getUnitTotalMoraleStateSum)
			.sum();
		
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
		
		return total + (troop.officers == null ? 0 :
			troop.officers.size()
		);
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
			this.officers +
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
	
	private static void removeRandomChild(Troop troop) {
		
		if (troop == null || troop.children.isEmpty())
			return;
		
		troop.removeChild(troop.children.get(
			Game.getRandomInt(troop.children.size())
		));
	}
	
	public static void main(String ...args) {
		
		Troop troop = new Cohort(null);
		System.out.println(troop.getFullSummary());
		
		troop.children.forEach(child -> {
			
			Troop childTroop = (Troop) child;
			
			Soldier officerToRemove = childTroop.getOfficers().get(
				Game.getRandomInt(childTroop.getOfficers().size())
			);
			
			int limit = Game.getRandomInt(troop.getChildCapacity());
			
			IntStream.range(0, limit)
			         .forEach(i -> Troop.removeRandomChild(childTroop));
			
			if (Game.getRandomInt(100) > 30)
				childTroop.removeOfficer(officerToRemove);
		});
		
		System.out.println(troop.getFullSummary());
		troop.regroupUnits();
		System.out.println(troop.getFullSummary());
	}
}