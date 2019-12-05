package caesar.game.entity;

import caesar.game.Game;
import caesar.game.map.Location;
import caesar.game.map.Relief;
import caesar.game.map.Direction;
import caesar.game.status.Status;
import caesar.military.troop.Troop;
import org.jetbrains.annotations.NotNull;

public abstract class Entity {
	
	public ActionPoints actionPoints;
	public Location location;
	public Troop army;
	private Status wood;
	private Status food;
	
	Entity(int actionPointsAmount, int x, int y) {
		
		this.actionPoints = new ActionPoints(actionPointsAmount);
		this.location = new Location(x, y);
		
		this.wood = new Status(131072, 0, 1024);
		this.food = new Status(131072, 0, 65536);
	}
	
	public void move(@NotNull Direction direction, Relief relief) {
		
		this.location.change(
			direction.getX(),
			direction.getY()
		);
		
		this.location.setRelief(relief);
	}
	
	public void feedArmy() {
		this.food.decrease(
			Troop.countSoldiers(this.army)
		);
	}
	
	public void gatherResources() {
		
		Relief relief = this.location.getRelief();
		int resourceIndex = relief.getResourceIndex();
		
		this.wood.increase(
			(int) Math.round(Math.pow(
				Game.getRandomInt(20, 25) / 10.0,
				resourceIndex
			))
		);
		
		this.food.increase(
			(int) Math.round(Math.pow(
				Game.getRandomInt(32, 40) / 10.0,
				resourceIndex
			))
		);
	}
	
	@Override
	public String toString() {
		
		return ":: AP: " +
			this.actionPoints.get() +
			", POS: " +
			this.location +
			", REL: " +
			this.location.getRelief() +
			", ARMY: " +
			Troop.countSoldiers(this.army) +
			" ::";
	}
}