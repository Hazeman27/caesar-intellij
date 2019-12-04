package caesar.game.entity;
import caesar.military.rome.RomanArmy;

public class Player extends Entity {
	
	public Player(
		int troopsAmount,
		int actionPointsAmount,
		int x, int y
	) {
		super(actionPointsAmount, x, y);
		this.army = new RomanArmy(troopsAmount);
	}
}