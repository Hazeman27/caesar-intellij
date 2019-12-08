package caesar.military.gaul;

import caesar.military.Unit;
import caesar.military.UnitOrigin;
import caesar.military.officer.GaulOfficer;
import caesar.military.officer.GaulRank;
import caesar.military.officer.Rank;
import caesar.military.soldier.Soldier;
import caesar.military.troop.Troop;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

public class GaulArmy extends Troop {
	
	public GaulArmy(int capacity) {
		super(capacity, "[XGX]", UnitOrigin.GAUL);
	}
	
	@Override
	protected int getChildCapacity() {
		return Nation.UNIT_CAPACITY;
	}
	
	@NotNull
	@Override
	protected Soldier getOfficerInstance() {
		return new GaulOfficer(GaulRank.HERO_WARLORD, this);
	}
	
	@NotNull
	@Override
	protected Rank getOfficerRank() {
		return GaulRank.HERO_WARLORD;
	}
	
	@NotNull
	@Override
	protected Unit getChildInstance() {
		return new Nation(this);
	}
	
	@NotNull
	@Override
	protected Unit getEmptyChildInstance() {
		return new Nation(this, new LinkedList<>(), new LinkedList<>());
	}
}
