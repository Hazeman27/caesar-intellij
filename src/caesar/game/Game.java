package caesar.game;

import caesar.game.entity.Enemy;
import caesar.game.turn.Turn;
import caesar.game.turn.TurnType;
import caesar.game.map.Map;
import caesar.game.entity.Player;
import caesar.military.troop.ArmyType;
import caesar.ui.Message;
import caesar.ui.Printer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.security.SecureRandom;

public class Game {
	
    private final Turn turn = new Turn(this);
    private final SecureRandom random = new SecureRandom();
	
	private Map map;
	private Player player;
    private Enemy enemy;
    private int turnsCount;
    
    public Game() {
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
			ArmyType.ROMAN,
			playerTroopsAmount,
			playerActionPointsAmount,
			playerLocationX,
			playerLocationY
		);
		
		this.turn.setActionPoints(
			this.player.actionPoints
		);
		
		this.enemy = this.spawnEnemy();
		this.turn.next(TurnType.TRAVEL);
	}
    
    @NotNull
	@Contract(" -> new")
	private Enemy spawnEnemy() {
    
		return new Enemy(
			ArmyType.GALLIC,
			this.random.nextInt(40) + 10,
			this.random.nextInt(20) + 5,
			this.random.nextInt(this.map.getSize()),
			this.random.nextInt(this.map.getSize())
		);
	}

    public Map getMap() {
    	return this.map;
    }
    
    public Player getPlayer() {
    	return this.player;
    }
    
    public Enemy getEnemy() {
    	return this.enemy;
	}
    
    public Turn getTurn() {
    	return this.turn;
    }
    
    public int getTurnsCount() {
    	return this.turnsCount;
	}
    
    public void incrementTurnsCount() {
    	this.turnsCount++;
	}
    
    public void exit() {

        Printer.print(Message.EXIT);
        System.exit(0);
    }
	
	public static void main(String... args) {
    	new Game();
    }
}