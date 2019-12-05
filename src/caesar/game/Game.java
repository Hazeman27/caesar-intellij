package caesar.game;

import caesar.game.calendar.Calendar;
import caesar.game.calendar.Month;
import caesar.game.entity.Enemy;
import caesar.game.map.Location;
import caesar.game.map.Relief;
import caesar.game.turn.Turn;
import caesar.game.turn.TurnType;
import caesar.game.map.Map;
import caesar.game.entity.Player;
import caesar.game.weather.Weather;
import caesar.game.weather.WeatherType;
import caesar.military.troop.Troop;
import caesar.ui.Message;
import caesar.ui.Printer;

import org.jetbrains.annotations.NotNull;

import java.security.SecureRandom;

public class Game {
	
	private static final SecureRandom random = new SecureRandom();
	private final Turn turn = new Turn(this);
	
	private Map map;
	private Player player;
	private Enemy enemy;
	private int turnsCount;
	
	private Log log;
	private Calendar calendar;
	
	public Game(
		Month calendarMonth,
		int calendarDay,
		int calendarYear,
		boolean calendarBCE
	) {
		
		this.log = new Log();
		
		this.calendar = new Calendar(
			calendarMonth,
			calendarDay,
			calendarYear,
			calendarBCE
		);
		
		this.turn.next(TurnType.MAIN_MENU);
	}
	
	public void start(
		int playerActionPointsAmount,
		int playerTroopsAmount,
		int playerLocationX,
		int playerLocationY,
		int mapSize
	) {
		
		this.map = new Map(mapSize);
		
		this.player = new Player(
			playerTroopsAmount,
			playerActionPointsAmount,
			playerLocationX,
			playerLocationY
		);
		
		this.player.location.setRelief(
			this.map.getRelief(
				playerLocationX,
				playerLocationY
			)
		);
		
		this.turn.setActionPoints(
			this.player.actionPoints
		);
		
		this.enemy = this.spawnEnemy();
	}
	
	@NotNull
	private Enemy spawnEnemy() {
		
		return new Enemy(
			random.nextInt(40) + 10,
			random.nextInt(20) + 5,
			random.nextInt(this.map.getSize()),
			random.nextInt(this.map.getSize())
		);
	}
	
	public Map getMap() {
		return this.map;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public Location getPlayerLocation() {
		return this.player.location;
	}
	
	public Troop getPlayerArmy() {
		return this.player.army;
	}
	
	public Relief getPlayerRelief() {
		return this.player.location.getRelief();
	}
	
	public Enemy getEnemy() {
		return this.enemy;
	}
	
	public Location getEnemyLocation() {
		return this.enemy.location;
	}
	
	public Troop getEnemyArmy() {
		return this.enemy.army;
	}
	
	public int getTurnsCount() {
		return this.turnsCount;
	}
	
	public Log getLog() {
		return log;
	}
	
	public String getLogLastItem() {
		return this.log.getLastItem();
	}
	
	public Calendar getCalendar() {
		return calendar;
	}
	
	public Weather getWeather() {
		return this.calendar.getWeather();
	}
	
	public WeatherType getCurrentWeather() {
		return this.calendar.getWeather()
			.getCurrentWeather();
	}
	
	public void log(Object item) {
		this.log.addItem(item);
	}
	
	public void incrementTurnsCount() {
		this.turnsCount++;
	}
	
	public void nextDay() {
		this.calendar.nextDay();
	}
	
	public void changeWeather() {
		this.calendar.getWeather().change();
	}
	
	public void replenishPlayerAP() {
		this.player.actionPoints.set(15);
	}
	
	public void replenishEntitiesAP() {
		
		if (this.player.actionPoints.get() < 5) {
			
			Printer.print(Message.CONSIDER_RESTING);
			
			int value = random.nextInt(4) + 1;
			this.player.actionPoints.add(value);
			
			Printer.print("Action points gained: " + value + "!");
		}
		
		if (this.enemy.actionPoints.get() < 4) {
			
			this.enemy.actionPoints.add(
				random.nextInt(6) + 1
			);
		}
	}
	
	public void exit() {
		System.exit(0);
	}
	
	public static int getRandomInt(int bound) {
		return random.nextInt(bound);
	}
	
	public static int getRandomInt(int min, int max) {
		return random.nextInt(max - min) + min;
	}
}