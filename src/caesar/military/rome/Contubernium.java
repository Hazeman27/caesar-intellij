package caesar.military.rome;

import caesar.military.Unit;
import caesar.military.UnitOrigin;
import caesar.military.UnitParent;
import caesar.military.officer.Rank;
import caesar.military.officer.RomanOfficer;
import caesar.military.officer.RomanRank;
import caesar.military.soldier.Roman;
import caesar.military.soldier.Soldier;
import caesar.military.troop.Troop;

import java.util.List;
import java.util.stream.Collectors;

public class Contubernium extends Troop {
	
	static final int UNIT_CAPACITY = 7;
	
	Contubernium(UnitParent parent) {
		super(parent, UNIT_CAPACITY, ".", UnitOrigin.ROME);
	}
	
	Contubernium(UnitParent parent, List<Unit> children, List<Soldier> officers) {
		super(parent, children, officers, UNIT_CAPACITY, ".", UnitOrigin.ROME);
	}
	
	@Override
	protected int getChildCapacity() {
		return 0;
	}
	
	@Override
	protected Soldier getOfficerInstance() {
		return new RomanOfficer(RomanRank.DECANUS, this);
	}
	
	@Override
	protected Rank getOfficerRank() {
		return RomanRank.DECANUS;
	}
	
	@Override
	protected Soldier getChildInstance() {
		return new Roman(RomanRank.LEGIONARY, this);
	}
	
	@Override
	protected Soldier getEmptyChildInstance() {
		return new Roman(RomanRank.LEGIONARY, this);
	}
	
	@Override
	public String getFullSummary() {
		
		String summary = this.getSummary();
		
		summary += this.children
			.stream()
			.map(Unit::getFullSummary)
			.collect(Collectors.joining());
		
		return summary;
	}
}