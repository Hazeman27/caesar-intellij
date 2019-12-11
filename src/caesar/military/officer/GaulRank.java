package caesar.military.officer;

public enum GaulRank implements Rank {
	
	WARRIOR(0),
	CHIEF(1),
	WARLORD(2),
	CHIEF_WARLORD(3),
	HERO_WARLORD(7);
	
	private final int index;
	
	GaulRank(int index) {
		this.index = index;
	}
	
	@Override
	public int getIndex() {
		return this.index;
	}
}
