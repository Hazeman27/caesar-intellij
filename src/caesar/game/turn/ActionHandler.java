package caesar.game.turn;

import caesar.game.Game;
import caesar.game.map.Direction;
import caesar.game.map.Relief;
import caesar.ui.Message;
import caesar.ui.Printer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface ActionHandler {
	
    boolean handle(Game game);
    
	static boolean newGame(@NotNull Game game) {
		
		Printer.print(Message.NEW_GAME);
		game.start(10, 6, 0, 0, 100);
		
		return true;
	}
	
	static boolean continueGame(@NotNull Game game) {
		
		if (game.getPlayer() != null) {
			
			Printer.print(Message.CONTINUE);
			game.getTurn().next(TurnType.TRAVEL);
			
			return true;
		}
		
		else {
			
			Printer.print(Message.NO_CURRENT_GAME);
			return false;
		}
	}
	
	static boolean exitGame(@NotNull Game game) {
		
		game.exit();
		return true;
	}
	
	static boolean advance(@NotNull Game game) {
		
		game.getTurn().next(TurnType.ADVANCE);
		return true;
	}
	
	static boolean lookAround(@NotNull Game game) {

		Printer.print(Message.PLAYER_LOOKED_AROUND);
        Printer.printRelief(
        	game.getMap(),
        	game.getPlayer().location
        );
        
        return true;
	}
	
	@Contract(pure = true)
	static boolean buildCamp(Game game) {
		return true;
	}
	
	static boolean goToMainMenu(@NotNull Game game) {
		
		game.getTurn().next(TurnType.MAIN_MENU);
		return true;
	}
	
	static boolean goToPreviousTurn(@NotNull Game game) {
		
		game.getTurn().next(game.getTurn().getPrevious());
		return true;
	}
	
	static boolean advance(@NotNull Game game, @NotNull Direction direction) {
		
		Relief relief = game.getMap().getRelief(
			direction.getX(),
			direction.getY()
		);
		
		if (relief == Relief.UNKNOWN) {
			
			Printer.print(Message.UNKNOWN_DIRECTION);
			return false;
		}
		
		
		game.getPlayer().move(direction, relief);
		return true;
	}
	
	static boolean advanceNorth(@NotNull Game game) {
		return advance(game, Direction.NORTH);
	}
	
	static boolean advanceNorthwest(@NotNull Game game) {
		return advance(game, Direction.NORTHWEST);
	}
	
	static boolean advanceNortheast(@NotNull Game game) {
		return advance(game, Direction.NORTHEAST);
	}
	
	static boolean advanceWest(@NotNull Game game) {
		return advance(game, Direction.WEST);
	}
	
	static boolean advanceEast(@NotNull Game game) {
		return advance(game, Direction.EAST);
	}
	
	static boolean advanceSouth(@NotNull Game game) {
		return advance(game, Direction.SOUTH);
	}
	
	static boolean advanceSouthwest(@NotNull Game game) {
		return advance(game, Direction.SOUTHWEST);
	}
	
	static boolean advanceSoutheast(@NotNull Game game) {
		return advance(game, Direction.SOUTHEAST);
	}
}