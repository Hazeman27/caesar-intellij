package caesar.game.turn;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import caesar.game.Game;
import caesar.game.entity.ActionPoints;
import caesar.ui.Message;
import caesar.ui.Printer;
import caesar.utility.UserInput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Turn {
	
	private TurnType type;
	private TurnType previous;
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

    	Printer.print(this.type);
    	Printer.print("Current turn count: " + this.game.getTurnsCount());
    	Printer.print("Action points remaining: " + this.actionPoints.get());
    	Printer.print("Enter your choice: ");
    }

    @Nullable
	private Action scanInput() {

        String input = this.scanner.nextLine();
        input = input.toLowerCase().trim();

        if (UserInput.isNumeric(input)) {
        
			return this.type.getAction(
				Integer.parseInt(input) - 1
			);
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

            if (this.actionPoints.get() >= actionValue &&
				action.handle(this.game)) {
				this.actionPoints.remove(actionValue);
            }

            else Printer.print(Message.LOW_AP);
        }
    }

    private void startInteraction() {

        while (this.actionPoints.get() >= this.minCostAction) {
			
        	this.previous = this.type;
        	
            this.printMessage();
            this.handleInput(this.scanInput());
            
            UserInput.awaitInput(this.scanner);
        }

        Printer.print(Message.NEXT_TURN);
        UserInput.awaitInput(this.scanner);
        
        this.game.incrementTurnsCount();
    }
    
    public void next(TurnType type) {
    	
    	this.type = type;
    	
    	this.minCostAction = this.getMinCostAction(
    		this.type.getActions()
		);
    	
    	this.startInteraction();
    }
    
    TurnType getPrevious() {
    	return this.previous;
	}
	
	public void setActionPoints(ActionPoints actionPoints) {
		this.actionPoints = actionPoints;
	}
	
	public static void main(String[] args) {

        Turn turn = new Turn(new Game());
        turn.next(TurnType.TRAVEL);
    }
}