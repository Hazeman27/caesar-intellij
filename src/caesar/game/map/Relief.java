package caesar.game.map;

import org.jetbrains.annotations.Contract;

import java.security.SecureRandom;

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
		
		SecureRandom random = new SecureRandom();
		
		return values()[random.nextInt(
			values().length - 1
		) + 1];
	}
}
