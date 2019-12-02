package rubic;

public class Test {
	
	private Command command;

	Test(Command command) {
		this.command = command;
	}
	
	void testCommand() {
		CommandExtendedAlternative command = (CommandExtendedAlternative) this.command;
		command.function();
	}
	
	void testCommand(String string) {
		CommandExtended command = (CommandExtended) this.command;
		command.function(string);
	}
	
	public static void main(String[] args) {
		
		Command command1 = (CommandExtendedAlternative) () -> {
			System.out.println("Command 1");
		};
		
		Command command2 = (CommandExtended) (string) -> {
			System.out.println(string);
		};
		
		Test test1 = new Test(command1);
		Test test2 = new Test(command2);
		
		test1.testCommand();
		test2.testCommand("String");
	}
}
