package caesar.game.turn;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import caesar.game.Game;
import caesar.game.player.ActionPoints;
import caesar.ui.Message;
import caesar.ui.Printer;

public class Turn {
	
	private TurnType type;
    private Game game;
    private ActionPoints actionPoints;
    private Scanner scanner;
    private int minCostAction;

    public Turn(Game game, Scanner scanner) {
        
    	this.game = game;
    	this.actionPoints = game.getPlayer().actionPoints;
        this.scanner = scanner;
    }

    public Turn(Game game) {
    	
    	this.game = game;
    	this.actionPoints = game.getPlayer().actionPoints;
        this.scanner = new Scanner(System.in);
    }

    private int getMinCostAction(List<Action> actions) {
        
    	Optional<Action> min = actions.stream()
    			.reduce((a, b) -> a.getValue() < b.getValue() ? a : b);
    	
    	return min.get().getValue();
    }

    public void printMessage() {

    	Printer.print(this.type);
    	Printer.print("Action points remaining: " + this.actionPoints.get());
    	Printer.print("Enter your choice: ");
    }

    public Action scanInput() {

        String input = this.scanner.nextLine();
        input = input.toLowerCase().trim();

        if (UserInput.isNumeric(input))
            return this.type.getAction(Integer.parseInt(input) - 1);

        for (Action action: this.type.getActions()) {

            if (input.equalsIgnoreCase(action.toString()))
                return action;
        }

        return null;
    }

    public void handleInput(Action action) {

        if (action == null)
            Printer.print(Message.UNKNOWN_COMMAND);

        else {

            int actionValue = action.getValue();

            if (this.actionPoints.get() >= actionValue) {
                        
                this.actionPoints.remove(actionValue);
                action.handle(this.game);
            }

            else Printer.print(Message.LOW_AP);
        }
    }

    public void startInteraction() {

        while (this.actionPoints.get() >= this.minCostAction) {

            this.printMessage();
            this.handleInput(this.scanInput());
            
            UserInput.awaitInput(this.scanner);
        }

        Printer.print(Message.NOT_ENOUGH_AP);
        UserInput.awaitInput(this.scanner);
    }
    
    public void next(TurnType type) {
    	
    	this.type = type;
    	this.minCostAction = this.getMinCostAction(type.getActions());
    	this.startInteraction();
    }

    public static void main(String[] args) {

        Turn turn = new Turn(new Game(15, 6, 0, 0, 50));
        turn.next(TurnType.TRAVEL);
    }
}