package caesar.game.turn;

import caesar.game.Game;

public enum Action {
	
	NEW_GAME("New game", 0, ActionHandler::newGame),
	CONTINUE_GAME("Continue game", 0, ActionHandler::continueGame),
	EXIT("Exit", 0, ActionHandler::exitGame),
	
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
	
	Action(String name, int value, ActionHandler handler) {
		
		this.name = name;
		this.value = value;
		this.handler = handler;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public void handle(Game game) {
		this.handler.handle(game);
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	public String toString(boolean addValue) {
		return this.name + (addValue ? " [" + this.value + "]" : "");
	}
}
