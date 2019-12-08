package caesar.military.troop;

import caesar.game.Game;
import caesar.military.Unit;
import caesar.military.soldier.Soldier;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class Grouper {
	
	private static List<Unit> getNotFullUnits(@NotNull Troop troop) {
		
		return troop.children
			.stream()
			.filter(unit -> !unit.isFull())
			.collect(Collectors.toList());
	}
	
	@NotNull
	private static List<Unit> getNotFullUnitsPool(
		@NotNull List<Unit> notFullUnits
	) {
		
		List<Unit> unitsPool = new LinkedList<>();
		
		notFullUnits.stream()
		            .map(Unit::getChildren)
		            .forEach(unitsPool::addAll);
		
		return unitsPool;
	}
	
	@NotNull
	private static List<Soldier> getNotFullOfficersPool(
		@NotNull List<Unit> notFullUnits
	) {
		
		List<Soldier> officersPool = new LinkedList<>();
		
		notFullUnits.stream()
		            .map(unit -> ((Troop) unit).getOfficer())
		            .filter(Objects::nonNull)
		            .forEach(officersPool::add);
		
		return officersPool;
	}
	
	private static Soldier getNewOfficer(List<Unit> unitsPool) {
		
		if (unitsPool == null || unitsPool.isEmpty())
			return null;
		
		
		int randomIndex = Game.getRandomInt(unitsPool.size());
		
		Soldier officer = (Soldier) unitsPool.get(randomIndex);
		unitsPool.remove(officer);
		
		return officer;
	}
	
	protected static void assignOfficer(
		@NotNull Troop troop,
		@NotNull Troop child,
		@NotNull List<Unit> unitsPool,
		@NotNull List<Soldier> officersPool
	) {
		
		if (officersPool.isEmpty())
			child.setOfficer(getNewOfficer(unitsPool));
		
		else {
			child.setOfficer(officersPool.get(0));
			officersPool.remove(0);
		}
	}
	
	public static void regroup(Troop troop) {
		
		if (troop == null)
			return;
		
		List<Unit> notFullUnits = getNotFullUnits(troop);
		
		if (notFullUnits.isEmpty())
			return;
		
		List<Unit> unitsPool =
			getNotFullUnitsPool(notFullUnits);
		
		List<Soldier> officersPool =
			getNotFullOfficersPool(notFullUnits);
		
		int childCapacity = troop.getChildCapacity();
		int limit;
		
		troop.removeAll(notFullUnits);
		
		while (!unitsPool.isEmpty()) {
			
			Troop child = (Troop) troop.getEmptyChildInstance();
			assignOfficer(troop, child, unitsPool, officersPool);
			
			limit = Math.min(childCapacity, unitsPool.size());
			Troop.transferUnitsRange(unitsPool, child, limit);
			
			troop.addChild(child);
		}
	}
}
