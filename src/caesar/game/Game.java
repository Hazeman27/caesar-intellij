package caesar.game;

import caesar.game.entity.Enemy;
import caesar.game.turn.Turn;
import caesar.game.turn.TurnType;
import caesar.game.map.Map;
import caesar.game.entity.Player;
import caesar.military.troop.ArmyType;
import caesar.ui.Message;
import caesar.ui.Printer;

import java.security.SecureRandom;

public class Game {

    private final Map map;
    private final Player player;
    private final Turn turn;
    private final SecureRandom random;
    
    private Enemy enemy;
    private int turnsCount;
    
    public Game(
	    int playerActionPointsAmount, 
        int playerTroopsAmount,
        int playerLocationX,
        int playerLocationY,
        int mapSize
    ) {

        this.map = new Map(mapSize);
        this.random = new SecureRandom();
        
        this.player = new Player(
			ArmyType.ROMAN,
			playerTroopsAmount,
        	playerActionPointsAmount,
    		playerLocationX,
    		playerLocationY
    	);
        
        this.enemy = spawnEnemy();
        
        this.turn = new Turn(this);
        this.turn.next(TurnType.MAIN_MENU);
    }
    
    private Enemy spawnEnemy() {
    
		return new Enemy(
			ArmyType.GALLIC,
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

    public static void main(String[] args) {
    	new Game(10, 6, 0, 0, 100);
    }
}