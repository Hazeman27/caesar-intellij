package caesar.game.turn;

import caesar.game.Game;
import caesar.game.battle.BattleReport;
import caesar.game.relief.Direction;
import caesar.game.relief.Location;
import caesar.game.relief.Relief;
import caesar.game.response.Response;
import caesar.game.response.ResponseType;
import caesar.game.status.StatusType;
import caesar.game.weather.WeatherType;
import caesar.ui.Message;
import caesar.ui.Printer;

import java.util.Map;

public enum Action {
	
	NEW_GAME("New game", game -> {
		
		game.start();
		
		return new Response(
			Message.NEW_GAME,
			ResponseType.SUCCESS,
			TurnType.TRAVEL
		);
	}),
	
	CONTINUE_GAME("Continue game", game -> {
		
		if (game.getTurnsCount() == 0) {
			
			return new Response(
				Message.NO_CURRENT_GAME,
				ResponseType.FAILURE,
				game.getCurrentTurn()
			);
		}
		
		return new Response(
			Message.CONTINUE,
			ResponseType.SUCCESS,
			TurnType.TRAVEL
		);
	}),
	
	EXIT("Exit", game -> {
		
		return new Response(
			Message.EXIT,
			ResponseType.SUCCESS,
			game::exit
		);
	}),
	
	TO_MAIN_MENU("<< Main menu", game -> {
		
		return new Response(
			ResponseType.SUCCESS,
			TurnType.MAIN_MENU
		);
	}),
	
	TO_NEXT_DAY("Next day >>", game -> {
		
		game.getEnemy().act();
		game.getEnemy().feedArmy();
		game.incrementTurnsCount();
		
		game.replenishEntitiesAP();
		game.nextDay();
		game.changeWeather();
		
		Response armyFeeding = game.getPlayer().feedArmy();
		Printer.print(armyFeeding.getMessage());
		
		if (game.getEnemyLocation().equals(game.getPlayerLocation())) {
			
			return new Response(
				Message.NEXT_TURN,
				ResponseType.SUCCESS,
				TurnType.ENCOUNTER
			);
		}
		
		return new Response(
			Message.NEXT_TURN,
			ResponseType.SUCCESS,
			game.getCurrentTurn()
		);
	}),
	
	TO_TRAVEL("<< Previous", game -> {
		
		return new Response(
			ResponseType.SUCCESS,
			TurnType.TRAVEL
		);
	}),
	
	TO_ENCOUNTER("<< Previous", game -> {
		
		return new Response(
			ResponseType.SUCCESS,
			TurnType.ENCOUNTER
		);
	}),
	
	ADVANCE("Advance", game -> {
		
		return new Response(
			ResponseType.SUCCESS,
			TurnType.ADVANCE
		);
	}),
	
	LOOK_AROUND("Look around", 3, game -> {
		
		Location playerLocation = game.getPlayerLocation();
		Location enemyLocation = game.getEnemyLocation();
		
		int deltaX = enemyLocation.getX() - playerLocation.getX();
		int deltaY = enemyLocation.getY() - playerLocation.getY();
		
		Printer.printRelief(game.getReliefMap(), playerLocation);
		
		if (WeatherType.isClear(game.getCurrentWeather()) &&
			(Math.abs(deltaX) == 1 || Math.abs(deltaY) == 1)) {
			
			Direction direction = Direction.valueOf(deltaX, deltaY);
			
			Printer.print(Message.ENEMY_NEARBY);
			Printer.print("Enemy is " + direction + " of you...");
		}
		
		return new Response(
			ResponseType.SUCCESS,
			game.getCurrentTurn()
		);
	}),
	
	ANALYZE_ARMY("Analyze your army", game -> {
		
		return new Response(
			ResponseType.SUCCESS,
			TurnType.ANALYZE_ARMY
		);
	}),
	
	OPEN_JOURNAL("Open journal", game -> {
		
		return new Response(
			game.getLog(),
			ResponseType.SUCCESS,
			game.getCurrentTurn()
		);
	}),
	
	GENERAL_ANALYSIS("General analysis", 1, game -> {
		
		return new Response(
			game.getPlayerArmy().getSummary(),
			ResponseType.SUCCESS,
			TurnType.ANALYZE_ARMY
		);
	}),
	
	THOROUGH_ANALYSIS("Thorough analysis", 3, game -> {
		
		return new Response(
			game.getPlayerArmy().getFullSummary(),
			ResponseType.SUCCESS,
			TurnType.ANALYZE_ARMY
		);
	}),
	
