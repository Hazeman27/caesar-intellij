package caesar.game;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Log {
	
	private List<String> log;
	
	@Contract(pure = true)
	Log() {
		this.log = new ArrayList<>();
	}
	
	<T> void addItem(@NotNull T item) {
		this.log.add(item.toString());
	}
	
	String getLastItem() {
		return this.log.get(this.log.size() - 1);
	}
	
	@Override
	public String toString() {
		
		StringBuilder stringBuilder = new StringBuilder();
		int listSize = this.log.size();
		
		stringBuilder
			.append(":: Game Log (")
			.append(listSize)
			.append(" items) ::")
			.append("\n");
		
		
		for (int i = 0; i < listSize; i++) {
			
			stringBuilder
				.append("[ ")
				.append(i + 1)
				.append(": ")
				.append(this.log.get(i))
				.append(" ]")
				.append("\n");
		}
		
		return stringBuilder.toString();
	}
}
