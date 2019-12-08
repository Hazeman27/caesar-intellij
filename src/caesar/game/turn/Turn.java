package caesar.game.turn;

import java.util.Optional;
import java.util.Scanner;

import caesar.game.Game;
import caesar.game.response.Response;
import caesar.ui.Message;
import caesar.ui.Printer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Turn {
	
	private TurnType current;
	
	private final Game game;
	private final Scanner scanner;
	
	public Turn(@NotNull Game game) {
		
		this.game = game;
		this.scanner = new Scanner(System.in);
	}
	
	public TurnType getCurrent() {
		return current;
	}
	
	private void printMessage() {
		
		Printer.print(this.current);
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
	
	private boolean incorrectInputValue(int value) {
		return value < 1 || value > this.current.getActions().size();
	}
	
	@Nullable
	private Action scanInput() {
		
		final String input = this.scanner
			.nextLine()
			.toLowerCase()
			.trim();
		
		if (this.isNumeric(input)) {
			
			int inputValue = Integer.parseInt(input);
			
			if (this.incorrectInputValue(inputValue))
				return null;
			
			return this.current.getAction(inputValue - 1);
		}
		
		Optional<Action> actionOptional = this.current
			.getActions()
			.stream()
			.filter(a -> a.toString().equalsIgnoreCase(input))
			.findFirst();
		
		return actionOptional.orElse(null);
	}
	
	private void handleInput(Action action) {
		
		if (action == null) {
			
			Printer.print(Message.UNKNOWN_COMMAND);
			this.next(this.current);
			return;
		}
		
		int actionValue = action.getValue();
		
		if (this.game.getPlayerAP().get() < actionValue) {
			
			Printer.print(Message.LOW_AP);
			this.next(this.current);
			return;
		}
			
		this.game.log(action);
		Response response = action.handle(this.game);
		
		if (response.hasMessage())
			Printer.print(response.getMessage());
		
		if (response.isSuccessful())
			this.game.getPlayerAP().remove(actionValue);
		
		if (response.hasAction())
			response.initAction();
		
		if (response.hasNextTurn())
			this.next(response.getNextTurn());
	}
	
	public void next(TurnType type) {
		
		this.current = type;
		
		this.awaitInput();
		this.printMessage();
		this.handleInput(this.scanInput());
	}
	
	private void awaitInput() {
		
		Printer.print("press enter to continue...");
		this.scanner.nextLine();
	}
	
	private boolean isNumeric(String candidate) {
		
		try {
			Double.parseDouble(candidate);
		} catch (NumberFormatException | NullPointerException nfe) {
			return false;
		}
		
		return true;
	}
}