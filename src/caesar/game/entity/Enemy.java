package caesar.game.entity;

import caesar.game.relief.Direction;
import caesar.game.relief.Relief;
import caesar.game.relief.ReliefMap;
import caesar.game.relief.Vector;
import caesar.game.status.Status;
import caesar.game.status.StatusType;
import caesar.game.turn.Action;
import caesar.military.gaul.GaulArmy;
import caesar.military.troop.Troop;

import java.util.Map;
import java.util.Optional;

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
	
	private boolean findReliefForResourceGathering(boolean highPriority) {
		
		if (this.actionPoints.atMinimum())
			return false;
		
		if (highPriority && Relief.isSuperResourceRich(this.location.getRelief()))
			return true;
		
		if (!highPriority && Relief.isResourceRich(this.location.getRelief()))
			return true;
		
		Map<Direction, Relief> reliefAround =
			this.reliefMap.getReliefAround(this.location);
		
		Optional<Map.Entry<Direction, Relief>> resourceRichReliefOptional =
			
			reliefAround
				.entrySet()
				.stream()
				.filter(entry -> {
					
					if (highPriority)
						return Relief.isSuperResourceRich(entry.getValue());
					
					else return Relief.isResourceRich(entry.getValue());
				})
				.findFirst();
		
		if (resourceRichReliefOptional.isPresent()) {
			
			Map.Entry<Direction, Relief> resourceRichRelief =
				resourceRichReliefOptional.get();
			
			this.move(
				resourceRichRelief.getKey(),
				resourceRichRelief.getValue()
			);
			
			return true;
		}
		
		this.moveTowardsPlayer();
		this.findReliefForResourceGathering(highPriority);
		return false;
	}
	
	public void gatherResources(boolean highPriority) {
		
		int actionValue = Action.GATHER_RESOURCES.getValue();
		
		if (this.actionPoints.get() >= actionValue &&
			this.findReliefForResourceGathering(highPriority)) {
			
			this.actionPoints.remove(actionValue);
			super.gatherResources();
		}
	}
	
	public void act() {
		
		while (!this.actionPoints.atMinimum() && !this.location.equals(this.player.location)) {
			
			System.out.println(this.actionPoints.get());
			
			int soldiersCount = Troop.getSoldiersCount(this.army);
			
			if (this.foodResource.getCurrentState() <= soldiersCount)
				this.gatherResources(true);
			
			else if (this.foodResource.getCurrentState() > soldiersCount &&
				this.foodResource.getCurrentState() <= soldiersCount * 2)
				this.gatherResources(false);
			
			this.moveTowardsPlayer();
		}
	}
	
	public boolean isAtPlayer() {
		return this.location.getX() == this.player.getLocation().getX() &&
			this.location.getY() == this.player.getLocation().getY();
	}
}