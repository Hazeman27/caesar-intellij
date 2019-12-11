package caesar.game.weather;

public class Weather {
	
	private Season season;
	private WeatherType currentWeather;
	private int currentTemperature;
	
	public Weather(Season season) {
		
		this.season = season;
		this.change();
	}
	
	public void change() {
		
		this.currentWeather =
			Season.getRandomWeatherType(this.season);
		
		this.currentTemperature =
			WeatherType.getRandomTemperature(currentWeather);
	}
	
	public void setSeason(Season season) {
		this.season = season;
	}
	
	public WeatherType getCurrentWeather() {
		return currentWeather;
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