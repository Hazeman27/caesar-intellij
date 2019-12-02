package caesar.game.calendar;

import caesar.game.weather.Season;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public enum Month {
	
	IANUARIUS(1, 29, Season.WINTER),
	FEBRUARIUS(2, 28, Season.WINTER),
	MARTIUS(3, 31, Season.SPRING),
	APRILIS(4, 29, Season.SPRING),
	MAIUS(5, 31, Season.SPRING),
	IUNIUS(6, 29, Season.SUMMER),
	QUINTILIS(7, 31, Season.SUMMER),
	SEXTILIS(8, 29, Season.SUMMER),
	SEPTEMBER(9, 29, Season.AUTUMN),
	OCTOBER(10, 31, Season.AUTUMN),
	NOVEMBER(11, 29, Season.AUTUMN),
	DECEMBER(12, 29, Season.WINTER);
	
	private final int number;
	private final int days;
	private final Season season;
	
	@Contract(pure = true)
	Month(int number, int days, Season season) {
		
		this.number = number;
		this.days = days;
		this.season = season;
	}
	
	@Contract(pure = true)
	public int getDays() {
		return this.days;
	}
	
	@Contract(pure = true)
	public Season getSeason() {
		return this.season;
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
