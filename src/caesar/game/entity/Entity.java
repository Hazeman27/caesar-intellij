package caesar.game.entity;

import caesar.game.Game;
import caesar.game.map.Location;
import caesar.game.map.Relief;
import caesar.game.map.Direction;
import caesar.game.status.State;
import caesar.game.status.StateType;
import caesar.military.troop.Troop;

public abstract class Entity {
	
	private static final int CAMP_BUILD_COST = 256;
	private static final int RESOURCE_MAX_STATE = 131072;
	private static final int ENTITY_WOOD_INITIAL_STATE = 1024;
	private static final int ENTITY_FOOD_INITIAL_STATE = 65536;
	
	private static final int MORALE_MODIFIER = 1;
	private static final int SATIETY_MODIFIER = 25;
	
	public ActionPoints actionPoints;
	public Location location;
	public Troop army;
	private State wood;
	private State food;
	
	Entity(int actionPointsAmount, int x, int y) {
		
		this.actionPoints = new ActionPoints(actionPointsAmount);
		this.location = new Location(x, y);
		
		this.wood = new State(
			RESOURCE_MAX_STATE,
			ENTITY_WOOD_INITIAL_STATE
		);
		
		this.food = new State(
			RESOURCE_MAX_STATE,
			ENTITY_FOOD_INITIAL_STATE
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
		
		Troop.updateUnitState(
			this.army,
			StateType.MORALE,
			modifier
		);
	}
	
	private void updateArmySatiety(int modifier) {
		
		Troop.updateUnitState(
			this.army,
			StateType.SATIETY,
			modifier * SATIETY_MODIFIER
		);
		
		this.updateArmyMorale(modifier * MORALE_MODIFIER);
	}
	
	public boolean canBuildCamp() {
		return this.wood.getCurrent() >= CAMP_BUILD_COST;
	}
	
	public boolean buildCamp() {
		
		if (this.canBuildCamp()) {
			
			this.wood.modify(-CAMP_BUILD_COST);
			return true;
		}
		
		return false;
	}
	
	public boolean feedArmy() {
		
		int soldiersCount = Troop.countSoldiers(this.army);
		
		if (this.food.getCurrent() >= soldiersCount) {
			
			this.food.modify(-soldiersCount);
			this.updateArmySatiety(1);
			return true;
		}
		
		this.updateArmySatiety(-1);
		return false;
	}
	
	public void gatherResources() {
		
		Relief relief = this.location.getRelief();
		int resourceIndex = relief.getResourceIndex();
		
		this.wood.modify(
			(int) Math.round(Math.pow(
				Game.getRandomInt(20, 25) / 10.0,
				resourceIndex
			))
		);
		
		this.food.modify(
			(int) Math.round(Math.pow(
				Game.getRandomInt(42, 50) / 10.0,
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