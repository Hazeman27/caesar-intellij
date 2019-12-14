package caesar.game.entity;

import caesar.game.relief.*;
import caesar.game.status.Status;
import caesar.game.status.StatusType;
import caesar.game.turn.Action;
import caesar.military.gaul.GaulArmy;
import caesar.military.troop.Troop;

import java.util.Map;

public class Enemy extends Entity {
	
	private final Player player;
	private Destination currentDestination;
	
	public Enemy(
		Player player,
		ReliefMap reliefMap,
		int troopsAmount,
		int actionPointsAmount,
		int x, int y
	) {
		
		super(reliefMap, actionPointsAmount, x, y);
		
		this.army = new GaulArmy(troopsAmount);
		this.player = player;
		this.foodResource = new Status(
			StatusType.FOOD_RESOURCE,
			Troop.getSoldiersCount(this.army) * 20
		);
	}
	
	private void move() {
		
		int actionValue = Action.NORTH.getValue();
		
		if (this.actionPoints.get() < actionValue ||
			this.currentDestination == null)
			return;
		
		this.actionPoints.remove(actionValue);
		super.move(this.currentDestination);
	}
	
	private Destination getDestinationToPlayer() {
		
		Direction direction = this.location.getDirection(
			this.player.location
		);
		
		if (direction == null)
			return null;
		
		Relief relief = this.getDirectionRelief(direction);
		return new Destination(direction, relief);
	}
	
	@Override
	public Map<StatusType, Integer> gatherResources() {
		
		int actionValue = Action.GATHER_RESOURCES.getValue();
		
		if (this.actionPoints.get() < actionValue)
			return null;
		
		if (Relief.isResourceRich(this.location.getRelief())) {
			
			this.actionPoints.remove(actionValue);
			return super.gatherResources();
		}
		
		ReliefAnalyzer reliefAnalyzer = new ReliefAnalyzer(this);
		
		Destination resourceRich =
			reliefAnalyzer.findResourceRich();
		
		if (resourceRich == null)
			return null;
		
		this.currentDestination = resourceRich;
		return null;
	}
	
	public void act() {
		
		while (!this.actionPoints.atMinimum() && !this.atPlayer()) {
			
			int soldiersCount = Troop.getSoldiersCount(this.army);
			this.currentDestination = this.getDestinationToPlayer();
			
			if (this.foodResource.getCurrentState() <= soldiersCount * 5)
				this.gatherResources();
			
			this.move();
		}
	}
	
	public boolean atPlayer() {
		return this.location.getX() == this.player.getLocation().getX() &&
			this.location.getY() == this.player.getLocation().getY();
	}
}