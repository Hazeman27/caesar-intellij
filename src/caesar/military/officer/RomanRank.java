package caesar.military.officer;

public enum RomanRank implements Rank {
	
	LEGIONARY(1),
	DECANUS(1),
	CENTURION(2),
	LEAD_CENTURION(3),
	LEGATE(4),
	GENERAL(5);
	
	private final int index;
	
	RomanRank(int index) {
		this.index = index;
	}
	
	@Override
	public int getIndex() {
		return this.index;
	}
}
