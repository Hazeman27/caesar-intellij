package caesar.game.map;

import caesar.game.Game;
import org.jetbrains.annotations.Contract;

public enum Relief {
	
	UNKNOWN(".."),
	MOUNTAIN("^^"),
    MARSH("mm"),
    VALLEY("vv"),
    HILL("hh"),
    OPEN("--"),
    RIVER("~~"),
    SWAMP("sw"),
    FOREST("ff");
    
    private final String symbol;

	@Contract(pure = true)
	Relief(String symbol) {
		this.symbol = symbol;
	}
	
	public String toString(boolean asSymbol) {
		return asSymbol ? this.symbol : super.toString();
	}
	
	public static Relief getRandom() {
		return values()[Game.getRandomInt(values().length, 1)];
	}
	
	@Contract(pure = true)
	public static boolean isSolid(Relief relief) {
		return relief != RIVER && relief != SWAMP;
	}
}
