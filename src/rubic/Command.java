package rubic;

import org.jetbrains.annotations.Contract;

public class Command {
	
	private CommandFunctionality functionality;

	@Contract(pure = true)
	Command(CommandFunctionality functionality) {
		this.functionality = functionality;
	}
	
	private void execute() {
		
		if (this.functionality instanceof CommandFunction)
			return;
		
		CommandProcedure procedure = (CommandProcedure) this.functionality;
		procedure.function();
	}
	
	private void execute(String string) {
		
		if (this.functionality instanceof CommandProcedure)
			return;
		
		CommandFunction function = (CommandFunction) this.functionality;
		function.function(string);
	}
	
	public static void main(String[] args) {
		
		CommandFunctionality commandFunctionality1 = (CommandProcedure) () -> {
			System.out.println("Command 1");
		};
		
		CommandFunctionality commandFunctionality2 = (CommandFunction) System.out::println;
		
		Command command1 = new Command(commandFunctionality1);
		Command command2 = new Command(commandFunctionality2);
		
		command1.execute();
		command2.execute("String");
		
		command1.execute("String");
	}
}
