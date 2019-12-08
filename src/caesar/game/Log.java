package caesar.game;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Log {
	
	@NotNull
	private final List<String> log;
	
	Log() {
		this.log = new ArrayList<>();
	}
	
	<T> void addItem(@NotNull T item) {
		this.log.add(item.toString());
	}
	
	String getLastItem() {
		return this.log.get(this.log.size() - 1);
	}
	
	@NotNull
	@Override
	public String toString() {
		
		StringBuilder stringBuilder = new StringBuilder();
		int listSize = this.log.size();
		
		stringBuilder
			.append(":: Game Log (")
			.append(listSize)
			.append(" items) ::")
			.append("\n");
		
		IntStream.range(0, this.log.size())
		         .forEach(i -> stringBuilder
			         .append("[ ")
			         .append(i + 1)
			         .append(": ")
			         .append(this.log.get(i))
			         .append(" ]")
			         .append("\n")
		         );
		
		return stringBuilder.toString();
	}
}
