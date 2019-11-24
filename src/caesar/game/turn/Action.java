package caesar.game.turn;

import caesar.game.Game;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public enum Action {
	
	NEW_GAME			("New game", 			0, 	ActionHandler::startNewGame					),
	CONTINUE_GAME		("Continue game", 		0, 	ActionHandler::continueGame					),
	EXIT				("Exit", 				0, 	ActionHandler::exitGame						),
	
	TO_MAIN_MENU		("Main menu", 			0, 	ActionHandler::goToMainMenu					),
	TO_NEXT_TURN		("Next turn", 			0, 	ActionHandler::goToNextTurn					),
	TO_TRAVEL			("Previous", 			0, 	ActionHandler::goToTravelMenu				),
	
	ADVANCE				("Advance", 			0, 	ActionHandler::showAdvanceOptions			),
	LOOK_AROUND			("Look around", 		3, 	ActionHandler::lookAround					),
	ANALYZE_ARMY		("Analyze your army", 	0, 	ActionHandler::analyzePlayerArmy			),
	OPEN_JOURNAL		("Open journal",		0,	ActionHandler::openActionsLog				),
	
	GENERAL_ANALYSIS	("General analysis", 	1, 	ActionHandler::conductGeneralArmyAnalysis	),
	THOROUGH_ANALYSIS	("Thorough analysis", 	3, 	ActionHandler::conductThoroughArmyAnalysis	),
	
	BUILD_CAMP			("Build camp", 			10, ActionHandler::buildCamp					),
	
	NORTH				("North", 				1, 	ActionHandler::advance						),
	NORTHWEST			("Northwest", 			1, 	ActionHandler::advance						),
	NORTHEAST			("Northeast", 			1, 	ActionHandler::advance						),
	WEST				("West", 				1, 	ActionHandler::advance						),
	EAST				("East", 				1, 	ActionHandler::advance						),
	SOUTH				("South", 				1, 	ActionHandler::advance						),
	SOUTHWEST			("Southwest", 			1, 	ActionHandler::advance						),
	SOUTHEAST			("Southeast", 			1, 	ActionHandler::advance						);
	
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
