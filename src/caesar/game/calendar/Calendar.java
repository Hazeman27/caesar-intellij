package caesar.game.calendar;

import caesar.game.weather.Weather;

public class Calendar {
	
	private Weather weather;
	
	private Month month;
	private int day;
	private int year;
	private boolean bce;
	
	public Calendar(Month month, int day, int year, boolean bce) {
		
		if (month == null)
			return;
		
		if (day > month.getDays()) {
			month = Month.next(month);
			day -= month.getDays();
		}
		
		this.month = month;
		this.day = Math.max(day, 1);
		
		this.weather = new Weather(this.month.getSeason());
		this.year = Math.max(year, 1);
		this.bce = bce;
	}
	
	public void nextDay() {
		
		this.day++;
		
		if (this.day > this.month.getDays())
			this.nextMonth();
	}
	
	private void nextMonth() {
		
		this.month = Month.next(this.month);
		this.weather.setSeason(this.month.getSeason());
		
		this.day = 1;
		
		if (this.month == Month.IANUARIUS)
			this.nextYear();
	}
	
	private void nextYear() {
		
		if (bce) this.year--;
		else this.year++;
		
		if (this.year == 0) {
			this.bce = false;
			this.year = 1;
		}
	}
	
	public Weather getWeather() {
		return weather;
	}
	
	@Override
	public String toString() {
		return this.month +
			" " +
			this.day +
			", " +
			this.year +
			(this.bce ? " A.D." : "");
	}
}
