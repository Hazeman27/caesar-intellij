package caesar.game.turn;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import caesar.ui.Printer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public enum TurnType {
	
	MAIN_MENU("Caesar", Arrays.asList(
		Action.NEW_GAME, 
		Action.CONTINUE_GAME,
		Action.OPEN_JOURNAL,
		Action.EXIT
	)),
	
	TRAVEL("Travelling", Arrays.asList(
		Action.ADVANCE, 
		Action.LOOK_AROUND,
		Action.ANALYZE_ARMY,
		Action.BUILD_CAMP,
		Action.OPEN_JOURNAL,
		Action.TO_NEXT_DAY,
		Action.TO_MAIN_MENU
	)),
	
	CAMP("Camp", Arrays.asList(
		Action.REST,
		Action.LEAVE_CAMP,
		Action.OPEN_JOURNAL,
		Action.TO_NEXT_DAY,
		Action.TO_MAIN_MENU
	)),
	
	ADVANCE("Advance options", Arrays.asList(
		Action.NORTH,
		Action.NORTHWEST, 
		Action.NORTHEAST, 
		Action.WEST, 
		Action.EAST, 
		Action.SOUTH, 
		Action.SOUTHWEST, 
		Action.SOUTHEAST,
		Action.TO_TRAVEL
	)),
	
	ANALYZE_ARMY("Army analysis options", Arrays.asList(
		Action.GENERAL_ANALYSIS,
		Action.THOROUGH_ANALYSIS,
		Action.TO_TRAVEL
	)),
	
	ENCOUNTER("Encounter", Arrays.asList(
		Action.PREPARE_FOR_BATTLE,
		Action.SEND_MESSAGE,
		Action.RETREAT,
		Action.TO_MAIN_MENU
	)),
	
	PREPARE_FOR_BATTLE("Preparing for battle", Arrays.asList(
		Action.ATTACK,
		Action.CHANGE_FORMATION,
		Action.TO_ENCOUNTER
	)),
	
	SEND_MESSAGE("Send message", Arrays.asList(
		Action.PROPOSE_ALLIANCE,
		Action.DEMAND_SURRENDER,
		Action.ASK_FOR_SUPPLIES,
		Action.TO_ENCOUNTER
	)),
	
	RETREAT("Retreat options", Arrays.asList(
		Action.NORTH,
		Action.NORTHWEST,
		Action.NORTHEAST,
		Action.WEST,
		Action.EAST,
		Action.SOUTH,
		Action.SOUTHWEST,
		Action.SOUTHEAST,
		Action.TO_ENCOUNTER
	));
	
	private final String title;
	private final List<Action> actions;
	private final List<String> actionNames;
	private final int actionNamesMaxLength;
	
	TurnType(String title, List<Action> actions) {
		
		this.title = title;
		this.actions = actions;
		this.actionNames = this.getActionNames();
		this.actionNamesMaxLength = this.getActionNamesMaxLength();
	}
	
	private List<String> getActionNames() {
		
		return this.actions.stream()
		                   .map(action -> action.toString(true))
		                   .collect(Collectors.toList());
	}
	
	private int getActionNamesMaxLength() {
		
		Optional<String> longestActionName = this.actionNames.stream()
			.reduce((a, b) -> a.length() > b.length() ? a : b);
		
		int actionNamesMaxLength;
		int titleLength = this.title.length();
		
		if (longestActionName.isPresent())
			actionNamesMaxLength = longestActionName.get().length();
		
		else return -1;

		return Math.max(titleLength, actionNamesMaxLength);
	}
	
	@Contract(pure = true)
	public List<Action> getActions() {
		return this.actions;
	}
	
	@Contract(pure = true)
	public Action getAction(int index) {
		return this.actions.get(index);
	}
	
	@NotNull
	@Override
	public String toString() {
		
		StringBuilder stringBuilder = new StringBuilder(
			Printer.getBorder(
				this.title,
				this.actionNamesMaxLength, 5
			)
		);
		
		stringBuilder.append(
			Printer.getEmptyLine(this.actionNamesMaxLength, 5)
		);
		
		IntStream.range(0, actionNames.size()).forEach(i -> {
			
			String actionName = actionNames.get(i);
			
			stringBuilder.append("| ")
			             .append(i + 1)
			             .append(": ")
			             .append(actionName)
			             .append(Printer.getFillingSpaces(
				             actionName,
				             this.actionNamesMaxLength
			             ));
		});
	        
	        stringBuilder.append(
	                Printer.getEmptyLine(this.actionNamesMaxLength, 5)
	        );
	        
	        stringBuilder.append(
	                Printer.getBorder(this.actionNamesMaxLength, 5)
	        );
        
                return stringBuilder.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(Action.ADVANCE);
	}
}