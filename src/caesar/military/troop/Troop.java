package caesar.military.troop;

import caesar.game.battle.BattleController;
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
	
	private List<Unit> getTroops() {
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
			this.findReplacementOfficer(unit.getTroops());
			
			return officer;
		}
		
		for (Unit unit: units) {
			return this.findReplacementOfficer(
				((Troop) unit).getTroops()
			);
		}
		
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
	
			((Troop) unit).getTroops().add(unitToAdd);
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
			
			((Troop) unit).getTroops().add(unitToAdd);
		}
	}
	
	private void regroupUnits() {
		
		List<Unit> unitsPool = new LinkedList<>();
		List<Officer> officersPool = new LinkedList<>();
		
		IntStream.range(0, this.unitCapacity).forEach(i -> {
			
			Troop troop = (Troop) this.units.get(i);
			unitsPool.addAll(troop.getTroops());
			
			if (troop.getOfficer() != null)
				officersPool.add(troop.getOfficer());
		});
		
		this.clearUnits();
		
		for (int i = 0; i < this.unitCapacity; i++) {
			
			if (unitsPool.isEmpty() && officersPool.isEmpty())
				return;
			
			Unit unit = this.initChildUnit(
				officersPool,
				unitsPool
			);
			
			if (unitsPool.isEmpty() && officersPool.isEmpty()) {
				this.units.add(unit);
				return;
			}
			
			if (((Troop) unit).getChildUnitCapacity() == 0) {
				
				this.populateChildTroop(
					unit,
					unitsPool,
					officersPool
				);
			} else {
				this.populateChildTroop(unit, unitsPool);
			}
			
			this.units.add(unit);
		}
	}
	
	public BattleReport engage(Unit target, boolean verbose) {
		
		if (target == null)
			return null;
		
		Troop targetTroop = (Troop) target;
		
		new BattleController(this.officer, targetTroop.officer)
			.start(verbose);
		
		List<Soldier> soldiers = getSoldiers(this);
		
		soldiers.addAll(getSoldiers(target));
		soldiers.add(this.officer);
		soldiers.add(targetTroop.officer);
		
		return new BattleController(
			soldiers.toArray(new Soldier[0])
		).start(verbose);
	}
	
	public static void updateUnitStatus(
		Unit unit,
		StatusType statusType,
		int amount
	) {
		
		if (unit == null)
			return;
		
		if (unit instanceof Soldier) {
			((Soldier) unit).updateStatusState(statusType, amount);
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
	
	@Contract(pure = true)
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
			         Troop current = (Troop) this.units.get(i);
			         fullSummary.append(current.getSummary());
		         });
		
		return fullSummary.toString();
	}
	
	@Override
	public String toString() {
		return super.toString() +
			"[" +
			this.symbol +
			"] (" +
			(this.units.size()) +
			")";
	}
}