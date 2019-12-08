package caesar.game;

import caesar.game.calendar.Calendar;
import caesar.game.calendar.Month;
import caesar.game.entity.ActionPoints;
import caesar.game.entity.Enemy;
import caesar.game.entity.Player;
import caesar.game.relief.Location;
import caesar.game.relief.ReliefMap;
import caesar.game.turn.Turn;
import caesar.game.turn.TurnType;
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
	private static final int AP_REPLENISH_THRESHOLD = 5;
	private static final int EXIT_CODE = 0;
	
	@NotNull
	private final ReliefMap reliefMap;
	@NotNull
	private final Player player;
	private Enemy enemy;
	private int turnsCount;
	
	@NotNull
	private final Turn turn;
	@NotNull
	private final Log log;
	@NotNull
	private final Calendar calendar;
	
	public Game(
		Month calendarMonth,
		int calendarDay,
		int calendarYear,
		boolean calendarBCE,
		int playerTroopsAmount,
		int playerLocationX,
		int playerLocationY,
		int mapSize
	) {
		
		this.reliefMap = new ReliefMap(mapSize);
		
		this.calendar = new Calendar(
			calendarMonth,
			calendarDay,
			calendarYear,
			calendarBCE
		);
		
		this.player = new Player(
			this.reliefMap,
			playerTroopsAmount,
			PLAYER_MAX_AP,
			playerLocationX,
			playerLocationY
		);
		
		this.player.getLocation().setRelief(
			this.reliefMap.getRelief(
				playerLocationX,
				playerLocationY
			)
		);
		
		this.turn = new Turn(this);
		this.log = new Log();
		
		turn.next(TurnType.MAIN_MENU);
	}
	
	public void start() {
		this.spawnEnemy();
	}
	
	private void spawnEnemy() {
		
		this.enemy = new Enemy(
			this.player,
			this.reliefMap,
			getRandomInt(ENEMY_ARMY_SIZE),
			getRandomInt(ENEMY_AP),
			4,
			6
		);
		
		this.enemy.getLocation().setRelief(
			this.reliefMap.getRelief(
				this.enemy.getLocation().getX(),
				this.enemy.getLocation().getY()
			)
		);
	}
	
	@NotNull
	public ReliefMap getReliefMap() {
		return this.reliefMap;
	}
	
	public TurnType getCurrentTurn() {
		return this.turn.getCurrent();
	}
	
	@NotNull
	public Player getPlayer() {
		return this.player;
	}
	
	@NotNull
	public Location getPlayerLocation() {
		return this.player.getLocation();
	}
	
	public Troop getPlayerArmy() {
		return this.player.getArmy();
	}
	
	@NotNull
	public ActionPoints getPlayerAP() {
		return this.player.getActionPoints();
	}
	
	public Enemy getEnemy() {
		return this.enemy;
	}
	
	@NotNull
	public Location getEnemyLocation() {
		return this.enemy.getLocation();
	}
	
	public Troop getEnemyArmy() {
		return this.enemy.getArmy();
	}
	
	public int getTurnsCount() {
		return this.turnsCount;
	}
	
	@NotNull
	public Log getLog() {
		return log;
	}
	
	public String getLogLastItem() {
		return this.log.getLastItem();
	}
	
	@NotNull
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
	
	public void log(@NotNull Object item) {
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
		this.player.getActionPoints().set(PLAYER_MAX_AP);
	}
	
	public void replenishEntitiesAP() {
		
		ActionPoints playerAP = this.player.getActionPoints();
		ActionPoints enemyAP = this.enemy.getActionPoints();
		
		if (playerAP.get() < AP_REPLENISH_THRESHOLD) {
			
			int value = getRandomInt(1, AP_REPLENISH_THRESHOLD);
			playerAP.add(value);
			
			Printer.print(Message.CONSIDER_RESTING);
			Printer.print("Action points gained: " + value + "!");
		}
		
		if (enemyAP.get() < AP_REPLENISH_THRESHOLD)
			enemyAP.add(getRandomInt(2, AP_REPLENISH_THRESHOLD));
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