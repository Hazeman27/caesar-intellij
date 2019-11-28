package caesar.ui;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public enum Message {
	
	PRELUDE("Prelude", "Story is under development..."),
	PLAYER_MOVED("You have moved"),
	NEW_GAME("Starting new game..."),
	CONTINUE("Continuing game..."),
	NEXT_TURN("Moving to next turn..."),
	EXIT("Exiting Caesar..."),
	NO_CURRENT_GAME("Error", "No current game found..."),
	UNKNOWN_DIRECTION("Error", "Unknown direction for movement..."),
	UNKNOWN_COMMAND("Error", "Unknown command. Try again..."),
	LOW_AP("Error", "Not enough action points..."),
	CANT_REMOVE_TROOP("Error", "Can't remove troop with no parent..."),
	CONSIDER_RESTING("You seem to be low on action points. Consider building a camp and letting your army rest..."),
	ENEMY_NEARBY("You have spotted a gallic army!"),
	TEST("Really Long Text", "Contrary to popular belief, " +
		"Lorem Ipsum is not simply random text. It has roots in a piece of classical " +
		"Latin literature from 45 BC, making it over 2000 years old. " +
		"Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, " +
		"looked up one of the more obscure Latin words, consectetur, from a " +
		"Lorem Ipsum passage, and going through the cites of the word in classical literature, " +
		"discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of " +
		"\"de Finibus Bonorum et Malorum\" " +
		"(The Extremes of Good and Evil) by Cicero, written in 45 BC. " +
		"This book is a treatise on the theory of ethics, very popular during the Renaissance. " +
		"The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", " +
		"comes from a line in section 1.10.32.");
	
	private final String title;
	private final String content;
	private final int maxLength;
	
	@Contract(pure = true)
	Message(@NotNull String title, String content) {
		
		this.title = title;
		this.maxLength = Math.max(title.length(), 50);
		this.content = this.wrapContent(content);
	}
	
	@Contract(pure = true)
	Message(String content) {
		
		this.title = null;
		this.maxLength = 50;
		this.content = this.wrapContent(content);
	}
	
	@NotNull
	private String wrapContent(@NotNull String content) {
		
		int contentLength = content.length();
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(content);
		
		if (contentLength > this.maxLength) {
			
			for (int i = this.maxLength; i < contentLength; i += this.maxLength) {
				
				for (int j = i; j >= 0; j--) {
					
					if (Character.isWhitespace(stringBuilder.charAt(j))) {
						stringBuilder.setCharAt(j, '\n');
						i += j - i + 1;
						break;
					}
				}
			}
		}
		
		return stringBuilder.toString();
	}
	
	@NotNull
	@Override
	public String toString() {
		
		StringBuilder stringBuilder = new StringBuilder("\n");
		
		if (this.title != null) {
			
			stringBuilder.append(
				Printer.getBorder(this.title, this.maxLength, 0)
			);
		}
		
		else stringBuilder.append(
			Printer.getBorder(this.maxLength, 0)
		);
		
		stringBuilder.append(this.content)
			.append("\n");
		
		stringBuilder.append(Printer.getBorder(this.maxLength, 0));
		
		return stringBuilder.toString();
	}
	
	public static void main(String[] args) {
		Printer.print(Message.TEST);
	}
}
