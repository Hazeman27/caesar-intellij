package caesar.military.rome;

import caesar.game.Game;
import caesar.military.Unit;
import caesar.military.UnitOrigin;
import caesar.military.UnitParent;
import caesar.military.officer.RomanOfficer;
import caesar.military.officer.RomanRank;
import caesar.military.soldier.Soldier;
import caesar.military.troop.Troop;

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
	protected void regroupUnits() {
		
		List<Unit> nonFullUnits = this.children
			.stream()
			.filter(unit -> !unit.isFull())
			.collect(Collectors.toList());
		
		if (nonFullUnits.isEmpty())
			return;
		
		List<Unit> unitsPool = new LinkedList<>();
		List<Soldier> officersPool = new LinkedList<>();
		
		int childCapacity = this.getChildCapacity();
		this.removeAll(nonFullUnits);
		
		nonFullUnits.stream()
		            .map(Unit::getChildren)
		            .forEach(unitsPool::addAll);
		
		nonFullUnits.stream()
		            .map(unit -> ((Troop) unit).getOfficer())
		            .filter(Objects::nonNull)
		            .forEach(officersPool::add);
		
		while (!unitsPool.isEmpty()) {
			
			Troop child = (Troop) this.getEmptyChildInstance();
			
			if (officersPool.isEmpty())
				child.setOfficer(this.getNewOfficer(unitsPool));
			
			else {
				child.setOfficer(officersPool.get(0));
				officersPool.remove(0);
			}
			
			int limit = Math.min(childCapacity, unitsPool.size());
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