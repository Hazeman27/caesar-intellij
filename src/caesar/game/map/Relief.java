package caesar.game.map;

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
	
	private Relief(String symbol) {
		this.symbol = symbol;
	}
	
	@Override
	public String toString() {
		return this.symbol;
	}
}
