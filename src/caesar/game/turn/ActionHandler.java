package caesar.game.turn;

import caesar.game.Game;
import caesar.ui.Message;
import caesar.ui.Printer;
import org.jetbrains.annotations.NotNull;

public interface ActionHandler {
	
    void handle(Game game);
    
	static void newGame(@NotNull Game game) {
		game.getTurn().next(TurnType.TRAVEL);
	}
	
	static void continueGame(@NotNull Game game) {
		
	}
	
	static void exitGame(@NotNull Game game) {
		game.exit();
	}
	
	static void advance(@NotNull Game game) {
		game.getTurn().next(TurnType.ADVANCE);
	}
	
	static void lookAround(@NotNull Game game) {

		Printer.print(Message.PLAYER_LOOKED_AROUND);
        Printer.printRelief(
        	game.getMap(),
        	game.getPlayer().location.get()
        );
	}
	
	static void buildCamp(Game game) {
		
	}
	
	static void advanceNorth(@NotNull Game game) {
		game.getPlayer().location.change(0, 1);
	}
	
	static void advanceNorthwest(@NotNull Game game) {
		game.getPlayer().location.change(-1, 1);
	}
	
	static void advanceNortheast(@NotNull Game game) {
		game.getPlayer().location.change(1, 1);
	}
	
	static void advanceWest(@NotNull Game game) {
		game.getPlayer().location.change(-1, 0);
	}
	
	static void advanceEast(@NotNull Game game) {
		game.getPlayer().location.change(1, 0);
	}
	
	static void advanceSouth(@NotNull Game game) {
		game.getPlayer().location.change(0, -1);
	}
	
	static void advanceSouthwest(@NotNull Game game) {
		game.getPlayer().location.change(-1, -1);
	}
	
	static void advanceSoutheast(@NotNull Game game) {
		game.getPlayer().location.change(1, -1);
	}
}