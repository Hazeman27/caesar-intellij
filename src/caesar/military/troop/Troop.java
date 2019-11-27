package caesar.military.troop;

import caesar.military.soldier.Officer;
import caesar.military.soldier.Soldier;
import caesar.ui.Message;
import caesar.ui.Printer;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class Troop {
    
    private final String type;
    private final String symbol;
    
    private List<Soldier> soldiers;
    private List<Troop> troops;
    private Soldier officer;
    private Troop parentTroop;
    private boolean alive;
    
    public Troop(@NotNull TroopType troopType, Troop parentTroop) {
        
        this.officer = new Officer(troopType.officerRank, this);
        this.type = troopType.toString();
        
        this.symbol = troopType.symbol;
        this.parentTroop = parentTroop;
        this.alive = true;
    
        this.initTroops(troopType.troops);
    }

    public Troop(@NotNull TroopType troopType) {
     
    	this.officer = new Officer(troopType.officerRank, this);
        this.type = troopType.toString();
    	this.symbol = troopType.symbol;
    	this.alive = false;
    
        this.initTroops(troopType.troops);
    }
    
    public Troop(@NotNull ArmyType armyType, int troopsAmount) {
        
        this.officer = new Officer(armyType.officerRank, this);
        this.type = armyType.toString();
        this.symbol = armyType.symbol;
        this.alive = false;
    
        this.initTroops(armyType.troopsType, troopsAmount);
    }
    
    private void initTroops(@NotNull Map<TroopType, Integer> troopsMap) {
        
        if (troopsMap.containsKey(null)) {
            
            this.initSoldiers(troopsMap.get(null));
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
    
    private void initSoldiers(int soldiersAmount) {
        
        this.soldiers = new ArrayList<>();
        
        for (int i = 0; i < soldiersAmount; i++)
            this.soldiers.add(new Soldier(this));
    }

    private void perish() {

        if (this.parentTroop != null) {
            
            this.parentTroop.troops.remove(this);
            this.alive = false;
        }

        else
            Printer.print(Message.CANT_REMOVE_TROOP);
    }
    
    public List<Soldier> getSoldiers() {
        return this.soldiers;
    }
    
    public List<Troop> getTroops() {
        return this.troops;
    }
    
    public boolean isAlive() {
        return this.alive;
    }
    
    public void removeTroop(Troop troop) {
        
        if (this.troops == null)
            return;
        
        this.troops.remove(troop);
        
        if (this.troops.size() == 0)
            this.perish();
    }
    
    public void removeSoldier(Soldier soldier) {
        
        if (this.soldiers == null)
            return;
        
        this.soldiers.remove(soldier);
        
        if (this.soldiers.size() == 0)
            this.perish();
    }

    public void printSymbol(boolean addSpace, boolean addNewLine) {

        Printer.print(
            this.symbol + (addSpace ? " " : ""),
            addNewLine
        );
    }

    public void printSymbol(boolean addSpace) {
        Printer.print(this.symbol + (addSpace ? " " : ""), false);
    }

    public void printSymbol() {
        Printer.print(this.symbol);
    }

    public void printTroopSymbols() {

        int amount = this.troops.size();

        for (int i = 0; i < amount; i++)
            this.troops.get(i)
                .printSymbol(i != amount - 1, i == amount - 1);
    }
	
	@Override
	public String toString() {
		return Troop.getSummary(this);
	}
	
	public String toString(boolean full) {
		
		return full ? Troop.getFullSummary(this) :
			Troop.getSummary(this);
	}

    public static int countSoldiers(@NotNull Troop troop, boolean countOfficers) {

        int total = 0;
        
        if (troop.troops == null)
            return troop.soldiers.size() + (countOfficers ? 1 : 0);
        
        for (Troop t: troop.troops)
            total += countSoldiers(t, countOfficers);

        return total + (countOfficers ? 1 : 0);
    }

    @NotNull
    private static String getSummary(@NotNull Troop troop) {

        StringBuilder summary = new StringBuilder();
        
        summary.append("\n::: ")
            .append(troop.type)
            .append(" :::\n");

        summary.append("Officer: ")
            .append(troop.officer)
            .append("\n");
        
        summary.append("Soldiers count: ")
            .append(countSoldiers(troop, true))
            .append("\n");

        if (troop.troops != null)
            summary.append("Troops count: ")
                .append(troop.troops.size())
                .append("\n");
            
        summary.append("::::::::::::\n");

        return summary.toString();
    }

    @NotNull
    private static String getFullSummary(Troop troop) {

        StringBuilder fullSummary = new StringBuilder(getSummary(troop));
        int troopsCount = troop.troops.size();

        for (int i = 0; i < troopsCount; i++)
            fullSummary.append(getSummary(troop.troops.get(i)));

        return fullSummary.toString();
    }

    public static void engage(Troop A, Troop B) {
    	
    	while (A.isAlive() && B.isAlive()) {
    		
    		if (A.troops == null && B.troops == null) {
    			
    			for (Soldier soldier: A.soldiers)
			}
    		
    		for (Troop troop: A.troops) {
    		
    		
			}
		}
	}
    
    public static void main(String[] args) {
        
        Troop troop = new Troop(TroopType.LEGION);
        
        troop.troops.get(0).perish();
        
        
        troop.printTroopSymbols();
    }
}