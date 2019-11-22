package caesar.game;

import caesar.game.turn.Turn;
import caesar.game.turn.TurnType;
import caesar.game.map.Map;
import caesar.game.player.Player;
import caesar.ui.Message;
import caesar.ui.Printer;

public class Game {

    private final Map reliefMap;
    private final Player player;
    private final Turn turn;
    private int turnsCount;
    
    public Game(
	    int playerActionPointsAmount, 
        int playerLegionsAmount,
        int playerLocationX,
        int playerLocationY,
        int mapSize
    ) {

        this.reliefMap = new Map(mapSize);
        
        this.player = new Player(
        	playerActionPointsAmount,
    		playerLegionsAmount,
    		playerLocationX,
    		playerLocationY
    	);
        
        this.turn = new Turn(this);
        this.turn.next(TurnType.MAIN_MENU);
    }

    public Map getReliefMap() {
    	return this.reliefMap;
    }
    
    public Player getPlayer() {
    	return this.player;
    }
    
    public Turn getTurn() {
    	return this.turn;
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