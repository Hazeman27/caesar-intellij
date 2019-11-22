package caesar.game.player;

import caesar.military.troop.ArmyType;
import caesar.military.troop.Troop;

public class Player {

    public ActionPoints actionPoints;
    public Location location;

    private Troop army;

    public Player(int actionPointsAmount, int legionsAmount, int x, int y) {

        this.actionPoints = new ActionPoints(actionPointsAmount);
        this.army = new Troop(ArmyType.ROMAN, legionsAmount);
        
        this.location = new Location(x, y);
    }

    public Troop getArmy() {
        return this.army;
    }
    
    public static void main(String[] args) {

        Player player = new Player(10, 6, 0, 0);
        Troop.printFullSummary(player.army.getTroops().get(0));
    }
}