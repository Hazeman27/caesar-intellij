package caesar.game.turn;

import caesar.game.Game;
import caesar.game.map.Direction;
import caesar.game.map.Relief;
import caesar.ui.Message;
import caesar.ui.Printer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface ActionHandler {
	
    Response handle(Game game);
    
	@NotNull
	@Contract("_ -> new")
	static Response newGame(@NotNull Game game) {
		
		game.start(10, 6, 0, 0, 100);
		return new Response(ResponseType.SUCCESS);
	}
	
	@NotNull
	static Response continueGame(@NotNull Game game) {
		
		Response response = new Response();
		
		if (game.getPlayer() != null) {
			
			response.message = Message.CONTINUE;
			response.type = ResponseType.SUCCESS;
			
			game.getTurn().next(TurnType.TRAVEL);
			
			return response;
		}
		
		else {
			
			response.message = Message.NO_CURRENT_GAME;
			response.type = ResponseType.FAILURE;
			
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
	static Response advance(@NotNull Game game) {
		
		game.getTurn().setPrevious(TurnType.TRAVEL);
		game.getTurn().next(TurnType.ADVANCE);
		
		return new Response(ResponseType.SUCCESS);
	}
	
	@NotNull
	@Contract("_ -> new")
	static Response lookAround(@NotNull Game game) {

		Printer.print(Message.PLAYER_LOOKED_AROUND);
        Printer.printRelief(
        	game.getMap(),
        	game.getPlayer().location
        );
		
		return new Response(ResponseType.SUCCESS);
	}
	
	@NotNull
	@Contract(pure = true)
	static Response buildCamp(Game game) {
		return new Response(ResponseType.SUCCESS);
	}
	
	@NotNull
	@Contract("_ -> new")
	static Response goToMainMenu(@NotNull Game game) {
		
		game.getTurn().next(TurnType.MAIN_MENU);
		return new Response(ResponseType.SUCCESS);
	}
	
	@NotNull
	@Contract("_ -> new")
	static Response goToPreviousTurn(@NotNull Game game) {
		
		game.getTurn().next(game.getTurn().getPrevious());
		return new Response(ResponseType.SUCCESS);
	}
	
	@NotNull
	static Response advance(@NotNull Game game, @NotNull Direction direction) {
		
		Relief relief = game.getMap().getRelief(
			direction.getX(),
			direction.getY()
		);
		
		Response response = new Response();
		
		if (relief == Relief.UNKNOWN) {
			
			response.message = Message.UNKNOWN_DIRECTION;
			response.type = ResponseType.FAILURE;
			
			return response;
		}
		
		response.message = Message.PLAYER_MOVED;
		response.type = ResponseType.SUCCESS;
		
		game.getPlayer().move(direction, relief);
		return response;
	}
	
	@NotNull
	static Response advanceNorth(@NotNull Game game) {
		return advance(game, Direction.NORTH);
	}
	
	@NotNull
	static Response advanceNorthwest(@NotNull Game game) {
		return advance(game, Direction.NORTHWEST);
	}
	
	@NotNull
	static Response advanceNortheast(@NotNull Game game) {
		return advance(game, Direction.NORTHEAST);
	}
	
	@NotNull
	static Response advanceWest(@NotNull Game game) {
		return advance(game, Direction.WEST);
	}
	
	@NotNull
	static Response advanceEast(@NotNull Game game) {
		return advance(game, Direction.EAST);
	}
	
	@NotNull
	static Response advanceSouth(@NotNull Game game) {
		return advance(game, Direction.SOUTH);
	}
	
	@NotNull
	static Response advanceSouthwest(@NotNull Game game) {
		return advance(game, Direction.SOUTHWEST);
	}
	
	@NotNull
	static Response advanceSoutheast(@NotNull Game game) {
		return advance(game, Direction.SOUTHEAST);
	}
}