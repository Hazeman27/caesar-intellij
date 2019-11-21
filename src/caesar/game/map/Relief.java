package caesar.game.map;

import org.jetbrains.annotations.Contract;

public enum Relief {
	
	MOUNTAIN("^^"),
    MARSH("mm"),
    VALLEY("vv"),
    HILL("hh"),
    OPEN("--"),
    RIVER("~~"),
    SWAMP("sw"),
    FOREST("ff");
    
    private final String symbol;

	Relief(String symbol) {
		this.symbol = symbol;
	}

	@Override
	public String toString() {
		return this.symbol;
	}
}
