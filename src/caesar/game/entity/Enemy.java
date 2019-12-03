package caesar.game.entity;

import caesar.game.map.Direction;
import caesar.game.map.Map;
import caesar.game.map.Relief;
import caesar.game.map.Vector;
import caesar.game.turn.Action;
import caesar.military.troop.ArmyType;
import org.jetbrains.annotations.NotNull;

public class Enemy extends Entity {
	
	public Enemy(
		ArmyType armyType,
		int troopsAmount,
		int actionPointsAmount,
		int x,
		int y
	) {
		super(armyType, troopsAmount, actionPointsAmount, x, y);
	}
	
	public void makeMove(@NotNull Player player, @NotNull Map map) {
		
		Vector vector = this.location.calcVector(player.location);
		Direction direction = vector.getDirection();
		
		if (direction != null) {
			
			Relief relief = map.getRelief(
				direction.getX() + this.location.getX(),
				direction.getY() + this.location.getY()
			);
			
			this.move(direction, relief);
			this.actionPoints.remove(Action.NORTH.getValue());
		}
	}
}