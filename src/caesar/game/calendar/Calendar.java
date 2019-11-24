package caesar.game.calendar;

import org.jetbrains.annotations.NotNull;

public class Calendar {
	
	private Month month;
	private int day;
	private int year;
	private boolean annoDomini;
	
	public Calendar(
		@NotNull Month month,
		int day,
		int year,
		boolean annoDomini
	) {
		
		if (day > month.getDays()) {
			
			this.month = Month.next(month);
			this.day = day - month.getDays();
		}
		
		else {
			
			this.month = month;
			this.day = Math.max(day, 1);
		}
		
		this.year = Math.max(year, 1);
		this.annoDomini = annoDomini;
	}
	
	public Month getMonth() {
		return month;
	}
	
	public int getDay() {
		return day;
	}
	
	public int getYear() {
		return year;
	}
	
	public void nextDay() {
		
		this.day++;
		
		if (this.day > this.month.getDays())
			this.nextMonth();
	}
	
	public void nextMonth() {
		
		this.month = Month.next(this.month);
		this.day = 1;
		
		if (this.month == Month.IANUARIUS)
			this.nextYear();
	}
	
	public void nextYear() {
		
		if (annoDomini)
			this.year--;
		
		else this.year++;
		
		if (this.year == 0) {
			
			this.annoDomini = false;
			this.year = 1;
		}
	}
	
	@Override
	public String toString() {
		return this.month.toString() +
			" " +
			this.day +
			", " +
			this.year +
			(this.annoDomini ? " A.D." : "");
	}
	
	public static void main(String[] args) {
		
		Calendar calendar = new Calendar(Month.NOVEMBER, 29, 54, true);
		System.out.println(calendar);
		
		calendar.nextYear();
		System.out.println(calendar);
		
		calendar.nextDay();
		System.out.println(calendar);
		
		calendar.nextMonth();
		System.out.println(calendar);
	}
}
