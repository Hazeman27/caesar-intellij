package caesar.game.turn;

import caesar.game.Game;
import caesar.game.map.Direction;
import caesar.game.map.Location;
import caesar.game.map.Relief;
import caesar.game.weather.WeatherType;
import caesar.military.troop.Troop;
import caesar.ui.Message;
import caesar.ui.Printer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface ActionHandler {
	
    Response handle(Game game);
    
	@NotNull
	@Contract("_ -> new")
	static Response startNewGame(@NotNull Game game) {
		
		game.start(10, 6, 4, 6, 100);
		
		return new Response(
			Message.NEW_GAME,
			ResponseType.SUCCESS
		);
	}
	
	@NotNull
	static Response continueGame(@NotNull Game game) {
		
		Response response = new Response();
		
		if (game.getPlayer() != null) {
			
			response.setMessage(Message.CONTINUE);
			response.setType(ResponseType.SUCCESS);
			
			game.nextTurn(TurnType.TRAVEL);
			
			return response;
		}
		
		else {
			
			response.setMessage(Message.NO_CURRENT_GAME);
			response.setType(ResponseType.FAILURE);
			
			return response;
		}
	}
	
	@NotNull
	@Contract("_ -> new")
	static Response exitGame(@NotNull Game game) {
		
		game.exit();
		return new Response(ResponseType.SUCCESS);
	}
	
	@NotNull
	@Contract("_ -> new")
	static Response showAdvanceOptions(@NotNull Game game) {
		
		game.nextTurn(TurnType.ADVANCE);
		return new Response(ResponseType.SUCCESS);
	}
	
	@NotNull
	@Contract("_ -> new")
	static Response lookAround(@NotNull Game game) {
		
		Location playerLocation = game.getPlayerLocation();
		Location enemyLocation = game.getEnemyLocation();
		
		int deltaX = enemyLocation.getX() - playerLocation.getX();
		int deltaY = enemyLocation.getY() - playerLocation.getY();
		
        Printer.printRelief(
        	game.getMap(),
        	playerLocation
        );
		
        if (WeatherType.isClear(game.getCurrentWeather()) &&
			(Math.abs(deltaX) == 1 || Math.abs(deltaY) == 1)) {
        	
        	Direction direction = Direction.valueOf(deltaX, deltaY);
        	
        	Printer.print(Message.ENEMY_NEARBY);
        	Printer.print("Enemy is " + direction + " of you...");
		}
  
		return new Response(ResponseType.SUCCESS);
	}
	
	@NotNull
	@Contract("_ -> new")
	static Response openActionsLog(@NotNull Game game) {
		
		return new Response(
			game.getLog(),
			ResponseType.SUCCESS
		);
	}
	
	@NotNull
	@Contract(pure = true)
	static Response buildCamp(@NotNull Game game) {
		
		Response response = new Response();
		
		if (Relief.isSolid(game.getPlayerRelief())) {
		
		
		}
		
		return new Response(ResponseType.SUCCESS);
	}
	
	@NotNull
	@Contract("_ -> new")
	static Response goToMainMenu(@NotNull Game game) {
		
		game.nextTurn(TurnType.MAIN_MENU);
		return new Response(ResponseType.SUCCESS);
	}
	
	@NotNull
	@Contract("_ -> new")
	static Response goToTravelMenu(@NotNull Game game) {
		
		game.nextTurn(TurnType.TRAVEL);
		return new Response(ResponseType.SUCCESS);
	}
	
	@NotNull
	@Contract("_ -> new")
	static Response goToEncounterMenu(@NotNull Game game) {
		
		game.nextTurn(TurnType.ENCOUNTER);
		return new Response(ResponseType.SUCCESS);
	}
	
	@NotNull
	@Contract("_ -> new")
	static Response goToNextDay(@NotNull Game game) {
		
		game.getEnemy().makeMove(game.getPlayer(), game.getMap());
		game.incrementTurnsCount();
		
		game.replenishEntitiesAP();
		game.nextDay();
		game.changeWeather();
		
		if (game.getEnemyLocation().equals(game.getPlayerLocation()))
			game.nextTurn(TurnType.ENCOUNTER);
		
		else game.nextTurn(TurnType.TRAVEL);
		
		return new Response(
			Message.NEXT_TURN,
			ResponseType.SUCCESS
		);
	}
	
	@NotNull
	static Response advance(@NotNull Game game) {
		
		Direction direction = Direction.valueOf(
			game.getLogLastItem().toUpperCase()
		);
		
		Relief relief = game.getMap().getRelief(
			direction.getX() + game.getPlayerLocation().getX(),
			direction.getY() + game.getPlayerLocation().getY()
		);
		
		Response response = new Response();
		
		if (relief == Relief.UNKNOWN) {
			
			response.setMessage(Message.UNKNOWN_DIRECTION);
			response.setType(ResponseType.FAILURE);
			
			return response;
		}
		
		response.setMessage(Message.PLAYER_MOVED);
		response.setType(ResponseType.SUCCESS);
		
		game.getPlayer().move(direction, relief);
		return response;
	}
	
	@NotNull
	@Contract("_ -> new")
	static Response analyzePlayerArmy(@NotNull Game game) {
		
		game.nextTurn(TurnType.ANALYZE_ARMY);
		return new Response(ResponseType.SUCCESS);
	}
	
	@NotNull
	@Contract("_ -> new")
	static Response conductGeneralArmyAnalysis(@NotNull Game game) {
		
		return new Response(
			Troop.getSummary(game.getPlayerArmy()),
			ResponseType.SUCCESS
		);
	}
	
	@NotNull
	@Contract("_ -> new")
	static Response conductThoroughArmyAnalysis(@NotNull Game game) {
		
		return new Response(
			Troop.getFullSummary(game.getPlayerArmy()),
			ResponseType.SUCCESS
		);
	}
	
	@NotNull
	@Contract("_ -> new")
	static Response prepareForBattle(@NotNull Game game) {
		
		game.nextTurn(TurnType.PREPARE_FOR_BATTLE);
		return new Response(ResponseType.SUCCESS);
	}
	
	@NotNull
	@Contract("_ -> new")
	static Response sendMessage(@NotNull Game game) {
		
		game.nextTurn(TurnType.SEND_MESSAGE);
		return new Response(ResponseType.SUCCESS);
	}
	
	@NotNull
	@Contract("_ -> new")
	static Response retreat(@NotNull Game game) {
		
		game.nextTurn(TurnType.RETREAT);
		return new Response(ResponseType.SUCCESS);
	}
	
	@NotNull
	@Contract("_ -> new")
	static Response attack(@NotNull Game game) {
		return new Response(ResponseType.SUCCESS);
	}
	
	@NotNull
	@Contract("_ -> new")
	static Response changeFormation(@NotNull Game game) {
		return new Response(ResponseType.SUCCESS);
	}
	
	@NotNull
	@Contract("_ -> new")
	static Response proposeAlliance(@NotNull Game game) {
		return new Response(ResponseType.SUCCESS);
	}
	
	@NotNull
	@Contract("_ -> new")
	static Response demandSurrender(@NotNull Game game) {
		return new Response(ResponseType.SUCCESS);
	}
	
	@NotNull
	@Contract("_ -> new")
	static Response askForSupplies(@NotNull Game game) {
		return new Response(ResponseType.SUCCESS);
	}
}