package caesar.game.entity;

import caesar.game.Game;
import caesar.game.map.Location;
import caesar.game.map.Relief;
import caesar.game.map.Direction;
import caesar.game.response.Response;
import caesar.game.response.ResponseType;
import caesar.game.status.Status;
import caesar.game.status.StatusType;
import caesar.military.troop.Troop;
import caesar.ui.Message;

import java.util.HashMap;
import java.util.Map;

public abstract class Entity {
	
	private static final int CAMP_BUILD_COST = 256;
	private static final int WOOD_RESOURCE_INITIAL_STATE = 1024;
	private static final int FOOD_RESOURCE_INITIAL_STATE = 65536;
	
	private static final int MORALE_MODIFIER = 1;
	private static final int SATIETY_MODIFIER = 25;
	private static final int HEALTH_MODIFIER = 50;
	
	private static final int[] WOOD_RESOURCE_GATHER_RANGE = new int[] {20, 25};
	private static final int[] FOOD_RESOURCE_GATHER_RANGE = new int[] {42, 50};
	
	public ActionPoints actionPoints;
	public Location location;
	public Troop army;
	private Status woodResource;
	private Status foodResource;
	private boolean camping;
	
	Entity(int actionPointsAmount, int x, int y) {
		
		this.actionPoints = new ActionPoints(actionPointsAmount);
		this.location = new Location(x, y);
		this.camping = false;
		
		this.woodResource = new Status(
			StatusType.WOOD_RESOURCE,
			WOOD_RESOURCE_INITIAL_STATE
		);
		
		this.foodResource = new Status(
			StatusType.FOOD_RESOURCE,
			FOOD_RESOURCE_INITIAL_STATE
		);
	}
	
	public void move(Direction direction, Relief relief) {
		
		if (direction == null)
			return;
		
		this.location.change(
			direction.getX(),
			direction.getY()
		);
		
		this.location.setRelief(relief);
	}
	
	private void updateArmyMorale(int modifier) {
		
		Troop.updateUnitStatusState(
			this.army,
			StatusType.MORALE,
			modifier
		);
	}
	
	private void updateArmySatiety(int modifier) {
		
		Troop.updateUnitStatusState(
			this.army,
			StatusType.SATIETY,
			modifier * SATIETY_MODIFIER
		);
		
		this.updateArmyMorale(modifier * MORALE_MODIFIER);
	}
	
	public boolean canBuildCamp() {
		return this.woodResource.getCurrentState() >= CAMP_BUILD_COST;
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
	
	public Response feedArmy() {
		
		int soldiersCount = Troop.countSoldiers(this.army);
		
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
	
	public void healSoldiers() {
		
		Troop.updateUnitStatusState(
			this.army,
			StatusType.HEALTH,
			HEALTH_MODIFIER
		);
	}
	
	public Map<StatusType, Integer> gatherResources() {
		
		int resourceIndex = this.location.getRelief().getResourceIndex();
		int modifier = this.camping ? 2 : 1;
		
		Map<StatusType, Integer> resourcesGathered = new HashMap<>();
		
		resourcesGathered.put(StatusType.WOOD_RESOURCE, (int) Math.round(Math.pow(
			Game.getRandomInt(WOOD_RESOURCE_GATHER_RANGE) / 10.0,
			resourceIndex
		)) * modifier);
		
		resourcesGathered.put(StatusType.FOOD_RESOURCE, (int) Math.round(Math.pow(
			Game.getRandomInt(FOOD_RESOURCE_GATHER_RANGE) / 10.0,
			resourceIndex
		)) * modifier);
		
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
			", POS: " +
			this.location +
			", REL: " +
			this.location.getRelief() +
			", ARMY: " +
			Troop.countSoldiers(this.army) +
			" ::";
	}
}