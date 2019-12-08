package caesar.game.weather;

import caesar.game.Game;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public enum WeatherType {
	
	RAINY(2, 30),
	STORMY(0, 30),
	SUNNY(18, 40),
	CLOUDY(9, 27),
	WINDY(5, 25),
	FOGGY(3, 15),
	SNOW(-15, 5),
	BLIZZARD(-25, -15),
	MIST(-3, 10),
	THUNDERSTORMS(10, 24);
	
	private final int minTemperature;
	private final int maxTemperature;
	
	@Contract(pure = true)
	WeatherType(int minTemperature, int maxTemperature) {
		
		this.minTemperature = minTemperature;
		this.maxTemperature = maxTemperature;
	}
	
	public static int getRandomTemperature(@NotNull WeatherType weatherType) {
		
		return Game.getRandomInt(
			weatherType.minTemperature,
			weatherType.maxTemperature
		);
	}
	
	public static boolean isClear(WeatherType weatherType) {
		return weatherType != FOGGY && weatherType != MIST;
	}
}