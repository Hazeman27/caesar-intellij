package caesar.military.rome;

import caesar.game.Game;
import caesar.military.Unit;
import caesar.military.UnitOrigin;
import caesar.military.UnitParent;
import caesar.military.officer.RomanOfficer;
import caesar.military.officer.RomanRank;
import caesar.military.soldier.Soldier;
import caesar.military.troop.Grouper;
import caesar.military.troop.Troop;

import java.util.LinkedList;
import java.util.List;
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
	protected void regroupUnits() {
		Grouper.regroup(this);
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