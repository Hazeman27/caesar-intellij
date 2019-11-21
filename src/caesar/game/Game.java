package caesar.game;

import java.security.SecureRandom;
import java.util.Scanner;

import caesar.game.turn.Turn;
import caesar.game.turn.TurnType;
import caesar.game.map.Map;
import caesar.game.player.Player;
import caesar.ui.Message;
import caesar.ui.Printer;
import caesar.utility.RandomEnum;

public class Game {

    private final Map reliefMap;
    private final Player player;
    private final Turn turn;
    
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
    
    public void start() {
    	this.turn.next(RandomEnum.get(TurnType.class));
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
    
    public void exit() {

        Printer.print(Message.EXIT);
        System.exit(0);
    }

    public static void main(String[] args) {
    	new Game(10, 6, 0, 0, 100);
    }
}