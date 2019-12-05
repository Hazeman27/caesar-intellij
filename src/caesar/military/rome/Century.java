package caesar.military.rome;

import caesar.military.MilitaryUnit;
import caesar.military.officer.Officer;
import caesar.military.officer.RomanOfficer;
import caesar.military.officer.RomanRank;
import caesar.military.troop.Troop;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class Century extends Troop {
	
	Century(Troop parentTroop) {
		super(parentTroop, 10, ":");
		this.officer = new RomanOfficer(RomanRank.CENTURION, this);
	}
	
	Century(Troop parentTroop, int troopsAmount, String symbol) {
		super(parentTroop, troopsAmount, symbol);
		this.officer = new RomanOfficer(RomanRank.CENTURION, this);
	}
	
	public Century() {
		super(10, ":");
		this.officer = new RomanOfficer(RomanRank.CENTURION, this);
	}
	
	@Override
	protected List<MilitaryUnit> initUnits() {
		
		List<MilitaryUnit> units = new ArrayList<>();
		IntStream.range(0, this.unitCapacity)
		         .forEach(i -> units.add(new Contubernium(this)));
		
		return units;
	}
	
	@Override
	protected Troop getChildTroopInstance(
		List<MilitaryUnit> units,
		Officer officer
	) {
		return new Contubernium(this, units, officer);
	}
	
	@Override
	protected Troop initChildTroop(
		@NotNull List<Officer> officersPool,
		List<MilitaryUnit> unitsPool
	) {
		
		Officer officer;
		
		if (officersPool.isEmpty()) {
			officer = (Officer) unitsPool.get(0);
			unitsPool.remove(0);
		} else {
			officer = officersPool.get(0);
			officersPool.remove(0);
		}
		
		return this.getChildTroopInstance(
			Collections.emptyList(),
			officer
		);
	}
	
	protected void populateChildTroop(
		Troop unit,
		List<Officer> officersPool,
		List<MilitaryUnit> unitsPool
	) {
		
		int childTroopUnitCapacity = this.getChildTroopUnitCapacity();
		
		for (int j = 0; j < childTroopUnitCapacity; j++) {
			
			if (unitsPool.isEmpty() && officersPool.isEmpty())
				return;
			
			if (unitsPool.isEmpty()) {
				unit.getUnits().add(officersPool.get(0));
				officersPool.remove(0);
			} else {
				unit.getUnits().add(unitsPool.get(0));
				unitsPool.remove(0);
			}
		}
		
		if (unitsPool.size() + officersPool.size() <= (childTroopUnitCapacity + 1) / 2) {
			
			if (!officersPool.isEmpty()) {
				
				IntStream.range(0, officersPool.size()).forEach(l -> {
					unit.getUnits().add(officersPool.get(0));
					officersPool.remove(0);
				});
			}
			
			if (!unitsPool.isEmpty()) {
				
				IntStream.range(0, unitsPool.size()).forEach(l -> {
					unit.getUnits().add(unitsPool.get(0));
					unitsPool.remove(0);
				});
			}
		}
	}
	
	protected void regroupUnits() {
		
		List<MilitaryUnit> unitsPool = new ArrayList<>();
		List<Officer> officersPool = new ArrayList<>();
		
		IntStream.range(0, this.unitCapacity).forEach(i -> {
			
			Troop troop = (Troop) this.units.get(i);
			unitsPool.addAll(troop.getUnits());
			
			if (troop.getOfficer() != null)
				officersPool.add(troop.getOfficer());
		});
		
		this.units = new ArrayList<>();
		
		for (int i = 0; i < this.unitCapacity; i++) {
			
			if (unitsPool.isEmpty() && officersPool.isEmpty())
				break;
			
			Troop unit = this.initChildTroop(
				officersPool,
				unitsPool
			);
			
			this.populateChildTroop(unit, officersPool, unitsPool);
			this.units.add(unit);
		}
	}
}