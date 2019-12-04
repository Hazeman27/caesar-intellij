package caesar.military.soldier;

import caesar.game.Game;
import caesar.military.troop.Troop;

public class Gaul extends Soldier {
	
	public Gaul(Troop troop) {
		super(troop);
		this.name = Name.getRandomGallic();
	}
	
	@Override
	protected int getDamageBoost() {
		
		return this.morale.getState() / 10 +
			this.satiety.getState() / 10;
	};
	
	@Override
	protected int block(int damageAmount) {
		return Game.getRandomInt(damageAmount);
	};
}