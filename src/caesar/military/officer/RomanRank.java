package caesar.military.officer;

import org.jetbrains.annotations.Contract;

public enum RomanRank implements Rank {
	
	LEGIONARY(1),
	DECANUS(1),
	CENTURION(2),
	LEAD_CENTURION(3),
	LEGATE(4),
	GENERAL(5);
	
	private final int index;
	
	@Contract(pure = true)
	RomanRank(int index) {
		this.index = index;
	}
	
	@Override
	@Contract(pure = true)
	public int getIndex() {
		return this.index;
	}
}
