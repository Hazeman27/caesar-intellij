package caesar.game.entity;

import caesar.game.Game;
import caesar.game.relief.*;
import caesar.game.response.Response;
import caesar.game.response.ResponseType;
import caesar.game.status.Status;
import caesar.game.status.StatusType;
import caesar.military.troop.Troop;
import caesar.ui.Message;
import sun.security.krb5.internal.crypto.Des;

import java.util.HashMap;
import java.util.Map;

public abstract class Entity {
	
	private static final int CAMP_BUILD_COST = 256;
	private static final int WOOD_RESOURCE_INITIAL_STATE = 1024;
	
	private static final int MORALE_MODIFIER = 1;
	private static final int SATIETY_MODIFIER = 25;
	private static final int HEALTH_MODIFIER = 50;
	
	private static final int[] WOOD_GATHER_RANGE = new int[]{20, 25};
	private static final int[] FOOD_GATHER_RANGE = new int[]{42, 50};
	
	protected final ActionPoints actionPoints;
	protected final Location location;
	protected final ReliefMap reliefMap;
	protected final Status woodResource;
	protected Troop army;
	protected Status foodResource;
	private boolean camping;
	
	Entity(ReliefMap reliefMap, int actionPointsAmount, int x, int y) {
		
		this.actionPoints = new ActionPoints(actionPointsAmount);
		this.location = new Location(x, y);
		this.camping = false;
		
		this.reliefMap = reliefMap;
		
		this.woodResource = new Status(
			StatusType.WOOD_RESOURCE,
			WOOD_RESOURCE_INITIAL_STATE
		);
	}
	
	public ActionPoints getActionPoints() {
		return this.actionPoints;
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	public ReliefMap getReliefMap() {
		return reliefMap;
	}
	
	public Troop getArmy() {
		return this.army;
	}
	
	public Relief getDirectionRelief(Direction direction) {
		
		if (direction == null)
			return null;
		
		return this.reliefMap.getRelief(
			direction.getX() + this.location.getX(),
			direction.getY() + this.location.getY()
		);
	}
	
	public void move(Destination destination) {
		
		if (destination == null)
			return;
		
		this.location.change(
			destination.getDirection().getX(),
			destination.getDirection().getY()
		);
		
		this.location.setRelief(destination.getRelief());
	}
	
	public void leaveCamp() {
		
		this.woodResource.updateState(CAMP_BUILD_COST / 2);
		this.camping = false;
	}
	
	public Response buildCamp() {
		
		if (!Relief.isSolid(this.location.getRelief())) {
			
			return new Response(
				Message.CANT_BUILD_CAMP_NOT_SOLID_RELIEF,
				ResponseType.FAILURE
			);
		}
		
		if (this.canBuildCamp()) {
			
			this.woodResource.updateState(-CAMP_BUILD_COST);
			this.camping = true;
			
			return new Response(
				Message.CAMP_BUILT,
				ResponseType.SUCCESS
			);
		}
		
		return new Response(
			Message.CANT_BUILD_CAMP_NOT_ENOUGH_WOOD,
			ResponseType.FAILURE
		);
	}
	
	public boolean canBuildCamp() {
		return this.woodResource.getCurrentState() >= CAMP_BUILD_COST;
	}
	
	public Response feedArmy() {
		
		int soldiersCount = Troop.getSoldiersCount(this.army);
		
		if (this.foodResource.getCurrentState() < soldiersCount) {
			
			this.updateArmySatiety(-1);
			
			return new Response(
				Message.NOT_ENOUGH_FOOD + Message.MORALE_DOWN.toString(),
				ResponseType.FAILURE
			);
		}
		
		this.foodResource.updateState(-soldiersCount);
		this.updateArmySatiety(1);
		
		return new Response(
			Message.ARMY_FED + Message.MORALE_UP.toString(),
			ResponseType.SUCCESS
		);
	}
	
	private void updateArmySatiety(int modifier) {
		
		Troop.updateUnitStatus(
			this.army,
			StatusType.SATIETY,
			modifier * SATIETY_MODIFIER
		);
		
		this.updateArmyMorale(modifier * MORALE_MODIFIER);
	}
	
	private void updateArmyMorale(int modifier) {
		
		Troop.updateUnitStatus(
			this.army,
			StatusType.MORALE,
			modifier
		);
	}
	
	public void healSoldiers() {
		
		Troop.updateUnitStatus(
			this.army,
			StatusType.HEALTH,
			HEALTH_MODIFIER
		);
	}
	
	public Map<StatusType, Integer> gatherResources() {
		
		int resourceIndex = this.location.getRelief().getResourceIndex();
		int modifier = this.camping ? 2 : 1;
		
		Map<StatusType, Integer> resourcesGathered = new HashMap<>();
		
		resourcesGathered.put(
			StatusType.WOOD_RESOURCE,
			(int) Math.round(Math.pow(Game.getRandomInt(
				WOOD_GATHER_RANGE) / 10.0,
				resourceIndex
			)) * modifier
		);
		
		resourcesGathered.put(
			StatusType.FOOD_RESOURCE,
			(int) Math.round(Math.pow(Game.getRandomInt(
				FOOD_GATHER_RANGE) / 10.0,
				resourceIndex
			)) * modifier
		);
		
		this.woodResource.updateState(
			resourcesGathered.get(StatusType.WOOD_RESOURCE)
		);
		
		this.foodResource.updateState(
			resourcesGathered.get(StatusType.FOOD_RESOURCE)
		);
		
		return resourcesGathered;
	}
	
	@Override
	public String toString() {
		
		return ":: AP: " +
			this.actionPoints.get() +
			", POSITION: " +
			this.location +
			", RELIEF: " +
			this.location.getRelief() +
			", ARMY: " +
			Troop.getSoldiersCount(this.army) +
			", MORALE (AVG): [" +
			this.army.getAverageMoraleState() +
			"/" +
			StatusType.MORALE.getMaxState() +
			"], WOOD: [" +
			this.woodResource.getCurrentState() +
			"/" +
			StatusType.WOOD_RESOURCE.getMaxState() +
			"], FOOD: [" +
			this.foodResource.getCurrentState() +
			"/" +
			StatusType.FOOD_RESOURCE.getMaxState() +
			"] ::";
	}
}