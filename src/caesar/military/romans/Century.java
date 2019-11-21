package caesar.military.romans;

import caesar.military.troop.Troop;

public class Century extends Troop {

    public Century(Troop parentTroop) {
        super("Century", parentTroop, 10);
    }

    public static void main(String[] args) {

        Century c = new Century(null);
        System.out.println(Troop.countAllSoldiers(c));
    }
}