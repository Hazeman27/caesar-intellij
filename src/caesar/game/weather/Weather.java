package caesar.game.weather;

import org.jetbrains.annotations.Contract;

public class Weather {
	
	private Season season;
	private WeatherType currentWeather;
	private int currentTemperature;
	
	@Contract(pure = true)
	public Weather(Season season) {
		
		this.season = season;
		this.change();
	}
	
	public void setSeason(Season season) {
		this.season = season;
	}
	
	public void change() {
		
		this.currentWeather =
			Season.getRandomWeatherType(this.season);
		
		this.currentTemperature =
			WeatherType.getRandomTemperature(currentWeather);
	}
	
	@Override
	public String toString() {
		return this.season +
			", " +
			this.currentWeather +
			", " +
			this.currentTemperature +
			" degrees C";
	}
}
