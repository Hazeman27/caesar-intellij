package caesar.game.entity;

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
}
