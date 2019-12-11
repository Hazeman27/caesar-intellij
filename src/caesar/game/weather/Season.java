package caesar.game.weather;

import caesar.game.Game;

import java.util.Arrays;
import java.util.List;

public enum Season {
	
	WINTER(Arrays.asList(
		WeatherType.SNOW,
		WeatherType.BLIZZARD,
		WeatherType.FOGGY,
		WeatherType.MIST,
		WeatherType.CLOUDY,
		WeatherType.WINDY,
		WeatherType.SUNNY
	)),
	
	SPRING(Arrays.asList(
		WeatherType.SUNNY,
		WeatherType.CLOUDY,
		WeatherType.RAINY,
		WeatherType.STORMY,
		WeatherType.THUNDERSTORMS,
		WeatherType.WINDY
	)),
	
	SUMMER(Arrays.asList(
		WeatherType.SUNNY,
		WeatherType.WINDY,
		WeatherType.CLOUDY,
		WeatherType.RAINY
	)),
	
	AUTUMN(Arrays.asList(
		WeatherType.RAINY,
		WeatherType.CLOUDY,
		WeatherType.WINDY,
		WeatherType.SNOW,
		WeatherType.FOGGY,
		WeatherType.MIST
	));
	
	//	Temperature unit: celsius
	private final List<WeatherType> weatherTypes;
	
	Season(List<WeatherType> weatherTypes) {
		this.weatherTypes = weatherTypes;
	}
	
	public static WeatherType getRandomWeatherType(Season season) {
		
		return season.weatherTypes.get(
			Game.getRandomInt(season.weatherTypes.size())
		);
	}
}