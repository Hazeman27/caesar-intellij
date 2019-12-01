package caesar.game.turn;

import caesar.game.Game;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public enum Action {
	
	NEW_GAME	        ("New game",            0,      ActionHandler::startNewGame),
	CONTINUE_GAME		("Continue game", 	0,      ActionHandler::continueGame),
	EXIT			("Exit", 		0,      ActionHandler::exitGame),
	
	TO_MAIN_MENU		("<< Main menu", 	0,      ActionHandler::goToMainMenu),
	TO_NEXT_DAY		("Next day >>", 	0, 	ActionHandler::goToNextDay),
	TO_TRAVEL		("<< Previous", 	0, 	ActionHandler::goToTravelMenu),
	TO_ENCOUNTER		("<< Previous", 	0, 	ActionHandler::goToEncounterMenu),
	
	ADVANCE			("Advance", 		0, 	ActionHandler::showAdvanceOptions),
	LOOK_AROUND		("Look around", 	3, 	ActionHandler::lookAround),
	ANALYZE_ARMY		("Analyze your army", 	0, 	ActionHandler::analyzePlayerArmy),
	OPEN_JOURNAL		("Open journal",	0,	ActionHandler::openActionsLog),
	
	GENERAL_ANALYSIS	("General analysis", 	1, 	ActionHandler::conductGeneralArmyAnalysis),
	THOROUGH_ANALYSIS	("Thorough analysis", 	3, 	ActionHandler::conductThoroughArmyAnalysis),
	
	BUILD_CAMP		("Build camp", 		10,     ActionHandler::buildCamp),
	
	NORTH			("North", 		1, 	ActionHandler::advance),
	NORTHWEST		("Northwest", 		1, 	ActionHandler::advance),
	NORTHEAST		("Northeast", 		1, 	ActionHandler::advance),
	WEST			("West", 		1, 	ActionHandler::advance),
	EAST			("East", 		1, 	ActionHandler::advance),
	SOUTH			("South", 		1, 	ActionHandler::advance),
	SOUTHWEST		("Southwest", 		1, 	ActionHandler::advance),
	SOUTHEAST		("Southeast", 		1, 	ActionHandler::advance),
	
	PREPARE_FOR_BATTLE	("Prepare for battle",	0,	ActionHandler::prepareForBattle),
	SEND_MESSAGE		("Send message",	0,	ActionHandler::sendMessage),
	RETREAT			("Retreat",		0,	ActionHandler::retreat),
	
	ATTACK			("Attack", 		0, 	ActionHandler::attack),
	CHANGE_FORMATION	("Change formation",	0, 	ActionHandler::changeFormation),
	
	PROPOSE_ALLIANCE	("Propose alliance",	2, 	ActionHandler::proposeAlliance),
	DEMAND_SURRENDER	("Demand surrender",	2,	ActionHandler::demandSurrender),
	ASK_FOR_SUPPLIES	("Ask for supplies",	2, 	ActionHandler::askForSupplies);
	
	
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
		return this.name + (
			addValue && this.value != 0 ? " [" + this.value + "]" : ""
		);
	}
}
