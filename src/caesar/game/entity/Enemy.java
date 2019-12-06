package caesar.game.entity;

import caesar.game.map.Direction;
import caesar.game.map.Map;
import caesar.game.map.Relief;
import caesar.game.map.Vector;
import caesar.game.turn.Action;
import caesar.military.gaul.GaulArmy;

public class Enemy extends Entity {
	
	public Enemy(
		int troopsAmount,
		int actionPointsAmount,
		int x, int y
	) {
		super(actionPointsAmount, x, y);
		this.army = new GaulArmy(troopsAmount);
	}
	
	public void act(Player player, Map map) {
		
		if (player == null || map == null)
			return;
		
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