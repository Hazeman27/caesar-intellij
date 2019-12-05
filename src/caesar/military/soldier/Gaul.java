package caesar.military.soldier;

import caesar.game.Game;
import caesar.game.status.StateType;
import caesar.military.troop.Troop;

public class Gaul extends Soldier {
	
	public Gaul(Troop troop) {
		super(troop);
		this.name = Name.getRandomGallic();
	}
	
	@Override
	protected int getDamageBoost() {
		
		return this.state.get(StateType.MORALE).getCurrent() / 10 +
			this.state.get(StateType.SATIETY).getCurrent() / 10;
	};
	
	@Override
	protected int block(int damageAmount) {
		return Game.getRandomInt(damageAmount);
	};
}