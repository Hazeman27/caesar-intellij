package caesar.ui;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public enum Message {
	
	PLAYER_MOVED("You have moved"),
    PLAYER_LOOKED_AROUND("You have looked around you and here is what you see:"),
    PRELUDE("Prelude", "Story is under development..."),
	NEW_GAME("Starting new game..."),
    CONTINUE("Continuing game..."),
    EXIT("Exiting Caesar..."),
    NO_CURRENT_GAME("Error", "No current game found..."),
    UNKNOWN_DIRECTION("Error", "Unknown direction for movement..."),
    UNKNOWN_COMMAND("Error", "Unknown command. Try again..."),
    NEXT_TURN("Error", "Moving to next turn..."),
    LOW_AP("Error", "Not enough action points..."),
	CANT_REMOVE_TROOP("Error", "Can't remove troop with no parent...");
		
	private final String title;
	private final String content;

	@Contract(pure = true)
	Message(String title, String content) {
		
		this.title = title;
		this.content = content;
	}
	
	@Contract(pure = true)
	Message(String content) {
		
		this.title = null;
		this.content = content;
	}
	
	@Contract(pure = true)
	public String getContent() {
		return this.content;
	}
	
	@Contract(pure = true)
	public String getTitle() {
		return this.title;
	}
	
	@NotNull
	@Override
	public String toString() {
		
		int maxLength;
		String string = "\n";
		
		if (this.title == null) {
			maxLength = 15;
			string += Printer.getBorder(maxLength);
		}
		
		else {
			maxLength = this.title.length() + 4;
			string += Printer.getBorder(this.title, maxLength);
		}
			
        string += this.content + "\n";
        string += Printer.getBorder(maxLength) + "\n";
		
		return string;
	}
}
