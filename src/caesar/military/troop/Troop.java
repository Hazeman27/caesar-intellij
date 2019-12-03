package caesar.military.troop;

import caesar.game.engagement.EngagementController;
import caesar.military.MilitaryUnit;
import caesar.military.soldier.Gaul;
import caesar.military.soldier.Officer;
import caesar.military.soldier.Roman;
import caesar.military.soldier.Soldier;
import caesar.ui.Message;
import caesar.ui.Printer;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class Troop implements MilitaryUnit {
	
	private final String type;
	private final String symbol;
	private final String origin;
	
	private List<MilitaryUnit> units;
	private Soldier officer;
	private Troop parentTroop;
	
	public Troop(@NotNull TroopType troopType, Troop parentTroop) {
		
		this.type = troopType.toString();
		this.origin = troopType.origin.toString();
		this.symbol = troopType.symbol;
		this.parentTroop = parentTroop;
		
		this.officer = new Officer(troopType.officerRank, this);
		this.units = this.initUnits(troopType.troops, troopType.origin);
	}
	
	public Troop(@NotNull TroopType troopType) {
		
		this.type = troopType.toString();
		this.origin = troopType.origin.toString();
		this.symbol = troopType.symbol;
		
		this.officer = new Officer(troopType.officerRank, this);
		this.units = this.initUnits(troopType.troops, troopType.origin);
	}
	
	public Troop(@NotNull ArmyType armyType, int troopsAmount) {
		
		this.type = armyType.toString();
		this.origin = armyType.toString();
		this.symbol = armyType.symbol;
		
		this.officer = new Officer(armyType.officerRank, this);
		this.units = this.initUnits(armyType.troopsType, troopsAmount);
	}
	
	@NotNull
	private List<MilitaryUnit> initUnits(
		@NotNull Map<TroopType, Integer> troopsMap,
		TroopOrigin troopOrigin
	) {
		
		if (troopsMap.containsKey(null)) {
			
			return this.initSoldiers(
				troopsMap.get(null),
				troopOrigin
			);
		}
		
		List<MilitaryUnit> units = new ArrayList<>();
		
		for (Map.Entry<TroopType, Integer> entry: troopsMap.entrySet()) {
			
			units.addAll(this.initUnits(
				entry.getKey(),
				entry.getValue()
			));
		}
		
		return units;
	}
	
	@NotNull
	private List<MilitaryUnit> initUnits(
		TroopType troopsType,
		int troopsAmount
	) {
		
		List<MilitaryUnit> units = new ArrayList<>();
		
		for (int i = 0; i < troopsAmount; i++)
			units.add(new Troop(troopsType, this));
		
		return units;
	}
	
	@NotNull
	private List<MilitaryUnit> initSoldiers(
		int soldiersAmount,
		TroopOrigin troopOrigin
	) {
		
		List<MilitaryUnit> units = new ArrayList<>();
		
		for (int i = 0; i < soldiersAmount; i++) {
			
			if (troopOrigin == TroopOrigin.ROMAN)
				units.add(new Roman(this));
			
			else if (troopOrigin == TroopOrigin.GALLIC)
				units.add(new Gaul(this));
		}
		
		return units;
	}
	
	@Override
	public void perish() {
		
		if (this.parentTroop != null) {
			
			this.parentTroop.units.remove(this);
			this.parentTroop = null;
		}
		
		else Printer.print(Message.CANT_REMOVE_TROOP);
		
	}
	
	@Override
	public void flee() {
		
		if (this.parentTroop != null) {
			
			this.parentTroop.units.remove(this);
			this.parentTroop = null;
		}
		
		else Printer.print(Message.CANT_REMOVE_TROOP);
	}
	
	@Override
	public MilitaryUnit engage(MilitaryUnit target, boolean verbose) {
		
		if (target == null)
			return this;
		
		Troop targetTroop = (Troop) target;
		
		List<MilitaryUnit> thisUnits;
		List<MilitaryUnit> targetUnits;
		
		thisUnits = this.units;
		targetUnits = targetTroop.units;
		
		new EngagementController(
			Collections.singletonList(this.officer),
			Collections.singletonList(targetTroop.officer)
		).start(verbose);
		
		new EngagementController(thisUnits, targetUnits)
			.start(verbose);
		
		return this;
	}
	
	public String getOrigin() {
		return origin;
	}
	
	public void removeSoldier(@NotNull Soldier soldier) {
		
		this.units.remove(soldier);
		
		if (this.units.size() == 0 && this.officer == null)
			this.perish();
	}
	
	public void removeOfficer() {
		
		this.officer.setTroop(null);
		
		if (this.units.size() == 0)
			this.perish();
	}
	
	public void setOfficer(Soldier soldier) {
		this.officer = soldier;
	}
	
	public void printSymbol(boolean addSpace, boolean addNewLine) {
		
		Printer.print(
			this.symbol + (addSpace ? " " : ""),
			addNewLine
		);
	}
	
	public void printTroopSymbols() {
		
		int amount = this.units.size();
		
		for (int i = 0; i < amount; i++) {
			
			Troop troop = (Troop) this.units.get(i);
			troop.printSymbol(i != amount - 1, i == amount - 1);
		}
	}
	
	@Override
	public String toString() {
		return this.type +
			"[" +
			this.symbol +
			"] (" +
			(this.units.size()) +
			")";
	}
	
	@Contract(pure = true)
	public static int countSoldiers(MilitaryUnit unit) {
		
		if (unit instanceof Soldier)
			return 1;
		
		int total = 0;
		Troop troop = (Troop) unit;
		
		for (MilitaryUnit u:  troop.units)
			total += countSoldiers(u);
		
		return total + (troop.officer == null ? 0 : 1);
	}
	
	@NotNull
	public static String getSummary(@NotNull Troop troop) {
		
		return "\n[" +
			troop.origin +
			"] " +
			troop.type +
			":\n" +
			"-> Officer: " +
			troop.officer +
			"\n" +
			"-> Soldiers count: " +
			countSoldiers(troop) +
			"\n" +
			"-> Troops count: " +
			troop.units.size() +
			"\n";
	}
	
	@NotNull
	public static String getFullSummary(Troop troop) {
		
		StringBuilder fullSummary = new StringBuilder(getSummary(troop));
		int unitsCount = troop.units.size();
		
		for (int i = 0; i < unitsCount; i++) {
			
			Troop current = (Troop) troop.units.get(i);
			fullSummary.append(getSummary(current));
		}
		
		return fullSummary.toString();
	}
	
	public static void main(String[] args) {
		
		Troop troop = new Troop(TroopType.LEGION);
		Printer.print(Troop.getFullSummary(troop));
	}
}