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
    	
    	if (this.game.getPlayer() != null)
    		Printer.print(this.game.getPlayer());
    	
    	if (this.game.getEnemy() != null)
    		Printer.print(this.game.getEnemy());
    	
    	Printer.print("Turns count: " + this.game.getTurnsCount());
    	Printer.print("Enter your choice: ", false);
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

            if (this.actionPoints.get() >= actionValue) {
            	
            	Response response = action.handle(this.game);
            	
            	if (response.isSuccessful())
					this.actionPoints.remove(actionValue);
            	
            	if (response.hasMessage())
            		Printer.print(response.getMessage());
            }
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

        Turn turn = new Turn(new Game());
        turn.next(TurnType.TRAVEL);
    }
}