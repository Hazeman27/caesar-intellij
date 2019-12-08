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
			.filter(unit -> !((Troop) unit).isFull())
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
		            .map(unit -> ((Troop) unit).getOfficers())
		            .filter(Objects::nonNull)
		            .filter(officers -> !officers.isEmpty())
		            .forEach(officersPool::addAll);
		
		return officersPool;
	}
	
	private static Soldier getNewOfficer(Troop troop, List<Unit> unitsPool) {
		
		if (unitsPool == null || unitsPool.isEmpty())
			return null;
		
		if (troop.getChildCapacity() != 0) {
			
			for (Unit unit : unitsPool) {
				
				return getNewOfficer(
					(Troop) unit, unit.getChildren()
				);
			}
		}
		
		int randomIndex = Game.getRandomInt(unitsPool.size());
		
		Soldier officer = (Soldier) unitsPool.get(randomIndex);
		unitsPool.remove(officer);
		
		return officer;
	}
	
	private static void assignOfficer(
		@NotNull Troop child,
		@NotNull List<Unit> unitsPool,
		@NotNull List<Soldier> officersPool
	) {
		
		if (officersPool.isEmpty()) {
			
			Soldier officer = getNewOfficer(child, unitsPool);
			officer.setRank(child.getOfficerRank());
			
			Troop.transferOfficer(officer, child);
		}
		
		else {
			Troop.transferOfficer(officersPool.get(0), child);
			officersPool.remove(0);
		}
	}
	
	public static void regroup(Unit unit) {
		
		Troop troop = (Troop) unit;
		
		if (troop == null || troop.getChildCapacity() == 0)
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
		
		troop.removeAllChildren(notFullUnits);
		
		while (!unitsPool.isEmpty()) {
			
			Troop child = (Troop) troop.getEmptyChildInstance();
			assignOfficer(child, unitsPool, officersPool);
			
			limit = Math.min(childCapacity, unitsPool.size());
			Troop.transferUnitsRange(unitsPool, child, limit);
			
			troop.addChild(child);
		}
		
		if (!officersPool.isEmpty()) {
			
			Troop.transferAllOfficers(
				officersPool,
				(Troop) troop.getChildren().get(0)
			);
		}
	}
}
