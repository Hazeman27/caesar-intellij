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
	CONSIDER_RESTING("You seem to be low on action points. Consider building a camp and letting your army rest..."),
	RESTED("You and your army have rested. Wounds have been healed, morale is now higher..."),
	RESOURCES_GATHERED("You have gathered following resources:"),
	ENEMY_NEARBY("You have spotted a gallic army!"),
	CAMP_BUILT("You have built a camp, now you can rest there..."),
	CAMP_LEFT("You have left your camp, now you can travel..."),
	ARMY_FED("Your army have been successfully fed!"),
	MORALE_UP("Your army's morale is up!"),
	ENGAGING("Engaging with enemy army!"),
	
//	Errors and failures...
	NO_CURRENT_GAME("No current game found..."),
	UNKNOWN_DIRECTION("Error", "Unknown direction for movement..."),
	UNKNOWN_COMMAND("Error", "Unknown command. Try again..."),
	LOW_AP("Not enough action points..."),
	CANT_REMOVE_TROOP("Error", "Can't remove troop with no parent..."),
	CANT_BUILD_CAMP_NOT_ENOUGH_WOOD("Not enough wood to build camp!"),
	CANT_BUILD_CAMP_NOT_SOLID_RELIEF("Can't build camp on this relief!"),
	NOT_ENOUGH_FOOD("You don't have enough food to feed your entire army!"),
	MORALE_DOWN("Your army's morale is down");
	
	private final String title;
	private final String content;
	private final int maxLength;
	
	@Contract(pure = true)
	Message(String title, String content) {
		
		if (title == null)
			title = "";
		
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
		
		if (contentLength <= this.maxLength)
			return stringBuilder.toString();
		
		for (int i = this.maxLength; i < contentLength; i += this.maxLength) {
			
			for (int j = i; j >= 0; j--) {
				
				if (Character.isWhitespace(stringBuilder.charAt(j))) {
					stringBuilder.setCharAt(j, '\n');
					i += j - i + 1;
					break;
				}
			}
		}
		
		return stringBuilder.toString();
	}
	
	@NotNull
	@Override
	public String toString() {
		
		StringBuilder stringBuilder = new StringBuilder();
		
		if (this.title != null) stringBuilder.append(
			Printer.getBorder(this.title, this.maxLength, 0)
		);
		
		else stringBuilder.append(
			Printer.getBorder(this.maxLength, 0)
		);
		
		stringBuilder.append(this.content)
			.append("\n");
		
		stringBuilder.append(Printer.getBorder(this.maxLength, 0));
		return stringBuilder.toString();
	}
}