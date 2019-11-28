package caesar.app;

import caesar.game.Game;
import caesar.game.calendar.Month;

public class App {
	
	public static void main(String[] args) {
		new Game(Month.IANUARIUS, 13, 58, true);
	}
}