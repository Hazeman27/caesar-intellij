package caesar.military.romans;

import caesar.military.troop.Troop;

public class Legion extends Troop {

    private int number;

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