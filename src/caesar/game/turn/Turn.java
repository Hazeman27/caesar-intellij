package caesar.game.turn;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import caesar.game.Game;
import caesar.game.calendar.Month;
import caesar.game.entity.ActionPoints;
import caesar.ui.Message;
import caesar.ui.Printer;
import caesar.utility.UserInput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Turn {
	
	private TurnType type;
    private Game game;
    private ActionPoints actionPoints;
    private Scanner scanner;
    private int minCostAction;

    public Turn(@NotNull Game game) {

	    this.game = game;
	    this.actionPoints = new ActionPoints(0);
	    this.scanner = new Scanner(System.in);
    }

    private int getMinCostAction(@NotNull List<Action> actions) {

    	Optional<Action> min = actions.stream()
			.reduce((a, b) -> a.getValue() < b.getValue() ? a : b);

	    return min.map(Action::getValue).orElse(-1);
    }

    private void printMessage() {
		
    	Printer.printEmptyLine();
    	Printer.print(this.type);
    	Printer.print(this.game.getCalendar());
    	Printer.print(this.game.getWeather());
    	Printer.printEmptyLine();
    	
    	if (this.game.getPlayer() != null)
    		Printer.print(this.game.getPlayer());
    	
    	if (this.game.getEnemy() != null)
    		Printer.print(this.game.getEnemy());
	
		Printer.printEmptyLine();
    	
    	if (this.game.getTurnsCount() > 0)
			Printer.print("Turns count: " + this.game.getTurnsCount());
    	
    	Printer.print("Enter your choice: ", false);
    }

    @Nullable
	private Action scanInput() {

        String input = this.scanner.nextLine();
        input = input.toLowerCase().trim();

        if (UserInput.isNumeric(input)) {
        	
        	int inputValue = Integer.parseInt(input);
        	
        	if (inputValue < 1 || inputValue > this.type.getActions().size())
        		return null;
        	
			return this.type.getAction(inputValue - 1);
		}

        for (Action action: this.type.getActions()) {

            if (input.equalsIgnoreCase(action.toString()))
                return action;
        }

        return null;
    }

    private void handleInput(Action action) {

        if (action == null)
            Printer.print(Message.UNKNOWN_COMMAND);

        else {

            int actionValue = action.getValue();

            if (this.actionPoints.get() >= actionValue) {
	
				this.game.log(action);
            	Response response = action.handle(this.game);
            	
            	if (response.isSuccessful())
					this.actionPoints.remove(actionValue);
            	
            	if (response.hasMessage())
            		Printer.print(response.getMessage());
            }
            
            else Printer.print(Message.LOW_AP);
        }
    }

    private void startInteraction() {

        while (this.actionPoints.get() >= this.minCostAction) {
        	
            this.printMessage();
            this.handleInput(this.scanInput());
            
            UserInput.awaitInput(this.scanner);
        }
		
        Action.TO_NEXT_TURN.handle(this.game);
    }
    
    public void next(TurnType type) {
    	
    	this.type = type;
    	
    	this.minCostAction = this.getMinCostAction(
    		this.type.getActions()
		);
    	
    	this.startInteraction();
    }
	
	public void setActionPoints(ActionPoints actionPoints) {
		this.actionPoints = actionPoints;
	}
	
	public static void main(String[] args) {

        Turn turn = new Turn(new Game(Month.IANUARIUS, 13, 58, true));
        turn.next(TurnType.TRAVEL);
    }
}