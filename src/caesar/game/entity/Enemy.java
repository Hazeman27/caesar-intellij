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
	
	@Override
	public void move(Direction direction, Relief relief) {
		
		int actionValue = Action.NORTH.getValue();
		
		if (this.actionPoints.get() < actionValue)
			return;
		
		super.move(direction, relief);
		this.actionPoints.remove(actionValue);
	}
	
	private void moveTowardsPlayer() {
		
		Vector vector = this.location.calcVector(this.player.location);
		Direction direction = vector.getDirection();
		
		if (direction != null) {
			
			Relief relief = this.getDirectionRelief(direction);
			this.move(direction, relief);
		}
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
		
		Map.Entry<Direction, Relief> resourceRichRelief =
			reliefAnalyzer.findResourceRich();
		
		if (resourceRichRelief == null)
			return null;
		
		this.move(
			resourceRichRelief.getKey(),
			resourceRichRelief.getValue()
		);
		
		return null;
	}
	
	public void act() {
		
		while (!this.actionPoints.atMinimum() && !this.atPlayer()) {
			
			int soldiersCount = Troop.getSoldiersCount(this.army);
			
			if (this.foodResource.getCurrentState() <= soldiersCount * 2)
				this.gatherResources();
			
			this.moveTowardsPlayer();
		}
	}
	
	public boolean atPlayer() {
		return this.location.getX() == this.player.getLocation().getX() &&
			this.location.getY() == this.player.getLocation().getY();
	}
}