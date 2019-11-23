package caesar.game.entity;

import caesar.game.map.Direction;
import caesar.game.map.Map;
import caesar.game.map.Relief;
import caesar.game.map.Vector;
import caesar.military.troop.ArmyType;

public class Enemy extends Entity {
	
	public Enemy(
		ArmyType armyType,
		int troopsAmount,
		int actionPointsAmount,
		int x, int y
	) {
		super(armyType, troopsAmount, actionPointsAmount, x, y);
	}
	
	public void makeMove(Player player, Map map) {
		
		Vector vector = this.location.calcVector(player.location);
		Direction direction = vector.getDirection();
		
		Relief relief = map.getRelief(
			direction.getX(),
			direction.getY()
		);

		this.move(direction, relief);
	}
}
