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
    
    public Troop(@NotNull TroopType troopType, Troop parentTroop) {
        
        this.officer = new Officer(troopType.officerRank, this);
        this.type = troopType.toString();
        
        this.symbol = troopType.symbol;
        this.parentTroop = parentTroop;
    
        this.initTroops(troopType.troops);
    }

    public Troop(@NotNull TroopType troopType) {
     
    	this.officer = new Officer(troopType.officerRank, this);
        this.type = troopType.toString();
    	this.symbol = troopType.symbol;
    
        this.initTroops(troopType.troops);
    }
    
    public Troop(@NotNull ArmyType armyType, int troopsAmount) {
        
        this.officer = new Officer(armyType.officerRank, this);
        this.type = armyType.toString();
        this.symbol = armyType.symbol;
    
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

    public void removeTroop(Troop troop) {
        this.troops.remove(troop);
    }

    public void removeSoldier(Soldier soldier) {
        this.soldiers.remove(soldier);
    }

    public void perish() {

        if (this.parentTroop != null)
            this.parentTroop.troops.remove(this);

        else
            Printer.print(Message.CANT_REMOVE_TROOP);
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

    public static int countSoldiers(@NotNull Troop troop) {

        int total = 0;
        
        if (troop.troops == null)
            return troop.soldiers.size() + 1;
        
        for (Troop t: troop.troops)
            total += countSoldiers(t);

        return total + 1;
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
            .append(countSoldiers(troop))
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

    public static void printSummary(Troop troop) {
        Printer.print(getSummary(troop));
    }

    public static void printFullSummary(Troop troop) {
        Printer.print(getFullSummary(troop));
    }
    
    @Override
    public String toString() {
        return Troop.getSummary(this);
    }
    
    public static void main(String[] args) {
        
        Troop troop = new Troop(TroopType.LEGION);
        Troop.printSummary(troop);
        
        troop.troops.get(0).perish();
        Troop.printSummary(troop);
        
        troop.printTroopSymbols();
    }
}