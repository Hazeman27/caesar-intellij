package caesar.military.rome;

import caesar.game.Game;
import caesar.military.Unit;
import caesar.military.UnitOrigin;
import caesar.military.UnitParent;
import caesar.military.officer.RomanOfficer;
import caesar.military.officer.RomanRank;
import caesar.military.soldier.Soldier;
import caesar.military.troop.Troop;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Century extends Troop {
	
	static final int UNIT_CAPACITY = 10;
	
	Century(UnitParent parentUnit) {
		super(parentUnit, UNIT_CAPACITY, ":", UnitOrigin.ROME);
	}
	
	Century(UnitParent parentUnit, List<Unit> units) {
		super(parentUnit, units, UNIT_CAPACITY, ":", UnitOrigin.ROME);
	}
	
	@Override
	protected int getChildCapacity() {
		return Contubernium.UNIT_CAPACITY;
	}
	
	@Override
	protected Soldier getOfficerInstance() {
		return new RomanOfficer(RomanRank.CENTURION, this);
	}
	
	@Override
	protected Unit getChildInstance() {
		return new Contubernium(this);
	}
	
	@Override
	protected Unit getEmptyChildInstance() {
		return new Contubernium(this, new LinkedList<>());
	}
	
	@Override
	protected Soldier getNewOfficer(List<Unit> unitsPool) {
		
		if (unitsPool == null || unitsPool.isEmpty())
			return null;
		
		int randomIndex = Game.getRandomInt(unitsPool.size());
		
		Soldier officer = (Soldier) unitsPool.get(randomIndex);
		unitsPool.remove(officer);
		
		return officer;
	}
	
	@Override
	protected List<Unit> getNotFullUnits() {
		
		return this.children
			.stream()
			.filter(unit -> !unit.isFull())
			.collect(Collectors.toList());
	}
	
	@Override
	protected List<Unit> getNotFullUnitsPool(
		@NotNull List<Unit> notFullUnits
	) {
		
		List<Unit> unitsPool = new LinkedList<>();
		
		notFullUnits.stream()
		            .map(Unit::getChildren)
		            .forEach(unitsPool::addAll);
		
		return unitsPool;
	}
	
	@Override
	protected List<Soldier> getNotFullOfficersPool(
		@NotNull List<Unit> notFullUnits
	) {
		
		List<Soldier> officersPool = new LinkedList<>();
		
		notFullUnits.stream()
		            .map(unit -> ((Troop) unit).getOfficer())
		            .filter(Objects::nonNull)
		            .forEach(officersPool::add);
		
		return officersPool;
	}
	
	@Override
	protected void assignOfficer(
		@NotNull Troop child,
		@NotNull List<Unit> unitsPool,
		@NotNull List<Soldier> officersPool
	) {
		
		if (officersPool.isEmpty())
			child.setOfficer(this.getNewOfficer(unitsPool));
		
		else {
			child.setOfficer(officersPool.get(0));
			officersPool.remove(0);
		}
	}
	
	@Override
	protected void regroupUnits() {
		
		List<Unit> notFullUnits = this.getNotFullUnits();
		
		if (notFullUnits.isEmpty())
			return;
		
		List<Unit> unitsPool =
			this.getNotFullUnitsPool(notFullUnits);
		
		List<Soldier> officersPool =
			this.getNotFullOfficersPool(notFullUnits);
		
		int childCapacity = this.getChildCapacity();
		int limit;
		
		this.removeAll(notFullUnits);
		
		while (!unitsPool.isEmpty()) {
			
			Troop child = (Troop) this.getEmptyChildInstance();
			this.assignOfficer(child, unitsPool, officersPool);
			
			limit = Math.min(childCapacity, unitsPool.size());
			transferUnitsRange(unitsPool, child, limit);
			
			this.addChild(child);
		}
	}
	
	public static void main(String ...args) {
		
		Century century = new Century(null);
		System.out.println(century.getFullSummary());
		
		century.children.forEach(child -> {
			
			int range = Game.getRandomInt(century.getChildCapacity());
			
			IntStream.range(0, range)
			         .forEach(i -> Troop.removeRandomChild((Troop) child));
			
			if (Game.getRandomInt(100) > 30)
				((Troop) child).removeOfficer();
		});
		
		System.out.println(century.getFullSummary());
		century.regroupUnits();
		System.out.println(century.getFullSummary());
	}
}