	BUILD_CAMP("Build camp", 10, game -> {
		
		Response campBuilding = game.getPlayer().buildCamp();
		
		if (campBuilding.isSuccessful()) {
			
			return new Response(
				campBuilding.getMessage(),
				ResponseType.SUCCESS,
				TurnType.CAMP
			);
		}
		
		return new Response(
			campBuilding.getMessage(),
			ResponseType.FAILURE,
			game.getCurrentTurn()
		);
	}),
	
	REST("Rest", 3, game -> {
		
		return new Response(
			Message.RESTED,
			ResponseType.SUCCESS,
			game::replenishPlayerAP,
			TurnType.CAMP
		);
	}),
	
	GATHER_RESOURCES("Gather resources", 3, game -> {
		
		Printer.print(Message.RESOURCES_GATHERED);
		
		Map<StatusType, Integer> resourcesGathered =
			game.getPlayer().gatherResources();
		
		Printer.print("Wood: " + resourcesGathered.get(
			StatusType.WOOD_RESOURCE)
		);
		
		Printer.print("Food: " + resourcesGathered.get(
			StatusType.FOOD_RESOURCE)
		);
		
		return new Response(
			ResponseType.SUCCESS,
			game.getCurrentTurn()
		);
	}),
	
	LEAVE_CAMP("Leave camp", 2, game -> {
		
		game.getPlayer().leaveCamp();
		
		return new Response(
			Message.CAMP_LEFT,
			ResponseType.SUCCESS,
			TurnType.TRAVEL
		);
	}),
	
	PREPARE_FOR_BATTLE("Prepare for battle", game -> {
		
		return new Response(
			ResponseType.SUCCESS,
			TurnType.PREPARE_FOR_BATTLE
		);
	}),
	
	SEND_MESSAGE("Send message", game -> {
		
		return new Response(
			ResponseType.SUCCESS,
			TurnType.SEND_MESSAGE
		);
	}),
	
	RETREAT("Retreat", game -> {
		
		return new Response(
			ResponseType.SUCCESS,
			TurnType.RETREAT
		);
	}),
	
	ATTACK("Attack", game -> {
		
		Printer.print(Message.ENGAGING);
		
		BattleReport report = game
			.getPlayerArmy()
			.engage(game.getEnemyArmy(), false);
		
		Printer.print(report);
		
		return new Response(
			ResponseType.SUCCESS,
			TurnType.PREPARE_FOR_BATTLE
		);
	}),
	
	CHANGE_FORMATION("Change formation", game -> {
		
		return new Response(
			ResponseType.SUCCESS,
			game.getCurrentTurn()
		);
	}),
	
	PROPOSE_ALLIANCE("Propose alliance", 2, game -> {
		
		return new Response(
			ResponseType.SUCCESS,
			TurnType.ENCOUNTER
		);
	}),
	
	DEMAND_SURRENDER("Demand surrender", 2, game -> {
		
		return new Response(
			ResponseType.SUCCESS,
			TurnType.ENCOUNTER
		);
	}),
	
	ASK_FOR_SUPPLIES("Ask for supplies", 2, game -> {
		
		return new Response(
			ResponseType.SUCCESS,
			TurnType.ENCOUNTER
		);
	}),
	
	NORTH("North", 1, Action::advance),
	NORTHWEST("Northwest", 1, Action::advance),
	NORTHEAST("Northeast", 1, Action::advance),
	WEST("West", 1, Action::advance),
	EAST("East", 1, Action::advance),
	SOUTH("South", 1, Action::advance),
	SOUTHWEST("Southwest", 1, Action::advance),
	SOUTHEAST("Southeast", 1, Action::advance);
	
	private final int value;
	private final String name;
	private final ActionHandler handler;
	
	Action(String name, int value, ActionHandler handler) {
		
		this.name = name;
		this.value = value;
		this.handler = handler;
	}
	
	Action(String name, ActionHandler handler) {
		
		this.name = name;
		this.value = 0;
		this.handler = handler;
	}
	
	private static Response advance(Game game) {
		
		Direction direction = Direction.valueOf(
			game.getLogLastItem().toUpperCase()
		);
		
		Relief relief = game.getPlayer().getDirectionRelief(direction);
		
		if (relief == Relief.UNKNOWN) {
			
			return new Response(
				Message.UNKNOWN_DIRECTION,
				ResponseType.FAILURE,
				game.getCurrentTurn()
			);
		}
		
		game.getPlayer().move(direction, relief);
		
		return new Response(
			Message.PLAYER_MOVED,
			ResponseType.SUCCESS,
			game.getCurrentTurn()
		);
	}
	
	public int getValue() {
		return this.value;
	}
	
	public Response handle(Game game) {
		return this.handler.handle(game);
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	public String toString(boolean addValue) {
		return this.name + (
			addValue && this.value != 0 ? " [" + this.value + "]" : ""
		);
	}
}