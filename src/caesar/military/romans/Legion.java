package caesar.military.romans;

import caesar.military.soldier.Officer;
import caesar.military.troop.Troop;

import java.util.List;

public class Legion extends Troop {

    private int number;

    public Legion(List<Troop> cohorts, Troop parentTroop, int number, Officer officer) {

        super("Legion", parentTroop, cohorts, 9, officer);

        this.addFirstCohort();
        this.number = number;
    }

    public Legion(int number, Troop parentTroop) {
        
        super("Legion", parentTroop, 9);

        this.addFirstCohort();
        this.number = number;
    }

    public Legion(Troop parentTroop) {
        
        super("Legion", parentTroop, 9);

        this.addFirstCohort();
        this.number = this.parentTroop.getTroops().size() + 1;
    }

    private void addFirstCohort() {

        CohortFirst cohortFirst = new CohortFirst(this);
        this.troops.add(cohortFirst);
    }

    @Override
    public void printSymbol(boolean addSpace, boolean addNewLine) {

        System.out.print(
            "[>" + (this.number != 0 ? this.number : "") + "<]" + 
            (addSpace ? " " : "") + 
            (addNewLine ? "\n" : "")
        );
    }

    @Override
    public void printSymbol(boolean addSpace) {
        
        System.out.print(
            "[>" + (this.number != 0 ? this.number : "") + "<]" + 
            (addSpace ? " " : "")
        );
    }

    @Override
    public void printSymbol() {
        System.out.println("[>" + (this.number != 0 ? this.number : "") + "<]");
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}