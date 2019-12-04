package caesar.game.entity;

import caesar.game.map.Location;
import caesar.game.map.Relief;
import caesar.game.map.Direction;
import caesar.military.troop.Troop;
import org.jetbrains.annotations.NotNull;

public abstract class Entity {
	
	public ActionPoints actionPoints;
	public Location location;
	public Troop army;
	private boolean camping;
	
	Entity(int actionPointsAmount, int x, int y) {
		
		this.actionPoints = new ActionPoints(actionPointsAmount);
		this.location = new Location(x, y);
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