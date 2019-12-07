package caesar.game.map;

import caesar.game.Game;
import org.jetbrains.annotations.Contract;

public enum Relief {
	
	UNKNOWN("..", 0),
	MOUNTAIN("^^", 2),
	MARSH("mm", 3),
	VALLEY("vv", 5),
	HILL("hh", 3),
	OPEN("--", 2),
	RIVER("~~", 4),
	SWAMP("sw", 1),
	FOREST("ff", 7);
	
	private final String symbol;
	private final int resourceIndex;
	
	@Contract(pure = true)
	Relief(String symbol, int resourceIndex) {
		this.symbol = symbol;
		this.resourceIndex = resourceIndex;
	}
	
	@Contract(pure = true)
	public int getResourceIndex() {
		return this.resourceIndex;
	}
	
	public String toString(boolean asSymbol) {
		return asSymbol ? this.symbol : super.toString();
	}
	
	public static Relief getRandom() {
		return values()[Game.getRandomInt(1, values().length)];
	}
	
	@Contract(pure = true)
	public static boolean isSolid(Relief relief) {
		return relief != RIVER && relief != SWAMP;
	}
}