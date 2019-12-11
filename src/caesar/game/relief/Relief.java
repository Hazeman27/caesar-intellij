package caesar.game.relief;

import caesar.game.Game;

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
	
	Relief(String symbol, int resourceIndex) {
		this.symbol = symbol;
		this.resourceIndex = resourceIndex;
	}
	
	public static Relief getRandom() {
		return values()[Game.getRandomInt(1, values().length)];
	}
	
	public static boolean isSolid(Relief relief) {
		return relief != RIVER && relief != SWAMP;
	}
	
	public static boolean isResourceRich(Relief relief) {
		return relief == FOREST || relief == VALLEY;
	}
	
	public static boolean isSuperResourceRich(Relief relief) {
		return relief == FOREST;
	}
	
	public int getResourceIndex() {
		return this.resourceIndex;
	}
	
	public String toString(boolean asSymbol) {
		return asSymbol ? this.symbol : super.toString();
	}
}