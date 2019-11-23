package caesar.game.turn;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import caesar.ui.Printer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public enum TurnType {
	
	MAIN_MENU("Caesar", Arrays.asList(
		Action.NEW_GAME, 
		Action.CONTINUE_GAME, 
		Action.EXIT
	)),
	
	TRAVEL("Travelling", Arrays.asList(
		Action.ADVANCE, 
		Action.LOOK_AROUND,
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
		Action.PREVIOUS
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
		
		StringBuilder string = new StringBuilder(Printer.getBorder(this.title, this.actionNamesMaxLength));
		string.append(Printer.getEmptyLine(this.actionNamesMaxLength));
		
        for (int i = 0; i < actionNames.size(); i++) {
            
            String actionName = actionNames.get(i);

            string.append("| ").append(i + 1).append(": ").append(actionName);
            string.append(Printer.getFillingSpaces(
				actionName,
				this.actionNamesMaxLength
            ));
        }
        
        string.append(Printer.getEmptyLine(this.actionNamesMaxLength));
        string.append(Printer.getBorder(this.actionNamesMaxLength));
        
        return string.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(Action.ADVANCE);
	}
}
