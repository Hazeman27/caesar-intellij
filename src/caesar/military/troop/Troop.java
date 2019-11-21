package caesar.military.troop;

import caesar.military.soldier.Officer;
import caesar.military.soldier.OfficerFactory;
import caesar.military.soldier.Soldier;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Troop {

    private TroopFactory troopFactory = new TroopFactory();
    private OfficerFactory officerFactory = new OfficerFactory();
    private Map<String, String> troopTypes = new HashMap<String, String>();

    protected List<Troop> troops = new ArrayList<Troop>();
    protected List<Soldier> soldiers = new ArrayList<Soldier>();

    private String type;
    private String symbol;
    private Soldier officer;
    private Troop parentTroop;

    private void initTroops(String type, List<Troop> troops, List<Troop> newTroops, int amount) {

        for (int i = 0; i < amount; i++)
            troops.set(i, newTroops.get(i));
    }

    private void initTroops(String type, Troop parentTroop, List<Troop> troops, int amount) {

        for (int i = 0; i < amount; i++)
            troops.add(this.troopFactory.newTroop(type, parentTroop));
    }

    protected static void initSoldiers(List<Soldier> soldiers, int soldiersAmount, Troop troop) {

        for (int i = 0; i < soldiersAmount; i++)
            soldiers.add(new Soldier(troop));
    }

    public Troop(String type, Troop parentTroop, List<Troop> newTroops, int troopsAmount, Officer officer) {

        this.initTroopTypes();
        this.initTroops(troopTypes.get(type), this.troops, newTroops, troopsAmount);

        this.type = type;
        this.officer = officer;

        this.parentTroop = parentTroop;
    }

    public Troop(String type, Troop parentTroop, int troopsAmount) {

        this.initTroopTypes();
        this.initTroops(troopTypes.get(type), this, this.troops, troopsAmount);

        this.type = type;
        this.officer = officerFactory.newOfficer(type, this);

        this.parentTroop = parentTroop;
    }

    public Troop(String type, Troop parentTroop, Soldier officer) {

        this.type = type;
        this.officer = officer;

        this.troops = null;
        this.parentTroop = parentTroop;
    }

    public Troop(String type, Troop parentTroop) {

        this.type = type;
        this.officer = officerFactory.newOfficer(type, this);

        this.troops = null;
        this.parentTroop = parentTroop;
    }

    public String getType() {
        return this.type;
    }

    public Soldier getOfficer() {
        return this.officer;
    }

    public Troop getParentTroop() {
        return this.parentTroop;
    }

    public List<Troop> getTroops() {
        return this.troops;
    }

    public List<Soldier> getSoldiers() {
        return this.soldiers;
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
            System.out.println("Can't remove entire army...");
    }

    public void advance() {

    }

    public void retreat() {

    }

    public void engage() {

    }

    public void hold() {

    }

    public void changeFormation() {

    }

    public void receiveOrder() {

    }

    public void acceptOrder() {

    }

    public void rejectOrder() {

    }

    private void initTroopTypes() {

        this.troopTypes.put("Century", "Contubernium");
        this.troopTypes.put("CenturyFirstCohort", "Contubernium");
        this.troopTypes.put("Cohort", "Century");
        this.troopTypes.put("CohortFirst", "CenturyFirstCohort");
        this.troopTypes.put("Legion", "Cohort");
        this.troopTypes.put("RomanArmy", "Legion");
    }

    public void printSymbol(boolean addSpace, boolean addNewLine) {

        System.out.print(
            this.symbol + (addSpace ? " " : "") + (addNewLine ? "\n" : "")
        );
    }

    public void printSymbol(boolean addSpace) {
        System.out.print(this.symbol + (addSpace ? " " : ""));
    }

    public void printSymbol() {
        System.out.println(this.symbol);
    }

    public void printTroopSymbols() {

        int amount = this.troops.size();

        for (int i = 0; i < amount; i++)
            this.troops.get(i).printSymbol(i != amount - 1, i == amount - 1);     
    }

    public static int countAllSoldiers(Troop troop) {

        int total = 0;

        if (troop.troops == null)
            return troop.soldiers.size() + 1;

        for (Troop t: troop.troops)
            total += countAllSoldiers(t);

        return total + 1;
    }

    @Override
    public String toString() {
        return Troop.summary(this);
    }

    private static String summary(Troop troop) {

        String summary = "\n::: " + troop.type.toUpperCase() + " summary :::\n";

        summary += "Leader: " + troop.officer + "\n";
        summary += "Soldiers count: " + countAllSoldiers(troop) + "\n";

        if (troop.troops != null)
            summary += "Troops count: " + troop.troops.size() + "\n";
            
        summary += "::::::::::::\n";

        return summary;
    }

    private static String fullSummary(Troop troop) {

        String fullSummary = summary(troop);
        int troopsCount = troop.troops.size();

        for (int i = 0; i < troopsCount; i++)
            fullSummary += summary(troop.troops.get(i));

        return fullSummary;
    }

    public static void printSummary(Troop troop) {
        System.out.println(summary(troop));
    }

    public static void printFullSummary(Troop troop) {
        System.out.println(fullSummary(troop));
    }
}