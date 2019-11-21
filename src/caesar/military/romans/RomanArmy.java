package caesar.military.romans;

import caesar.military.soldier.Officer;
import caesar.military.troop.Troop;

import java.util.List;

public class RomanArmy extends Troop {

    public RomanArmy(List<Troop> centuries, int legionsAmount, Officer officer) {
        super("RomanArmy", null, centuries, legionsAmount, officer);
    }

    public RomanArmy(int legionsAmount) {
        super("RomanArmy", null, legionsAmount);
    }
}