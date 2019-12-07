package caesar.game.entity;
import caesar.game.relief.ReliefMap;
import caesar.game.status.Status;
import caesar.game.status.StatusType;
import caesar.military.rome.RomanArmy;
import caesar.military.troop.Troop;

public class Player extends Entity {
	
	public Player(
		ReliefMap reliefMap,
		int troopsAmount,
		int actionPointsAmount,
		int x, int y
	) {
		
		super(reliefMap, actionPointsAmount, x, y);
		this.army = new RomanArmy(troopsAmount);
		
		this.foodResource = new Status(
			StatusType.FOOD_RESOURCE,
			Troop.countSoldiers(this.army) * 10
		);
	}
}