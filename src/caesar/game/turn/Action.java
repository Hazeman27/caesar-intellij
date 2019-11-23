package caesar.game.turn;

import caesar.game.Game;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public enum Action {
	
	NEW_GAME("New game", 0, ActionHandler::newGame),
	CONTINUE_GAME("Continue game", 0, ActionHandler::continueGame),
	EXIT("Exit", 0, ActionHandler::exitGame),
	TO_MAIN_MENU("Main menu", 0, ActionHandler::goToMainMenu),
	
	PREVIOUS("Previous", 0, ActionHandler::goToPreviousTurn),
	ADVANCE("Advance", 0, ActionHandler::advance),
	LOOK_AROUND("Look around", 3, ActionHandler::lookAround),
	BUILD_CAMP("Build camp", 10, ActionHandler::buildCamp),
	
	NORTH("North", 1, ActionHandler::advanceNorth),
	NORTHWEST("Northwest", 1, ActionHandler::advanceNorthwest),
	NORTHEAST("Northeast", 1, ActionHandler::advanceNortheast),
	WEST("West", 1, ActionHandler::advanceWest),
	EAST("East", 1, ActionHandler::advanceEast),
	SOUTH("South", 1, ActionHandler::advanceSouth),
	SOUTHWEST("Southwest", 1, ActionHandler::advanceSouthwest),
	SOUTHEAST("Southeast", 1, ActionHandler::advanceSoutheast);
	
	private final int value;
	private final String name;
	private final ActionHandler handler;
	
	@Contract(pure = true)
	Action(String name, int value, ActionHandler handler) {
		
		this.name = name;
		this.value = value;
		this.handler = handler;
	}
	
	@Contract(pure = true)
	public int getValue() {
		return this.value;
	}
	
	public Response handle(Game game) {
		return this.handler.handle(game);
	}
	
	@Contract(pure = true)
	@Override
	public String toString() {
		return this.name;
	}
	
	@NotNull
	@Contract(pure = true)
	public String toString(boolean addValue) {
		return this.name + (addValue ? " [" + this.value + "]" : "");
	}
}
