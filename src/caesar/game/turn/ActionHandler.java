package caesar.game.turn;

import caesar.game.Game;
import caesar.ui.Message;
import caesar.ui.Printer;

public interface ActionHandler {
	
    public void handle(Game game);
    
	public static void newGame(Game game) {
		game.start();
	}
	
	public static void continueGame(Game game) {
		
	}
	
	public static void exitGame(Game game) {
		game.exit();
	}
	
	public static void advance(Game game) {
		game.getTurn().next(TurnType.ADVANCE);
	}
	
	public static void lookAround(Game game) {

		Printer.print(Message.PLAYER_LOOKED_AROUND);
        Printer.printRelief(
        	game.getReliefMap(),
        	game.getPlayer().location.get()
        );
	}
	
	public static void buildCamp(Game game) {
		
	}
	
	public static void advanceNorth(Game game) {
		game.getPlayer().location.change(0, 1);
	}
	
	public static void advanceNorthwest(Game game) {
		game.getPlayer().location.change(-1, 1);
	}
	
	public static void advanceNortheast(Game game) {
		game.getPlayer().location.change(1, 1);
	}
	
	public static void advanceWest(Game game) {
		game.getPlayer().location.change(-1, 0);
	}
	
	public static void advanceEast(Game game) {
		game.getPlayer().location.change(1, 0);
	}
	
	public static void advanceSouth(Game game) {
		game.getPlayer().location.change(0, -1);
	}
	
	public static void advanceSouthwest(Game game) {
		game.getPlayer().location.change(-1, -1);
	}
	
	public static void advanceSoutheast(Game game) {
		game.getPlayer().location.change(1, -1);
	}
}