package caesar.military.romans;

import caesar.military.soldier.Officer;
import caesar.military.troop.Troop;

import java.util.List;

public class Century extends Troop {
    
    public Century(List<Troop> contuberniums, Troop parentTroop, Officer officer) {
        super("Century", parentTroop, contuberniums, 10, officer);
    }

    public Century(Troop parentTroop) {
        super("Century", parentTroop, 10);
    }

    public static void main(String[] args) {

        Century c = new Century(null);
        System.out.println(Troop.countAllSoldiers(c));
    }
}