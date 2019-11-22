package caesar.game.entity;

import caesar.military.troop.ArmyType;
import caesar.military.troop.Troop;

public abstract class Entity {
	
	public ActionPoints actionPoints;
	public Location location;
	public Troop army;
	
	Entity(
		ArmyType armyType,
		int troopsAmount,
		int actionPointsAmount,
		int x, int y
	) {
		
		this.actionPoints = new ActionPoints(actionPointsAmount);
		this.army = new Troop(armyType, troopsAmount);
		this.location = new Location(x, y);
	}
}
