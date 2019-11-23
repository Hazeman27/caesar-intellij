package caesar.game.entity;

import caesar.game.map.Relief;
import caesar.game.map.Direction;
import caesar.military.troop.ArmyType;
import caesar.military.troop.Troop;
import org.jetbrains.annotations.NotNull;

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
	
	public void move(@NotNull Direction direction, Relief relief) {
		
		this.location.change(
			direction.getX(),
			direction.getY()
		);
		
		this.location.setRelief(relief);
	}
}
