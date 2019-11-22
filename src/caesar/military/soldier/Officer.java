package caesar.military.soldier;

import caesar.military.troop.Troop;

public class Officer extends Soldier {

    private final Rank rank;

    public Officer(Rank rank, Troop troop) {
        
        super(troop);
        this.rank = rank;
    }

    public Rank getRank() {
        return this.rank;
    }
}