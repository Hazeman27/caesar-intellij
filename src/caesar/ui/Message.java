package caesar.ui;

public enum Message {
	
	PLAYER_MOVED("You have moved"),
    PLAYER_LOOKED_AROUND("You have looked around you and here is what you see:"),
    PRELUDE("Prelude", "Story is under development..."),
    CONTINUE("Continuing game..."),
    EXIT("Exiting Caesar..."),
    NO_CURRENT_GAME("Error", "No current game found..."),
    UNKNOWN_DIRECTION("Error", "Unknown direction for movement..."),
    UNKNOWN_COMMAND("Error", "Unknown command. Try again..."),
    NOT_ENOUGH_AP("Error", "Not enough action points. Moving to next turn..."),
    LOW_AP("Error", "Not enough action points...");
		
	private final String title;
	private final String content;
	private final int borderDefaultLength = 15;
	
	private Message(String title, String content) {
		
		this.title = title;
		this.content = content;
	}
	
private Message(String content) {
		
		this.title = null;
		this.content = content;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	@Override
	public String toString() {
		
		int maxLength;
		String string = "\n";
		
		if (this.title == null) {
			maxLength = this.borderDefaultLength;
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
	
	public static void main(String[] args) {
		System.out.println(PLAYER_MOVED);
	}
}
