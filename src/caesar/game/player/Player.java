package caesar.game.player;

import caesar.military.romans.RomanArmy;
import caesar.military.troop.Troop;

public class Player {

    public ActionPoints actionPoints;
    public Location location;

    private Troop army;

    public Player(int actionPointsAmount, int legionsAmount, int x, int y) {

        this.actionPoints = new ActionPoints(actionPointsAmount);
        this.army = new RomanArmy(legionsAmount);
        this.location = new Location(x, y);
    }

    public Troop getArmy() {
        return this.army;
    }
    
    public static void main(String[] args) {

        Player player = new Player(10, 6, 0, 0);
        Troop.printSummary(player.army);

        player.army.printTroopSymbols();
        System.out.println(player.location);
        System.out.println(player.location);
    }
}