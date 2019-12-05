package caesar.game.entity;

import caesar.game.map.Location;
import caesar.game.map.Relief;
import caesar.game.map.Direction;
import caesar.game.status.Resource;
import caesar.military.troop.Troop;
import org.jetbrains.annotations.NotNull;

public abstract class Entity {
	
	public ActionPoints actionPoints;
	public Location location;
	public Troop army;
	private boolean camping;
	private Resource wood;
	private Resource food;
	
	Entity(int actionPointsAmount, int x, int y) {
		
		this.actionPoints = new ActionPoints(actionPointsAmount);
		this.location = new Location(x, y);
		
		this.wood = new Resource();
		this.food = new Resource();
		this.camping = false;
	}
	
	public void move(@NotNull Direction direction, Relief relief) {
		
		this.location.change(
			direction.getX(),
			direction.getY()
		);
		
		this.location.setRelief(relief);
	}
	
	public boolean isCamping() {
		return camping;
	}
	
	public void setCamping(boolean camping) {
		this.camping = camping;
	}
	
	public void decreaseWoodResource(int amount) {
		this.wood.decrease(amount);
	}
	
	public void increaseWoodResource(int amount) {
		this.wood.increase(amount);
	}
	
	public void feedArmy() {
		this.food.decrease(
			Troop.countSoldiers(this.army)
		);
	}
	
	public void gatherFood() {
		this.food.increase(1);
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