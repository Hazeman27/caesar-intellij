package caesar.game.calendar;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public enum Month {
	
	IANUARIUS(1, 29),
	FEBRUARIUS(2, 28),
	MARTIUS(3, 31),
	APRILIS(4, 29),
	MAIUS(5, 31),
	IUNIUS(6, 29),
	QUINTILIS(7, 31),
	SEXTILIS(8, 29),
	SEPTEMBER(9, 29),
	OCTOBER(10, 31),
	NOVEMBER(11, 29),
	DECEMBER(12, 29);
	
	private final int number;
	private final int days;
	
	@Contract(pure = true)
	Month(int number, int days) {
		
		this.number = number;
		this.days = days;
	}
	
	public int getDays() {
		return days;
	}
	
	@Nullable
	@Contract(pure = true)
	static Month next(Month current) {
		
		if (current == DECEMBER)
			return IANUARIUS;
		
		for (Month month: values()) {
			
			if (month.number == current.number + 1)
				return month;
		}
		
		return null;
	}
}
