package caesar.military.rome;

import caesar.military.Unit;
import caesar.military.UnitOrigin;
import caesar.military.officer.Rank;
import caesar.military.officer.RomanOfficer;
import caesar.military.officer.RomanRank;
import caesar.military.soldier.Soldier;
import caesar.military.troop.Troop;

import java.util.LinkedList;

public class RomanArmy extends Troop {
	
	public RomanArmy(int capacity) {
		super(capacity, "[>R<]", UnitOrigin.ROME);
	}
	
	@Override
	protected int getChildCapacity() {
		return Legion.UNIT_CAPACITY;
	}
	
	@Override
	protected Soldier getOfficerInstance() {
		return new RomanOfficer(RomanRank.GENERAL, this);
	}
	
	@Override
	protected Rank getOfficerRank() {
		return RomanRank.GENERAL;
	}
	
	@Override
	protected Unit getChildInstance() {
		return new Legion(this);
	}
	
	@Override
	protected Unit getEmptyChildInstance() {
		return new Legion(this, new LinkedList<>(), new LinkedList<>());
	}
}
