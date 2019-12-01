package caesar.military.troop;

import caesar.game.engagement.EngagementController;
import caesar.military.MilitaryUnit;
import caesar.military.soldier.Gaul;
import caesar.military.soldier.Officer;
import caesar.military.soldier.Roman;
import caesar.military.soldier.Soldier;
import caesar.ui.Message;
import caesar.ui.Printer;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Troop implements MilitaryUnit {
	
	private final String type;
	private final String symbol;
	private final String origin;
	
	private List<MilitaryUnit> soldiers;
	private List<MilitaryUnit> troops;
	private Soldier officer;
	private Troop parentTroop;
	private boolean alive;
	
	public Troop(@NotNull TroopType troopType, Troop parentTroop) {
		
		this.type = troopType.toString();
		this.origin = troopType.origin.toString();
		this.symbol = troopType.symbol;
		this.parentTroop = parentTroop;
		this.alive = true;
		
		this.officer = new Officer(troopType.officerRank, this);
		this.initTroops(troopType.troops, troopType.origin);
	}
	
	public Troop(@NotNull TroopType troopType) {
		
		this.type = troopType.toString();
		this.origin = troopType.origin.toString();
		this.symbol = troopType.symbol;
		this.alive = false;
		
		this.officer = new Officer(troopType.officerRank, this);
		this.initTroops(troopType.troops, troopType.origin);
	}
	
	public Troop(@NotNull ArmyType armyType, int troopsAmount) {
		
		this.type = armyType.toString();
		this.origin = armyType.toString();
		this.symbol = armyType.symbol;
		this.alive = false;
		
		this.officer = new Officer(armyType.officerRank, this);
		this.initTroops(armyType.troopsType, troopsAmount);
	}
	
	private void initTroops(
		@NotNull Map<TroopType, Integer> troopsMap,
		TroopOrigin troopOrigin
	) {
		
		if (troopsMap.containsKey(null)) {
			this.initSoldiers(troopsMap.get(null), troopOrigin);
			return;
		}
		
		this.troops = new ArrayList<>();
		
		for (Map.Entry<TroopType, Integer> entry: troopsMap.entrySet()) {
			
			TroopType troopsType = entry.getKey();
			int troopsAmount = entry.getValue();
			
			for (int i = 0; i < troopsAmount; i++)
				this.troops.add(new Troop(troopsType, this));
		}
	}
	
	private void initTroops(TroopType troopsType, int troopsAmount) {
		
		this.troops = new ArrayList<>();
		
		for (int i = 0; i < troopsAmount; i++)
			this.troops.add(new Troop(troopsType, this));
	}
	
	private void initSoldiers(int soldiersAmount, TroopOrigin troopOrigin) {
		
		this.soldiers = new ArrayList<>();
		
		for (int i = 0; i < soldiersAmount; i++) {
			
			if (troopOrigin == TroopOrigin.ROMAN)
				this.soldiers.add(new Roman(this));
			
			else if (troopOrigin == TroopOrigin.GALLIC)
				this.soldiers.add(new Gaul(this));
		}
	}
	
	@Override
	public boolean isAlive() {
		return this.alive;
	}
	
	@Override
	public void perish() {
		
		if (this.parentTroop != null) {
			this.parentTroop.troops.remove(this);
			this.alive = false;
		} else {
			Printer.print(Message.CANT_REMOVE_TROOP);
		}
	}
	
	@Override
	public void flee() {
		
		if (this.parentTroop != null)
			this.parentTroop.troops.remove(this);
		
		else Printer.print(Message.CANT_REMOVE_TROOP);
	}
	
	@Override
	public void engage(MilitaryUnit target, boolean verbose) {
		
		if (target == null)
			return;
		
		Troop targetTroop = (Troop) target;
		
		List<MilitaryUnit> thisUnits;
		List<MilitaryUnit> targetUnits;
		
		if (targetTroop.troops == null) {
			
			thisUnits = this.soldiers;
			thisUnits.add(this.officer);
			
			targetUnits = targetTroop.soldiers;
			targetUnits.add(targetTroop.officer);
		} else {
			
			thisUnits = this.troops;
			targetUnits = targetTroop.troops;
			
			try {
				new EngagementController(
					Collections.singletonList(this.officer),
					Collections.singletonList(targetTroop.officer)
				).start(verbose);
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		try {
			new EngagementController(thisUnits, targetUnits)
				.start(verbose);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	public String getOrigin() {
		return origin;
	}
	
	public List<MilitaryUnit> getTroops() {
		return troops;
	}
	
	public void removeSoldier(Soldier soldier) {
		
		if (this.soldiers == null)
			return;
		
		this.soldiers.remove(soldier);
		
		if (this.soldiers.size() == 0 && this.officer == null)
			this.perish();
	}
	
	public void removeOfficer() {
		
		this.officer = null;
		
		if ((this.soldiers != null && this.soldiers.size() == 0) ||
			(this.troops != null && this.troops.size() == 0)) {
			this.perish();
		}
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
		
		int amount = this.troops.size();
		
		for (int i = 0; i < amount; i++) {
			
			Troop troop = (Troop) this.troops.get(i);
			troop.printSymbol(i != amount - 1, i == amount - 1);
		}
	}
	
	@Override
	public String toString() {
		return this.type +
			"[" +
			this.symbol +
			"] (" +
			(this.troops == null ? this.soldiers.size() : this.troops.size()) +
			")";
	}
	
	public static int countSoldiers(@NotNull Troop troop) {
		
		int total = 0;
		
		if (troop.troops == null)
			return troop.soldiers.size() + (troop.officer == null ? 0 : 1);
		
		for (MilitaryUnit militaryUnit: troop.troops)
			total += countSoldiers((Troop) militaryUnit);
		
		return total + (troop.officer == null ? 0 : 1);
	}
	
	@NotNull
	public static String getSummary(@NotNull Troop troop) {
		
		StringBuilder summary = new StringBuilder();
		
		summary.append("\n[")
		       .append(troop.origin)
		       .append("] ")
		       .append(troop.type)
		       .append(":\n");
		
		summary.append("-> Officer: ")
		       .append(troop.officer)
		       .append("\n");
		
		summary.append("-> Soldiers count: ")
		       .append(countSoldiers(troop))
		       .append("\n");
		
		if (troop.troops != null) {
			summary.append("-> Troops count: ")
			       .append(troop.troops.size())
			       .append("\n");
		}
		
		return summary.toString();
	}
	
	@NotNull
	public static String getFullSummary(Troop troop) {
		
		StringBuilder fullSummary = new StringBuilder(getSummary(troop));
		int troopsCount = troop.troops.size();
		
		for (int i = 0; i < troopsCount; i++) {
			Troop current = (Troop) troop.troops.get(i);
			fullSummary.append(getSummary(current));
		}
		
		return fullSummary.toString();
	}
	
	public static void main(String[] args) {
		
		Troop troop = new Troop(TroopType.LEGION);
		
		troop.troops.get(0).perish();
		troop.printTroopSymbols();
	}
}