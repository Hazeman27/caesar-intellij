package caesar.military.romans;

import caesar.military.troop.Troop;

public class Cohort extends Troop {

    public Cohort(Troop parentTroop) {
        super("Cohort", parentTroop, 6);
    }

    public static void main(String[] args) {

        Cohort cohort = new Cohort(null);
        System.out.println(Troop.countAllSoldiers(cohort));
    }
}