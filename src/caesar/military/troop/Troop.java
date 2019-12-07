package caesar.military.troop;

import caesar.game.battle.Battle;
import caesar.game.battle.BattleReport;
import caesar.game.status.StatusType;
import caesar.military.Unit;
import caesar.military.UnitOrigin;
import caesar.military.officer.Officer;
import caesar.military.soldier.Soldier;
import caesar.ui.Message;
import caesar.ui.Printer;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class Troop implements Unit {
	
	private final int unitCapacity;
	private final String symbol;
	private final List<Unit> units;
	private final UnitOrigin origin;
	
	private Troop parentUnit;
	private Officer officer;
	
	protected Troop(
		Troop parentUnit,
		int unitCapacity,
		String symbol,
		UnitOrigin origin
	) {
		
		this.unitCapacity = unitCapacity;
		this.parentUnit = parentUnit;
		
		this.symbol = symbol;
		this.origin = origin;
		
		this.units = this.initUnits();
		this.officer = this.getOfficerInstance();
	}
	
	@Contract(pure = true)
	protected Troop(
		Troop parentUnit,
		int unitCapacity,
		List<Unit> units,
		String symbol,
		UnitOrigin origin
	) {
		
		this.unitCapacity = unitCapacity;
		this.units = units;
		this.parentUnit = parentUnit;
		this.symbol = symbol;
		this.origin = origin;
	}
	
	protected Troop(
		int unitCapacity,
		String symbol,
		UnitOrigin origin
	) {
		
		this.unitCapacity = unitCapacity;
		this.symbol = symbol;
		this.origin = origin;
		this.units = this.initUnits();
		this.officer = this.getOfficerInstance();
	}

	protected abstract int getChildUnitCapacity();
	protected abstract Officer getOfficerInstance();
	protected abstract Unit getChildUnitInstance();
	
	public Officer getOfficer() {
		return this.officer;
	}
	
	public void setOfficer(Officer officer) {
		
		this.officer = officer;
		this.officer.setParentUnit(this);
	}
	
	public List<Unit> getUnits() {
		return this.units;
	}
	
	@NotNull
	protected List<Unit> initUnits() {
		
		List<Unit> units = new LinkedList<>();
		
		IntStream.range(0, this.unitCapacity)
		         .forEach(i -> units.add(
		         	this.getChildUnitInstance()
		         ));
		
		return units;
	}
	
	@Override
	public void die() {
		
		if (this.parentUnit != null) {
			this.parentUnit.units.remove(this);
			this.parentUnit = null;
		}
		
		else Printer.print(Message.CANT_REMOVE_TROOP);
	}
	
	@Override
	public void setParentUnit(Unit parentUnit) {
		this.parentUnit = (Troop) parentUnit;
	}
	
	@Override
	public UnitOrigin getOrigin() {
		return this.origin;
	}
	
	public void removeSoldier(@NotNull Soldier soldier) {
		
		this.units.remove(soldier);
		
		if (this.units.size() == 0 && this.officer == null)
			this.die();
	}
	
	public void removeOfficer() {
		
		this.officer.setParentUnit(null);
		
		if (this.units.size() == 0)
			this.die();
	}
	
	public int getAverageMoraleState() {
		return getUnitTotalMoraleStateSum(this) / countSoldiers(this);
	}
	
	private void clearUnits() {
		this.units.clear();
	}
	
	private Officer getUnitOfficer(Unit unit) {
		
		if (unit instanceof Troop)
			return ((Troop) unit).getOfficer();
		
		return null;
	}
	
	@Nullable
	private Officer findReplacementOfficer(@NotNull List<Unit> units) {
		
		if (units.isEmpty())
			return null;
		
		if (units.get(0) instanceof Soldier) {
			
			Officer officer = (Officer) units.get(0);
			units.remove(0);
			
			return officer;
		}
		
		Optional<Unit> optional =
			units.parallelStream()
			     .filter(u -> ((Troop) u).getOfficer() != null)
			     .findFirst();
		
		if (optional.isPresent()) {
			
			Troop unit = (Troop) optional.get();
			Officer officer = this.getUnitOfficer(unit);
			
			unit.removeOfficer();
			this.findReplacementOfficer(unit.getUnits());
			
			return officer;
		}
		
		for (Unit unit: units)
			return this.findReplacementOfficer(
				((Troop) unit).getUnits()
			);
		
		return null;
	}
	
	@NotNull
	private Unit initChildUnit(
		@NotNull List<Officer> officersPool,
		@NotNull List<Unit> unitsPool
	) {
		
		Officer officer;
		
		if (officersPool.isEmpty()) {
			officer = this.findReplacementOfficer(unitsPool);
		} else {
			officer = officersPool.get(0);
			officersPool.remove(0);
		}
		
		Troop unit = (Troop) this.getChildUnitInstance();
		unit.setOfficer(officer);
		
		unit.clearUnits();
		return unit;
	}
	
	private void populateChildTroop(Unit unit, List<Unit> unitsPool) {
		
		Unit unitToAdd;
		
		for (int j = 0; j < this.getChildUnitCapacity(); j++) {
			
			if (unitsPool.isEmpty())
				return;
			
			unitToAdd = unitsPool.get(0);
			unitToAdd.setParentUnit(unit);
	
			((Troop) unit).getUnits().add(unitToAdd);
			unitsPool.remove(0);
		}
	}
	
	private void populateChildTroop(
		Unit unit,
		List<Unit> unitsPool,
		List<Officer> officersPool
	) {
		
		Unit unitToAdd;
		
		for (int j = 0; j < this.getChildUnitCapacity(); j++) {
			
			if (unitsPool.isEmpty() && officersPool.isEmpty()) {
				return;
			} else if (unitsPool.isEmpty()) {
				
				unitToAdd = officersPool.get(0);
				unitToAdd.setParentUnit(unit);
				officersPool.remove(0);
			} else {
				
				unitToAdd = unitsPool.get(0);
				unitToAdd.setParentUnit(unit);
				unitsPool.remove(0);
			}
			
			((Troop) unit).getUnits().add(unitToAdd);
		}
	}
	
	private void regroupUnits(Unit unit) {
		
		if (unit instanceof Soldier)
			return;
		
		Troop troop = (Troop) unit;
		int unitsSize = troop.units.size();
		
		List<Unit> unitsPool = new LinkedList<>();
		List<Officer> officersPool = new LinkedList<>();
		
		for (int i = 0; i < unitsSize; i++) {
			
			if (troop.units.get(i) instanceof Soldier)
				return;
			
			Troop current = (Troop) troop.units.get(i);
			unitsPool.addAll(current.getUnits());
			
			if (current.getOfficer() != null)
				officersPool.add(current.getOfficer());
		}
		
		troop.clearUnits();
		
		while (!unitsPool.isEmpty() && !officersPool.isEmpty()) {
			
			Unit childUnit = troop.initChildUnit(
				officersPool,
				unitsPool
			);
			
			if (unitsPool.isEmpty() && officersPool.isEmpty()) {
				troop.units.add(childUnit);
				return;
			}
			
			if (((Troop) childUnit).getChildUnitCapacity() == 0) {
				
				troop.populateChildTroop(
					childUnit,
					unitsPool,
					officersPool
				);
			} else {
				troop.populateChildTroop(childUnit, unitsPool);
			}
			
			troop.units.add(childUnit);
		}
		
		troop.units.forEach(this::regroupUnits);
	}
	
	public BattleReport engage(Unit target, boolean verbose) {
		
		if (target == null)
			return null;
		
		Troop targetTroop = (Troop) target;
	
		List<Soldier> soldiers = getSoldiers(this);
		soldiers.addAll(getSoldiers(target));
		
		BattleReport report = new Battle(
			soldiers.toArray(new Soldier[0])
		).start(verbose);
		
		this.regroupUnits(this);
		targetTroop.regroupUnits(target);
		
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
		
		((Troop) unit).units.forEach(
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
		
		troop.units.forEach(u -> units.addAll(getSoldiers(u)));
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
		
		int moraleState = troop.units
			.stream()
			.mapToInt(Troop::getUnitTotalMoraleStateSum)
			.sum();
		
		moraleState += troop.officer == null ? 0 :
			troop.officer.getMorale()
			             .getCurrentState();
		
		return moraleState;
	}
	
	public static int countSoldiers(Unit unit) {
		
		if (unit == null) return 0;
		if (unit instanceof Soldier) return 1;
		
		Troop troop = (Troop) unit;
		int total = troop.units
			.stream()
			.mapToInt(Troop::countSoldiers)
			.sum();
		
		return total + (troop.officer == null ? 0 : 1);
	}
	
	public static String getFullSummary(Unit unit) {
		
		if (unit == null)
			return null;
		
		if (unit instanceof Soldier)
			return unit.getFullSummary();
		
		Troop troop = (Troop) unit;
		
		return troop.units.stream()
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
			this.units.size() +
			"\n";
	}
	
	@Override
	public String getFullSummary() {
		
		StringBuilder fullSummary = new StringBuilder(
			this.getSummary()
		);
		
		IntStream.range(0, this.units.size())
		         .forEach(i -> {
			         Unit current = this.units.get(i);
			         fullSummary.append(current.getSummary());
		         });
		
		return fullSummary.toString();
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() +
			"[" +
			this.symbol +
			"] (" +
			(this.units.size()) +
			")";
	}
}