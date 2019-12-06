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
	
	private static final SecureRandom RANDOM = new SecureRandom();
	
	private static final int[] ENEMY_ARMY_SIZE = new int[] {10, 40};
	private static final int[] ENEMY_AP = new int[] {5, 20};
	private static final int PLAYER_MAX_AP = 15;
	private static final int ENTITY_AP_REPLENISH_THRESHOLD = 5;
	private static final int EXIT_CODE = 0;
	
	private final Turn turn;
	
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
		
		this.turn = new Turn(this);
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
		int playerTroopsAmount,
		int playerLocationX,
		int playerLocationY,
		int mapSize
	) {
		
		this.map = new Map(mapSize);
		
		this.player = new Player(
			playerTroopsAmount,
			PLAYER_MAX_AP,
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
			getRandomInt(ENEMY_ARMY_SIZE),
			getRandomInt(ENEMY_AP),
			getRandomInt(this.map.getSize()),
			getRandomInt(this.map.getSize())
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
		this.player.actionPoints.set(PLAYER_MAX_AP);
	}
	
	public void replenishEntitiesAP() {
		
		if (this.player.actionPoints.get() < ENTITY_AP_REPLENISH_THRESHOLD) {
			
			Printer.print(Message.CONSIDER_RESTING);
			
			int value = getRandomInt(1, ENTITY_AP_REPLENISH_THRESHOLD);
			this.player.actionPoints.add(value);
			
			Printer.print("Action points gained: " + value + "!");
		}
		
		if (this.enemy.actionPoints.get() < ENTITY_AP_REPLENISH_THRESHOLD) {
			
			this.enemy.actionPoints.add(
				getRandomInt(2, ENTITY_AP_REPLENISH_THRESHOLD)
			);
		}
	}
	
	public void exit() {
		System.exit(EXIT_CODE);
	}
	
	public static int getRandomInt(int bound) {
		return RANDOM.nextInt(bound);
	}
	
	public static int getRandomInt(int min, int max) {
		return RANDOM.nextInt(max - min) + min;
	}
	
	public static int getRandomInt(@NotNull int[] range) {
		return RANDOM.nextInt(range[1] - range[0]) + range[0];
	}
}