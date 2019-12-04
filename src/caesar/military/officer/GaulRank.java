package caesar.military.officer;

import org.jetbrains.annotations.Contract;

public enum GaulRank implements Rank {
	
	CHIEF(1),
	WARLORD(2),
	CHIEF_WARLORD(3),
	HERO_WARLORD(7);
	
	private final int index;
	
	@Contract(pure = true)
	GaulRank(int index) {
		this.index = index;
	}
	
	@Override
	@Contract(pure = true)
	public int getIndex() {
		return this.index;
	}
}
