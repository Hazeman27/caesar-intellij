package caesar.military.soldier;

import caesar.military.troop.Troop;

public class Officer extends Soldier {

    private Rank rank;

    public Officer(Troop troop, Rank rank) {
        
        super(troop);
        this.rank = rank;
    }

    public Rank getRank() {
        return this.rank;
    }
}