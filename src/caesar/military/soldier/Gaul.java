package caesar.military.soldier;

import caesar.game.Game;
import caesar.military.UnitOrigin;
import caesar.military.troop.Troop;

public class Gaul extends Soldier {
	
	private static final int DAMAGE_SATIETY_RELATION = 10;
	
	public Gaul(Troop troop) {
		
		super(troop, UnitOrigin.GAUL);
		this.name = Name.getRandomGallic();
	}
	
	@Override
	protected int getDamageBoost() {
		
		return this.getMorale().getCurrentState() +
			this.getSatiety()
			    .getCurrentState() / DAMAGE_SATIETY_RELATION;
	}
	
	@Override
	protected int block(int damage) {
		return Game.getRandomInt(damage);
	}
}