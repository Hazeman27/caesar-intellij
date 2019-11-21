package caesar.military.romans;

import caesar.military.soldier.Officer;
import caesar.military.troop.Troop;

import java.util.List;

public class Cohort extends Troop {
    
    public Cohort(List<Troop> centuries, Troop parentTroop, Officer officer) {
        super("Cohort", parentTroop, centuries, 6, officer);
    }

    public Cohort(Troop parentTroop) {
        super("Cohort", parentTroop, 6);
    }

    public static void main(String[] args) {

        Cohort cohort = new Cohort(null);
        System.out.println(Troop.countAllSoldiers(cohort));
    }
}